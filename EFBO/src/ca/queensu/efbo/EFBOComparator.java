package ca.queensu.efbo;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.semanticweb.owlapi.model.OWLOntology;

public class EFBOComparator 
{
	private OWLOntology firstSystemKBase = null;
	private OWLOntology secondSystemKBase = null;
	
	private ArrayList<Annotation> firstSystemAnnotations;
	private ArrayList<Annotation> secondSystemAnnotations;
	
	private String firstSystemName;
	private String secondSystemName;
	
	private String firstSystemID = "SYSTEM-01";
	private String secondSystemID = "SYSTEM-02";
	
	//Default Constructor. 
	public EFBOComparator() throws Exception
	{
		this.displayEFBOComparatorInterface();
		
		this.firstSystemName = this.setFirstSystemName();
		this.firstSystemAnnotations = this.getSystemAnnotations();
		this.firstSystemKBase = this.getEFBOKnowledeBase(firstSystemID,
								firstSystemName, firstSystemAnnotations);
		
		JOptionPane.showMessageDialog(null, "First System Loaded.");
		
		this.secondSystemName = this.setSecondSystemName();
		this.secondSystemAnnotations = this.getSystemAnnotations();
		this.secondSystemKBase = this.getEFBOKnowledeBase(secondSystemID, 
								secondSystemName, secondSystemAnnotations);
		
		JOptionPane.showMessageDialog(null, "Second System Loaded.");
	
	}
	
	private void displayEFBOComparatorInterface()
	{
		String firstDialog = "Please Click OK to Continue.";
				
		int OKOption = JOptionPane.showConfirmDialog(null, firstDialog, 
				"Welcome to EFBO Comparator System", 
				JOptionPane.OK_CANCEL_OPTION);
		if (OKOption == JOptionPane.OK_OPTION)
		{
			System.out.println("> Wecome to EFBO Comparator!");
		}	
		else
		{
			System.out.println("> EXIT: EFBO Comparator System.");
			System.exit(1);
		}
	}
	

	

	/**
	 * @param firstSystemKBase the firstSystemKBase to set
	 */
	private OWLOntology getEFBOKnowledeBase(String systemID, String systemName,
									ArrayList<Annotation> annotations) throws Exception
	{
		EFBOKnowledgeBase efboKBase = new EFBOKnowledgeBase(systemID, systemName);
		efboKBase.processExtractedAnnotations(annotations);
		return efboKBase.getEFBOKnowledgeBase();
	}

	/**
	 * @return the firstSystemAnnotations
	 */
	public ArrayList<Annotation> getFirstSystemAnnotations()
	{
		return firstSystemAnnotations;
	}

	/**
	 * @param firstSystemAnnotations the firstSystemAnnotations to set
	 */
	private ArrayList<Annotation> getSystemAnnotations() throws Exception
	{
		AnnotationExtractor annotationExtractor = new AnnotationExtractor();
		return annotationExtractor.getExtractedAnnotations();		
	}

	
	/**
	 * @return the firstSystemName
	 */
	private String getFirstSystemName()
	{
		return firstSystemName;
	}

	/**
	 * @param firstSystemName the firstSystemName to set
	 */
	private String setFirstSystemName()
	{
		firstSystemName =  JOptionPane.showInputDialog(null, "Enter a name for System-01");
		System.out.println("First System's Name: " +  firstSystemName);
		System.out.println("First System's Assigned ID: " +  this.firstSystemID);
		return firstSystemName;
	}

	/**
	 * @return the secondSystemName
	 */
	public String getSecondSystemName()
	{
		return secondSystemName;
	}

	/**
	 * @param secondSystemName the secondSystemName to set
	 */
	private String setSecondSystemName()
	{
		secondSystemName =  JOptionPane.showInputDialog(null, "Enter a name for System-02");
		System.out.println("Second System's Name: " +  secondSystemName);
		System.out.println("Second System's Assigned ID: " +  this.secondSystemID);
		
		return secondSystemName;		
	}

	/**
	 * @return the firstSystemID
	 */
	public String getFirstSystemID()
	{
		return firstSystemID;
	}

	/**
	 * @param firstSystemID the firstSystemID to set
	 */
	public void setFirstSystemID(String firstSystemID)
	{
		this.firstSystemID = firstSystemID;
	}

	/**
	 * @return the secondSystemID
	 */
	public String getSecondSystemID()
	{
		return secondSystemID;
	}

	/**
	 * @param secondSystemID the secondSystemID to set
	 */
	public void setSecondSystemID(String secondSystemID)
	{
		this.secondSystemID = secondSystemID;
	}
	


}// End of public class EFBOComparator. 