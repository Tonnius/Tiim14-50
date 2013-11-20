package ee.ut.math.tvt.salessystem.domain.data;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "SALES")
public class Order implements Cloneable, DisplayableItem{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "TIME")
	private Date date;
	
	@Column(name = "SALE_SUM")
	private double totalPrice;
	
	@Transient
	private List<SoldItem> orderedItems;
	
	public Order() {
		this.id = null;
		this.date = new Date();
		this.totalPrice = 0;
		this.orderedItems = null;
	}

	public Order(Date date, List<SoldItem> orderedItems) {
		this.id = null;
		this.date = date;
		this.orderedItems = orderedItems;
		this.totalPrice = 0;
		for (final SoldItem item : orderedItems) {
			totalPrice = totalPrice + item.getPrice()*item.getQuantity();
		}
	}
	
	@Override
	public Long getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}
	
	public double getTotalPrice() {
		return totalPrice;
	}
	
	public List<SoldItem> getOrderedItems() {
		return orderedItems;
	}
	
	public void setOrderedItems(List<SoldItem> orderedItems) {
		this.orderedItems = orderedItems;
	}
	
}
