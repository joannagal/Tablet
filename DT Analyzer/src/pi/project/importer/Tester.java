package pi.project.importer;

import java.io.IOException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import pi.project.Project;
import pi.project.saver.PopSaver;

public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		try {
			PopImporter pi = new PopImporter();
			
			XMLReader p = XMLReaderFactory.createXMLReader();
			p.setContentHandler(pi);
			p.parse("population_ex.xml");
			
			Project importedProject = pi.getProject();
			
			
			System.out.println("\nZapis: ");
			
			PopSaver ps = new PopSaver(importedProject);
			ps.save("savedXml.xml");
				
			
			
		} catch (IOException | SAXException e) {
			System.out.println("Project load failure");
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
			System.out.println("B£¥D ZAPISU");
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
			System.out.println("B£¥D ZAPISU");
		}
	}

}
