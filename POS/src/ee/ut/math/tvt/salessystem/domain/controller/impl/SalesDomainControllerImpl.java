package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.Order;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.tabs.PurchaseTab;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {
	
	private static final Logger log = Logger.getLogger(PurchaseTab.class);
	
	private SalesSystemModel model;
	
	@Override
	public void setModel(SalesSystemModel model) {
		this.model = model;
	}
	
	public void submitCurrentPurchase(List<SoldItem> goods) throws VerificationFailedException {
		// Update warehouse state removing purchased items
		List<StockItem> warehouseItems = loadWarehouseState();
		List<StockItem> updatedItems = new ArrayList<StockItem>();
		for (final SoldItem soldItem : goods) {
			for (final StockItem stockItem : warehouseItems) {
				if (stockItem.getName().equals(soldItem.getName())) {
					stockItem.setQuantity(stockItem.getQuantity() - soldItem.getQuantity());
					// Not enough items in stock
					if (stockItem.getQuantity() < 0) {
						log.warn("Not enough items in stock!");
						return;
					}
					updatedItems.add(stockItem);
				}
			}
		}
		for (final StockItem stockItem : updatedItems) {
			model.getWarehouseTableModel().updateItem(stockItem);
		}
		// Save purchase
		Order order = new Order(new Date(), goods);
		model.getOrderHistoryTableModel().addOrder(order);
		
		// XXX - Implement exceptions
		//throw new VerificationFailedException("Underaged!");
	}

	public void cancelCurrentPurchase() throws VerificationFailedException {				
		// XXX - Cancel current purchase
	}
	

	public void startNewPurchase() throws VerificationFailedException {
		// XXX - Start new purchase
	}

	public List<StockItem> loadWarehouseState() {
		// XXX mock implementation
		if (model == null) {
			List<StockItem> dataset = new ArrayList<StockItem>();

			StockItem chips = new StockItem(1l, "Lays chips", "Potato chips", 11.0, 5);
			StockItem chupaChups = new StockItem(2l, "Chupa-chups", "Sweets", 8.0, 8);
			StockItem frankfurters = new StockItem(3l, "Frankfurters", "Beer sausages", 15.0, 12);
			StockItem beer = new StockItem(4l, "Free Beer", "Student's delight", 0.0, 100);

			dataset.add(chips);
			dataset.add(chupaChups);
			dataset.add(frankfurters);
			dataset.add(beer);
		
			return dataset;
		}
		else {
			return model.getWarehouseTableModel().getTableRows();

		}
	}
	
	public void endSession() {
	    HibernateUtil.closeSession();
	}

}
