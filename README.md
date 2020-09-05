# Programming demo: Tool Rental
Author: Philip R. Smith

Initial Completion date: 9/5/2020

Note: My goal was to have this as completely self contained as possible for easy reading and review. There are no outside dependencies, everything should be runnable just using the java sdk and the jar file with the instructions.

### Instructions to run
- Have the text files, "inventory.txt" and "toolTypeCharges.txt" in the same directory as MiniRental.jar .
- the text files serve as the data used in the program refer to section "Text file format" for more info if you want to change the contents.
- Run the jar in the command line: java -jar /somepath/MiniRental.jar .
- follow the prompts for required user input.

### Tests
- The tests were made using JUnit 4. 
- Usable within eclipses JUnit support features.
- The suite tests the checkout function in the RentalHelper class which generates and returns the rental agreement object.
- The rental agreement object contains the information printed out at the end.

### Text file format

The text files contain information in the below format.

**

inventory.txt : contains parseable info that fills in the tool objects in the ToolsInventory hashmap

###### format: ToolType , ToolBrand, ToolCode
###### Example: Ladder, Werner, LADW

**

toolTypeCharges.txt : contains parseable info that fills in the toolTypeInfo objects in the ToolsInventory hashmap

###### format: ToolType , daily charge, hasWeekdayCharge, hasWeekendCharge, hasHolidayCharge
###### Example: Ladder, 2.99, yes, no, yes

