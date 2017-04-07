package ca.queensu.efbo.annotation.extractor;
import java.util.ArrayList;
import ca.queensu.efbo.Console;


public class Tester 
{

	public static void main(String[] args) throws Exception
	{
		//new Console();
		
	 new KnowledgeBaseGenerator();
	

		
		AnnotationExtractor annot = new AnnotationExtractor();
		ArrayList <Annotation> annotations = annot.getExtractedAnnotations();
		
	
			for (Annotation annotation: annotations)
			{
			System.out.println("\n\n");
			System.out.println(annotation.getFileLocation());
			System.out.println(annotation.getLineNumber());
			System.out.println(annotation.getSubject());
			System.out.println(annotation.getPredicate());
			System.out.println(annotation.getObject());
			}

			System.out.println(annot.getExtractedAnnotations());
	
	}
}
