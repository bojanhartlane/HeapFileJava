import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
import java.net.UnknownHostException;

/*
 * This is a Java program to implement heap file. The pages are specifically designed for
 * the ABN database and this program implements fixed-length records. This means that, for example,
 * a field with maximum length of 200 will have 200 characters for every entry in the memory,
 * even though the actual value is less than 200 characters. The rest will be filled with
 * substitute characters.
 */

public class dbload {
	
	public static void main(String[] args) throws IOException, SocketException, UnknownHostException {
        //BufferedReader to accept file name as input and to read the .csv file
		BufferedReader buffFileRead = null;
		//String to contain value of each line in the .csv file
        String line = "";
        //String to access file location
        String fileLocation = "";
        //Delimiter for the records. In this case, it's Tab or \t 
        String csvSplitBy = "\t";
        //Field names
        String[] fieldNames = null;
        //Page size
        int pageSize = 0;
        //Number of records
        int numOfRecords = 0;
        //Number of pages
        int numOfPages = 1;
        //Remaining size
        int remainingSize = 0;
        //Record size
        int recordSize = 0;
        //Fixed arrays for the record       
        char[][] record = { new char[14], new char[200], new char[12],
        					new char[10], new char[10], new char[10],
        					new char[10], new char[3], new char[20] };
        
        for (int i = 0; i < record.length; i++) {
    		recordSize += record[i].length;        	
        }
        //1 character is 2 bytes, so the actual record size is total characters * 2 bytes
        recordSize *= 2;
        //Print one record's size
        System.out.println("One record's size: " + recordSize);
        
        //Initialise the array again with placeholder. In this program, we use "Tab" character
        for (int i = 0; i < record.length; i++) {
        	for (int j = 0; j < record[i].length; j++) {        		
        		record[i][j] = '\t';
        	}
        }
        
        //Check arguments for page size and file location
        if (args.length == 3) {
        	for (int i = 0; i < args.length; i++) {
            	if (Character.digit(args[i].charAt(args[i].length() - 1), 10) < 0) {
            		if (args[i].length() > 2) {
            			fileLocation = args[i];
            			System.out.println(fileLocation);
            		}
            	} else {
            		pageSize = Integer.parseInt(args[i]);
            		remainingSize = Integer.parseInt(args[i]);
            		System.out.println(pageSize);
            	}
            }
        }
        
        //File to be written
        File saveFile = new File("heap." + String.valueOf(pageSize));
        //Create the file if it doesn't exist
        saveFile.createNewFile();
        //Prepare DataOutputStream for write process
        DataOutputStream os = new DataOutputStream(new FileOutputStream(saveFile));
                
        
        try {
        	//Read the input file
        	buffFileRead = new BufferedReader(new FileReader(fileLocation));
        	//Read the first line, which is the field names
        	if ((line = buffFileRead.readLine()) != null) {
        		//Split the fields based on the delimiter
        		fieldNames = line.split(csvSplitBy);
        	}
        	//System time when the operation starts
        	long startOperation = System.currentTimeMillis();
        	//Read until the file reaches last line
        	while ((line = buffFileRead.readLine()) != null) {
        		//Add total number of records
        		numOfRecords++;
        		//If remaining size is smaller than record size,
        		//then run this method
        		if ((remainingSize - recordSize) < 0) {
        			String filler = "";
        			for (int i = 0; i < (remainingSize / 2); i++) {
        				//Gaps at the end of the page
        				filler += "\t";
        			}
        			//Write gaps at the end of the page
        			os.writeChars(filler);
        			//Add new page
        			numOfPages++;
        			System.out.println("Page number: " + numOfPages);
        			//Reset remaining size to page size for the next page
        			remainingSize = pageSize;
        		}
        		//Split the values in each line based on the delimiter
        		String[] lines = line.split(csvSplitBy);     		
        		for (int i = 0; i < lines.length; i++) {
        			//Add each value to the appropriate field by inserting one char at a time
        			for (int j = 0; j < lines[i].length(); j++) {
        				record[i][j] = lines[i].charAt(j);
        			}        	        
        		}
        		for (int i = 0; i < record.length; i++) {
        			//String to contain characters to be written
	        		String valueToWrite = "";
	        		for (int j = 0; j < record[i].length; j++) {
	        			//Add character to valueToWrite to be written to the file
	    				valueToWrite += record[i][j];
	    				//Initialise the array again with placeholder    	        	
		        		record[i][j] = '\t';
	    			}
	    			//Write value to the page
	    			os.writeChars(valueToWrite);        	
        		}
        		//Subtract remaining size with this record's size
    			remainingSize -= recordSize;   
        	}
        	os.close();
        	//System time when the operation starts
        	long endOperation = System.currentTimeMillis();
        	//Total operation time
        	long operationTime = endOperation - startOperation;
        	
        	//File to keep number of pages
        	File recFile = new File("pages");
        	//Create the file if it doesn't exist
            recFile.createNewFile();
            //Write the number of pages to "pages" file
            BufferedWriter buffRecFile = new BufferedWriter(new FileWriter(recFile));
            PrintWriter printRecFile = new PrintWriter(buffRecFile);
            printRecFile.println(numOfPages);
            //Close the PrintWriter and BufferedWriter streams
            printRecFile.close();
            buffRecFile.close();
        	
        	//File for statistics
            File statFile = new File("stdout");
            //Create the file if it doesn't exist
            statFile.createNewFile();
            //Prepare DataOutputStream for write process
            BufferedWriter buffStatFile = new BufferedWriter(new FileWriter(statFile));
            PrintWriter printStatFile = new PrintWriter(buffStatFile);
            //Print statistics to the file
            printStatFile.println("Total records: " + numOfRecords);
            printStatFile.println("Total pages: " + numOfPages);
            printStatFile.println("Total operation time: " + operationTime + " milliseconds");
            //Close the stream
            printStatFile.close();
            buffStatFile.close();
            
        	
    	 } catch (FileNotFoundException e) {
    		 e.printStackTrace();
    	 }         
        
	}
	
}
