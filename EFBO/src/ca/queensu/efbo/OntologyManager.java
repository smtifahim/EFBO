/**
 * 
 */
package ca.queensu.efbo;


import java.io.File; 
import java.util.HashMap; 
import java.util.HashSet; 
import java.util.Map; 
import java.util.Set; 
 
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom; 
import org.semanticweb.owlapi.model.IRI; 
import org.semanticweb.owlapi.model.OWLAnnotation; 
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom; 
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression; 
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividualAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException; 
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary; 
 
public class OntologyManager 
{ 
 private String ontologyURI = null; 
 private String ontologyName = null; 
 private File ontologyFile = null; 
 private OWLDataFactory factory = null; 
 private OWLOntology ontology = null;
 private OWLOntologyManager manager = null; 
 private Set<String> synonymsProperties; 
 private Set<String> owlObjectProperties; 
  
 private Map<String, OWLClass> hashToRetrieveClass = new HashMap<String, OWLClass>();
 private  Set<OWLNamedIndividual> owlIndividuals = new HashSet<OWLNamedIndividual>(); 
 
 public OntologyManager()
 {
	 this.manager = OWLManager.createOWLOntologyManager();
	 this.factory = manager.getOWLDataFactory();
 }
 
 public OntologyManager(OWLOntologyManager manager, OWLDataFactory factory) 
 { 
  this.manager = manager; 
  this.factory = factory; 
 } 
 
 public OntologyManager(String ontologyName, File ontologyFile) throws OWLOntologyCreationException 
 { 
  this.ontologyFile = ontologyFile; 
  this.manager = OWLManager.createOWLOntologyManager(); 
  this.factory = manager.getOWLDataFactory(); 
  this.ontologyName = ontologyName; 
  this.ontology = manager.loadOntologyFromOntologyDocument(ontologyFile); 
  this.ontologyURI = ontology.getOntologyID().getOntologyIRI().toString(); 
 } 
 
 public void loadOntology(String ontologyName, File ontologyFile) throws OWLOntologyCreationException 
 { 
  this.ontologyName = ontologyName; 
  this.ontologyFile = ontologyFile; 
  this.ontology = manager.loadOntologyFromOntologyDocument(ontologyFile); 
  this.ontologyURI = ontology.getOntologyID().getOntologyIRI().toString(); 
 } 
 
 public void loadOntology(String ontologyName, 
		 				  String ontologyURI) 
		 			      throws OWLOntologyCreationException 
 { 
  this.ontologyName = ontologyName; 
  this.ontologyURI = ontologyURI;
  IRI documentIRI = IRI.create(ontologyURI);
  this.ontology = manager.loadOntology(documentIRI); 
 } 
 
 public void preProcessing() 
 { 
  for (OWLClass cls : ontology.getClassesInSignature()) 
  { 
   hashToRetrieveClass.put(getLabel(cls).trim().toLowerCase(), cls); 
  } 
 } 
 
//To add an OWL individual from an individual's id, label, and URI strings to the ontology.
//The method also returns the individual as OWLNamedIndividual object to its caller.  
public OWLNamedIndividual addOWLNamedIndividual(String individualURI, 
								  String individualID,
								  String individualLabel)
 {
	IRI individualIRI = IRI.create(individualURI + "#" + individualID);
	OWLNamedIndividual namedIndividual = factory.getOWLNamedIndividual(individualIRI);
	
	OWLAnnotationProperty rdfsLabelProperty;
	rdfsLabelProperty = factory.getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_LABEL.getIRI());
	
	OWLLiteral owlLiteral = factory.getOWLLiteral(individualLabel);	
	OWLAnnotation label = factory.getOWLAnnotation(rdfsLabelProperty, owlLiteral);
	
	OWLAxiom axiom = factory.getOWLAnnotationAssertionAxiom(namedIndividual.getIRI(), label);
	AddAxiom addAxiom = new AddAxiom(ontology, axiom);
	manager.applyChange(addAxiom);
	
	owlIndividuals.add(namedIndividual);
	
	return namedIndividual;
 }

 
//Returns an object property from an existing ontology.
public OWLObjectProperty getOWLObjectProperty (String propertyURI, String propertyName)
 {
	 IRI propertyIRI = IRI.create(propertyURI + "#" + propertyName);
	 return factory.getOWLObjectProperty(propertyIRI);
 }
 
 //Assert object property axiom between two OWL named individuals.
public void setOWLPropertyAxiom(OWLNamedIndividual subjectIndividual, 
		 						OWLObjectProperty objectProperty, 
		 						OWLNamedIndividual objectIndividual)
 {
	 OWLObjectPropertyAssertionAxiom axiom = null;
	 axiom = factory.getOWLObjectPropertyAssertionAxiom
			(objectProperty, subjectIndividual, objectIndividual);
	 AddAxiom addAxiom = new AddAxiom(ontology, axiom);
	 manager.applyChange(addAxiom);
 }

public OWLOntology getLoadedOntology()
 {
	 return ontology;
 }
 
public Set<OWLAnnotationAssertionAxiom> getAllAnnotationAxiom(OWLClass cls) 
 { 
  Set<OWLAnnotationAssertionAxiom> axioms = new HashSet<OWLAnnotationAssertionAxiom>(); 
  for (OWLAnnotationProperty annotation : cls.getAnnotationPropertiesInSignature()) 
  { 
   axioms.add(factory.getOWLAnnotationAssertionAxiom(cls.getIRI(), (OWLAnnotation) annotation)); 
  } 
  return axioms; 
 } 
 
 public Set<OWLClass> getRootClasses() 
 { 
  Set<OWLClass> listOfTopClasses = new HashSet<OWLClass>(); 
  for (OWLClass cls : ontology.getClassesInSignature()) 
  { 
   if (ontology.getSubClassAxiomsForSubClass(cls).size() == 0 
     && ontology.getEquivalentClassesAxioms(cls).size() == 0) listOfTopClasses.add(cls); 
  } 
  return listOfTopClasses; 
 } 
 
 public OWLClass getTopClass() 
 { 
  return factory.getOWLThing(); 
 } 
 
public Set<OWLClass> getChildClass(OWLClass cls) 
 { 
  Set<OWLClass> listOfClasses = new HashSet<OWLClass>(); 
  for (OWLSubClassOfAxiom axiom : ontology.getSubClassAxiomsForSuperClass(cls)) 
  { 
   OWLClassExpression expression = axiom.getSubClass(); 
   if (!expression.isAnonymous()) 
   { 
    OWLClass asOWLClass = expression.asOWLClass(); 
    listOfClasses.add(asOWLClass); 
   } 
  } 
  return listOfClasses; 
 } 
 
 // TODO: what if the ontology terms have multiple IDs? 
 public String getId(OWLClass entity) 
 { 
  for (OWLAnnotationProperty owlObjectProperty : ontology.getAnnotationPropertiesInSignature()) 
  { 
   if (ifExistsAnnotation(owlObjectProperty.toString(), "id")) 
   { 
    for (String annotation : getAnnotation(entity, owlObjectProperty.getIRI().toString())) 
    { 
     return annotation; 
    } 
   } 
  } 
  return ""; 
 } 
 
 private boolean ifExistsAnnotation(String propertyUrl, String keyword) 
 { 
  String pattern = "[\\W_]*" + keyword + "[\\W_]*"; 
  // Use # as the separator 
  String[] urlFragments = propertyUrl.split("[#/]"); 
  if (urlFragments.length > 1) 
  { 
   String label = urlFragments[urlFragments.length - 1].replaceAll("[\\W]", "_"); 
   for (String token : label.split("_")) 
   { 
    if (token.matches(pattern)) return true; 
   } 
  } 
  return false; 
 } 
 
public String getLabel(OWLEntity entity) 
 { 
  for (String annotation : getAnnotation(entity, OWLRDFVocabulary.RDFS_LABEL.toString())) 
  { 
   return annotation; 
  } 
  return extractOWLClassId(entity); 
 } 
 
 private Set<String> getAnnotation(OWLEntity entity, String property) 
 { 
  Set<String> annotations = new HashSet<String>(); 
  try 
  { 
   OWLAnnotationProperty owlAnnotationProperty = factory.getOWLAnnotationProperty(IRI.create(property)); 
   
   for(OWLAnnotation a : EntitySearcher.getAnnotations(entity, ontology, owlAnnotationProperty)) 
   	{
	    OWLAnnotationValue value = a.getValue();
	    if(value instanceof OWLLiteral) 
	    {
	       annotations.add(((OWLLiteral) value).getLiteral());	    	   
	    }
   	}
  } 
  
  catch (Exception e) 
  { 
   throw new RuntimeException("Failed to get label for OWL Entity " + entity); 
  } 
  return annotations; 
 } 
 
public String extractOWLClassId(OWLEntity cls) 
 { 
  StringBuilder stringBuilder = new StringBuilder(); 
  String clsIri = cls.getIRI().toString(); 
  // Case where id is separated by # 
  String[] split = null; 
  if (clsIri.contains("#")) 
  { 
   split = clsIri.split("#"); 
  } 
  else 
  { 
   split = clsIri.split("/"); 
  } 
  stringBuilder.append(split[split.length - 1]); 
  return stringBuilder.toString(); 
 } 
 
 public String getOntologyIRI() 
 { 
  return ontologyURI; 
 } 
 
 public String getOntologyName() 
 { 
  return ontologyName; 
 } 
 
 public String getOntologyFilePath() 
 { 
  return ontologyFile.getAbsolutePath(); 
 } 
 
 public Map<String, OWLClass> getHashToRetrieveClass() 
 { 
  return hashToRetrieveClass; 
 } 
 
 public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSuperClass(OWLClass cls) 
 { 
  return ontology.getSubClassAxiomsForSuperClass(cls); 
 } 
 
 public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSubClass(OWLClass cls) 
 { 
  return ontology.getSubClassAxiomsForSubClass(cls); 
 } 
 
 public OWLClass createClass(String iri, Set<OWLClass> rootClasses) 
 { 
  OWLClass owlClass = factory.getOWLClass(IRI.create(iri)); 
  for (OWLClass rootClass : rootClasses) 
  { 
   if (rootClass != owlClass) addClass(rootClass, owlClass); 
  } 
  return owlClass; 
 } 
 
 public void addClass(OWLClass cls, OWLClass parentClass) 
 { 
  if (parentClass == null) parentClass = factory.getOWLThing(); 
  manager.applyChange(new AddAxiom(ontology, factory.getOWLSubClassOfAxiom(cls, parentClass))); 
 } 
 
public void printOntologyMetrics()
 {
	System.out.println("-----------------------------------"); 
	System.out.println("Loaded Ontology Metrics");
	System.out.println("-----------------------------------"); 
    System.out.println("Ontology Name: " + this.getOntologyName());
    System.out.println("Ontology IRI: " + this.getOntologyIRI());
    System.out.println("Ontology ID: " + ontology.getOntologyID());
    System.out.println("Format: " + manager.getOntologyFormat(ontology));
    
    System.out.println("Number of Named Classes: "
    				   + ontology.getClassesInSignature().size());
    System.out.println("Number of Object Properties: " 
    				   + ontology.getObjectPropertiesInSignature().size());
    System.out.println("Number of Named Individuals: " 
    				   + ontology.getIndividualsInSignature().size());
    System.out.println("Number of Axioms: " 
    				   +  ontology.getLogicalAxioms().size());
    System.out.println("-----------------------------------"); 
 }

public void printAllObjectProperties()
	{
	    Set<OWLObjectProperty> owlObjectProperties = null; 
	    owlObjectProperties = ontology.getObjectPropertiesInSignature();
	    
	    System.out.println("-----------------------------------");   
		System.out.println("List of All OWL Object Properites (" 
						   + owlObjectProperties.size() + ")");
		System.out.println("-----------------------------------"); 
		 
		for (OWLObjectProperty p : owlObjectProperties)  
		  System.out.println(this.getLabel(p)); 

	}
	
public void printAllClasses() 
	{ 
		Set<OWLClass> owlClasses = ontology.getClassesInSignature();        	
	    System.out.println("-----------------------------------");   
		System.out.println("List of All OWL Classes (" 
						   + owlClasses.size() + ")");
		System.out.println("-----------------------------------"); 
		
		for (OWLClass c : owlClasses)
			System.out.println(this.getLabel(c));		   	
		  
	}

 public void printAllIndividuals()
	{
		Set<OWLNamedIndividual> individuals;
		individuals = ontology.getIndividualsInSignature();
		
		System.out.println("-----------------------------------");   
		System.out.println("List of All OWL Individuals ("
						   + individuals.size() + ")");
		System.out.println("-----------------------------------"); 
        
		for (OWLNamedIndividual i : individuals) 
			System.out.println(this.getLabel(i));		   	
	}
 
public void printAllAxioms() 
	{ 
		  Set<OWLAxiom> axioms = ontology.getAxioms();
	      Set<OWLAxiom> individualAxioms = new HashSet<OWLAxiom>(); 
		  Set<OWLAxiom> dataPropertyAxioms = new HashSet<OWLAxiom>(); 
		  Set<OWLAxiom> objectPropertyAxioms = new HashSet<OWLAxiom>(); 
		  Set<OWLAxiom> owlClassAxioms = new HashSet<OWLAxiom>(); 
		  Set<OWLAxiom> otherAxioms = new HashSet<OWLAxiom>(); 
		 
		  for (OWLAxiom axiom : axioms) 
		  { 
		   axiom.getSignature(); 
		   if ((axiom instanceof OWLClassAxiom))
		   		{owlClassAxioms.add(axiom);} 
		   else if (axiom instanceof OWLDataPropertyAxiom)
		   		{dataPropertyAxioms.add(axiom);} 
		   else if (axiom instanceof OWLObjectPropertyAxiom)
		    	{dataPropertyAxioms.add(axiom);} 
		   else if (axiom instanceof OWLIndividualAxiom) 
		   		{individualAxioms.add(axiom);}
		   else otherAxioms.add(axiom); 
		  } 
		 
		  System.out.println("-----------------------------------");   
		  System.out.println("List of All Axioms (" + axioms.size() + ")");
		  System.out.println("-----------------------------------");   
		  
		  for (OWLAxiom ax : individualAxioms)
			  { 
			   String line; 
			   line = ax.toString() + " TYPE: Individual"; 
			   System.out.println(line); 
			  } 
		  
		  for (OWLAxiom ax : dataPropertyAxioms)
			  { 
			   String line; 
			   line = ax.toString() + " TYPE: DataProperty"; 
			   System.out.println(line); 
			  } 
			  
		  for (OWLAxiom ax : objectPropertyAxioms) 
			  { 
			   String line; 
			   line = ax.toString() + " TYPE: ObjectProperty"; 
			   System.out.println(line); 
			  } 
		  
		  for (OWLAxiom ax : owlClassAxioms)
			  { 
			   String line; 
			   line = ax.toString() + " TYPE: Class"; 
			   System.out.println(line); 
			  }
		  
		  for (OWLAxiom ax : otherAxioms)
			  { 
			   String line; 
			   line = ax.toString() + " TYPE: Other"; 
			   System.out.println(line); 
			  } 
		  		
	} // End of public void printAxioms(Set<OWLAxiom> axioms)

}