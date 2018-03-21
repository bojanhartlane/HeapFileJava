package heapfile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.net.UnknownHostException;

/*
 * This is a Java program to implement heap file. In the first iteration, this program
 * will try to read each field value through delimiter and print it separately. 
 */

public class dbload {
	
	public static void main(String[] args) throws IOException, SocketException, UnknownHostException {
        //BufferedReader to accept file name as input and to read the .csv file
		BufferedReader buffFileRead = null, fileName = null;
		//String to contain value of each line in the .csv file
        String line = "";
        String fileLocation = "";
        //Delimiter for the records. In this case, it's Tab or \t 
        String csvSplitBy = "\t";
        //Field names
        String[] fieldNames = null;
        //Page size
        int pageSize = 0;
        
        if (args.length == 3) {
        	for (int i = 0; i < args.length; i++) {
            	if (Character.digit(args[i].charAt(args[i].length() - 1), 10) < 0) {
            		if (args[i].length() > 2) {
            			fileLocation = args[i];
            			System.out.println(fileLocation);
            		}
            	} else {
            		pageSize = Integer.parseInt(args[i]);
            		System.out.println(pageSize);
            	}
            }
        }        
        
        try {
        	//Ask user for the file name
        	System.out.print("Enter the file name: ");
        	//Use input as file name and access the file with BufferedReader buffFileRead
        	fileName = new BufferedReader(new InputStreamReader(System.in));
        	buffFileRead = new BufferedReader(new FileReader(fileName.readLine()));
        	//Read the first line, which is the field names
        	if ((line = buffFileRead.readLine()) != null) {
        		//Split the fields based on the delimiter
        		fieldNames = line.split(csvSplitBy);
        	}
        	//Print the field names
        	for (int i = 0; i < fieldNames.length; i++)  {
        		System.out.print(fieldNames[i] + " ");
        	}
        	System.out.println();
        	//Read until the file reaches last line
        	while ((line = buffFileRead.readLine()) != null) {
        		//Split the values in each line based on the delimiter
        		String[] lines = line.split(csvSplitBy);
        		String newLine = "";
        		for (int i = 0; i < lines.length; i++) {
        			if (lines[i].equals("")) {
                       	newLine += "NULL";
                   	}
                   	if (i == (lines.length - 1)) {
                   		newLine += lines[i];
                   	} else {
                   		newLine += lines[i] + "-";
                   	}                
        		}
        		//Print each line
        		System.out.println(newLine);
        		System.out.println();
        	}
    	 } catch (FileNotFoundException e) {
    		 e.printStackTrace();
    	 }
	}
	
}
