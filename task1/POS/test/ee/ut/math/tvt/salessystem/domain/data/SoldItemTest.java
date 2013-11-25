package ee.ut.math.tvt.salessystem.domain.data;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class SoldItemTest {
        
        private SoldItem soldItem;
        
        @Before
        public void setUpPrimalState(){
                StockItem stockItem = new StockItem(8L, "Cheesecake", "Delicious cheese", 12.5, 5);
                soldItem = new SoldItem(stockItem, 5);
        }
        
        @Test
        public void testGetSum(){
                assertEquals(12.5*5, soldItem.getSum(), 0.0001);
        }
        
        @Test
        public void testGetSumWithZeroQuantity(){
                soldItem.setQuantity(0);
                assertEquals(0.0, soldItem.getSum(), 0.0001);
        }

}