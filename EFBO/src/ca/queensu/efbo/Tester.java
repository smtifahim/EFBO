package ca.queensu.efbo;
import java.util.ArrayList;


public class Tester 
{

	public static void main(String[] args) throws Exception
	{
		//new Console();
		AnnotationExtractor annot = new AnnotationExtractor();
		ArrayList <Annotation> annotations = annot.getExtractedAnnotations();
		
		if (annotations.size()!=0)
		{
			EFBOKnowledgeBase efboKBGenerator1 = new EFBOKnowledgeBase("SYS-01", "Login System");
			efboKBGenerator1.processExtractedAnnotations(annotations);
			efboKBGenerator1.getEFBOManager().printOntologyMetrics();
			efboKBGenerator1.getEFBOManager().printAllIndividuals();
			efboKBGenerator1.getEFBOManager().printAllObjectProperties();
		}
		
		System.exit(1);
	
	}
}
