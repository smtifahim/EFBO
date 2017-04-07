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

public class KnowledgeBaseGenerator
{
	private static final String BASE_URL = "http://www.cs.queensu.ca/~imam/ontologies/efbo.owl" ;
	private ArrayList<Annotation> annotations;
	private static OWLOntology efbo;
	static OWLDataFactory owlDataFactory;
	private OWLOntology knowledgeBase;
	private OntologyManager ontologyManager;
	
	public KnowledgeBaseGenerator() throws OWLOntologyCreationException, OWLOntologyStorageException 
	{
        ontologyManager = new OntologyManager();
        ontologyManager.loadOntology("EFBO", BASE_URL);
        efbo = ontologyManager.getLoadedOntology();
        ontologyManager.addOWLIndividual("tapLoginButton", "showWelcomeMessage");
       // ontologyManager.createNewOntology(BASE_URL);
        
        efbo.getOWLOntologyManager().saveOntology(efbo, new StreamDocumentTarget(System.out));
        
       // this.showOntologyInformation();		
	}
	
	private void showOntologyInformation() throws OWLOntologyCreationException
	{
        System.out.println("Ontology Loaded...");
        System.out.println("Ontology Name: " + ontologyManager.getOntologyName());
        System.out.println("Document IRI: " + ontologyManager.getOntologyIRI());
        System.out.println("Ontology ID: " + efbo.getOntologyID());
        System.out.println("Format: " + efbo.getOWLOntologyManager().getOntologyFormat(efbo));
        
      Set<OWLAxiom> axioms = efbo.getAxioms(); 
      printAxioms(axioms); 
        
        Set<OWLClass> owlClasses = ontologyManager.getAllclasses();        	
        printOWLClasses(owlClasses); 
         
        Set<OWLObjectProperty> owlObjectProperties = efbo.getObjectPropertiesInSignature();
        printOWLObjectProperties(owlObjectProperties);
        
        Set<OWLNamedIndividual> owlIndividuals = ontologyManager.getAllIndividuals();
        printOWLIndividuals(owlIndividuals);
        
        printNumberOfLogicalAxioms(efbo.getLogicalAxioms());       
   }

	private void printOWLObjectProperties(Set<OWLObjectProperty> owlObjectProperties)
	{
		System.out.println("-----------------------------------");   
		System.out.println("List of OWL Object Properites (" + owlObjectProperties.size() + ")");
		System.out.println("-----------------------------------"); 
		 
		for (OWLObjectProperty p : owlObjectProperties) 
		  { 
		   System.out.println(ontologyManager.getLabel(p)); 
		  } 
	}
	
	private static void printNumberOfLogicalAxioms(Set<OWLLogicalAxiom> logicalAxioms) 
	{ 
		System.out.println("-----------------------------------");   
		System.out.println("Number of Logical Axioms (" + logicalAxioms.size() + ")");
		System.out.println("-----------------------------------"); 
	} 
		 
	private void printOWLClasses(Set<OWLClass> classes) 
	{ 
		System.out.println("-----------------------------------");   
		System.out.println("List of OWL Classes (" + classes.size() + ")");
		System.out.println("-----------------------------------"); 
		  for (OWLClass c : classes) 
		  { 
		   System.out.println(ontologyManager.getLabel(c));		   	
		  }
	}
	private void printOWLIndividuals(Set<OWLNamedIndividual> individuals)
	{
		System.out.println("-----------------------------------");   
		System.out.println("List of OWL Individuals (" + individuals.size() + ")");
		System.out.println("-----------------------------------"); 
		  for (OWLNamedIndividual i : individuals) 
		  { 
		   System.out.println(i);		   	
		  }
		
	}
		 
   private static void printAxioms(Set<OWLAxiom> axioms) 
	{ 
	   	  Set<OWLAxiom> axIndividual = new HashSet<OWLAxiom>(); 
		  Set<OWLAxiom> axDataProperty = new HashSet<OWLAxiom>(); 
		  Set<OWLAxiom> axObjectProperty = new HashSet<OWLAxiom>(); 
		  Set<OWLAxiom> axClass = new HashSet<OWLAxiom>(); 
		  Set<OWLAxiom> axOther = new HashSet<OWLAxiom>(); 
		 
		  for (OWLAxiom axiom : axioms) 
		  { 
		   axiom.getSignature(); 
		   if ((axiom instanceof OWLClassAxiom))
		   		{axClass.add(axiom);} 
		   	else if (axiom instanceof OWLDataPropertyAxiom)
		   		{axDataProperty.add(axiom);} 
		   		else if (axiom instanceof OWLObjectPropertyAxiom)
		   			{axDataProperty.add(axiom);} 
		   			else if (axiom instanceof OWLIndividualAxiom) 
		   				{axIndividual.add(axiom);}
		   				else axOther.add(axiom); 
		  } 
		 
		  System.out.println("ALL AXIOMS (" + axioms.size() + ")"); 
		  
		  for (OWLAxiom ax : axIndividual)
		  { 
		   String line; 
		   line = ax.toString() + " TYPE: Individual"; 
		   System.out.println(line); 
		  } 
		  
		  for (OWLAxiom ax : axDataProperty)
		  { 
		   String line; 
		   line = ax.toString() + " TYPE: DataProperty"; 
		   System.out.println(line); 
		  } 
		  
		  for (OWLAxiom ax : axObjectProperty) 
		  { 
		   String line; 
		   line = ax.toString() + " TYPE: ObjectProperty"; 
		   System.out.println(line); 
		  } 
		  
		  for (OWLAxiom ax : axClass)
		  { 
		   String line; 
		   line = ax.toString() + " TYPE: Class"; 
		   System.out.println(line); 
		  }
		  
		  for (OWLAxiom ax : axOther)
		  { 
		   String line; 
		   line = ax.toString() + " TYPE: Other"; 
		   System.out.println(line); 
		  } 
		  
		  System.out.println("-----------------------------------"); 
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
		return efbo;
	}

	public void setEfbo(OWLOntology efbo) {
		this.efbo = efbo;
	}

	public OWLOntology getKnowledgeBase() {
		return knowledgeBase;
	}

	public void setKnowledgeBase(OWLOntology knowledgeBase) {
		this.knowledgeBase = knowledgeBase;
	}
		
}
