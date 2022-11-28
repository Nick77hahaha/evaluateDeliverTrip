package grocer2u;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
/**
 * A simple text-based user interface for the Grocer2You Grocery Delivery Scheduling system.
 * 
 * @author S H S Wong
 * @version 24/11/2021
 */
public class TUI {
	private grocer2u.Controller controller;
	private Scanner stdIn;

	public TUI(Controller controller) {
		this.controller = controller;
		
		// Creates a Scanner object for obtaining user input
		stdIn = new Scanner(System.in);
		
		while (true) {
			displayMenu();
			getAndProcessUserOption();
		}
	}

	/**
	 * Displays the header of this application and a summary of menu options.
	 */
	private void displayMenu() {
		display(header());
		display(menu());
	}
	
	/**
	 * Obtains an user option and processes it.
	 */
	private void getAndProcessUserOption() {
		String command = stdIn.nextLine().trim();
		String inputLine, locationA, locationB;
		int deliverTripID;
		
		switch (command) {
		case "1" : // Lists the orders in each delivery trip
			display("Lists the orders in each delivery trip...");
			display(controller.listAllDeliveryTripOrders());
			break;
		case "2" : // Lists the orders in a specific delivery trip
			display("Lists the sequence of orders in a specified delivery trip...");
			display("Enter the ID of the delivery trip you'd like to view:");
			try {
				inputLine = stdIn.nextLine().trim();
				deliverTripID = Integer.parseInt(inputLine);
				display(controller.listOrdersInDeliveryTrip(deliverTripID));
			} catch (NumberFormatException e) {
				display("Error: Non-numeric input. The ID should be a whole number.");
			}
			break;
		case "3" : // Finds a path between two locations
			display("Finds a path between two locations...");
			display("Enter the name of the start location:");
			locationA = stdIn.nextLine().trim();
			display("Enter the name of the destination:");
			locationB = stdIn.nextLine().trim();
			display(controller.showPathBetween(locationA, locationB));
			break;
		case "4" : // Finds the shortest path between two locations
			display("Finds the shortest path between two locations...");
			display("Enter the name of the start location:");
			locationA = stdIn.nextLine().trim();
			display("Enter the name of the destination:");
			locationB = stdIn.nextLine().trim();
			display(controller.showShortestPathBetween(locationA, locationB));
			break;
		case "5" : // Finds a path for a specified delivery trip
			display("Finds a path for a specified delivery trip...");
			display("Enter the ID of the required delivery trip:");
			try {
				inputLine = stdIn.nextLine().trim();
				deliverTripID = Integer.parseInt(inputLine);
				display(controller.showPathForDeliveryTrip(deliverTripID));
			} catch (NumberFormatException e) {
				display("Error: Non-numeric input. The ID should be a whole number.");
			}
			break;
		case "6" : // Exits the application
			display("Goodbye!");
			System.exit(0);
			break;
		default : // Not a known command option
			display(unrecogniseCommandErrorMsg(command));
		}
	}
	
	/*
	 * Returns a string representation of a brief title for this application as the header.
	 * @return	a header
	 */
	private static String header() {
		return "\nGrocer2You Grocery Delivery Scheduling system\n";
	}
	
	/*
	 * Returns a string representation of the user menu.
	 * @return	the user menu
	 */
	private static String menu() {
		return "Enter the number associated with your chosen menu option.\n" +
			   "1: Lists the sequence of orders in a specified delivery trip\n" +
			   "2: Lists the orders in a specific delivery trip\n" +
			   "3: Finds a path between two locations\n" +
			   "4: Finds the shortest path between two locations\n" +
			   "5: Finds a path for a specified delivery trip\n" +
			   "6: Exit this application\n";
	}
	
	/*
	 * Displays the specified info for the user to view.
	 * @param info	info to be displayed on the screen
	 */
	private void display(String info) {
		System.out.println(info);
	}
	
    /*
     * Returns an error message for an unrecognised command.
     * 
     * @param error the unrecognised command
     * @return      an error message
     */
    private static String unrecogniseCommandErrorMsg(String error) {
            return String.format("Cannot recognise the given command: %s.%n", error);
    }
}
