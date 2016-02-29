/**
 * 
 */
package com.app.feeds;

import java.util.ArrayList;

import atg.nucleus.GenericService;
import atg.repository.MutableRepository;
import atg.repository.MutableRepositoryItem;
import atg.repository.Repository;
import atg.repository.RepositoryException;
import atg.repository.RepositoryItem;

/**
 * Loads product data in ATG Repository
 * 
 * @author prashant.joshi (prashant.joshi@cygrp.com)
 * @version (18-Dec-2013)
 */
public class ProductFeed extends GenericService {

	private Repository productCatalog;
	private MutableRepository mProductCatalog;

	/* Product Dummy data */
	private String[][] productList = {
			{ "100", "Shirt", "Mens Shirt", "10", "1001,1002,1003" },
			{ "101", "Jenas", "Mens Jeans", "10", "1004" },
			{ "102", "Sweater", "Women Sweater", "11", "1005" },
			{ "103", "Watches", "Women Watches", "11", "1006" },
			{ "104", "Football", "Kids Football", "12", "1007" } };

	/* SKU Dummy Data */
	private Object[][] skuList = {
			{ "1001", "Shirt-1", "Shirt-1 Decription", 200, 100 },
			{ "1002", "Shirt-2", "Shirt-2 Decription", 500, 120 },
			{ "1003", "Shirt-3", "Shirt-3 Decription", 100, 140 },
			{ "1004", "Jeans", "Jeans Decription", 20, 2000 },
			{ "1005", "Sweater", "Sweater Decription", 21, 2500 },
			{ "1006", "Watches", "Watches Decription", 50, 10000 },
			{ "1007", "Football", "Football Decription", 100, 300 } };

	/* Cat And Prod relationship */
	private Object[][] catProds = { { "10", "100,101" }, { "11", "102,103" },
			{ "12", "104" } };

	/**
	 * Load Initial Product data
	 * 
	 * @return
	 */
	public boolean loadInitialData() {
		try {
			mProductCatalog = (MutableRepository) productCatalog;
			/* Loading Sku Data */
			loadSkus();

			/* Loading Product Data */
			loadProducts();

			/* Set Product and Category relationship */
			createProductAndCategoryRelationship();
		} catch (RepositoryException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Loads initial skus
	 * 
	 * @throws RepositoryException
	 */
	private void loadSkus() throws RepositoryException {
		for (int i = 0; i < skuList.length; i++) {
			MutableRepositoryItem skuItem = mProductCatalog.createItem(
					(String) skuList[i][0], "sku");
			if (skuItem != null) {
				skuItem.setPropertyValue("displayName", (String) skuList[i][1]);
				skuItem.setPropertyValue("description", (String) skuList[i][2]);
				skuItem.setPropertyValue("quantity", new Double(
						(Integer) skuList[i][3]));
				skuItem.setPropertyValue("listPrice", new Double(
						(Integer) skuList[i][4]));
				mProductCatalog.addItem(skuItem);
			}
		}
	}

	/**
	 * Loads Initial Products
	 * 
	 * @throws RepositoryException
	 */
	private void loadProducts() throws RepositoryException {
		for (int i = 0; i < productList.length; i++) {
			MutableRepositoryItem productItem = mProductCatalog.createItem(
					(String) productList[i][0], "product");
			if (productItem != null) {
				productItem.setPropertyValue("displayName",
						(String) productList[i][1]);
				productItem.setPropertyValue("description",
						(String) productList[i][2]);
				String[] childSkus = ((String) productList[i][4]).split(",");
				ArrayList<RepositoryItem> childSkusList = new ArrayList<RepositoryItem>();
				for (int j = 0; j < childSkus.length; j++) {
					RepositoryItem sku = productCatalog.getItem(childSkus[j],
							"sku");
					if (sku != null) {
						childSkusList.add(sku);
					}
				}
				productItem.setPropertyValue("childSKUs", childSkusList);
				mProductCatalog.addItem(productItem);
			}
		}
	}

	/**
	 * Setup relationship between products and categories
	 * 
	 * @throws RepositoryException
	 */
	private void createProductAndCategoryRelationship()
			throws RepositoryException {
		for (int i = 0; i < catProds.length; i++) {
			MutableRepositoryItem parentCategory = (MutableRepositoryItem) mProductCatalog
					.getItem((String) catProds[i][0], "category");
			if (parentCategory != null) {
				String[] products = ((String) catProds[i][1]).split(",");
				ArrayList<RepositoryItem> childProductsList = new ArrayList<RepositoryItem>();
				for (String product : products) {
					RepositoryItem productItem = productCatalog.getItem(
							product, "product");
					if (productItem != null) {
						childProductsList.add(productItem);
					}
				}
				parentCategory.setPropertyValue("fixedChildProducts",
						childProductsList);
				mProductCatalog.updateItem(parentCategory);
			}
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
