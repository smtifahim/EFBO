package ca.queensu.efbo.annotation.extractor;

/**
 * Annotation class definition.
 * @author Fahim Imam
 */

public class Annotation 
{
	private String annotatedText;
	private String fileLocation;
	private int lineNumber;

	/**
	 * Default Constructor 
	 */
	public Annotation()
	{
		annotatedText = "";
		fileLocation = "";
		lineNumber = 0;
	}
	
	/**
	 * @param fileLocation
	 * @param lineNumber
	 * @param annotatedText
	 */
	public Annotation( String fileLocation, int lineNumber, String annotatedText) 
	{
		this.setFileLocation(fileLocation);
		this.setAnnotatedText(annotatedText);
		this.setLineNumber(lineNumber);
	}

	/**
	 * @return
	 */
	public String getAnnotatedText() 
	{
		return annotatedText;
	}

	/**
	 * @param annotatedText
	 */
	public void setAnnotatedText(String annotatedText) 
	{
		this.annotatedText = annotatedText;
	}

	public int getLineNumber() 
	{
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) 
	{
		this.lineNumber = lineNumber;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	
	public String toString()
	{
		String fileLocation = "\nLocation> " + this.getFileLocation();
		String annotation = "\n@line#" + this.getLineNumber()
							+ "> " + this.getAnnotatedText(); 
		return fileLocation + annotation;
		
	}

}
