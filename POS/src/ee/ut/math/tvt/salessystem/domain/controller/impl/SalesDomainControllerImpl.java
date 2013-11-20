package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

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
	
	private Session session = HibernateUtil.currentSession();
	
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
			@SuppressWarnings("unchecked")
			List<StockItem> dataset = session.createQuery("from StockItem").list();
		
			return dataset;
		}
		else {
			return model.getWarehouseTableModel().getTableRows();

		}
	}
	
	public void endSession() {
	    HibernateUtil.closeSession();
	}
	
	@SuppressWarnings("unchecked")
	public List<Order> loadOrderHistory() {
		if (model == null) {
			
			List<Order> dataset = session.createQuery("from Order").list();
			for (int i = 0; i < dataset.size(); i++) {
				long id = dataset.get(i).getId();
				List<SoldItem> orderedItems = session.createQuery("from SoldItem where sale_id = " + id).list();
				dataset.get(i).setOrderedItems(orderedItems);
			}
		
			return dataset;
		}
		else {
			return model.getOrderHistoryTableModel().getTableRows();

		}
	}

}
