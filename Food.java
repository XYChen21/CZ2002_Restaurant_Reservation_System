package restaurant;

import java.io.Serializable;

public class Food implements Serializable {

	private String name;
	private int index;
	private double price;

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}