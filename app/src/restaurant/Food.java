package restaurant;

import java.io.Serializable;

/**
 * An abstract class that will be inherited by subclasses Item and Package
 * @author Valencia Lie
 * @version 1.0
 * @since 2021-11-12
 */
public abstract class Food implements Serializable {

	/**
	 * Abstract method to get the name of the food object
	 * @return name of food
	 */
	public abstract String getName();
	
	/**
	 * Abstract method to get the price of the food object
	 * @return price of food
	 */
	public abstract double getPrice();

	/**
	 * Abstract method to get the index of the food object
	 * @return index of food
	 */
	public abstract int getIndex();

	/**
	 * Abstract method to set the index of the food object
	 * @param index The index of the food object
	 */
	public abstract void setIndex(int index);

	/**
	 * Abstract method to set the name of the food object
	 * @param name The name of the food object
	 */
	public abstract void setName(String name);

	/**
	 * Abstract method to set the price of the food object
	 * @param price The price of the food object
	 */
	public abstract void setPrice(double price);

}
