package ee.ut.math.tvt.salessystem.ui.model;


import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

public class StockTableModelTest {
        
        private StockTableModel model;

        @Before
        public void setUpPrimalState(){
                model = new StockTableModel();
        }
        
        @Test
        public void testValidateNameUniqueness(){
                StockItem stockItem  = new StockItem((long)7162534, "Suvaline toode", "Suvaline tootegrupp", 100, 25);
                StockItem stockItem2 = new StockItem((long)1726354, "Suvaline toode", "Suvaline tootegrupp", 50, 30);
                model.addItem(stockItem);
                model.addItem(stockItem2);
                
                assertEquals(25, model.getItemByName("Suvaline toode").getQuantity());
        }
        
        @Test
        public void testHasEnoughInStock(){
                StockItem stockItem  = new StockItem((long)198237645, "Suvaline toode", "Suvaline tootegrupp", 100, 25);
                model.addItem(stockItem);
                
                assertTrue(model.getItemByName("Suvaline toode").getQuantity() >= stockItem.getQuantity());
        }
        
        @Test
        public void testGetItemByIdWhenItemExists(){
                StockItem stockItem = new StockItem((long)771123, "Odav toode", "Odavad tooted", 0.5, 10);
                model.addItem(stockItem);
                
                assertEquals(stockItem, model.getItemById(stockItem.getId()));
        }
        
        @Test(expected=NoSuchElementException.class)
        public void testGetItemByIdWhenThrowsException(){
                model.getItemById(646546);
        }
        
        @Test
        public void testGetColumn() {
                StockItem stockItem = new StockItem((long)771123, "Odav toode", "Odavad tooted", 0.5, 10);
                model.addItem(stockItem);
                
                assertEquals(stockItem.getId(), model.getColumnValue(stockItem, 0));
                assertEquals(stockItem.getName(), model.getColumnValue(stockItem, 1));
                assertEquals(stockItem.getPrice(), model.getColumnValue(stockItem, 2));
                assertEquals(stockItem.getQuantity(), model.getColumnValue(stockItem, 3));
        }
        
        @Test
        public void testValidateIDUniqueness(){
        	StockItem stockItem  = new StockItem((long)246819, "Suvaline toode", "Suvaline tootegrupp", 100, 25);
            StockItem stockItem2 = new StockItem((long)246819, "Suvaline toode2", "Suvaline tootegrupp2", 50, 30);
                model.addItem(stockItem);
                model.addItem(stockItem2);
                
                assertEquals(25, model.getItemById(stockItem.getId()).getQuantity());
        }
        
        
}