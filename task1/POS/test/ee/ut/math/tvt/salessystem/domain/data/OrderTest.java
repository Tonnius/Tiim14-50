package ee.ut.math.tvt.salessystem.domain.data;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.Order;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class OrderTest {
	
	StockItem item1 = new StockItem(6L, "Cheesecake", "Delicious cheese", 12.5, 5);
	StockItem item2 = new StockItem(7L, "Cake", "Delicious cake", 2.0, 5);
	
	@Test
	public void testAddSoldItem() {
		Order order = new Order();
		
		// test adding item with no item list
		SoldItem soldItem1 = new SoldItem(item1, 4);
		
		order.addSoldItem(soldItem1);
		
		assertEquals(order.getOrderedItems().size(), 1);
		
		// test adding item that already exists in list
		order.addSoldItem(soldItem1);
		
		assertEquals((int) order.getOrderedItems().get(0).getQuantity(), 8);
		
		SoldItem soldItem2 = new SoldItem(item2, 4);
		
		// test adding new item to list
		order.addSoldItem(soldItem2);
		
		assertEquals(order.getOrderedItems().size(), 2);
	}
	
	@Test
	public void testGetSumWithNoItems() {
		Order order = new Order();
		
		order.getTotalPrice();
		
		assertEquals(order.getTotalPrice(), 0, 0.0001);
		
	}
	
	@Test
	public void testGetSumWithOneItem() {
		List<SoldItem> itemList = new ArrayList<SoldItem>();
		
		SoldItem soldItem = new SoldItem(item1, 4);
		
		itemList.add(soldItem);
		
		Order order = new Order(new Date(), itemList);
		
		assertEquals(order.getTotalPrice(), 50, 0.0001);
	}
	
	@Test 
	public void testGetSumWithMultipleItems() {
		List<SoldItem> itemList = new ArrayList<SoldItem>();
		
		SoldItem soldItem1 = new SoldItem(item1, 4);		
		SoldItem soldItem2 = new SoldItem(item2, 3);
		
		itemList.add(soldItem1);
		itemList.add(soldItem2);
		
		Order order = new Order(new Date(), itemList);
		
		assertEquals(order.getTotalPrice(), 56, 0.0001);
	}
}
