package toolRental;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.util.Locale;
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
	 * Checkout function will output all necessary info to the console if the transaction
	 * is successfully completed.
	 * The only inputs the user should be required to have are:
	 * 1. The checkout date
	 * 2. The tool code
	 * 3. The number of days rented
	 * 4. Desired discount
	 * Everything else should be automated and the rental agreement should be generated on the console.
	 * Modifies object values to be used when printing the final agreement.
	 */
	private void Checkout(ToolsInventory ourInventory , String toolCode , String inputDate, int daysRented, int discountAmount) 
	{
		Calendar c = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
		
		try
		{c.setTime(dateFormat.parse(inputDate));}
		catch(ParseException e) 
		{
			System.out.println("Unparseable date, terminating program");
			return;
		}
		
		/*Check if input days rented throws exception*/
		try
		{checkIfEnoughDays(daysRented); this.daysRented=daysRented;}
		catch(Exception e)
		{System.out.println(e); return;}
		
		try
		{checkIfInPercentRange(discountAmount); this.discount = discountAmount;}
		catch(Exception e)
		{System.out.println(e); return;}
			
		
		/*double checks that the desired tool exists then calculates the number of chargeable days*/
	
		ToolTypeInfo selectedToolInfo = ourInventory.getToolCharges().get(this.rentedTool.getType());
		int chargeDays = getChargeableDays(c, selectedToolInfo, inputDate, daysRented);
		this.rentedToolInfo = selectedToolInfo;
		this.chargeableDays = chargeDays;
		System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
		System.out.println("Thank you. I'm generating the Rental Agreement now!");
		System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
		printRentalAgreement();
			
		
		
	}
	
	/**
	 * Performs the final output of Rental Agreement info using this instantiated objects properties that were filled in.
	 */
	private void printRentalAgreement() 
	{
		NumberFormat usd = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
		double[] calculatedCharges = calculateCharges();
		double preDiscountCharge = 0, discountAmount =0, finalCharge =0;
		
		for(int i=0;i<calculatedCharges.length;i++) 
		{
			switch(i) 
			{
				case 0:
				{preDiscountCharge = calculatedCharges[i]; break;}
				case 1:
				{discountAmount = calculatedCharges[i]; break;}
				case 2:
				{finalCharge = calculatedCharges[i]; break;}
			}
			
				
			
		}
		System.out.println("Tool code: " + this.rentedTool.getCode());
		System.out.println("Tool type: " + this.rentedTool.getType());
		System.out.println("Tool brand: " + this.rentedTool.getBrand());
		System.out.println("Days rented: " + this.daysRented);
		System.out.println("Checkout date: " + this.checkoutDate);
		System.out.println("Due date: " + this.dueDate);
		System.out.println("Daily rate: " + usd.format(this.rentedToolInfo.getCharge()));
		System.out.println("Chargeable days: "+ this.chargeableDays);
		System.out.println("Pre-Discount charge: " + usd.format(preDiscountCharge));
		System.out.println("Discount percentage: " + this.discount + "%");
		System.out.println("Discount amount: " + usd.format(discountAmount));
		System.out.println("Final charge: " + usd.format(finalCharge));
		System.out.println();
		System.out.println("Sign Here: ______________________________");
		System.out.println("Thank you for renting with us, have a great day!");
		//Note: Just need to format the output
	}
	
	private double[] calculateCharges()
	{	/* index 0 = pre-discount , index 1 = discount amount, index 2 = final charge*/
		double[] charges = new double[3];
		charges[0] = this.rentedToolInfo.getCharge() * this.chargeableDays;
		charges[1] = charges[0] * (Double.valueOf(this.discount)/100.0);
		charges[2] = charges[0] - charges[1];
		return charges;
	}
	
	/**
	 * 
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
	 * @throws InvalidRentalDaysException
	 */
	private boolean checkIfEnoughDays(int daysToRent) throws InvalidRentalDaysException
	{
		if(daysToRent<1)
		{
			throw new InvalidRentalDaysException("The number of days specified to rent the tool was less than 1, Please enter an integer of 1 or higher");
		}
		return true;
	}
	
	/**
	 * 
	 * @param discountPercent
	 * @return
	 * @throws InvalidDiscountPercentException
	 */
	private boolean checkIfInPercentRange(int discountPercent) throws InvalidDiscountPercentException
	{
		if(discountPercent<0||discountPercent>100)
		{
			throw new InvalidDiscountPercentException("The input discount percentage was invalid. Please input an integer value between 0 and 100");
		}
		return true;
	}
	
	
	/**
	 * Calculates the number of days to charge the user for based on the tooltype info
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
			
			c.add(Calendar.DATE, 1);
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
	 * Driver function, runs the actual program.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void runRental()throws FileNotFoundException, IOException
	{
		ToolsInventory rentalInventory = new ToolsInventory();
		String inputDate = null;
		String inputToolCode = null;
		Tool selectedTool;
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
			{isProperDate = checkIfProperDate(inputDate); 	this.checkoutDate = inputDate;}
			catch(Exception e)
			{isProperDate = false; System.out.println(e);}
		}
		
		boolean specifiedToolExists = false;
		selectedTool = null;
		while(specifiedToolExists==false)
		{
			System.out.println("Please input the four letter toolcode for the selected item - Example: JAKR");
			while(!input.hasNext()) 
			{
				System.out.println("Invalid input, please try again");
				input.next();
			}
			
			inputToolCode = input.nextLine();
		    selectedTool = rentalInventory.getInventory().get(inputToolCode);
			if(selectedTool!=null)
			{
				specifiedToolExists=true;
				this.rentedTool = selectedTool;
			}
			else
			{
				System.out.println("Couldn't find the tool associated with the input toolcode, please try again");
			}
		}
		
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
		Checkout(rentalInventory, inputToolCode, inputDate, daysRented, discountAmount);
		
		return;
	}
	
}
