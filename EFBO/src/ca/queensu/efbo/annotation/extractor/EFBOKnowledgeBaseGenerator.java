/**
 * 
 */
package ca.queensu.efbo.annotation.extractor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLIndividualAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import ca.queensu.efbo.OntologyManager;


/**
 * @author Fahim
 *
 */

public class EFBOKnowledgeBaseGenerator
{
	private static final String BASE_URL = "http://www.cs.queensu.ca/~imam/ontologies/efbo.owl" ;
	private ArrayList<Annotation> annotations;
	private static OWLOntology efboOntology;
	static OWLDataFactory owlDataFactory;
	private OWLOntology knowledgeBase;
	private OntologyManager efboManager;
	
	public EFBOKnowledgeBaseGenerator() 
			throws OWLOntologyCreationException,
				   OWLOntologyStorageException 
	{
        efboManager = new OntologyManager();
        efboManager.loadOntology("EFBO", BASE_URL);
        efboOntology = efboManager.getLoadedOntology();
      
        String kBaseURI = "http://efbo.owl/kbase/kb1";
        String test1IndividualID = "tapLoginButton";
        String test1IndividualLabel = "Tap Login Button";
        
        String test2IndividualID = "welcomeUser";
        String test2IndividualLabel = "Welcome The User";
        
        OWLNamedIndividual ind1 = efboManager.addOWLNamedIndividual(kBaseURI, test1IndividualID, test1IndividualLabel);
        OWLNamedIndividual ind2 = efboManager.addOWLNamedIndividual(kBaseURI, test2IndividualID, test2IndividualLabel);
        
        String test3IndividualID = "doWhatever";
        String test3IndividualLabel = "Do Whatever";
        OWLNamedIndividual ind3 = efboManager.addOWLNamedIndividual(kBaseURI, test3IndividualID, test3IndividualLabel);
        
        
        String propertyName = "hasNextEvent";
        String propertyURI = BASE_URL;
        OWLObjectProperty objectProperty = efboManager.getOWLObjectProperty(propertyURI, propertyName);
        
        efboManager.setOWLPropertyAxiom(ind1, objectProperty, ind2);
        efboManager.setOWLPropertyAxiom(ind2, objectProperty, ind3);
        
        efboOntology.getOWLOntologyManager().saveOntology(efboOntology, new StreamDocumentTarget(System.out));
        
        //this.printOntologyInformation();		
	}
	
	private void printOntologyInformation() throws OWLOntologyCreationException
	{
        efboManager.printOntologyMetrics();
        efboManager.printAllClasses();
        efboManager.printAllObjectProperties();
        efboManager.printAllIndividuals();
        efboManager.printAllAxioms();
   }

	
	private void processKnowledgeBase()
	{
		
	}
	
	private void saveKnowledgeBase()
	{
		
	}

	public ArrayList<Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(ArrayList<Annotation> annotations) {
		this.annotations = annotations;
	}

	public OWLOntology getEfbo() {
		return efboOntology;
	}

	public void setEfbo(OWLOntology efbo) {
		this.efboOntology = efbo;
	}

	public OWLOntology getKnowledgeBase() {
		return knowledgeBase;
	}

	public void setKnowledgeBase(OWLOntology knowledgeBase) {
		this.knowledgeBase = knowledgeBase;
	}
		
}
