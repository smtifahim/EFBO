package ca.queensu.efbo;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class EFBOComparator 
{
	private EFBOKnowledgeBase firstSystemKBase;
	private EFBOKnowledgeBase secondSystemKBase;
	
	private ArrayList<Annotation> firstSystemAnnotations;
	private ArrayList<Annotation> secondSystemAnnotations;
	
	private String firstSystemName;
	private String secondSystemName;
	
	private static final String FIRST_SYSTEM_ID = "System-1";
	private static final String SECOND_SYSTEM_ID = "System-2";
	
	private OWLOntology efboValidator;
	private OntologyManager efboValidatorManager;
	
	private static final String 
	EFBO_Validator_URI = "http://www.cs.queensu.ca/~imam/ontologies/efbo-v.owl";
	
	private static final String 
	EFBO_V_File_Location = System.getProperty("user.dir") 
		   			     + "/Resources/Ontologies/";  
	
	//Default Constructor. 
	public EFBOComparator() throws Exception
	{
		this.displayEFBOComparatorInterface();
		this.firstSystemName = this.getSystemName(FIRST_SYSTEM_ID);
		this.firstSystemAnnotations = this.getSystemAnnotations();
		this.firstSystemKBase = this.getEFBOKnowledeBase(FIRST_SYSTEM_ID,
								firstSystemName, firstSystemAnnotations);
		
		
		this.secondSystemName = this.getSystemName(SECOND_SYSTEM_ID);
		this.secondSystemAnnotations = this.getSystemAnnotations();
		this.secondSystemKBase = this.getEFBOKnowledeBase(SECOND_SYSTEM_ID, 
								 secondSystemName, secondSystemAnnotations);
				
		this.setEFBOValidatorOntology();
		
		this.addKnowledgeBase(this.firstSystemKBase);
		this.setSystemID(this.firstSystemKBase, FIRST_SYSTEM_ID);
		
		this.addKnowledgeBase(this.secondSystemKBase);
		this.setSystemID(this.secondSystemKBase, SECOND_SYSTEM_ID);
		
		this.saveEFBOValidatorOntology();
	
	}
	
	private void setSystemID(EFBOKnowledgeBase kBase, String systemID)
	{
		
		OWLNamedIndividual kBaseSystemID = kBase.getSystemIDInstance();
			
		IRI classIRI = IRI.create(EFBO_Validator_URI + "#" + systemID);		
		OWLClass systemClass = efboValidatorManager.getOWLDataFactory().getOWLClass(classIRI);
		
		efboValidatorManager.assertOWLNamedIndividual(kBaseSystemID, systemClass);
				
	}
	
	private void displayEFBOComparatorInterface()
	{
		String firstDialog = "Please Click OK to Continue.";
		int OKOption = JOptionPane.showConfirmDialog(null, firstDialog, 
													"Welcome to EFBO Comparator", 
													JOptionPane.OK_CANCEL_OPTION);
		if (OKOption == JOptionPane.OK_OPTION)
		{
			System.out.println("> Wecome to EFBO Comparator!");
		}	
		else
		{
			System.out.println("> EXIT: EFBO Comparator Terminated.");
			System.exit(1);
		}
	}
	
    private void addKnowledgeBase(EFBOKnowledgeBase efboKBase) throws Exception
    {
    	String fileLocation = efboKBase.getLocalKBLocation();    	
    	efboValidatorManager.importOWLOntology(efboKBase.getEFBOKnowledgebase(), fileLocation);   
    }
	

	private EFBOKnowledgeBase getEFBOKnowledeBase(String systemID, String systemName,
									ArrayList<Annotation> annotations) throws Exception
	{
		EFBOKnowledgeBase efboKBase = new EFBOKnowledgeBase(systemID, systemName);
		efboKBase.processExtractedAnnotations(annotations);
		return efboKBase;
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
	public String getFirstSystemName()
	{
		return firstSystemName;
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
	 * @return the secondSystemID
	 */
	public String getSecondSystemName()
	{
		return secondSystemName;
	}

	/**
	 * @return the efboValidatorOntology
	 */
	public OWLOntology getEfboValidatorOntology()
	{
		return efboValidator;
	}

	/**
	 * load efbo validation ontology.
	 */
	public void setEFBOValidatorOntology() throws Exception
	{
		this.efboValidatorManager = new OntologyManager();
	    this.efboValidatorManager.loadOntology("EFBO-V", EFBO_Validator_URI);
	    this.efboValidator = efboValidatorManager.getLoadedOntology();
	    System.out.println("The EFBO-V Ontology Loaded Successfully.");
	}
	
	private void saveEFBOValidatorOntology()
			throws OWLOntologyCreationException,
			OWLOntologyStorageException 
	{
        efboValidator.getOWLOntologyManager()
        		 	 .saveOntology(efboValidator, 
        		      new StreamDocumentTarget(System.out));
       
        String fileLocation = EFBO_V_File_Location + "EFBO-V-Merged.owl";
        File defaultSaveLocation = new File(fileLocation);
        IRI efboVIRI = IRI.create(defaultSaveLocation.toURI());
        
        efboValidator.getOWLOntologyManager().saveOntology(efboValidator, efboVIRI);
        
        System.out.println("The EFBO-V Saved Successfully.");
        System.out.println("\nLocation: " + fileLocation);
	}
	
}// End of public class EFBOComparator. 