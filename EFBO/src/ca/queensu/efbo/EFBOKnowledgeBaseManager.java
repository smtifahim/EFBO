/**
 * 
 */
package ca.queensu.efbo;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/**
 * @author Fahim
 *
 */

public class EFBOKnowledgeBaseManager
{
	private static final String 
		EFBO_CORE_URI = "http://www.cs.queensu.ca/~imam/ontologies/efbo.owl";
	
	private String
		EFBO_KBASE_URI = "http://www.cs.queensu.ca/~imam/ontologies/efbo-kb/";
	
	private static final String 
		KBASE_File_Location = System.getProperty("user.dir") 
			   			    + "/Resources/Extracted-Kbases";  
	
	private OWLOntology efboKBase = null;
	private EFBOOntologyManager efboKBaseManager = null;
	private String systemID = null;
	private String systemName = null;
	private String localKBLocation = null;
	private OWLNamedIndividual systemIDInstance = null;

	//Default constructor.
//	public EFBOKnowledgeBase(String systemID, String systemName)
//				   throws OWLOntologyCreationException, OWLOntologyStorageException 
//	{
//		this.systemID = systemID;
//		this.systemName = systemName;
//		this.efboKBaseManager = new OntologyManager();
//        this.efboKBaseManager.loadOntology(systemID, EFBO_KB_URI);
//        this.efboKBase = efboKBaseManager.getLoadedOntology();
//        this.setSystemEntity();
//        
//   }
	
	public EFBOKnowledgeBaseManager(String systemID, String systemName)
				   throws OWLOntologyCreationException, OWLOntologyStorageException, Exception 
	{
		this.systemID = systemID;
		this.systemName = systemName;
		this.efboKBaseManager = new EFBOOntologyManager();
		this.EFBO_KBASE_URI = EFBO_KBASE_URI + systemID + ".owl";
        this.efboKBaseManager.createNewOntology(EFBO_KBASE_URI);
        this.efboKBaseManager.loadOntology(systemID,efboKBaseManager.getNewOntology());
        this.efboKBase = efboKBaseManager.getLoadedOntology();
        this.efboKBaseManager.importOWLOntology(EFBO_CORE_URI);
        this.setSystemEntity();        
   	}
	
	
	//Set the source system information from which the annotations 
	//are extracted.
	private void setSystemEntity()
	{
		OWLNamedIndividual owlNamedIndividual = null;
		owlNamedIndividual = efboKBaseManager.addOWLNamedIndividual(EFBO_KBASE_URI, systemID, systemName);
		
		IRI classIRI = IRI.create(EFBO_CORE_URI + "#" + "System");		
		OWLClass systemClass = efboKBaseManager.getOWLDataFactory().getOWLClass(classIRI);
		
		efboKBaseManager.assertOWLNamedIndividual(owlNamedIndividual, systemClass);
		this.systemIDInstance = owlNamedIndividual;
		
	}
	
	private void saveEFBOKnowledgeBase()
			throws OWLOntologyCreationException,
			OWLOntologyStorageException 
	{
		// efboKBase.getOWLOntologyManager().saveOntology(efboKBase, new StreamDocumentTarget(System.out));
     
        String fileLocation = KBASE_File_Location + "/" + systemName
				   		    + "/" + systemID + ".owl";
        File defaultSaveLocation = new File(fileLocation);
        IRI kBaseIRI = IRI.create(defaultSaveLocation.toURI());
        
        efboKBase.getOWLOntologyManager().saveOntology(efboKBase, kBaseIRI);
        
        String savedSuccessMessage = "\nThe EFBO Knowledgebase for " + this.getSystemName() + " has been Saved."
        		      			   + "\nLocation: " + fileLocation + "  ";
        		      			  
            
        
        this.localKBLocation = fileLocation;        
        System.out.println(savedSuccessMessage);
        this.efboKBaseManager.printOntologyMetrics();
        
        JTextArea textArea = new JTextArea(18, 70);
        textArea.setText(savedSuccessMessage + "\n" + this.efboKBaseManager.getOntologyMetrics());
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
           
        textArea.setEditable(false);
        textArea.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), 
        		           "EFBO Knowledge Base for " + this.getSystemName() , TitledBorder.CENTER,
        		           TitledBorder.TOP, null, new Color(0, 0, 0)));
        
        JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Success!",
									  JOptionPane.INFORMATION_MESSAGE);
        
	}
	
 public void processExtractedAnnotations(ArrayList<EFBOAnnotation> annotations)
			throws OWLOntologyCreationException, OWLOntologyStorageException 
	{
		for (EFBOAnnotation exAnnotation: annotations)
		{
			this.setEFBOKnowledgeBase(exAnnotation);
		}
		
		// For the events that have associated Time Point.
	 	this.setEFBONextEventProperties(annotations);	
		this.saveEFBOKnowledgeBase();
	}
	
	//Set an annotation line of type Annotation into an EFBO instances and relations for the KBase.
	private void setEFBOKnowledgeBase(EFBOAnnotation annotation)
	{
		String propertyName = annotation.getPredicate();
		
		if (!propertyName.equals("hasTimePoint"))
		{
	      		String whereDeclared = getWhereDeclared(annotation);
				
				String subject = annotation.getSubject();
				
				//remove all the spaces (if any) within the name of the entity.
				String subjectID = subject.replaceAll("\\s+", "");
					   subjectID = systemName + "." + whereDeclared + "." + subjectID;
				
				String object = annotation.getObject();
				String objectID = object.replaceAll("\\s+", "");
					   objectID = systemName + "." + whereDeclared + "." + objectID;
				
				OWLNamedIndividual owlIndividualSubject = null;
				owlIndividualSubject = efboKBaseManager.addOWLNamedIndividual(EFBO_KBASE_URI, subjectID, subject);
				this.setEFBOAnnotationText(owlIndividualSubject, annotation);
				this.setIndividualSystemEntity(owlIndividualSubject);
		        
				OWLNamedIndividual owlIndividualObject = null;
				owlIndividualObject = efboKBaseManager.addOWLNamedIndividual(EFBO_KBASE_URI, objectID, object);
				this.setEFBOAnnotationText(owlIndividualObject, annotation);
				this.setIndividualSystemEntity(owlIndividualObject);
		        
		        OWLObjectProperty objectProperty = null;
		        objectProperty = efboKBaseManager.getOWLObjectProperty(EFBO_CORE_URI, propertyName);
		        
		        efboKBaseManager.addOWLObjectPropertyAxiom(owlIndividualSubject, objectProperty, owlIndividualObject);
			
		} //End of if (!propertyName.equals("hasTimePoint")).
		
		if (propertyName.equals("hasTimePoint"))
			{this.setEFBOEventTimePoint(annotation);}
			
	}
	
	// Set the annotation from the source file that includes the source file name and the 
	// line number where the annotation was asserted.
	private void setEFBOAnnotationText(OWLNamedIndividual individual, EFBOAnnotation annotation)
	{
		String propertyName = "annotationText";
		String exAnnotation = "Annotation: " + annotation.getAnnotatedText();
		String filePath = annotation.getFileLocation();
		
		if (isCurrentOS("Windows"))
			filePath = "\nLocation: file://" + filePath.replace("\\", "/"); 
		else
			filePath = "\nLocation: file://" + filePath;
		
		String lineNumber =   "\nLine Number: " + annotation.getLineNumber();
		String annotationText = exAnnotation + filePath + lineNumber;
		OWLLiteral owlLiteral = efboKBaseManager.getOWLDataFactory()
										   .getOWLLiteral(annotationText);
		
		OWLAnnotationProperty efboAnnotation = null;		
		efboAnnotation = efboKBaseManager.getOWLAnnotationProperty(EFBO_CORE_URI, propertyName);
		efboKBaseManager.addOWLAnnotationPropertyAxiom(individual, efboAnnotation, owlLiteral);
	}
	
	//To associate each of the individuals to the system instance.
	private void setIndividualSystemEntity(OWLNamedIndividual individual)
	{
		OWLObjectProperty objectProperty = null;
        objectProperty = efboKBaseManager.getOWLObjectProperty(EFBO_CORE_URI, "isSystemEntityOf");
        
        efboKBaseManager.addOWLObjectPropertyAxiom(individual, objectProperty, this.systemIDInstance);
	}
	
	//Set an annotation that contains hasTimePoint data property into into the EFBO KBase.
	private void setEFBOEventTimePoint(EFBOAnnotation annotation)
	{
		OWLDataProperty dataProperty = null;
		OWLNamedIndividual owlIndividualSubject = null;
	
		String whereDeclared = Paths.get(annotation.getFileLocation())
				  					.getFileName().toString();
		
		String subject = annotation.getSubject();
		//remove all the spaces (if any) within the name of the entity.
		String subjectID = subject.replaceAll("\\s+", "");
		       subjectID = systemName + "." + whereDeclared + "." + subjectID;
		
		owlIndividualSubject = efboKBaseManager.addOWLNamedIndividual(EFBO_KBASE_URI, subjectID, subject);
		this.setEFBOAnnotationText(owlIndividualSubject, annotation);
		this.setIndividualSystemEntity(owlIndividualSubject);
		
		int timePoint = Integer.parseInt(annotation.getObject());
		OWLLiteral owlLiteral = efboKBaseManager.getOWLDataFactory().getOWLLiteral(timePoint);		
					
		dataProperty = efboKBaseManager.getOWLDataProperty(EFBO_CORE_URI, "hasTimePoint");
		efboKBaseManager.addOWLDataPropertyAxiom(owlIndividualSubject, dataProperty, owlLiteral);
				
	}
	
	private void setEFBONextEventProperties(ArrayList<EFBOAnnotation> annotations)
	{
		OWLNamedIndividual event1 = null; OWLNamedIndividual event2 = null;
		OWLObjectProperty hasNextEvent = null;
		hasNextEvent = efboKBaseManager.getOWLObjectProperty(EFBO_CORE_URI, "hasNextEvent");
		OWLDataFactory odf = efboKBaseManager.getOWLDataFactory();
		int timePoint=0;
		
		 
		// to hold the mapping between a set of events and their corresponding time points.
		Map<String, Integer> mapEventTime = new HashMap <String, Integer>();
		
		for (EFBOAnnotation a: annotations)
			{
			if (a.getPredicate().equals("hasTimePoint"))
				mapEventTime.put(a.getSubject(), Integer.parseInt(a.getObject()));	
			String individualURI = EFBO_KBASE_URI + "#" + systemName + ".";	
			       individualURI = individualURI + getWhereDeclared(a) + "."; 
			for (Map.Entry<String, Integer> e1 : mapEventTime.entrySet())
				{
				    timePoint = e1.getValue();
				    	for (Map.Entry<String, Integer> e2 : mapEventTime.entrySet())
				    		if (e2.getValue() == timePoint+1)
				    		{
				    			IRI event1IRI = IRI.create(individualURI + e1.getKey());
				    			event1 = odf.getOWLNamedIndividual(event1IRI);
				    			
				    			IRI event2IRI = IRI.create(individualURI + e2.getKey());
				    			event2 = odf.getOWLNamedIndividual(event2IRI);
				    			
				    			efboKBaseManager.addOWLObjectPropertyAxiom(event1, hasNextEvent, event2);
				    			//System.out.println(e1.getKey() + " hasNextEvent " + e2.getKey());
				    		}
				}
			}// End of for (Map.Entry<String, Integer> e1 : mapEventTime.entrySet())   
   } // End of setEFBONextEventProperties(ArrayList<Annotation> annotations).
	

// returns the file name from a directory where the annotation was declared.
private	String getWhereDeclared (EFBOAnnotation annotation)
{
	String whereDeclared = Paths.get(annotation.getFileLocation())
				.getFileName().toString();
	
	return whereDeclared;
}


// To fix the file path formats according to the OS.
public boolean isCurrentOS (String osName)
{
	String runningOSName = System.getProperty("os.name").toString();
	if (runningOSName.contains(osName))
		return true;
	return false;
}

public String getLocalKBLocation()
{
	return localKBLocation;
}

public OWLOntology getEFBOKnowledgeBase()
{
	return efboKBase;
}


 public String getSystemID()
	{
		return systemID;
	}

 public void setSystemID(String systemID)
	{
		this.systemID = systemID;
	}

 public String getSystemName() 
	{
		return systemName;
	}

 public void setSystemName(String systemName)
	{
		this.systemName = systemName;
	}
 public EFBOOntologyManager getEFBOManager()
 { 
	return efboKBaseManager; 
 }
 
 public OWLNamedIndividual getSystemIDInstance()
 {
	 return systemIDInstance;
 }

}//End of Class.
