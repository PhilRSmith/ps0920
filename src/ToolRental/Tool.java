package toolRental;

public class Tool {

	private String toolCode;
	private String brand;
	private String toolType;
	
	public Tool() //Just a default constructor, hope I don't forget to remove this. REMOVE THIS
	{
		this.toolCode = null;
		this.brand = null;
		this.toolType = null;
	}
	
	public Tool(String code , String brand, String type)
	{
		this.toolCode = code;
		this.brand = brand;
		this.toolType = type;
	}
	
	public String getCode() 
	{
		return this.toolCode;
	}
	
	public String getBrand()
	{
		return this.brand;
	}
	
	public String getType()
	{
		return this.toolType;
	}
}
