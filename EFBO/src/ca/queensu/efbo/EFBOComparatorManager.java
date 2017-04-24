package ca.queensu.efbo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.mindswap.pellet.jena.PelletReasonerFactory;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.search.EntitySearcher;

public class EFBOComparatorManager 
{
	private EFBOKnowledgeBaseManager firstSystemKBaseManager;
	private EFBOKnowledgeBaseManager secondSystemKBaseManager;
	
	private Set<EFBOAnnotation> firstSystemAnnotations;
	private Set<EFBOAnnotation> secondSystemAnnotations;
	
	private Map<OWLNamedIndividual, String> firstSystemEvents = new HashMap <OWLNamedIndividual, String>();
	private Map<OWLNamedIndividual, String> secondSystemEvents = new HashMap <OWLNamedIndividual, String>();
	private EFBOMappingEventsManager efboMappingEventsManager;
	
	private String firstSystemName;
	private String secondSystemName;
	
    private OWLOntology efboValidationOntology;
    private OWLOntology efboInferredOntology;
	private EFBOOntologyManager efboValidationManager;
	
	private static final String 
	EFBO_V_URI = "http://www.cs.queensu.ca/~imam/ontologies/efbo-v.owl";
	
	private static final String FIRST_SYSTEM_ID = "System-1";
	private static final String SECOND_SYSTEM_ID = "System-2";
    
	private File efboValidationOntologyFile;
	private File inferredOntologyFile;
	
	//Default Constructor. 
	public EFBOComparatorManager() throws Exception
	{
		
		firstSystemKBaseManager = null;
		firstSystemAnnotations = null;
		firstSystemName = null;
		
		secondSystemKBaseManager = null;
		secondSystemAnnotations = null;
		secondSystemName = null;
		
		efboValidationOntology = null;
		efboInferredOntology = null;
		efboValidationManager = null;
		
		efboValidationOntologyFile = null;
		inferredOntologyFile = null;
		
		firstSystemEvents = null;
		secondSystemEvents = null;
		efboMappingEventsManager = null;
				
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
		
		this.saveEFBOValidationOntology();
		this.saveEFBOInferredOntology();
		
		this.setFirstSystemEvents();
		this.setSecondSystemEvents();
		
		this.efboMappingEventsManager = new EFBOMappingEventsManager();
		this.efboMappingEventsManager.setFirstSystemEvents(this.firstSystemEvents);
		this.efboMappingEventsManager.setSecondSystemEvents(this.secondSystemEvents);
		this.efboMappingEventsManager.populateTreeElements();
		this.efboMappingEventsManager.setVisible(true);
	//	efboMappingEventsManager.main();
		
		this.importEFBOInferredOntology();
		
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
		OWLClass systemClass = efboValidationManager.getOWLDataFactory().getOWLClass(classIRI);
		
		efboValidationManager.assertOWLNamedIndividual(kBaseSystemID, systemClass);
				
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
    	efboValidationManager.importOWLOntology(efboKBaseManager.getEFBOKnowledgeBase(), fileLocation);   
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
									Set<EFBOAnnotation> annotations) throws Exception
	{
		EFBOKnowledgeBaseManager efboKBaseManager = new EFBOKnowledgeBaseManager(systemID, systemName);
		efboKBaseManager.processExtractedAnnotations(annotations);
		//System.out.println(efboKBaseManager.getInferredEvents());
		return efboKBaseManager;
	}

	/**
	 * @return the firstSystemAnnotations
	 */
	public Set<EFBOAnnotation> getFirstSystemAnnotations()
	{
		return firstSystemAnnotations;
	}
	
	public Set<EFBOAnnotation> getSecondSystemAnnotations()
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
	
	private Set<EFBOAnnotation> getSystemAnnotations() throws Exception
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
		
		EFBOUserInterfaceManager.progressBar.setValue(10);
		System.out.println("System ID: " +  systemID);
		System.out.println("Name : " +  systemName);
		
		return systemName;		
	}
	
	/**
	 * @return the efboValidatorOntology
	 */
	public OWLOntology getEFBOValidatorOntology()
	{
		return efboValidationOntology;
	}

	/**
	 * load efbo validation ontology.
	 */
	public void loadEFBOValidatorOntology() throws Exception
	{
		this.efboValidationManager = new EFBOOntologyManager();
	    this.efboValidationManager.loadOntology("EFBO-V", EFBO_V_URI);
	    this.efboValidationOntology = efboValidationManager.getLoadedOntology();
	    
	    String loadSuccessMessage = "\nThe EFBO-V Ontology has been Loaded Successfully.";	    						  
	    System.out.println(loadSuccessMessage);
	    
	    JTextArea textArea = new JTextArea(20, 65);
	    textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
	    textArea.append(loadSuccessMessage + "\n" + efboValidationManager.getOntologyMetrics());
	    textArea.setCaretPosition(0);
	    textArea.setMargin(new Insets(10, 10, 10, 10));
	   	textArea.setEditable(false);
        textArea.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), 
        		           "The EFBO-V Loading Status", TitledBorder.CENTER,
        		           TitledBorder.TOP, null, new Color(0, 0, 0)));
        
        JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Success!", 
				  JOptionPane.INFORMATION_MESSAGE);
	    	        
	}
	
	private void saveEFBOValidationOntology()
			throws OWLOntologyCreationException,
			OWLOntologyStorageException, Exception 
	{
  
		JFrame fileSaveFrame = new JFrame();
		String defaultFilePath = EFBOSystemLauncher.EXTRACTED_ONTOLOGY_LOCATION;
		
		//String defaultFilePath = System.getProperty("user.dir") + "/Resources/Ontologies/"; 
		JFileChooser fileChooser = new JFileChooser(new File(defaultFilePath));
		fileChooser.setDialogTitle("Save the EFBO-V Merged Ontology");
		
		int userSelection = fileChooser.showSaveDialog(fileSaveFrame);
		 
		
		if (userSelection == JFileChooser.APPROVE_OPTION) 
		{
			 File fileToSave = fileChooser.getSelectedFile();
	         IRI efboVIRI = IRI.create(fileToSave.toURI());
	         
	         efboValidationOntology.getOWLOntologyManager().saveOntology(efboValidationOntology, efboVIRI);
	        
	         String messageSavedSuccess = "Ontology Saved Successfully!\n"
		    			 				   + "File Location> " 
		    			 				   + fileToSave.getAbsolutePath();
	         
	         this.efboValidationOntologyFile = new File (fileToSave.getAbsolutePath());
         	        		 
	         //efboValidatorManager.printOntologyMetrics();
	         
			 System.out.println(messageSavedSuccess);				
			 JOptionPane.showMessageDialog(fileSaveFrame, messageSavedSuccess, "Success!", 
						  					  JOptionPane.INFORMATION_MESSAGE);
         }
		
		else
		 {
			String notSavedMessage = "You have chosen NOT to save the Ontology.";
			System.out.println(notSavedMessage);
			JOptionPane.showMessageDialog(fileSaveFrame, notSavedMessage,
										  "Ontology NOT Saved", 
					  					  JOptionPane.INFORMATION_MESSAGE);
		 }
	}
	
	private void saveEFBOInferredOntology() throws Exception
	{
		final String inferredEFBOFilePath = System.getProperty("user.dir") 
			  		 				 + "/Resources/Ontologies/"+ EFBOSystemLauncher.PROJECT_NAME + "/efbo-inferred.owl"; 
        
		inferredOntologyFile =  new File(inferredEFBOFilePath);
		IRI efboInferredIRI = IRI.create(inferredOntologyFile.toURI());
		
	    this.setEFBOInferredOntology();
		efboInferredOntology.getOWLOntologyManager().saveOntology(efboInferredOntology, efboInferredIRI);
			        		
	}
	
	private void importEFBOInferredOntology() throws Exception
	{
		File fileToSave = new File (this.efboValidationOntologyFile.getAbsolutePath());
        IRI efboVIRI = IRI.create(fileToSave.toURI());
		
        efboValidationManager.importOWLOntology(efboInferredOntology, inferredOntologyFile.getAbsolutePath());
		efboValidationOntology.getOWLOntologyManager().saveOntology(efboValidationOntology, efboVIRI);
		
	}
	
	public OWLOntology getEFBOInferredOntology()
	{
		return efboInferredOntology;
	}

	
	public void setEFBOInferredOntology() throws Exception
	{
	    this.efboValidationManager.setInferredOntology(this.efboValidationOntologyFile);
	    
		this.efboInferredOntology = this.efboValidationManager.getInferredOntology();		
	}

	/**
	 * @return the firstSystemEvents
	 */
	public Map<OWLNamedIndividual, String> getFirstSystemEvents() 
	{
		return firstSystemEvents;
	}

	private void setFirstSystemEvents() 
	{
		OWLClass eventClass = efboInferredOntology.getOWLOntologyManager().getOWLDataFactory()
                				.getOWLClass(IRI.create(EFBO_V_URI + "#System-1_Event"));

		this.firstSystemEvents = this.getSystemEvents(eventClass);
	}
	
	private void setSecondSystemEvents() 
	{
		OWLClass eventClass = efboInferredOntology.getOWLOntologyManager().getOWLDataFactory()
                				.getOWLClass(IRI.create(EFBO_V_URI + "#System-2_Event"));

	    this.secondSystemEvents = this.getSystemEvents(eventClass);
	}
	
	private Map<OWLNamedIndividual, String> getSystemEvents(OWLClass eventClass)
	{
		
		Map<OWLNamedIndividual, String> systemEvents = new HashMap <OWLNamedIndividual, String>();
		
		Set <OWLNamedIndividual> eventIndividuals = efboValidationManager.getOWLNamedIndividuals(eventClass);
			for(OWLNamedIndividual i : eventIndividuals)
			{
			systemEvents.put(i, efboValidationManager.getLabel(i));
			System.out.println(efboValidationManager.getLabel(i));
			}
		
		return systemEvents;
	}

	/**
	 * @return the secondSystemEvents
	 */
	public Map<OWLNamedIndividual, String> getSecondSystemEvents() 
	{
		return secondSystemEvents;
	}

	
}// End of public class EFBOComparator. 