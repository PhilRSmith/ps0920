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
	
	
	
	public String getCheckoutDate() {
		return checkoutDate;
	}



	public void setCheckoutDate(String checkoutDate) {
		this.checkoutDate = checkoutDate;
	}



	public String getDueDate() {
		return dueDate;
	}



	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}



	public String getToolCode() {
		return toolCode;
	}



	public void setToolCode(String toolCode) {
		this.toolCode = toolCode;
	}



	public String getToolType() {
		return toolType;
	}



	public void setToolType(String toolType) {
		this.toolType = toolType;
	}



	public String getToolBrand() {
		return toolBrand;
	}



	public void setToolBrand(String toolBrand) {
		this.toolBrand = toolBrand;
	}



	public int getDaysRented() {
		return daysRented;
	}



	public void setDaysRented(int daysRented) {
		this.daysRented = daysRented;
	}



	public int getChargeableDays() {
		return chargeableDays;
	}



	public void setChargeableDays(int chargeableDays) {
		this.chargeableDays = chargeableDays;
	}



	public int getDiscount() {
		return discount;
	}



	public void setDiscount(int discount) {
		this.discount = discount;
	}



	public double getDailyCharge() {
		return dailyCharge;
	}



	public void setDailyCharge(double dailyCharge) {
		this.dailyCharge = dailyCharge;
	}



	public double getPreDiscountCharge() {
		return preDiscountCharge;
	}



	public void setPreDiscountCharge(double preDiscountCharge) {
		this.preDiscountCharge = preDiscountCharge;
	}



	public double getDiscountAmount() {
		return discountAmount;
	}



	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}



	public double getFinalCharge() {
		return finalCharge;
	}



	public void setFinalCharge(double finalCharge) {
		this.finalCharge = finalCharge;
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
