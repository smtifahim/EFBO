package ca.queensu.efbo;
import java.util.ArrayList;

import ca.queensu.efbo.annotation.extractor.Annotation;
import ca.queensu.efbo.annotation.extractor.AnnotationExtractor;
import ca.queensu.efbo.annotation.extractor.EFBOKnowledgeBase;


public class Tester 
{

	public static void main(String[] args) throws Exception
	{
		//new Console();
		AnnotationExtractor annot = new AnnotationExtractor();
		ArrayList <Annotation> annotations = annot.getExtractedAnnotations();
		
		EFBOKnowledgeBase efboKBGenerator1 = new EFBOKnowledgeBase("SYS-01", "Login System");
		efboKBGenerator1.processExtractedAnnotations(annotations);
		efboKBGenerator1.getEFBOManager().printOntologyMetrics();
		efboKBGenerator1.getEFBOManager().printAllIndividuals();
		//efboKBGenerator1.getEFBOManager().printAllAxioms();
		
		System.exit(1);
	
	}
}
