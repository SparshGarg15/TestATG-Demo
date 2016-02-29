/**
 * 
 */
package com.app.feeds;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import atg.commerce.catalog.CatalogTools;
import atg.core.util.StringUtils;
import atg.nucleus.GenericService;
import atg.repository.MutableRepository;
import atg.repository.MutableRepositoryItem;
import atg.repository.Repository;
import atg.repository.RepositoryException;
import atg.repository.RepositoryItem;

/**
 * Loads Category data in ATG Repository
 * 
 * @author prashant.joshi (prashant.joshi@cygrp.com)
 * @version (18-Dec-2013)
 */
public class CategoryFeed extends GenericService {

	private String filePath;
	private Repository productCatalog;
	private String rootCategoryId;
	private String rootCatalogId;
	private ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();
	private MutableRepository mProductCatalog;

	/** The Product Catalog tools. */
	private CatalogTools catalogTools;

	public void loadCategoryData() throws IOException, RepositoryException {
		try {
			/* Reads data file */
			readFile();

			mProductCatalog = (MutableRepository) productCatalog;

			for (ArrayList<String> row : dataList) {
				MutableRepositoryItem lCategoryItem = mProductCatalog
						.createItem(row.get(0), "category");
				if (lCategoryItem != null) {
					RepositoryItem lCatalogItem = null;

					/* Adding Category Data */
					lCategoryItem.setPropertyValue("displayName", row.get(1));
					mProductCatalog.addItem(lCategoryItem);

					String lParentId = row.get(2);
					if (row.get(0).equalsIgnoreCase(getRootCategoryId())) {
						lCatalogItem = productCatalog.getItem(
								getRootCatalogId(), "catalog");
						if (lCatalogItem != null) {
							createCatalogCategoryRelationships(lCategoryItem,
									lCatalogItem);
						}
					} else {
						if (lCategoryItem != null
								&& !StringUtils.isBlank(lParentId)) {
							createCategoryRelationships(lCategoryItem,
									lParentId);
						}
					}
				}
			}
		} catch (RepositoryException e) {
			e.printStackTrace();
			throw new RepositoryException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will create the catalog and category relationship for the
	 * root category
	 * 
	 * @param pItem
	 * @param pParentId
	 * @throws RepositoryException
	 */
	private void createCatalogCategoryRelationships(
			RepositoryItem pCategoryItem, RepositoryItem pCatalogItem)
			throws RepositoryException {
		try {
			MutableRepositoryItem lMutableCatalogItem = (MutableRepositoryItem) pCatalogItem;
			Set<RepositoryItem> lRootCategories = null;
			if (lMutableCatalogItem.getPropertyValue("rootCategories") != null) {
				lRootCategories = (Set<RepositoryItem>) lMutableCatalogItem
						.getPropertyValue("rootCategories");
			} else {
				lRootCategories = new HashSet<RepositoryItem>();
			}
			lRootCategories.add(pCategoryItem);
			lMutableCatalogItem.setPropertyValue("rootCategories",
					lRootCategories);
			mProductCatalog.updateItem(lMutableCatalogItem);
		} catch (RepositoryException e) {
			e.printStackTrace();
			throw new RepositoryException();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will create the category relationship with the parent
	 * category based on the parent Id returned from the CSV file.
	 * 
	 * @param pItem
	 * @param pParentId
	 * @throws RepositoryException
	 */
	private void createCategoryRelationships(RepositoryItem pItem,
			String pParentId) throws RepositoryException {
		try {
			RepositoryItem lParentItem = getCatalogTools().findCategory(
					pParentId);
			if (lParentItem != null) {
				List<RepositoryItem> lFixedChildCategories = null;
				MutableRepositoryItem lMutableItem = (MutableRepositoryItem) lParentItem;
				if (lMutableItem.getPropertyValue("fixedChildCategories") != null) {
					lFixedChildCategories = (List<RepositoryItem>) lMutableItem
							.getPropertyValue("fixedChildCategories");
				} else {
					lFixedChildCategories = new ArrayList<RepositoryItem>();
				}
				if (!lFixedChildCategories.contains(pItem)) {
					if (isLoggingDebug()) {
						logDebug("Updateing  the category relationship "
								+ pItem.getRepositoryId()
								+ " to Parent Category " + pParentId);
					}
					lFixedChildCategories.add(pItem);
					lMutableItem.setPropertyValue("fixedChildCategories",
							lFixedChildCategories);
					mProductCatalog.updateItem(lMutableItem);
				}
			}
		} catch (RepositoryException e) {
			e.printStackTrace();
			throw new RepositoryException();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads catalog data from CSV file and store in a list
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void readFile() throws FileNotFoundException, IOException {
		File file = new File(filePath);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = "";
		while ((line = br.readLine()) != null) {
			String[] data = line.split(",");
			ArrayList<String> row = new ArrayList<String>();
			for (String col : data) {
				row.add(col);
			}
			dataList.add(row);
		}
		br.close();
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath
	 *            the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
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

	/**
	 * @return the rootCategoryId
	 */
	public String getRootCategoryId() {
		return rootCategoryId;
	}

	/**
	 * @param rootCategoryId
	 *            the rootCategoryId to set
	 */
	public void setRootCategoryId(String rootCategoryId) {
		this.rootCategoryId = rootCategoryId;
	}

	/**
	 * @return the rootCatalogId
	 */
	public String getRootCatalogId() {
		return rootCatalogId;
	}

	/**
	 * @param rootCatalogId
	 *            the rootCatalogId to set
	 */
	public void setRootCatalogId(String rootCatalogId) {
		this.rootCatalogId = rootCatalogId;
	}

	/**
	 * @return the catalogTools
	 */
	public CatalogTools getCatalogTools() {
		return catalogTools;
	}

	/**
	 * @param catalogTools
	 *            the catalogTools to set
	 */
	public void setCatalogTools(CatalogTools catalogTools) {
		this.catalogTools = catalogTools;
	}

}
