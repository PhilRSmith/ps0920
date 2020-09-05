package toolRentalTests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import toolRental.RentalHelper;
import toolRental.ToolsInventory;
import toolRental.RentalAgreement;
import toolRental.InvalidDiscountPercentException;
import toolRental.InvalidRentalDaysException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CheckoutTests {

	RentalHelper testHelper = new RentalHelper();
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test (expected = InvalidDiscountPercentException.class)
	public void test1() throws FileNotFoundException, InvalidRentalDaysException, InvalidDiscountPercentException, IOException 
	{
		RentalAgreement testAgreement;
		String toolCode = "JAKR";
		String checkoutDate = "09/03/15";
		int daysRented = 5;
		int discount = 101;
		
		testAgreement = testHelper.Checkout(toolCode, checkoutDate, daysRented, discount);
		testAgreement.getChargeableDays();
		fail("Should throw InvalidDiscountPercentException");
	}
	
	@Test
	public void test2() throws FileNotFoundException, InvalidRentalDaysException, InvalidDiscountPercentException, IOException 
	{
		RentalAgreement testAgreement;
		ToolsInventory inventory = new ToolsInventory();
		String toolCode = "LADW";
		String checkoutDate = "07/02/20";
		int daysRented = 3;
		String dueDate = "07/05/20";
		int discount = 10;
		int chargeableDays = 2; // expected value
		
		double dailyRentalCharge = inventory.getToolsInfo().get(inventory.getInventory().get(toolCode).getType()).getCharge();
		double preDiscountCharge = dailyRentalCharge * chargeableDays;
		double discountAmount = preDiscountCharge *(Double.valueOf(discount)/100.0);
		double finalCharge = preDiscountCharge - discountAmount;
		
		testAgreement = testHelper.Checkout(toolCode, checkoutDate, daysRented, discount);
		assertEquals(testAgreement.getToolCode(), toolCode);
		assertEquals(testAgreement.getToolType(), inventory.getInventory().get(toolCode).getType());
		assertEquals(testAgreement.getToolBrand(), inventory.getInventory().get(toolCode).getBrand());
		assertEquals(testAgreement.getDaysRented(), daysRented);
		assertEquals(testAgreement.getCheckoutDate(), checkoutDate);
		assertEquals(testAgreement.getDueDate(), dueDate);
		assertEquals(testAgreement.getDailyCharge(),  0.005, dailyRentalCharge);
		assertEquals(testAgreement.getChargeableDays() , chargeableDays);
		assertEquals(testAgreement.getPreDiscountCharge(), 0.005, preDiscountCharge);
		assertEquals(testAgreement.getDiscount(), discount);
		assertEquals(testAgreement.getDiscountAmount(), 0.005, discountAmount);
		assertEquals(testAgreement.getFinalCharge(), 0.005, finalCharge);
		
	}
	
	@Test
	public void test3() throws FileNotFoundException, InvalidRentalDaysException, InvalidDiscountPercentException, IOException 
	{
		RentalAgreement testAgreement;
		ToolsInventory inventory = new ToolsInventory();
		String toolCode = "CHNS";
		String checkoutDate = "07/02/15";
		int daysRented = 5;
		String dueDate = "07/07/15";
		int discount = 25;
		int chargeableDays = 4; // expected value
		
		double dailyRentalCharge = inventory.getToolsInfo().get(inventory.getInventory().get(toolCode).getType()).getCharge();
		double preDiscountCharge = dailyRentalCharge * chargeableDays;
		double discountAmount = preDiscountCharge *(Double.valueOf(discount)/100.0);
		double finalCharge = preDiscountCharge - discountAmount;
		
		testAgreement = testHelper.Checkout(toolCode, checkoutDate, daysRented, discount);
		assertEquals(testAgreement.getToolCode(), toolCode);
		assertEquals(testAgreement.getToolType(), inventory.getInventory().get(toolCode).getType());
		assertEquals(testAgreement.getToolBrand(), inventory.getInventory().get(toolCode).getBrand());
		assertEquals(testAgreement.getDaysRented(), daysRented);
		assertEquals(testAgreement.getCheckoutDate(), checkoutDate);
		assertEquals(testAgreement.getDueDate(), dueDate);
		assertEquals(testAgreement.getDailyCharge(),  0.005, dailyRentalCharge);
		assertEquals(testAgreement.getChargeableDays() , chargeableDays);
		assertEquals(testAgreement.getPreDiscountCharge(), 0.005, preDiscountCharge);
		assertEquals(testAgreement.getDiscount(), discount);
		assertEquals(testAgreement.getDiscountAmount(), 0.005, discountAmount);
		assertEquals(testAgreement.getFinalCharge(), 0.005, finalCharge);
	}
	
	@Test
	public void test4() throws FileNotFoundException, InvalidRentalDaysException, InvalidDiscountPercentException, IOException 
	{
		RentalAgreement testAgreement;
		ToolsInventory inventory = new ToolsInventory();
		String toolCode = "JAKD";
		String checkoutDate = "09/03/15";
		int daysRented = 6;
		String dueDate = "09/09/15";
		int discount = 0;
		int chargeableDays = 3; // expected value
		
		double dailyRentalCharge = inventory.getToolsInfo().get(inventory.getInventory().get(toolCode).getType()).getCharge();
		double preDiscountCharge = dailyRentalCharge * chargeableDays;
		double discountAmount = preDiscountCharge *(Double.valueOf(discount)/100.0);
		double finalCharge = preDiscountCharge - discountAmount;
		
		testAgreement = testHelper.Checkout(toolCode, checkoutDate, daysRented, discount);
		assertEquals(testAgreement.getToolCode(), toolCode);
		assertEquals(testAgreement.getToolType(), inventory.getInventory().get(toolCode).getType());
		assertEquals(testAgreement.getToolBrand(), inventory.getInventory().get(toolCode).getBrand());
		assertEquals(testAgreement.getDaysRented(), daysRented);
		assertEquals(testAgreement.getCheckoutDate(), checkoutDate);
		assertEquals(testAgreement.getDueDate(), dueDate);
		assertEquals(testAgreement.getDailyCharge(),  0.005, dailyRentalCharge);
		assertEquals(testAgreement.getChargeableDays() , chargeableDays);
		assertEquals(testAgreement.getPreDiscountCharge(), 0.005, preDiscountCharge);
		assertEquals(testAgreement.getDiscount(), discount);
		assertEquals(testAgreement.getDiscountAmount(), 0.00, discountAmount); //Expecting 0 here
		assertEquals(testAgreement.getFinalCharge(), 0.005, finalCharge);
	}
	@Test
	public void test5() throws FileNotFoundException, InvalidRentalDaysException, InvalidDiscountPercentException, IOException 
	{
		RentalAgreement testAgreement;
		ToolsInventory inventory = new ToolsInventory();
		String toolCode = "JAKR";
		String checkoutDate = "07/02/15";
		int daysRented = 9;
		String dueDate = "07/11/15";
		int discount = 0;
		int chargeableDays = 6; // expected value
		
		double dailyRentalCharge = inventory.getToolsInfo().get(inventory.getInventory().get(toolCode).getType()).getCharge();
		double preDiscountCharge = dailyRentalCharge * chargeableDays;
		double discountAmount = preDiscountCharge *(Double.valueOf(discount)/100.0);
		double finalCharge = preDiscountCharge - discountAmount;
		
		testAgreement = testHelper.Checkout(toolCode, checkoutDate, daysRented, discount);
		assertEquals(testAgreement.getToolCode(), toolCode);
		assertEquals(testAgreement.getToolType(), inventory.getInventory().get(toolCode).getType());
		assertEquals(testAgreement.getToolBrand(), inventory.getInventory().get(toolCode).getBrand());
		assertEquals(testAgreement.getDaysRented(), daysRented);
		assertEquals(testAgreement.getCheckoutDate(), checkoutDate);
		assertEquals(testAgreement.getDueDate(), dueDate);
		assertEquals(testAgreement.getDailyCharge(),  0.005, dailyRentalCharge);
		assertEquals(testAgreement.getChargeableDays() , chargeableDays);
		assertEquals(testAgreement.getPreDiscountCharge(), 0.005, preDiscountCharge);
		assertEquals(testAgreement.getDiscount(), discount);
		assertEquals(testAgreement.getDiscountAmount(), 0.00, discountAmount); //Expecting 0 here
		assertEquals(testAgreement.getFinalCharge(), 0.005, finalCharge);
	}
	
	@Test
	public void test6() throws FileNotFoundException, InvalidRentalDaysException, InvalidDiscountPercentException, IOException 
	{
		RentalAgreement testAgreement;
		ToolsInventory inventory = new ToolsInventory();
		String toolCode = "JAKR";
		String checkoutDate = "07/02/20";
		int daysRented = 4;
		String dueDate = "07/06/20";
		int discount = 50;
		int chargeableDays = 2; // expected value
		
		double dailyRentalCharge = inventory.getToolsInfo().get(inventory.getInventory().get(toolCode).getType()).getCharge();
		double preDiscountCharge = dailyRentalCharge * chargeableDays;
		double discountAmount = preDiscountCharge *(Double.valueOf(discount)/100.0);
		double finalCharge = preDiscountCharge - discountAmount;
		
		testAgreement = testHelper.Checkout(toolCode, checkoutDate, daysRented, discount);
		assertEquals(testAgreement.getToolCode(), toolCode);
		assertEquals(testAgreement.getToolType(), inventory.getInventory().get(toolCode).getType());
		assertEquals(testAgreement.getToolBrand(), inventory.getInventory().get(toolCode).getBrand());
		assertEquals(testAgreement.getDaysRented(), daysRented);
		assertEquals(testAgreement.getCheckoutDate(), checkoutDate);
		assertEquals(testAgreement.getDueDate(), dueDate);
		assertEquals(testAgreement.getDailyCharge(),  0.005, dailyRentalCharge);
		assertEquals(testAgreement.getChargeableDays() , chargeableDays);
		assertEquals(testAgreement.getPreDiscountCharge(), 0.005, preDiscountCharge);
		assertEquals(testAgreement.getDiscount(), discount);
		assertEquals(testAgreement.getDiscountAmount(), 0.0005, discountAmount);
		assertEquals(testAgreement.getFinalCharge(), 0.005, finalCharge);
	}
	
	@Test (expected = InvalidRentalDaysException.class)
	public void test7() throws FileNotFoundException, InvalidRentalDaysException, InvalidDiscountPercentException, IOException 
	{
		RentalAgreement testAgreement;
		String toolCode = "JAKR";
		String checkoutDate = "09/03/15";
		int daysRented = -1;
		int discount = 0;
		
		
		testAgreement = testHelper.Checkout(toolCode, checkoutDate, daysRented, discount);
		testAgreement.getChargeableDays();
		fail("Should throw InvalidRentalDaysException");
	}
}

