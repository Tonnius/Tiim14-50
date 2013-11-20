import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class StockTest {
	
	static StockItem stockItem = new StockItem(6L, "Cheesecake", "Delicious cheese", 12.5, 5);
	
	@Test
	public void testClone() {
		StockItem stockClone = (StockItem) stockItem.clone();
		
		assertEquals(stockClone, stockItem);
	}
	
	@Test
	public void testGetColumn() {
		long id = (Long) stockItem.getColumn(0);
		
		assertEquals(id, 6L);
		
		String name = (String) stockItem.getColumn(1);
		
		assertEquals(name, "Cheesecake");
		
		Double price = (Double) stockItem.getColumn(2);
		
		assertEquals(price, (Double) 12.5);
		
		Integer quantity = (Integer) stockItem.getColumn(3);
		
		assertEquals(quantity, (Integer) 5);
		
		// test with invalid column value
		boolean testvalue = false;
		try {
			stockItem.getColumn(4);
		}
		catch (RuntimeException ex) {
			testvalue = true;
		}
		assertEquals(testvalue, true);
		
	}
	
}
