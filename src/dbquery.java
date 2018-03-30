import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
import java.net.UnknownHostException;


public class dbquery {
	
	public static void main(String[] args) throws IOException, SocketException, UnknownHostException {
        //String to be searched for in the heap file
        String searchText = "";
        //String to access file location
        String fileLocation = "";        
        //Page size
        int pageSize = -1;
        //Number of pages
        int numOfPages = 0;
        //Total pages
        int totalPages = 0;
        //Number of records in a page
        int numOfRecords = 1;
        //Record position
        int recordPosition = 0;
        //Remaining size
        int remainingSize = 0;
        //Maximum characters in a record (289 characters or 578 bytes)
        int recordSize = 289;
        //Fixed arrays for the record       
        String[] record = new String[9];
        
        //Check arguments for page size and search text
        if (args.length == 2) {
        	if (Character.digit(args[1].charAt(args[1].length() - 1), 10) > 0) {
        		searchText = args[0].toLowerCase();
	    		pageSize = Integer.parseInt(args[1]);
	    		fileLocation = "heap." + args[1];
        	}            
        }
        
        if (pageSize >= 578) {
		    //DataInputStream to retrieve the total amount of records stored in the heap file
		    BufferedReader recBuffReader = new BufferedReader(new FileReader("pages"));
		    //Read total number of pages saved in the heap file 
		    totalPages = Integer.parseInt(recBuffReader.readLine());
		    //Close the BufferedReader stream
		    recBuffReader.close();
		    
		    System.out.println("Total number of pages: " + totalPages);
		    
		    //File to be read
		    File saveFile = new File(fileLocation);
		    //Create the file if it doesn't exist
		    saveFile.createNewFile();
		    //Prepare DataInputStream for read process
		    DataInputStream is = new DataInputStream(new FileInputStream(saveFile));        
		    
		    //recordsInPage stores the number of records in a page
		    int recordsInPage = (pageSize / 2) / recordSize;        
		    System.out.println("Num of records in a page: " + recordsInPage);        
		    System.out.println("Page number: " + (numOfPages + 1));
		    //String to store record reading result
		    String result = "";
		    //Scan all pages in the heap file
		    for (int i = 0; i < (totalPages * (pageSize /2)); i++) {
		    	//Read every character in a page one at a time
		    	char c = is.readChar();
		    	//Check if current character is the beginning of a new record
		    	if ((i - numOfPages * pageSize / 2) % 289 == 0) {
		    		//record[8] stores the ABN of a company
					record[8] = result;
					result = "";
		    		//If record[1], which is BN_NAME field, matches searchText, then save the
					//record to the file because it's a match
		    		if (record[1].toLowerCase().contains(searchText)) {
						System.out.println("Matching Record!");
						for (int j = 0; j < record.length; j++) {
							System.out.println(record[j]);
						}
					}
		    		recordPosition++;
		    	}
		    	//Check if current character reaches the end of REGISTER_NAME field
		    	if ((i - numOfPages * pageSize / 2) % 289 == 14) {
		    		//Store result to REGISTER_NAME field
		    		record[0] = result;
		    		result = "";	    		
		    	}
		    	//Check if current character reaches the end of BN_NAME field
		    	else if ((i - numOfPages * pageSize / 2) % 289 == 214) {
		    		//Store result to BN_NAME field
		    		record[1] = result;
		    		result = "";
		    	}
		    	//Check if current character reaches the end of BN_STATUS field
		    	else if ((i - numOfPages * pageSize / 2) % 289 == 226) {
		    		//Store result to BN_STATUS field
					record[2] = result;
					result = "";	 	    		
		    	}
		    	//Check if current character reaches the end of BN_REG_DT field
		    	else if ((i - numOfPages * pageSize / 2) % 289 == 236) {
		    		//Store result to BN_REG_DT field
					record[3] = result;
					result = "";	 	    		
		    	}
		    	//Check if current character reaches the end of BN_CANCEL_DT field
		    	else if ((i - numOfPages * pageSize / 2) % 289 == 246) {
		    		//Store result to BN_CANCEL_DT field
					record[4] = result;
					result = "";	 	    		
		    	}
		    	//Check if current character reaches the end of BN_RENEW_DT field
		    	else if ((i - numOfPages * pageSize / 2) % 289 == 256) {
		    		//Store result to BN_RENEW_DT field
					record[5] = result;
					result = "";	 	    		
		    	}
		    	//Check if current character reaches the end of BN_STATE_NUM field
		    	else if ((i - numOfPages * pageSize / 2) % 289 == 266) {
		    		//Store result to BN_STATE_NUM field
					record[6] = result;
					result = "";	 	    		
		    	}
		    	//Check if current character reaches the end of BN_STATE_OF_REG field
		    	else if ((i - numOfPages * pageSize / 2) % 289 == 269) {
		    		//Store result to BN_STATE_OF_REG field
					record[7] = result;
					result = "";	 	    		
		    	}
		    	
		    	//Only add character if it's not 'Tab', because 'Tab' character is a placeholder
		    	//character in the pages, so it's not a part of the actual record value
		    	if (c != '\t') {
		    		result += c;
		    	}
		    	//If number of records becomes bigger than the maximum number of records in a page,
		    	//then the program will move to the next page
		    	if (recordPosition > recordsInPage) {
		    		//Add number of pages to get the current page position
		    		numOfPages++;
		    		//Read the placeholders to avoid blank record reading
		    		for (int j = 0; j < ((pageSize / 2) - (recordsInPage * recordSize)) - 1; j++) {
		    			is.readChar();
		    		}
		    		//Set i to the end of the current page
		    		i = (numOfPages * pageSize / 2) - 1; 			
					System.out.println("Page number: " + (numOfPages + 1));
					//Set recordPosition back to 0 for the new page
					recordPosition = 0;
				}
		    }
		    
		    //Close the input stream
		    is.close();
        } else {
        	System.out.println("Page size must be bigger than 578 bytes");
        }
         
        
	}

}
