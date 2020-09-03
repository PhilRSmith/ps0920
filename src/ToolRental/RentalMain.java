package toolRental;
import java.util.HashMap;

public class RentalMain {

	public static void main(String[] args) 
	{
		ToolTypeCharges ladder = new ToolTypeCharges("ladder", 1.99, true, true, false);
		System.out.println(ladder.getChargePeriods()[2]);
		return;
	}

}
