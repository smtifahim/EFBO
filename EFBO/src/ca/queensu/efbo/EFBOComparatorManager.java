package ca.queensu.efbo;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class EFBOComparatorManager 
{
	private EFBOKnowledgeBaseManager firstSystemKBaseManager;
	private EFBOKnowledgeBaseManager secondSystemKBaseManager;
	
	private ArrayList<EFBOAnnotation> firstSystemAnnotations;
	private ArrayList<EFBOAnnotation> secondSystemAnnotations;
	
	private String firstSystemName;
	private String secondSystemName;
	
    private OWLOntology efboValidatorOntology;
	private EFBOOntologyManager efboValidatorManager;
	
	private static final String 
	EFBO_V_URI = "http://www.cs.queensu.ca/~imam/ontologies/efbo-v.owl";
	private static final String FIRST_SYSTEM_ID = "System-1";
	private static final String SECOND_SYSTEM_ID = "System-2";

	
	//Default Constructor. 
	public EFBOComparatorManager() throws Exception
	{
		
		firstSystemKBaseManager = null;
		firstSystemAnnotations = null;
		firstSystemName = null;
		
		secondSystemKBaseManager = null;
		secondSystemAnnotations = null;
		secondSystemName = null;
		
		efboValidatorOntology = null;
		efboValidatorManager = null;
	}
	
	public void loadFirstSystem() throws Exception
	{
		this.setFirstSystemName();
		this.setFirstSystemAnnotations();
		this.setFirstSystemKBaseManager();
		
	}
	
	public void loadSecondSystem() throws Exception
	{
		this.setSecondSystemName();
		this.setSecondSystemAnnotations();
		this.setSecondSystemKBaseManager();
		
	}
	
	public void mergeLoadedKBases() throws Exception
	{
		this.importFirstSystemKBase();
		this.assertFirstSystemID();
		
		this.importSecondSystemKBase();
		this.assertSecondSystemID();
		
		this.saveEFBOValidatorOntology();
	}
	
	public void assertFirstSystemID()
	{
		this.setSystemID(this.firstSystemKBaseManager, FIRST_SYSTEM_ID);
	}
	
	public void assertSecondSystemID()
	{
		this.setSystemID(this.secondSystemKBaseManager, SECOND_SYSTEM_ID);
	}
	
	
	private void setSystemID(EFBOKnowledgeBaseManager kBase, String systemID)
	{
		
		OWLNamedIndividual kBaseSystemID = kBase.getSystemIDInstance();
			
		IRI classIRI = IRI.create(EFBO_V_URI + "#" + systemID);		
		OWLClass systemClass = efboValidatorManager.getOWLDataFactory().getOWLClass(classIRI);
		
		efboValidatorManager.assertOWLNamedIndividual(kBaseSystemID, systemClass);
				
	}
	
    public void importFirstSystemKBase() throws Exception
	{
		this.importEFBOKnowledgeBase(this.firstSystemKBaseManager);
	}
	
	public void importSecondSystemKBase() throws Exception
	{
		this.importEFBOKnowledgeBase(this.secondSystemKBaseManager);
	}
	
    private void importEFBOKnowledgeBase(EFBOKnowledgeBaseManager efboKBaseManager) throws Exception
    {
    	String fileLocation = efboKBaseManager.getLocalKBLocation();    	
    	efboValidatorManager.importOWLOntology(efboKBaseManager.getEFBOKnowledgeBase(), fileLocation);   
    }
	
    public void setFirstSystemKBaseManager() throws Exception
	{
    	this.firstSystemKBaseManager = this.getEFBOKnowledeBaseManager(FIRST_SYSTEM_ID,
									   firstSystemName, firstSystemAnnotations);
		
	}
    
    public void setSecondSystemKBaseManager() throws Exception
 	{
     	this.secondSystemKBaseManager = this.getEFBOKnowledeBaseManager(SECOND_SYSTEM_ID,
 									   secondSystemName, secondSystemAnnotations);
 		
 	}
    
	private EFBOKnowledgeBaseManager getEFBOKnowledeBaseManager(String systemID, String systemName,
									ArrayList<EFBOAnnotation> annotations) throws Exception
	{
		EFBOKnowledgeBaseManager efboKBaseManager = new EFBOKnowledgeBaseManager(systemID, systemName);
		efboKBaseManager.processExtractedAnnotations(annotations);
		return efboKBaseManager;
	}

	/**
	 * @return the firstSystemAnnotations
	 */
	public ArrayList<EFBOAnnotation> getFirstSystemAnnotations()
	{
		return firstSystemAnnotations;
	}
	
	public ArrayList<EFBOAnnotation> getSecondSystemAnnotations()
	{
		return secondSystemAnnotations;
	}


	public void setFirstSystemAnnotations() throws Exception
	{
		this.firstSystemAnnotations = this.getSystemAnnotations();
	}
	
	public void setSecondSystemAnnotations() throws Exception
	{
		this.secondSystemAnnotations = this.getSystemAnnotations();
	}
	
	private ArrayList<EFBOAnnotation> getSystemAnnotations() throws Exception
	{
		return (new EFBOAnnotationExtractionManager().getExtractedAnnotations());		
	}

	
	/**
	 * @return the firstSystemName
	 */
	public String getFirstSystemName()
	{
		return firstSystemName;
	}
	
	public String getSecondSystemName()
	{
		return secondSystemName;
	}

	public void setFirstSystemName()
	{
		this.firstSystemName = this.getSystemName(FIRST_SYSTEM_ID);
	}
	
	public void setSecondSystemName()
	{
		this.secondSystemName = this.getSystemName(SECOND_SYSTEM_ID);
	}
	
	private String getSystemName(String systemID)
	{
		String systemName;
		String message =  "Enter the name for " + systemID;
		systemName = JOptionPane.showInputDialog (null, message);
		
		System.out.println("System ID: " +  systemID);
		System.out.println("Name : " +  systemName);
		
		return systemName;		
	}
	
	/**
	 * @return the efboValidatorOntology
	 */
	public OWLOntology getEFBOValidatorOntology()
	{
		return efboValidatorOntology;
	}

	/**
	 * load efbo validation ontology.
	 */
	public void loadEFBOValidatorOntology() throws Exception
	{
		this.efboValidatorManager = new EFBOOntologyManager();
	    this.efboValidatorManager.loadOntology("EFBO-V", EFBO_V_URI);
	    this.efboValidatorOntology = efboValidatorManager.getLoadedOntology();
	    
	    String loadSuccessMessage = "\nThe EFBO-V Ontology has been Loaded Successfully.";	    						  
	    System.out.println(loadSuccessMessage);
	    
	    JTextArea textArea = new JTextArea(20, 65);
	    textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
	    textArea.append(loadSuccessMessage + "\n" + efboValidatorManager.getOntologyMetrics());
	    textArea.setCaretPosition(0);
	   	textArea.setEditable(false);
        textArea.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), 
        		           "The EFBO-V Loading Status", TitledBorder.CENTER,
        		           TitledBorder.TOP, null, new Color(0, 0, 0)));
        
        JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Success!", 
				  JOptionPane.INFORMATION_MESSAGE);
	    	        
	}
	
	private void saveEFBOValidatorOntology()
			throws OWLOntologyCreationException,
			OWLOntologyStorageException 
	{
  
		JFrame fileSaveFrame = new JFrame();
		final String defaultFilePath = System.getProperty("user.dir") 
  							  		 + "/Resources/Ontologies/"; 
		JFileChooser fileChooser = new JFileChooser(new File(defaultFilePath));
		fileChooser.setDialogTitle("Save the EFBO-V Merged Ontology");
		
		int userSelection = fileChooser.showSaveDialog(fileSaveFrame);
		 
		if (userSelection == JFileChooser.APPROVE_OPTION) 
		{
			 File fileToSave = fileChooser.getSelectedFile();
	         IRI efboVIRI = IRI.create(fileToSave.toURI());
	         efboValidatorOntology.getOWLOntologyManager().saveOntology(efboValidatorOntology, efboVIRI);
	        
	         String messageSavedSuccess = "Ontology Saved Successfully!\n"
		    			 				   + "File Location> " 
		    			 				   + fileToSave.getAbsolutePath();
	         
	         //efboValidatorManager.printOntologyMetrics();
	         
			 System.out.println(messageSavedSuccess);				
			 JOptionPane.showMessageDialog(fileSaveFrame, messageSavedSuccess, "Success!", 
						  					  JOptionPane.INFORMATION_MESSAGE);
         }
		
		else
		 {
			String notSavedMessage = "You have chosen NOT to save the Ontology.";
			System.out.println(notSavedMessage);
			JOptionPane.showMessageDialog(fileSaveFrame, notSavedMessage, "Ontology NOT Saved", 
					  					  JOptionPane.INFORMATION_MESSAGE);
		 }
	}
	
}// End of public class EFBOComparator. 