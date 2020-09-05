package toolRental;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;

public class RentalHelper {

	private String checkoutDate;
	private String dueDate;
	private Tool rentedTool;
	private ToolTypeInfo rentedToolInfo;
	private int daysRented;
	private int chargeableDays;
	private int discount;
	
	public RentalHelper()
	{
		this.dueDate = null;
		this.checkoutDate=null;
		this.rentedTool=null;
		this.rentedToolInfo=null;
		this.daysRented=0;
		this.chargeableDays=0;
		this.discount =0;
	}
	
	/**
	 * Checkout function will sort through provided information and generate a rental agreement object, then prints its contents.
	 * is successfully completed.
	 * The only inputs the user should be required to have are:
	 * 1. The checkout date
	 * 2. The tool code
	 * 3. The number of days rented
	 * 4. Desired discount
	 * Everything else should be automated and the rental agreement should be generated on the console.
	 * Modifies object values to be used when printing the final agreement.
	 */
	private void Checkout(String toolCode , String inputDate, int daysRented, int discountAmount) 
	throws InvalidRentalDaysException, InvalidDiscountPercentException , FileNotFoundException, IOException
	{
		ToolsInventory ourInventory = new ToolsInventory();
		Calendar c = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
		
		this.checkoutDate = inputDate;
		try
		{c.setTime(dateFormat.parse(inputDate));}
		catch(ParseException e) 
		{
			System.out.println("Unparseable date, terminating program");
			return;
		}
		
		
		Tool selectedTool = null;
			
	    selectedTool = ourInventory.getInventory().get(toolCode);
		if(selectedTool==null)
		{
			System.out.println("Couldn't find the tool associated with the input toolcode, terminating program. Please try again");
			return;
		}
		else
		{
			this.rentedTool = selectedTool;
		}
		
		/*Check if input days rented throws exception*/
		if(checkIfEnoughDays(daysRented)==false)
		{
			throw new InvalidRentalDaysException("The number of days specified to rent the tool was less than 1. Please restart and enter an integer of 1 or higher");
		}
		else
		{
			this.daysRented = daysRented;
		}
		
		/*Check if input discount throws exception*/
		if(checkIfInPercentRange(discountAmount)==false) 
		{
			throw new InvalidDiscountPercentException("The input discount percentage was invalid. Please restart and input an integer value between 0 and 100");
		}
		else
		{
			this.discount = discountAmount;
		}
			
	
		ToolTypeInfo selectedToolInfo = ourInventory.getToolCharges().get(this.rentedTool.getType());
		int chargeDays = getChargeableDays(c, selectedToolInfo, inputDate, daysRented);
		this.rentedToolInfo = selectedToolInfo;
		this.chargeableDays = chargeDays;
		System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
		System.out.println("Thank you. I'm generating the Rental Agreement now!");
		System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
		double[] calculatedCharges = calculateCharges();
		RentalAgreement contract = new RentalAgreement(this.rentedTool , this.checkoutDate, 
														this.dueDate, this.daysRented, this.chargeableDays, 
														this.discount, calculatedCharges);
		contract.PrintAgreement();
		
	}
	
	/**
	 * Performs the final output of Rental Agreement info using this instantiated objects properties that were filled in.
	 */
	
	private double[] calculateCharges()
	{	/* index 0 = pre-discount , index 1 = discount amount, index 2 = final charge , index 3 = daily charge*/
		double[] charges = new double[4];
		charges[0] = this.rentedToolInfo.getCharge() * this.chargeableDays;
		charges[1] = charges[0] * (Double.valueOf(this.discount)/100.0);
		charges[2] = charges[0] - charges[1];
		charges[3] = this.rentedToolInfo.getCharge();
		return charges;
	}
	
	/**
	 * Checks if user input date with proper formatting
	 * @param inputDate
	 * @return
	 * @throws InvalidDateException
	 */
	private boolean checkIfProperDate(String inputDate) throws InvalidDateException
	{
		Pattern properDateFormat = Pattern.compile("[0-1][0-9]/[0-3][0-9]/[0-9][0-9]");
		Matcher properFormatChecker = properDateFormat.matcher(inputDate);
		
		if(properFormatChecker.matches()) 
		{
			String[] validDateCheck = inputDate.split("/");
			if(Integer.parseInt(validDateCheck[0])>12)
			{
				System.out.println("The entered month is greater than 12, not possible on calendar -- Terminating program, please try again.");
				throw new InvalidDateException("");
			}
			if(Integer.parseInt(validDateCheck[1]) > 31)
			{
				System.out.println("The entered day is greater than 31, not possible on calendar -- Terminating program, please try again.");
				throw new InvalidDateException("");
			}
			return true;
		}
		else
		{
			throw new InvalidDateException("The input date cannot exist. Please input the checkout date in the form 'MM-DD-YY' - Example: 01/01/20");
		}
		
		
	}
	
	/**
	 * 
	 * @param daysToRent
	 * @return
	 */
	private boolean checkIfEnoughDays(int daysToRent)
	{
		if(daysToRent<1)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param discountPercent
	 * @return
	 */
	private boolean checkIfInPercentRange(int discountPercent)
	{
		if(discountPercent<0||discountPercent>100)
		{
			return false;
		}
		return true;
	}
	
	
	/**
	 * Calculates the number of days to charge the user for based on the tooltype info
	 * Also sets the due date or the desired rental
	 * @param calendar : c, library for calendar functions
	 * @param toolInfo : object containing info on when to charge user for type of tool
	 * @param inputDate : initial checkout date
	 * @param daysRented : number of days desired to rent
	 * @return daysToCharge : integer for the number of days to charge a customer on rental.
	 */
	private int getChargeableDays(Calendar c, ToolTypeInfo toolInfo, String inputDate, int daysRented)
	{
		int daysToCharge = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
		String formattedDate = "";
		String[] toolChargePeriods = toolInfo.getChargePeriods();
		
		/*creating 4th of July holiday regex pattern*/
		Pattern fourthJulyDate = Pattern.compile("07/04/[0-9]{2}");
		/*creating labor day regex pattern*/
		Pattern laborDayDate = Pattern.compile(findLaborDay(c, dateFormat, inputDate)); /*grab labor day holiday date given the current year.*/
		
		for(int i=0; i<daysRented;i++)
		{
			c.add(Calendar.DATE, 1);
			int dayOfWeek = c.get(Calendar.DAY_OF_WEEK); /* 1 & 7 are sunday and saturday respectively*/
			formattedDate = dateFormat.format(c.getTime()); /*Formatting the time for the regex check on holidays*/
			Matcher fourthJulyMatcher = fourthJulyDate.matcher(formattedDate);
			Matcher laborDayMatcher = laborDayDate.matcher(formattedDate);
			
			if(fourthJulyMatcher.matches() || laborDayMatcher.matches()) /* Holidays */
			{
				if(toolChargePeriods[2].equalsIgnoreCase("yes"))
					{daysToCharge++;}
			}
			else if(dayOfWeek == 1 || dayOfWeek == 7) /*Weekend*/
			{
				if(toolChargePeriods[1].equalsIgnoreCase("yes"))
					{daysToCharge++;}
			}
			else if(dayOfWeek>1&&dayOfWeek<7) /*Weekday*/
			{
				if(toolChargePeriods[0].equalsIgnoreCase("yes"))
					{daysToCharge++;}
			}
		}
		
		
		this.dueDate = formattedDate;
		try 
		{
			c.setTime(dateFormat.parse(inputDate));
		}
		catch(ParseException ex)
		{
			System.out.println("Error made while resetting date after calculating number of days to charge");
		}
		return daysToCharge;
	}
	
	/**
	 * finds the exact date of labor day on a given year based on the current calendar.
	 * this operates on the assumption that the customer won't be renting something into September of the next year.
	 * @param c : calendar
	 * @param dateFormat : a simple date format that makes it easier to use regex
	 * @return
	 */
	private String findLaborDay(Calendar c, SimpleDateFormat dateFormat, String inputDate) throws NullPointerException
	{
		String currentDate = dateFormat.format(c.getTime());
		String currentYear = currentDate.split("/")[2]; //Since this is formatted, shouldn't throw exception
		String septemberInCurrentYear = "09/01/"+currentYear;
		
		try 
		{
			c.setTime(dateFormat.parse(septemberInCurrentYear));
		}
		catch(ParseException ex)
		{
			System.out.println("ERROR: invalid input date for calendar parse -- Exiting");
			return null;
		}
		
		for(int i = 0; i < 8; i++) 
		{
			int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			if(dayOfWeek==2)
			{
				break;
			}
			c.add(Calendar.DATE, 1);
		}
		String formattedLaborDayDate = dateFormat.format(c.getTime());
		try 
		{
			c.setTime(dateFormat.parse(inputDate));
		}
		catch(ParseException ex)
		{
			System.out.println("Error made while resetting date after labor day check");
		}
		
		return formattedLaborDayDate;
	}
	
	/**
	 * Driver function, accepts user input, performs basic handling of it, then runs the actual program.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void runRental()
	{
		String inputDate = null;
		String inputToolCode = null;
		int daysRented, discountAmount;
		
		Scanner input = new Scanner(System.in);
		
		boolean isProperDate = false;
		while(isProperDate==false)
		{
			System.out.println("Please input the checkout date in the form 'MM/DD/YY' - Example: 01/01/20");
			while(!input.hasNext()) 
			{
				System.out.println("Invalid input, please try again");
				input.next();
			}
			inputDate = input.nextLine();
			try
			{isProperDate = checkIfProperDate(inputDate);}
			catch(Exception e)
			{isProperDate = false; System.out.println(e);}
		}
		
		
		System.out.println("Please input the four letter toolcode for the selected item - Example: JAKR");
		while(!input.hasNext()) 
		{
			System.out.println("Invalid input, please try again. Enter the four letter toolcode for the selected item - Example: JAKR");
			input.next();
		}
		
		inputToolCode = input.nextLine();
	
		
		daysRented=1;
		System.out.println("Please input the number of days you would like to rent the tool for (integer value of 1 or greater)");
		while(!input.hasNextInt()) 
		{
			System.out.println("Input was not an integer, please try again");
			input.next();
		}
		daysRented = input.nextInt();
		
		System.out.println("Please input the percent discount you'd like to give the customer. (Integer value between 0 and 100)");
		while(!input.hasNextInt()) 
		{
			System.out.println("Input was not an integer, please try again");
			input.next();
		}
		discountAmount = input.nextInt();
			
		input.close();
		
		try 
		{
			Checkout(inputToolCode, inputDate, daysRented, discountAmount);
		}
		catch(Exception e)
		{
			System.out.println(e); return;
		}
		
		
		return;
	}
	
}
