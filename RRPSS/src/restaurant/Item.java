package restaurant;

import java.io.Serializable;

/**
 * Represents items that can be standalone items or inside a promotional package
 * @author Valencia Lie
 * @version 1.0
 * @since 2021-11-12
 */
public class Item extends Food implements Comparable<Item>, Serializable {
	/**
	 * A collection of types an item object can possibly have: either main, sides, beverages, dessert
	 *
	 */
	
	public enum KindofFood {
		/**
		 * Type of item is main
		 */
		MAIN, 
		/**
		 * Type of item is sides
		 */
		SIDES, 
		/**
		 * Type of item is beverage
		 */
		BEVERAGE, 
		/**
		 * Type of item is dessert
		 */
		DESSERT;

		/**
		 * Method to change integer to a corresponding type of an item, 1: main, 2: sides, 3: beverage, 4: dessert
		 * @param ordinal An integer from 1 to 4
		 * @return The corresponding type
		 */
		public static KindofFood getTypeByOrdinal(int ordinal) {
			for (KindofFood t : KindofFood.values()) {
				if (t.ordinal() == ordinal - 1) {
					return t;
				}
			}

			return null;
		}

	};
	
	/**
	 * Name of the item object
	 */
	private String name;
	/**
	 * Description of the item object
	 */
	private String description;
	/**
	 * Price of the item object
	 */
	private double price;
	/**
	 * Index of the item object
	 */
	private int index;
	/**
	 * Type of the item object
	 */
	private KindofFood type;

	/**
	 * Constructor of the item object
	 * @param ID Index of the item object
	 * @param name Name of the item object
	 * @param description Description of the item object
	 * @param price Price of the item object
	 * @param type Type of the item object
	 */
	public Item(int ID, String name, String description, double price, KindofFood type) {
		this.index = ID;
		this.name = name;
		this.description = description;
		this.price = price;
		this.type = type;
	}

	/**
	 * A method to get the index of the item object
	 * @return the index of the item object
	 */
	public int getIndex() {
		// return super.getIndex();
		return index;
	}

	/**
	 * A method to get the type of the item object
	 * @return the type of the item object
	 */
	public KindofFood getType() {
		return type;
	}
	
	/**
	 * A method to get the name of the item object
	 * @return the name of the item object
	 */
	public String getName() {
		return name;
	}

	/**
	 * A method to get the description of the item object
	 * @return the description of the item object
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * A method to get the price of the item object
	 * @return the price of the item object
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * A method to set the index of the item object
	 * @param index Index of the item object
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * A method to set the type of the item object
	 * @param type Type of the item object
	 */
	public void setType(KindofFood type) {
		this.type = type;
	}

	/**
	 * A method to set the name of the item object
	 * @param name Name of the item object
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * A method to set the description of the item object
	 * @param description Description of the item object
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * A method to set the price of the item object
	 * @param price Price of the item object
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * A method to print the details of the item object
	 * @return the details of the item object
	 */
	@Override
	public String toString() {
		return "Item Index = " + index + '\n' + "Name = " + name + '\n' + "Description = " + description + '\n'
				+ "Price = " + price + '\n' + "Type = " + type + '\n';
	}

	/**
	 * A method to compare item objects with each other
	 * @return 0
	 */
	@Override
	public int compareTo(Item o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
