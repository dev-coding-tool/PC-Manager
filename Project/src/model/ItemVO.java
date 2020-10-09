package model;

public class ItemVO {
	private int itemno;
	private String itemname;
	private int price;
	private int stock;
	
	public ItemVO() {}
	
	public ItemVO(String itemname) {
		super();
		this.itemname = itemname;
	}
	
	public ItemVO(String itemname, int price) {
		super();
		this.itemname = itemname;
		this.price = price;
	}



	public ItemVO(int itemno, String itemname, int price, int stock) {
		super();
		this.itemno = itemno;
		this.itemname = itemname;
		this.price = price;
		this.stock = stock;
	}

	public int getItemno() {
		return itemno;
	}

	public void setItemno(int itemno) {
		this.itemno = itemno;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
}
