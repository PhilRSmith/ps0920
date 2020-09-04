package toolRental;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;


public class ToolsInventory {
	
	private HashMap<String,Tool> inventory = new HashMap<String,Tool>();
	private HashMap<String, ToolTypeCharges> toolCharges = new HashMap<String, ToolTypeCharges>();
	
	/**
	 * Default Constructor of Inventory system (may just replace this all with postgresql)
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ToolsInventory() throws FileNotFoundException, IOException
	{
		this.inventory = readInventory();
		this.toolCharges = readToolCharges();
	}
	
	/**
	 * Reads in a text file with inventory info and stores it into a hashmap for later lookup
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public HashMap<String,Tool> readInventory() throws FileNotFoundException, IOException
	{
		HashMap<String, Tool> builtInventory = new HashMap<String, Tool>();
		try(BufferedReader br = new BufferedReader(new FileReader("inventory.txt")))
		{
			String line =br.readLine();
			while(line!=null)
			{
				String temp = line.replaceAll(" " , "");
				String[] typeBrandCode = temp.split(",");
				
				Tool tool = new Tool(typeBrandCode[2], typeBrandCode[1], typeBrandCode[0]);
				builtInventory.put(tool.getCode(), tool);
				line = br.readLine();
			}
			br.close();
		}
		return builtInventory;
	}
	
	
	/**
	 * grabs the inventory hashmap
	 * @return
	 */
	public HashMap<String,Tool> getInventory()
	{
		return this.inventory;
	}
	
	
	/**
	 * reads in the charge info for different tool types from a text file
	 * @return HashMap<String,ToolTypeCharges>
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public HashMap<String,ToolTypeCharges> readToolCharges() throws FileNotFoundException, IOException
	{
		HashMap<String, ToolTypeCharges> builtCharges = new HashMap<String, ToolTypeCharges>();
		try(BufferedReader br = new BufferedReader(new FileReader("toolTypeCharges.txt")))
		{
			String line = br.readLine();
			while(line!=null)
			{
				String temp = line.replaceAll(" " , "");
				String[] toolTypeInfo = temp.split(",");
				
				ToolTypeCharges charges = new ToolTypeCharges(toolTypeInfo[0], toolTypeInfo[1], toolTypeInfo[2], toolTypeInfo[3], toolTypeInfo[4]);
				builtCharges.put(charges.getType(), charges);
				line = br.readLine();
			}
			br.close();
		}
		return builtCharges;
	}
	
	/**
	 * grabs the tool type charges hashmap
	 * @return
	 */
	public HashMap<String, ToolTypeCharges> getToolCharges()
	{
		return this.toolCharges;
	}
	
}
