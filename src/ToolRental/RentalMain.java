package toolRental;
import java.io.FileNotFoundException;
import java.io.IOException;

public class RentalMain 
{
	
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		ToolsInventory rentalInventory = new ToolsInventory();
		
		System.out.println(rentalInventory.getInventory().get("JAKR").getType());
		return;
	}

}
