package restaurant;
/**
 * Use this class to test your Menu method. 
 * This class takes in two arguments:
 * - args[0] is the menu input file
 * - args[1] is the output file
 * 
 * This class:
 * - Reads the input and output file names from args
 * - Instantiates a new RUHungry object
 * - Calls the menu() method 
 * - Sets standard output to the output and prints the restaurant
 *   to that file
 * 
 * To run: java -cp bin restaurant.Menu menu.in menu.out
 * 
 */
public class Menu {
    public static void main(String[] args) {

	// 1. Read input files
	// Option to hardcode these values if you don't want to use the command line arguments
	   
        String inputFile = args[0];        /// STOCK.IN FOR STOCKHASHTABLE
        String stock = args[1];
        String order = args[2];
        String donate = args [3];
        String restock = args [4];
        String trans = args[5];
        String outputFile = args[6]; 
        StdOut.println("Input file: " + inputFile);
        StdOut.println("Output file: " + outputFile);
	
        // 2. Instantiate an RUHungry object
        RUHungry rh = new RUHungry();

	// 3. Call the menu() method to read the menu
        rh.menu(inputFile);
        StdOut.println("Called menu: " + inputFile);
        rh.createStockHashTable(stock);
        rh.updatePriceAndProfit(); 
        readOrders(order, rh);
        //readDonations(donate, rh);
        readRestock(restock, rh);
        //transactions(trans, rh);

        
        
        
        /*rh.order("Scarlet Special Salad", 25);  // yes
        rh.order("Coca Cola", 70);              // yes
        rh.order("Sprite", 2);                 // yes
        rh.order("Fries", 60);                  // no
        rh.restock("Broccoli", 30);       // yes
        rh.order("Fries", 59);                  // yes
        rh.donation("Sprite", 1);        // yes
        */

	// 4. Set output file
	// Option to remove this line if you want to print directly to the screen
        StdOut.setFile(outputFile);

	// 5. Print restaurant
        rh.printRestaurant();

    }

    public static void readOrders (String fileName, RUHungry rh)
    {
        StdIn.setFile(fileName);
        int numOfOrders = Integer.parseInt(StdIn.readLine()); 
        for (int  i = 0; i<numOfOrders; i++)
        {
                int amount = StdIn.readInt();
                StdIn.readChar();
                String item = StdIn.readLine();
                rh.order(item, amount);
        }
    }

    public static void readDonations (String fileName, RUHungry rh)
    {
        StdIn.setFile(fileName);
        int numOfDonations = Integer.parseInt(StdIn.readLine()); 
        for (int  i = 0; i<numOfDonations; i++)
        {
                int amount = StdIn.readInt();
                StdIn.readChar();
                String item = StdIn.readLine();
                rh.donation(item, amount);
        }
    }

    public static void readRestock (String fileName, RUHungry rh)
    {
        StdIn.setFile(fileName);
        int numOfRestock = Integer.parseInt(StdIn.readLine()); 
        for (int  i = 0; i<numOfRestock; i++)
        {
                int amount = StdIn.readInt();
                StdIn.readChar();
                String item = StdIn.readLine();
                rh.restock(item, amount);
        }
    }

    public static void transactions (String fileName, RUHungry rh) {
        StdIn.setFile(fileName);
        int numOfTransactions = StdIn.readInt(); 
        StdIn.readLine();
        for (int i = 0; i<numOfTransactions; i++) {
                String type = StdIn.readString();
                StdIn.readChar();
                int amount = StdIn.readInt();
                StdIn.readChar();
                String item = StdIn.readLine();

                if(type.equals("order"))
                {
                        readOrders2(item, amount, rh);
                }
                else if (type.equals("donation"))
                {
                        readDonations2(item, amount, rh);
                }
                else if (type.equals("restock"))
                {
                        readRestock2(item, amount, rh);
                }
                }       
        }       

    public static void readOrders2 (String dishName, int dishQuantity, RUHungry rh) {
        rh.order(dishName, dishQuantity);
        }
    public static void readDonations2 (String dishName, int dishQuantity, RUHungry rh){
        rh.donation(dishName, dishQuantity);
        } 
    public static void readRestock2 (String dishName, int dishQuantity, RUHungry rh) {
        rh.restock(dishName, dishQuantity);
        }

}

