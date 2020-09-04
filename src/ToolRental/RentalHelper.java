package toolRental;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RentalHelper {

	public boolean shouldWeTerminate = false;
	public String dueDate;
	
	public RentalHelper()
	{
		this.shouldWeTerminate = false;
		this.dueDate = null;
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
	 */
	private void Checkout(Calendar c, ToolsInventory ourInventory) 
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yy");
		/*
		 * We'll ask the user to input the desired info for checkout here:
		 * checkout date, toolCode, days rented, and discount amount.
		 */
		//Test variables, will add user input later
		String inputDate = "07-02-15";
		try
		{
			c.setTime(dateFormat.parse(inputDate));
		}
		catch(ParseException e) 
		{
			System.out.println("Invalid input date, please enter in the format: 'MM-DD-YY' .");
			return;
		}
		String inputToolCode = "JAKR";
		int daysRented = 6; //NOTE: Need to throw an exception on this if #days<1
		int discountAmount = 10; //NOTE: Need to throw an exception on this if discount is not between 0-100
		
		Tool selectedTool = ourInventory.getInventory().get(inputToolCode);
		if(selectedTool!=null)
		{
			ToolTypeInfo selectedToolInfo = ourInventory.getToolCharges().get(selectedTool.getType());
			int chargeDays = getChargeableDays(c, selectedToolInfo, inputDate, daysRented);
		}
		else
		{
			System.out.println("Input tool code was invalid!");
			shouldWeTerminate = true;
			return;
		}
		
		
		shouldWeTerminate=true;// will change this later to be based on user input at the end of transaction
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
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yy");
		String formattedDate = "";
		String[] toolChargePeriods = toolInfo.getChargePeriods();
		
		/*creating 4th of July holiday regex pattern*/
		Pattern fourthJulyDate = Pattern.compile("07-04-[0-9]{2}");
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
		String currentYear = currentDate.split("-")[2]; //Since this is formatted, shouldn't throw exception
		String septemberInCurrentYear = "09-01-"+currentYear;
		
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
		Calendar calendar = Calendar.getInstance();
		
		while(this.shouldWeTerminate==false) /*Runtime loop until user exits*/
			{Checkout(calendar, rentalInventory);}
		
		return;
	}
	
}
