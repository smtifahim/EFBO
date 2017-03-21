package ca.queensu.efbo.annotation.extractor;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;


public class AnnotationExtractor 
{
   static String readAnnotatedFiles(File inputFile)
	 {
		String annotatedLines = "";
	   
	   	try
		 {
			 FileInputStream fstream = new FileInputStream(inputFile);
			 DataInputStream in = new DataInputStream(fstream);
			 BufferedReader br = new BufferedReader(new InputStreamReader(in));
			 
			 String strLine;
			  //Read File Line By Line
			 while ((strLine = br.readLine()) != null)
			  	{
				  if (strLine.contains("//@EFBO:"))
				  {
					  //System.out.println (strLine);
					  annotatedLines += strLine + "\n"; 
					  StringTokenizer t = new StringTokenizer(strLine);
					  	while (t.hasMoreTokens())
					  		System.out.println (t.nextToken());  
				  }				  
			  	}
			  	//Close the input stream
			  	in.close();
		 	}
		 	
		 	catch (Exception e)
		 		{
			    	System.err.println("Error: " + e.getMessage());
		 		}
	 
	   		return annotatedLines;
	 }
	
	  public static void main (String[] argv) 
	  {
		  JFileChooser fileChooser = new JFileChooser();
		  
		  fileChooser.setDialogTitle("Select Annotated Files");
		  fileChooser.setMultiSelectionEnabled(true);
		  fileChooser.showOpenDialog(null);
		  
		  File[] selectedFiles = fileChooser.getSelectedFiles();
		  
		  String annotatedLines = "";
		  
		  for (int i = 0; i< selectedFiles.length; i++)
		  {
			 
			  try 
			  {
				System.out.println("Annotated File Selected: " + selectedFiles[i].getCanonicalPath());
				annotatedLines += readAnnotatedFiles(selectedFiles[i]);
			  } 
			  catch (IOException e) 
			  {
				// TODO Auto-generated catch block
				e.printStackTrace();
			  }
		  }
		  
		  System.out.println(annotatedLines);
		  
	  }//End of main.
}//End of class.

