/**
 * 
 */
package ca.queensu.efbo.annotation.extractor;

//import java.io.File;
import java.util.ArrayList;
import org.semanticweb.owlapi.io.StreamDocumentTarget;
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

public class EFBOKnowledgeBaseGenerator
{
	private static final String 
		EFBO_CORE_URI = "http://www.cs.queensu.ca/~imam/ontologies/efbo.owl";
	
	private static final String
		EFBO_KB_URI = "http://www.cs.queensu.ca/~imam/ontologies/efbo-kb.owl";
	
	private OWLOntology efboKBase = null;
	//private File efboKbaseFile = null;
	private OntologyManager efboManager = null;
	private String systemID = null;
	private String systemName = null;
	
	public EFBOKnowledgeBaseGenerator()
				   throws OWLOntologyCreationException, OWLOntologyStorageException 
	{
		this.efboManager = new OntologyManager();
        this.efboManager.loadOntology("EFBO-KB", EFBO_KB_URI);
        this.efboKBase = efboManager.getLoadedOntology();
   	}
	
	private void saveEFBOKnowledgeBase()
			throws OWLOntologyCreationException,
			OWLOntologyStorageException 
	{
        efboKBase.getOWLOntologyManager().saveOntology(efboKBase, 
        						  new StreamDocumentTarget(System.out));
	}
	

	public void processExtractedAnnotations(ArrayList<Annotation> annotations)
			throws OWLOntologyCreationException,
			OWLOntologyStorageException 
	{
		//int subID = 1001;
		//int objID = 2001;
		
		for (Annotation a: annotations)
		{
			this.setEFBOAnnotation(a);
			//subID += 1; objID += 1;
		}
		this.saveEFBOKnowledgeBase();	
	}
	
	//Set an annotation for the EFBO KBase.
	private void setEFBOAnnotation(Annotation annotation)
	{
		// System.out.println(a.getFileLocation());
		// System.out.println(a.getLineNumber());
		String subject = annotation.getSubject();
		//String subjectID = "IND_" + subID; //for now temp.
		String subjectID = subject.replaceAll("\\s+", ""); 
		
		String object = annotation.getObject();
		//String objectID = "IND_" + objID; //for now temp.
		String objectID = object.replaceAll("\\s+", "");
		
		String predicate = annotation.getPredicate();
		String propertyName = predicate;
        String propertyURI = EFBO_CORE_URI;
		
		OWLNamedIndividual owlIndividualSubject = null;
		owlIndividualSubject = efboManager.addOWLNamedIndividual(EFBO_KB_URI, subjectID, subject);
        
		OWLNamedIndividual owlIndividualObject = null;
		owlIndividualObject = efboManager.addOWLNamedIndividual(EFBO_KB_URI, objectID, object);
        
        OWLObjectProperty objectProperty = null;
        objectProperty = efboManager.getOWLObjectProperty(propertyURI, propertyName);
        
        efboManager.setOWLPropertyAxiom(owlIndividualSubject, objectProperty, owlIndividualObject);
        
	}
	
	public void printOntologyInformation() 
			throws OWLOntologyCreationException
	{
        efboManager.printOntologyMetrics();
        efboManager.printAllClasses();
        efboManager.printAllObjectProperties();
        efboManager.printAllIndividuals();
        efboManager.printAllAxioms();
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

}
