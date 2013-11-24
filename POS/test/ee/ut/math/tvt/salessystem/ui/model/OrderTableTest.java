package ee.ut.math.tvt.salessystem.ui.model;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.Order;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.OrderHistoryTableModel;

public class OrderTableTest {
	
	Order order;
	
	@Before
	public void setUp() {
		StockItem item1 = new StockItem(6L, "Cheesecake", "Delicious cheese", 12.5, 5);
		StockItem item2 = new StockItem(7L, "Cake", "Delicious cake", 2.0, 5);
		
		List<SoldItem> itemList = new ArrayList<SoldItem>();
		
		SoldItem soldItem1 = new SoldItem(item1, 4);		
		SoldItem soldItem2 = new SoldItem(item2, 3);
		
		itemList.add(soldItem1);
		itemList.add(soldItem2);
		
		this.order = new Order(new Date(), itemList);
	}
	
	@Test
	public void testAddOrder() {
		OrderHistoryTableModel table = new OrderHistoryTableModel();
		
		table.addOrder(order);
		
		@SuppressWarnings("unchecked")
		SoldItem item = ((List<SoldItem>) table.getColumnValue(order, 2)).get(0);		
		assertEquals(item.getOrder(), order);
		
	}
	
}
