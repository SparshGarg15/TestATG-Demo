/**
 * 
 */
package com.app.feeds;

import java.io.IOException;

import atg.commerce.catalog.CatalogTools;
import atg.core.util.StringUtils;
import atg.nucleus.GenericService;
import atg.repository.MutableRepository;
import atg.repository.MutableRepositoryItem;
import atg.repository.Repository;
import atg.repository.RepositoryException;
import atg.repository.RepositoryItem;

/**
 * Handles all the sku inventory operations
 * 
 * @author prashant.joshi (prashant.joshi@cygrp.com)
 * @version (02-Jan-2013)
 */
public class InventoryFeed extends GenericService {

	/* Holds inventory repository */
	private Repository inventoryRepository;

	/* Holds inventory repository for updation */
	private MutableRepository mInventoryRepository;

	/* Holds catalog tool instance to create or upgrade inventory */
	private CatalogTools catalogTools;

	/* Holds temp sku inventory data */
	private Object[][] inventoryTempData = {
			{ "catalogRefId", "displayName", "availabilityStatus", "stockLevel" },
			{ "1001", "Inventory1", 1000, 100L },
			{ "1002", "Inventory2", 1000, 200L },
			{ "1003", "Inventory3", 1000, 300L },
			{ "1004", "Inventory4", 1000, 400L },
			{ "1005", "Inventory5", 1000, 450L },
			{ "1006", "Inventory6", 1000, 500L },
			{ "1007", "Inventory7", 1000, 650L } };

	public boolean loadInitialInventoryData() throws IOException,
			RepositoryException {
		for (int i = 1; i < inventoryTempData.length; i++) {
			createOrUpdateInventoryItem(inventoryTempData[i]);
		}
		return true;
	}

	/**
	 * This method update or creates an inventory item run by the Inventory feed
	 * scheduler
	 * 
	 * @param pId
	 * @param pStockLevel
	 * @param pStatus
	 * @throws RepositoryException
	 */
	public boolean createOrUpdateInventoryItem(Object[] inventoryTempData) {
		boolean isSuccess = false;
		MutableRepositoryItem lItem = null;
		mInventoryRepository = (MutableRepository) inventoryRepository;
		if (!StringUtils.isBlank((String) inventoryTempData[0])) {
			try {
				RepositoryItem lSku = getCatalogTools().findSKU(
						(String) inventoryTempData[0]);
				if (lSku != null) {
					lItem = (MutableRepositoryItem) inventoryRepository
							.getItem((String) inventoryTempData[0], "inventory");
					if (lItem != null) {
						setInventoryProperties(lItem, inventoryTempData);
						mInventoryRepository.updateItem(lItem);
					} else {
						lItem = mInventoryRepository.createItem("inventory");
						setInventoryProperties(lItem, inventoryTempData);
						mInventoryRepository.addItem(lItem);
					}
					isSuccess = true;
				} else {
					System.out.println("No sku found for id "
							+ inventoryTempData[0]);
				}
			} catch (RepositoryException e) {
				System.out.println(e.getMessage());
			}
		}
		return isSuccess;
	}

	/**
	 * This method sets or updates the inventory properties
	 * 
	 * @param pItem
	 * @param pId
	 * @param pStockLevel
	 * @param pStatus
	 */
	private void setInventoryProperties(MutableRepositoryItem pItem,
			Object[] inventoryTempData) {
		pItem.setPropertyValue("catalogRefId", inventoryTempData[0]);
		pItem.setPropertyValue("displayName", inventoryTempData[1]);
		pItem.setPropertyValue("availabilityStatus", inventoryTempData[2]);
		pItem.setPropertyValue("stockLevel", inventoryTempData[3]);
	}

	/**
	 * @return the inventoryRepository
	 */
	public Repository getInventoryRepository() {
		return inventoryRepository;
	}

	/**
	 * @param inventoryRepository
	 *            the inventoryRepository to set
	 */
	public void setInventoryRepository(Repository inventoryRepository) {
		this.inventoryRepository = inventoryRepository;
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
