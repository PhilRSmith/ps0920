package toolRental;

import java.text.NumberFormat;
import java.util.Locale;


public class RentalAgreement {
	
	private String checkoutDate, dueDate, toolCode, toolType, toolBrand;
	private int daysRented, chargeableDays, discount;
	private double dailyCharge , preDiscountCharge, discountAmount ,finalCharge;
	
	public RentalAgreement(Tool rentedTool, String checkout, String due, int daysRented, int chargeableDays, int discount, double[] calculatedCharges)
	{
		this.toolCode = rentedTool.getCode();
		this.toolType = rentedTool.getType();
		this.toolBrand = rentedTool.getBrand();
		
		this.checkoutDate = checkout;
		this.dueDate = due;
		this.daysRented = daysRented;
		this.chargeableDays = chargeableDays;
		this.discount = discount;
		
		for(int i=0;i<calculatedCharges.length;i++) 
		{
			switch(i) 
			{
				case 0:
				{this.preDiscountCharge = calculatedCharges[i]; break;}
				case 1:
				{this.discountAmount = calculatedCharges[i]; break;}
				case 2:
				{this.finalCharge = calculatedCharges[i]; break;}
				case 3:
				{this.dailyCharge = calculatedCharges[i]; break;}
			}
			
		}
	}
	
	public void PrintAgreement() 
	{
		NumberFormat usd = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
		System.out.println("Tool code: " + this.toolCode);
		System.out.println("Tool type: " + this.toolType);
		System.out.println("Tool brand: " + this.toolBrand);
		System.out.println("Days rented: " + this.daysRented);
		System.out.println("Checkout date: " + this.checkoutDate);
		System.out.println("Due date: " + this.dueDate);
		System.out.println("Daily rate: " + usd.format(this.dailyCharge));
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
	
	

}
