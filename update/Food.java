package restaurant;

import java.io.Serializable;

public abstract class Food implements Serializable {
	public abstract String getName();

	public abstract double getPrice();

	public abstract int getIndex();

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