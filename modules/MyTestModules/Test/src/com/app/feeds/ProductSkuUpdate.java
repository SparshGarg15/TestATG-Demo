/**
 * 
 */
package com.app.feeds;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import atg.nucleus.GenericService;
import atg.repository.MutableRepository;
import atg.repository.MutableRepositoryItem;
import atg.repository.Repository;
import atg.repository.RepositoryException;
import atg.repository.RepositoryItem;

/**
 * @author prashant.joshi
 *
 */
public class ProductSkuUpdate extends GenericService {

	private Repository productCatalog;
	private MutableRepository mProductCatalog;

	/* Prod and SKU relationship */
	private String[][] prodSkus = { { "100", "1004" }, { "101", "1001,1002" } };

	/* Prod and SKU relationship */
	private String[][] actualProdSkus = { { "100", "1001,1002,1003" },
			{ "101", "1004" } };

	public boolean testResult() {
		try {
			mProductCatalog = (MutableRepository) productCatalog;
			RepositoryItem sItem = productCatalog.getItem("1001", "sku");
			/* Taking sku backup */
			if (sItem != null) {
				Set<RepositoryItem> listOfParentProducts = (Set<RepositoryItem>) sItem
						.getPropertyValue("parentProducts");
				System.out.println("Initial data!!!");
				displayParentProduct(listOfParentProducts);

				MutableRepositoryItem pItem = (MutableRepositoryItem) mProductCatalog
						.getItem("101", "product");
				List<RepositoryItem> childSkus = (List<RepositoryItem>) pItem
						.getPropertyValue("childSkus");

				RepositoryItem tItem = productCatalog.getItem("1001", "sku");

				childSkus.add(tItem);
				pItem.setPropertyValue("childSkus", childSkus);

				Set<RepositoryItem> parentProducts = (Set<RepositoryItem>) sItem
						.getPropertyValue("parentProducts");

				System.out.println("Altered data");
				displayParentProduct(parentProducts);

				System.out.println("Original Data.....");
				displayParentProduct(listOfParentProducts);
			}
		} catch (RepositoryException e) {
			System.out.println("Exception mil gaya re!!!!");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void displayParentProduct(Set<RepositoryItem> parentProducts) {
		for (RepositoryItem pItem : parentProducts) {
			System.out.println(pItem.getRepositoryId());
		}
	}

	public boolean correctProductSkuData() {
		try {
			mProductCatalog = (MutableRepository) productCatalog;
			for (int i = 0; i < actualProdSkus.length; i++) {
				MutableRepositoryItem mPItem = (MutableRepositoryItem) mProductCatalog
						.getItem(actualProdSkus[i][0], "product");
				if (mPItem == null) {
					System.out.println("Yes It's Howing null");
					return true;
				}
				List<RepositoryItem> childSkusList = (List<RepositoryItem>) mPItem
						.getPropertyValue("childSKUs");
				if (childSkusList != null) {

					childSkusList.clear();

					String[] skuList = actualProdSkus[i][1].split(",");
					for (int j = 0; j < skuList.length; j++) {
						RepositoryItem mSItem = productCatalog.getItem(
								skuList[j], "sku");
						childSkusList.add(mSItem);
					}
				}
				mPItem.setPropertyValue("childSKUs", childSkusList);
				mProductCatalog.updateItem(mPItem);
			}
		} catch (RepositoryException e) {
			System.out.println("Exception mil gaya re!!!!");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean loadProductSkuData() {
		try {
			mProductCatalog = (MutableRepository) productCatalog;
			for (int i = 0; i < prodSkus.length; i++) {
				MutableRepositoryItem mPItem = (MutableRepositoryItem) mProductCatalog
						.getItem(prodSkus[i][0], "product");
				List<RepositoryItem> childSkusList = (List<RepositoryItem>) mPItem
						.getPropertyValue("childSKUs");
				if (childSkusList != null) {
					String[] skuList = prodSkus[i][1].split(",");
					for (int j = 0; j < skuList.length; j++) {
						RepositoryItem mSItem = productCatalog.getItem(
								skuList[j], "sku");
						if (mSItem != null && !childSkusList.contains(mSItem)) {
							childSkusList.add(mSItem);
						}
						removeAllOccurrences(mPItem.getRepositoryId(), mSItem);
					}
				}
				mPItem.setPropertyValue("childSKUs", childSkusList);
				mProductCatalog.updateItem(mPItem);
			}
		} catch (RepositoryException e) {
			return false;
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	private void removeAllOccurrences(String productId, RepositoryItem sItem)
			throws RepositoryException {
		Set<RepositoryItem> prodSet = (Set<RepositoryItem>) sItem
				.getPropertyValue("parentProducts");
		if (prodSet != null && !prodSet.isEmpty()) {
			Iterator<RepositoryItem> itProdSet = prodSet.iterator();
			while (itProdSet.hasNext()) {
				MutableRepositoryItem mPItem = (MutableRepositoryItem) itProdSet
						.next();
				if (!mPItem.getRepositoryId().equals(productId)) {
					List<RepositoryItem> childSkusList = (List<RepositoryItem>) mPItem
							.getPropertyValue("childSKUs");
					if (childSkusList != null) {
						if (childSkusList.contains(sItem)) {
							childSkusList.remove(sItem);
							mPItem.setPropertyValue("childSKUs", childSkusList);
							((MutableRepository) getProductCatalog())
									.updateItem(mPItem);
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		String filePath = "C:/Users/prashant.joshi/Desktop/BackgroundVerification/sku_20140227223444.csv";
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			br.readLine();
			String row = br.readLine();
			String[] cols = row.split(",");
			System.out.println(cols[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the productCatalog
	 */
	public Repository getProductCatalog() {
		return productCatalog;
	}

	/**
	 * @param productCatalog
	 *            the productCatalog to set
	 */
	public void setProductCatalog(Repository productCatalog) {
		this.productCatalog = productCatalog;
	}

}
