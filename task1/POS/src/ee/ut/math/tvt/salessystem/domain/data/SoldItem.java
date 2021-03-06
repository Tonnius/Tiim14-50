package ee.ut.math.tvt.salessystem.domain.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



/**
 * Already bought StockItem. SoldItem duplicates name and price for preserving history. 
 */
@Entity
@Table(name = "SOLDITEM")
public class SoldItem implements Cloneable, DisplayableItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne()
	@JoinColumn(name = "STOCKITEM_ID", nullable = false)
    private StockItem stockItem;
	
	@ManyToOne()
	@JoinColumn(name = "SALE_ID")
	private Order order;
    
	@Column(name = "name")
    private String name;
	
	@Column(name = "quantity")
    private Integer quantity;
	
	@Column(name = "itemprice")
    private double price;
    
	
	
	public SoldItem() {
		this.id = null;
		this.stockItem = null;
		this.name = "";
		this.price = 0;
		this.quantity = 0;
	}
	
    public SoldItem(StockItem stockItem, int quantity) {
    	this.id = null;
        this.stockItem = stockItem;
        this.name = stockItem.getName();
        this.price = stockItem.getPrice();
        this.quantity = quantity;
        
    }
    
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getSum() {
        return price * ((double) quantity);
    }

    public StockItem getStockItem() {
        return stockItem;
    }

    public void setStockItem(StockItem stockItem) {
        this.stockItem = stockItem;
    }
    
    public void setOrder(Order order) {
    	this.order = order;
    }
    
	public Object getOrder() {
		return this.order;
	}
    
    public String toString() {
    	StringBuffer buffer = new StringBuffer();
    	buffer.append(getId() + "\t");
		buffer.append(getName() + "\t");
		buffer.append(getPrice() + "\t");
		buffer.append(getQuantity() + "\t");
		buffer.append(getSum() + "\n");
		return buffer.toString();
    }
    
}
