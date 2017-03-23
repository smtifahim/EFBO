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
import javax.swing.JFileChooser;
import javax.swing.JFrame;


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
		  fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
		  fileChooser.setDialogTitle("Select Annotated Files");
		  fileChooser.setMultiSelectionEnabled(true);
		  fileChooser.showOpenDialog(null);
		  selectedFiles = fileChooser.getSelectedFiles();
		  
		  annotations =  new ArrayList<Annotation>();
		 		  
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			  }
		  }
		  
		  if (!annotations.isEmpty())
			  this.saveExtractedAnnotations(annotations);
		  
	}
   	
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
			 boolean annotationFound = false;
			 
			 String strLine;
			  //Read File Line By Line
			 while ((strLine = bufferReader.readLine()) != null)
			  	{
				  lineNumber += 1;
				  if (strLine.contains("//@EFBO:"))
				  {
					annotationFound = true;
					System.out.println("Annotation Found @Line#" + lineNumber + ">" + strLine.trim());  
				  	annotations.add(new Annotation(fileLocation, lineNumber, strLine.trim()));
				  }
				  				  
				}
			  	
			  if (!annotationFound)
				  System.out.println("No Annotation Found within the Selected File.");
			 	
			    //Close the input stream
			  dataInputStream.close();
		 }//end of try.
		 	
		 	catch (Exception e)
		 		{
			    	System.err.println("Error: " + e.getMessage());
		 		}
	 
	   		return annotations;
	 }

	public File getExtractedAnnotationsFile()
	{
		return extractedAnnotationsFile;
	}

	public void saveExtractedAnnotations(ArrayList<Annotation> annotations)
	{
		// parent component of the dialog
		JFrame fileSaveFrame = new JFrame();
		 
		JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
		fileChooser.setDialogTitle("Specify the File Name to Save");   
		 
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
				System.out.println("Annotations Saved Successfully.");
			}
			
			catch (IOException e)
			{
				e.printStackTrace();
			}//End of catch 
			
		}//End of first if.
	}//End of method.

}//End of class.