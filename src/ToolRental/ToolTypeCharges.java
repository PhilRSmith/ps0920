package toolRental;

public class ToolTypeCharges {

	private String type;
	private String charge;
	private String weekdayCharge;
	private String weekendCharge;
	private String holidayCharge;
	
	/**
	 * Object that stores standard rental charge info.
	 * @param type
	 * @param weekdayCharge
	 * @param weekendCharge
	 * @param holidayCharge
	 */
	public ToolTypeCharges(String type, String charge, String weekdayCharge, String weekendCharge, String holidayCharge)
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
		double chargeValue = Double.parseDouble(this.charge);
		return chargeValue;
	}
	
	/**
	 * 
	 * @return charges
	 */
	public String[] getChargePeriods()
	{
		String[] charges = new String[3];
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
