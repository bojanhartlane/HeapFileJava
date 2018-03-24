package heapfile;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.net.UnknownHostException;

import sun.awt.image.FileImageSource;

public class test {
	
	public static void main(String[] args) throws IOException, SocketException, UnknownHostException {
        //BufferedReader to accept file name as input and to read the .csv file
		BufferedReader buffFileRead = null;
		//String to contain value of each line in the .csv file
        String line = "abcd";
        String fileLocation = "";
        //Delimiter for the records. In this case, it's Tab or \t 
        String csvSplitBy = "\t";
        //Field names
        String[] fieldNames = null;
        //Page size
        int pageSize = 2048;
        
        File saveFile = new File("heap." + String.valueOf(pageSize));
        saveFile.createNewFile();
        
        DataOutputStream os = new DataOutputStream(new FileOutputStream(saveFile));
        os.writeChars(line);
        System.out.println("Output stream size: " + os.size() + " bytes");
        os.writeChars(line);
        System.out.println("Output stream size: " + os.size() + " bytes");
        os.writeChars(line);
        System.out.println("Output stream size: " + os.size() + " bytes");
        os.close();
        
        DataInputStream is = new DataInputStream(new FileInputStream(saveFile));
        byte[] bs = new byte[is.available()];
        is.read(bs);
        
        String output = "";
        
        for (int i = 0; i < line.length() * 2; i++) {
        	if (i % 2 == 1) {
	        	char c = (char)bs[i];
	        	output += c;	        	
        	}
        }
        System.out.println(output);
        is.close();
	}

}
