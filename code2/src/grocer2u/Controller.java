package grocer2u;

/**
 * A controller for the Grocer2You Grocery Delivery Scheduling system.
 * This controller includes the 5 features that the intended
 * prototype Grocer2You Grocery Delivery Scheduling system 
 * is expected to have.
 * 
 * @author Sylvia Wong
 * @version 24/11/2021
 */
public interface Controller {
	
	/**
	 * Lists the orders in each delivery trip.
	 * @return a String representation of the list of delivery trips and the sequence of orders in each trip.
	 */
	String listAllDeliveryTripOrders();
	
	/**
	 * Lists the sequence of orders in a specified delivery trip.
	 * @param deliveryTripID	the ID of the required delivery trip, 1 means the first trip, 2 means the second trip, etc.
	 * @return	a String representation of the sequence of orders to be delivered in the delivery trip 
	 */
	String listOrdersInDeliveryTrip(int deliveryTripID);

	/**
	 * Lists a path between two specified locations and their estimated distance.
	 * The path is represented as a sequence of locations between the specified locations. 
	 * @param locationA	the name of the start location
	 * @param locationB	the name of the destination
	 * @return	a String representation of a path between the specified locations and the expected travel distance.
	 */
	String showPathBetween(String locationA, String locationB);

	/**
	 * Lists the shortest path between two specified locations and their estimated distance.
	 * The path is represented as a sequence of locations between the specified locations. 
	 * @param locationA	the name of the start location
	 * @param locationB	the name of the destination
	 * @return	a String representation of a path between the specified locations and the expected travel distance.
	 */
	String showShortestPathBetween(String locationA, String locationB);
	
	/**
	 * Lists a path for a specified delivery trip.
	 * @param deliveryTripID	the ID of the required delivery trip, 1 means the first trip, 2 means the second trip, etc.
	 * @return	a String representation of a path covering all locations in the required delivery trip.
	 */
	String showPathForDeliveryTrip(int deliveryTripID);

}
