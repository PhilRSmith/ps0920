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
	
	/**
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		RentalHelper mainDriver = new RentalHelper();
		mainDriver.runRental();
		
		return;
	}

}
