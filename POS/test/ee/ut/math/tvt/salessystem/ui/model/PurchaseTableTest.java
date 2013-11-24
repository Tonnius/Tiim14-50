package ee.ut.math.tvt.salessystem.ui.model;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

public class PurchaseTableTest {

	StockItem item1 = new StockItem(6L, "Cheesecake", "Delicious cheese", 12.5, 5);
	
	@Test
	public void testAddNewItem() {
		PurchaseInfoTableModel table = new PurchaseInfoTableModel();
		
		SoldItem soldItem = new SoldItem(item1, 4);
		
		table.addItem(soldItem, item1.getQuantity());
		
		assertEquals(table.getValueAt(0, 1), "Cheesecake");
		
	}
	
	@Test
	public void testIncreaseItemQuantity() {
		PurchaseInfoTableModel table = new PurchaseInfoTableModel();
		
		SoldItem soldItem1 = new SoldItem(item1, 2);
		
		table.addItem(soldItem1, item1.getQuantity());
		
		assertEquals(table.getValueAt(0, 3), 2);
		
		SoldItem soldItem2 = new SoldItem(item1, 2);
		
		table.addItem(soldItem2, item1.getQuantity());
		
		assertEquals(table.getValueAt(0, 3), 4);
		
	}
	
}
