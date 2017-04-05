package ca.queensu.efbo.annotation.extractor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 * @author Fahim Imam.
 *
 */
public class AnnotationExtractor 
{
	private JFileChooser fileChooser;
	private File[] selectedFiles;
	private File extractedAnnotationsFile;
	private ArrayList<Annotation> annotations;
	
	public AnnotationExtractor()
	{	  
		  final String defaultFilePath = System.getProperty("user.dir") 
				  				+ "/Resources/Annotated-Sources";
		  fileChooser = new JFileChooser(new File(defaultFilePath));
		  fileChooser.setDialogTitle("Select Annotated Files");
		  fileChooser.setMultiSelectionEnabled(true);
		  fileChooser.showOpenDialog(null);
		  selectedFiles = fileChooser.getSelectedFiles();
		  
		  annotations = new ArrayList<Annotation>();
		 		  
		  for (int i = 0; i <selectedFiles.length; i++)
		  {
			 try 
			  {
				System.out.println( "\nSelected File#"+ (i+1));
				System.out.println ("File Location> " + selectedFiles[i].getCanonicalPath());
				annotations = extractAnnotations(selectedFiles[i]);
			  } 
			  
			 catch (IOException e) 
			  {
				e.printStackTrace();
			  }
		  }
		  
		  if (!annotations.isEmpty())
		  	{
			  String messageExtractSuccess = "Annotations Extracted Successfully!\n"
										   + "Press OK to Save Your Annotations.";
			  
			  JOptionPane.showMessageDialog(null, messageExtractSuccess, "Success!", JOptionPane.INFORMATION_MESSAGE);
			  
			  this.saveExtractedAnnotations(annotations);
		  	}
		  
		  else
		  	{
			  String messageNoAnnotationFound = "No Annotation Found within the Selected Source(s)."
											  + "\nPlease Try Again.";
			  System.out.println(messageNoAnnotationFound);
			  JOptionPane.showMessageDialog(null, messageNoAnnotationFound, "No Annotation Found", JOptionPane.WARNING_MESSAGE);
		  		  
		  	}
			  
	} //End of public AnnotationExtractor().
   	
	public ArrayList<Annotation> getExtractedAnnotations()
	{
		return this.annotations;
	}
	
	private ArrayList<Annotation> extractAnnotations(File inputFile)
	 {
		try
		 {
	   		 String fileLocation = inputFile.getCanonicalPath();
	   		 FileInputStream fileInputStream = new FileInputStream(inputFile);
			 DataInputStream dataInputStream = new DataInputStream(fileInputStream);
			 BufferedReader bufferReader = new BufferedReader(new InputStreamReader(dataInputStream));
			 
			 int lineNumber = 0;
			 String strLine;
			 boolean annotationFound = false;
			 
			 //Read file. 
			 while ((strLine = bufferReader.readLine()) != null)
			  	{
				  lineNumber += 1;
				  if (strLine.contains("@EFBO:"))
				  {
					    annotationFound = true;
					    String annotatedText = strLine.replaceFirst((".*.@.*:"), "").trim(); //Remove all the characters until @EFBO:
						annotatedText = annotatedText.replace(".", "").trim();				
						String[] parsedAnnotations = parseAnnotatedText(annotatedText);
					  	if (parsedAnnotations != null && parsedAnnotations.length==3)
					  	{
					  	 System.out.println("Annotation Found @Line#" + lineNumber + ">" + annotatedText);
					  	 Annotation  currentAnnotation= new Annotation(fileLocation, lineNumber, annotatedText);	
					  	 currentAnnotation.setSubject(parsedAnnotations[0]);
					  	 currentAnnotation.setPredicate(parsedAnnotations[1]);
					  	 currentAnnotation.setObject(parsedAnnotations[2]);
					  	 annotations.add(currentAnnotation);					  	 
					    }
					  	else
					  	{
					  	 String messageParsingFailed = "\nERROR! Parsing Annotation Failed."
	  	 							+ "\nCheck Your Annoatation @Line#" + lineNumber + " of the Following File."
	  	 							+ "\nFile Location> " + fileLocation;
					  						  		
					  	 System.out.println(messageParsingFailed);
					  	 JOptionPane.showMessageDialog(null, messageParsingFailed, "ERROR", JOptionPane.ERROR_MESSAGE);
					  	 System.exit(1);
					    }
				   } //End of if (strLine.contains("@EFBO:")).
			  				
			  }//End of while loop.
			 	
			  //Close the input stream
			  dataInputStream.close();
			  if (!annotationFound)
				  System.out.println("No Annotation Found.");
			
		 }//end of try.
		 	
		 	catch (Exception e)
		 		{
			    	System.err.println("Error: " + e.getMessage());
		 		}
	 
	   		return annotations;
	 }
	
	private static String[] parseAnnotatedText(String annotatedText)
	{
		  annotatedText = annotatedText.replaceAll("\"", " \" ").trim();
		  String[] fragments = annotatedText.split("\\s+");

		  int current = 0;
		  boolean matched = fragments[current].matches("[^\"]*");
		  if (matched) current++;

		  for (int i = 1; i < fragments.length; i++)
		  {
		    if (!matched)
		      fragments[current] = fragments[current] + " " + fragments[i];

		    if (!fragments[current].matches("(\"[^\"]*\"|[^\"]*)"))
		      matched = false;
		    
		    else 
		    {
		      matched = true;

		      if (fragments[current].matches("\"[^\"]*\""))
		        fragments[current] = fragments[current].substring(1, fragments[current].length() - 1).trim();

		      if (fragments[current].length() != 0)
		        current++;

		      if (i + 1 < fragments.length)
		        fragments[current] = fragments[i + 1];
		    }
		  } //End of for loop.

		  if (matched) 
		  { 
		    return Arrays.copyOf(fragments, current);
		  }

		  return null; // if double-quotes did not match properly.
		}

	public File getExtractedAnnotationsFile()
	{
		return extractedAnnotationsFile;
	}

	public void saveExtractedAnnotations(ArrayList<Annotation> annotations)
	{
		// parent component of the dialog
		JFrame fileSaveFrame = new JFrame();
		final String defaultFilePath = System.getProperty("user.dir") 
  							  		+ "/Resources/Extracted-Annotations"; 
		JFileChooser fileChooser = new JFileChooser(new File(defaultFilePath));
		fileChooser.setDialogTitle("Specify the file name to save your annotations");   
		 
		int userSelection = fileChooser.showSaveDialog(fileSaveFrame);
		 
		if (userSelection == JFileChooser.APPROVE_OPTION) 
		{
		    File fileToSave = fileChooser.getSelectedFile();
		    System.out.println("\nSave Annotations Location> " + fileToSave.getAbsolutePath());
		    
		    try 
			{
				FileWriter fileWriter = new FileWriter(fileToSave.getAbsolutePath());
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
							
				for (Annotation annotation : annotations)
				 {	
					bufferedWriter.write(annotation.getAnnotatedText());
					bufferedWriter.write("\tLine:" + annotation.getLineNumber());
					bufferedWriter.write("\tLocation:" + annotation.getFileLocation());
					bufferedWriter.write("\n");
				 }
				
				this.extractedAnnotationsFile=fileToSave;
				bufferedWriter.close();
				
				String messageSavedSuccess = "Annotations Saved Successfully!\n"
							    			 + "File Location> " + fileToSave.getAbsolutePath();
				System.out.println(messageSavedSuccess);				
				JOptionPane.showMessageDialog(fileSaveFrame, messageSavedSuccess, "Success!", JOptionPane.INFORMATION_MESSAGE);
				
			}
			
			catch (IOException e)
			{
				e.printStackTrace();
			}//End of catch 
			
		}//End of first if.
	}//End of method.

}//End of class.