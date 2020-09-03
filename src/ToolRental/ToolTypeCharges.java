package toolRental;

public class ToolTypeCharges {

	private String type;
	private double charge;
	private boolean weekdayCharge;
	private boolean weekendCharge;
	private boolean holidayCharge;
	
	/**
	 * Object that stores standard rental charge info.
	 * @param type
	 * @param weekdayCharge
	 * @param weekendCharge
	 * @param holidayCharge
	 */
	public ToolTypeCharges(String type, double charge, boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge)
	{
		this.type = type;
		this.charge = charge;
		this.weekdayCharge = weekdayCharge;
		this.weekendCharge = weekendCharge;
		this.holidayCharge = holidayCharge;
	}
	
	public String getType() //Standard get
	{
		return this.type;
	}
	public double getCharge()
	{
		return this.charge;
	}
	
	/**
	 * 
	 * @return charges
	 */
	public boolean[] getChargePeriods()
	{
		boolean[] charges = new boolean[3];
		for(int i=0;i<charges.length;i++)
		{
			switch(i) 
			{
			case 0: charges[i]= this.weekdayCharge;
					break;
			case 1: charges[i] = this.weekendCharge;
					break;
			case 2: charges[i] = this.holidayCharge;
					break;
			}
		}
		
		return charges;
	}
}
