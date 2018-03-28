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
        int pageSize = 0;
        //Number of pages
        int numOfPages = 1;
        //Number of records
        int numOfRecords = 0;
        //Remaining size
        int remainingSize = 0;
        //Record size
        int recordSize = 0;
        //Fixed arrays for the record       
        char[][] record = { new char[14], new char[200], new char[12],
        					new char[10], new char[10], new char[10],
        					new char[10], new char[3], new char[20] };
        
        //Check arguments for page size and search text
        if (args.length == 2) {
        	for (int i = 0; i < args.length; i++) {
            	if (Character.digit(args[i].charAt(args[i].length() - 1), 10) < 0) {
            		if (args[i].length() > 2) {
            			searchText = args[i].toLowerCase();
            			System.out.println(searchText);
            		}
            	} else {
            		pageSize = Integer.parseInt(args[i]);
            		fileLocation = "heap." + args[i];
            		System.out.println(fileLocation);
            	}
            }
        }
        
        //File to be read
        File saveFile = new File(fileLocation);
        //Create the file if it doesn't exist
        saveFile.createNewFile();
        //Prepare DataOutputStream for write process
        DataInputStream is = new DataInputStream(new FileInputStream(saveFile));
        
        byte[] byteContent = new byte[1156];
        is.read(byteContent, 0, 1156);
        
        //String to contain business name from each record
        String businessName = "";
        
        //Read only the business name
        for (int i = 28; i < 428; i++) {        	
        	if (i % 2 == 1) {
        		System.out.println(i);
	        	char c = (char)byteContent[i];
	        	if (c == '\t') {
	        		i = 428;
	        	} else {
	        		businessName += c;
	        	}
        	}
        }
        System.out.println(businessName.toLowerCase());
        
        if (businessName.toLowerCase().contains(searchText)) {
        	System.out.println("True");
        } else {
        	System.out.println("False");
        }
        
        is.close();       
        
         
        
	}

}
