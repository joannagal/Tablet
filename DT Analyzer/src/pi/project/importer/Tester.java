package pi.project.importer;

import java.io.IOException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import pi.project.Project;
import pi.project.saver.PopSaver;

public class Tester
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{

		try
		{
			PopImporter pi = new PopImporter();

			XMLReader p = XMLReaderFactory.createXMLReader();
			p.setContentHandler(pi);
			p.parse("population_ex.xml");

			Project importedProject = pi.getProject();


			PopSaver ps = new PopSaver(importedProject);
			ps.save("savedXml.xml");

		} catch (IOException | SAXException e)
		{
			e.printStackTrace();
		} catch (XMLStreamException e)
		{
			e.printStackTrace();
		} catch (FactoryConfigurationError e)
		{
			e.printStackTrace();
		}
	}

}
