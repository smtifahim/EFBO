/**
 * 
 */
package ca.queensu.efbo.annotation.extractor;

import java.io.File;
//import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openrdf.model.URI;
import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import ca.queensu.efbo.OntologyManager;


/**
 * @author Fahim
 *
 */

public class EFBOKnowledgeBase
{
	private static final String 
		EFBO_CORE_URI = "http://www.cs.queensu.ca/~imam/ontologies/efbo.owl";
	
	private static final String
		EFBO_KB_URI = "http://www.cs.queensu.ca/~imam/ontologies/efbo-kb.owl";
	
	private static final String 
		kBaseFileLocation = System.getProperty("user.dir") 
			   			  + "/Resources/Extracted-Kbases";  
	
	private OWLOntology efboKBase = null;
	//private File efboKbaseFile = null;
	private OntologyManager efboManager = null;
	private String systemID = null;
	private String systemName = null;

	
	
	public EFBOKnowledgeBase()
				   throws OWLOntologyCreationException, OWLOntologyStorageException 
	{
		this.systemName = "EFBO-KB-Test";
		this.efboManager = new OntologyManager();
        this.efboManager.loadOntology(systemName, EFBO_KB_URI);
        this.efboKBase = efboManager.getLoadedOntology();
   	}
	
	private void saveEFBOKnowledgeBase()
			throws OWLOntologyCreationException,
			OWLOntologyStorageException 
	{
        efboKBase.getOWLOntologyManager()
        		 .saveOntology(efboKBase, new StreamDocumentTarget(System.out));
       
        File defaultSaveLocation = new File(kBaseFileLocation + "/" + systemName + ".owl");
        
        efboKBase.getOWLOntologyManager().saveOntology(efboKBase, IRI.create(defaultSaveLocation.toURI()));
	}
	

	public void processExtractedAnnotations(ArrayList<Annotation> annotations)
			throws OWLOntologyCreationException,
			OWLOntologyStorageException 
	{
		//int subID = 1001;
		//int objID = 2001;
		
		for (Annotation a: annotations)
		{
			this.setEFBOKnowledgeBase(a);
			//subID += 1; objID += 1;
		}
		
		// For the events that have associated Time Point.
		this.setEFBONextEventProperties(annotations);
				
		this.saveEFBOKnowledgeBase();	
	}
	
	//Set an annotation line of type Annotation into an EFBO instances and relations for the KBase.
	private void setEFBOKnowledgeBase(Annotation annotation)
	{
		if (!annotation.getPredicate().equals("hasTimePoint"))
		{
			String subject = annotation.getSubject();
			//String subjectID = "IND_" + subID; //for now temp.
			//remove all the spaces (if any) within the name of the entity.
			String subjectID = subject.replaceAll("\\s+", "");
			
			String object = annotation.getObject();
			//String objectID = "IND_" + objID; //for now temp.
			String objectID = object.replaceAll("\\s+", "");
			
			String predicate = annotation.getPredicate();
			String propertyName = predicate;
	        //String propertyURI = EFBO_CORE_URI;
			
			OWLNamedIndividual owlIndividualSubject = null;
			owlIndividualSubject = efboManager.addOWLNamedIndividual(EFBO_KB_URI, subjectID, subject);
			this.setEFBOAnnotationText(owlIndividualSubject, annotation);
	        
			OWLNamedIndividual owlIndividualObject = null;
			owlIndividualObject = efboManager.addOWLNamedIndividual(EFBO_KB_URI, objectID, object);
			this.setEFBOAnnotationText(owlIndividualObject, annotation);
	        
	        OWLObjectProperty objectProperty = null;
	        objectProperty = efboManager.getOWLObjectProperty(EFBO_CORE_URI, propertyName);
	        
	        efboManager.addOWLObjectPropertyAxiom(owlIndividualSubject, objectProperty, owlIndividualObject);
		
		}//End of if (!annotation.getPredicate().equals("hasTimePoint")).
		
		if (annotation.getPredicate().equals("hasTimePoint"))
			{this.setEFBOEventTimePoint(annotation);}
	
	}
	
	// Set the annotation from the source file that includes the source file name and the 
	// line number where the annotation was asserted.
	private void setEFBOAnnotationText(OWLNamedIndividual individual, Annotation annotation)
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
		OWLLiteral owlLiteral = efboManager.getOWLDataFactory()
										   .getOWLLiteral(annotationText);
		
		OWLAnnotationProperty efboAnnotation = null;		
		efboAnnotation = efboManager.getOWLAnnotationProperty(EFBO_CORE_URI, propertyName);
		efboManager.addOWLAnnotationPropertyAxiom(individual, efboAnnotation, owlLiteral);
	}
	
	
	//Set an annotation that contains hasTimePoint data property into into the EFBO KBase.
	private void setEFBOEventTimePoint(Annotation annotation)
	{
		OWLDataProperty dataProperty = null;
		OWLNamedIndividual owlIndividualSubject = null;
	
		String subject = annotation.getSubject();
		String subjectID = subject.replaceAll("\\s+", ""); //remove all the spaces (if any) within the name of the entity.
		owlIndividualSubject = efboManager.addOWLNamedIndividual(EFBO_KB_URI, subjectID, subject);
		this.setEFBOAnnotationText(owlIndividualSubject, annotation);
		
		int timePoint = Integer.parseInt(annotation.getObject());
		OWLLiteral owlLiteral = efboManager.getOWLDataFactory().getOWLLiteral(timePoint);		
					
		dataProperty = efboManager.getOWLDataProperty(EFBO_CORE_URI, "hasTimePoint");
		efboManager.addOWLDataPropertyAxiom(owlIndividualSubject, dataProperty, owlLiteral);		
		
	}
	
	private void setEFBONextEventProperties(ArrayList<Annotation> annotations)
	{
		OWLNamedIndividual event1 = null; OWLNamedIndividual event2 = null;
		OWLObjectProperty hasNextEvent = null;
		hasNextEvent = efboManager.getOWLObjectProperty(EFBO_CORE_URI, "hasNextEvent");
		OWLDataFactory odf = efboManager.getOWLDataFactory();
		int timePoint=0;
		
		// to hold the mapping between a set of events and their corresponding time points.
		Map<String, Integer> mapEventTime = new HashMap <String, Integer>();
		
		for (Annotation a: annotations)
			if (a.getPredicate().equals("hasTimePoint"))
				mapEventTime.put(a.getSubject(), Integer.parseInt(a.getObject()));	
		
		//System.out.println("List of Events#####");
		for (Map.Entry<String, Integer> e1 : mapEventTime.entrySet())
		{
		    timePoint = e1.getValue();
		    	for (Map.Entry<String, Integer> e2 : mapEventTime.entrySet())
		    		if (e2.getValue() == timePoint+1)
		    		{
		    			IRI event1IRI = IRI.create(EFBO_KB_URI + "#" + e1.getKey());
		    			event1 = odf.getOWLNamedIndividual(event1IRI);
		    			
		    			IRI event2IRI = IRI.create(EFBO_KB_URI + "#" + e2.getKey());
		    			event2 = odf.getOWLNamedIndividual(event2IRI);
		    			
		    			efboManager.addOWLObjectPropertyAxiom(event1, hasNextEvent, event2);
		    			//System.out.println(e1.getKey() + " hasNextEvent " + e2.getKey());
		    		}
		}// End of for (Map.Entry<String, Integer> e1 : mapEventTime.entrySet())
		        
   } // End of setEFBONextEventProperties(ArrayList<Annotation> annotations).
	


// To fix the file path formats according to the OS.
public boolean isCurrentOS (String osName)
{
	String runningOSName = System.getProperty("os.name").toString();
	if (runningOSName.contains(osName))
		return true;
	return false;
}
	
 public OWLOntology getEFBOKnowledgebase()
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
 public OntologyManager getEFBOManager()
 { 
	return efboManager; 
 }

}
