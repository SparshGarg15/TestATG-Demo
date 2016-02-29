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
import java.util.HashSet;
import java.util.Set;

import atg.nucleus.GenericService;
import atg.repository.MutableRepository;
import atg.repository.MutableRepositoryItem;
import atg.repository.Repository;
import atg.repository.RepositoryException;
import atg.repository.RepositoryItem;

/**
 * Loads Catalog data in ATG Repository
 * 
 * @author prashant.joshi (prashant.joshi@cygrp.com)
 * @version (18-Dec-2013)
 */
public class CatalogFeed extends GenericService {

	private String filePath;
	private Repository productCatalog;
	private ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();

	public boolean loadCatalogFeed() {
		try {
			/* Reading Data */
			readFile();

			/* Creating Data */
			MutableRepository mProductCatalog = (MutableRepository) productCatalog;
			for (ArrayList<String> row : dataList) {
				RepositoryItem catalogItem = productCatalog.getItem(row.get(0),
						"catalog");
				/* Create only when its not exist in ATG Repository */
				if (catalogItem == null) {
					MutableRepositoryItem lCatalogItem = mProductCatalog
							.createItem(row.get(0), "catalog");
					if (lCatalogItem != null) {
						lCatalogItem
								.setPropertyValue("displayName", row.get(1));
						Set<String> lSiteIds = new HashSet<String>();
						lSiteIds.add("TestSite");
						lCatalogItem.setPropertyValue("siteIds", lSiteIds);
						mProductCatalog.addItem(lCatalogItem);
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * This method will create the catalog and category relationship for the
	 * root category
	 * 
	 * @param pItem
	 * @param pParentId
	 * @throws RepositoryException
	 */
	public void updateCatalogCategoryRelationships() throws RepositoryException {
		try {
			MutableRepository mProductCatalog = (MutableRepository) productCatalog;
			MutableRepositoryItem lMutableCatalogItem = (MutableRepositoryItem) productCatalog
					.getItem("1", "catalog");
			RepositoryItem rootCategory = productCatalog.getItem("2",
					"category");

			Set<RepositoryItem> lRootCategories = null;
			if (lMutableCatalogItem.getPropertyValue("rootCategories") != null) {
				lRootCategories = (Set<RepositoryItem>) lMutableCatalogItem
						.getPropertyValue("rootCategories");
			} else {
				lRootCategories = new HashSet<RepositoryItem>();
			}
			lRootCategories.add(rootCategory);
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
	 * Just for testing this utility
	 */
	public static void main(String[] args) {
		CatalogFeed feed = new CatalogFeed();
		feed.setFilePath("C:/Development/feed/catalog.csv");
		feed.loadCatalogFeed();
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
}
