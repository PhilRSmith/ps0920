package toolRental;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Handles the transactional portion of the program
 * @author Philip Smith
 * Specs created by anonymous (notme)
 */
public class RentalMain 
{
	public static void Checkout() 
	{
		
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		ToolsInventory rentalInventory = new ToolsInventory();
		
		System.out.println(rentalInventory.getInventory().get("JAKR").getType());
		return;
	}

}
