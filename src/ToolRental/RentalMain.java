package toolRental;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.text.SimpleDateFormat;
/**
 * Handles the transactional portion of the program
 * @author Philip Smith
 * Specs created by anonymous (notme)
 */
public class RentalMain 
{
	public static boolean shouldWeTerminate = false;
	public static String dueDate;
	
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
	public static void Checkout(Calendar c, ToolsInventory ourInventory) 
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yy");
		/*
		 * We'll ask the user to input the desired info for checkout here:
		 * checkout date, toolCode, days rented, and discount amount.
		 */
		//Test variables, will add user input later
		String inputDate = "09-03-15";
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
			ToolTypeCharges selectedToolInfo = ourInventory.getToolCharges().get(selectedTool.getType());
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
	 * 
	 * @param calendar
	 * @param ourInventory
	 * @param toolCode
	 * @param inputDate
	 * @param daysRented
	 * @return
	 */
	public static int getChargeableDays(Calendar c, ToolTypeCharges toolInfo, String inputDate, int daysRented)
	{
		int daysToCharge = 0;
		System.out.println(c.getTime());
		for(int i=0; i<daysRented;i++)
		{
			
		}
		
		return daysToCharge;
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		ToolsInventory rentalInventory = new ToolsInventory();
		Calendar calendar = Calendar.getInstance();
		
		while(shouldWeTerminate==false) //Runtime loop until user exits
			{Checkout(calendar, rentalInventory);}
		
		return;
	}

}
