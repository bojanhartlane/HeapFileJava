import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
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
        System.out.println("Record size: " + recordSize * 2);
        
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
        
        try {
        	buffFileRead = new BufferedReader(new FileReader(fileLocation));
        	//Read the first line, which is the field names
        	if ((line = buffFileRead.readLine()) != null) {
        		//Split the fields based on the delimiter
        		fieldNames = line.split(csvSplitBy);
        	}
        	//Print the field names
        	for (int i = 0; i < fieldNames.length; i++)  {
        		System.out.println(fieldNames[i] + " length: " + fieldNames[i].length());
        	}
        	
        	//Read until the file reaches last line
        	while ((line = buffFileRead.readLine()) != null) {
        		//Split the values in each line based on the delimiter
        		String[] lines = line.split(csvSplitBy);        		
        		for (int i = 0; i < lines.length; i++) {
        			if (lines[i].equals("")) {
                       	System.out.println("NULL");
                   	} else {
                   		System.out.println(lines[i]);
                   	}                
        		}
        		if (lines.length < fieldNames.length) {
        			System.out.println("NULL");
    			} 
        	}
        	
    	 } catch (FileNotFoundException e) {
    		 e.printStackTrace();
    	 }
        
	}
	
}
