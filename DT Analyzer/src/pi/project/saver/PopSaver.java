package pi.project.saver;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import pi.inputs.drawing.Drawing;
import pi.inputs.drawing.Figure;
import pi.inputs.drawing.PacketData;
import pi.inputs.drawing.Segment;
import pi.population.Population;
import pi.population.Specimen;
import pi.project.Project;

public class PopSaver {

	private Project project;
	// private String path;
	private XMLStreamWriter out;

	public PopSaver(Project project) {
		this.project = project;
	}

	public void save(String path) throws FileNotFoundException,
			UnsupportedEncodingException, XMLStreamException,
			FactoryConfigurationError {
		OutputStream outStream = new FileOutputStream(new File(path));
		out = XMLOutputFactory.newInstance().createXMLStreamWriter(
				new OutputStreamWriter(outStream, "utf-8"));

		out.writeStartDocument();
		out.writeStartElement("PROJECT");
		if (project.getName() != null)
			out.writeAttribute("name", project.getName());
		if (project.getPath() != null)
			out.writeAttribute("path", project.getPath());
		if (project.getDate() != null) {
			out.writeAttribute("date", project.getDate().toString());
		} else {
			out.writeAttribute("date", "");
		}
		out.writeAttribute("type", String.valueOf(project.getType()));

		if (project.getFirstPopulation() != null) {
			savePopul(project.getFirstPopulation());
		}

		if (project.getSecondPopulation() != null) {
			savePopul(project.getSecondPopulation());
		}

		out.writeEndElement();
		out.writeEndDocument();

		out.close();
	}

	private void savePopul(Population popul) throws XMLStreamException {
		out.writeStartElement("POPUL");
		if (popul.getName() != null) {
			out.writeAttribute("id", popul.getName());
		} else {
			out.writeAttribute("id", "");
		}
		out.writeAttribute("specimens",
				String.valueOf(popul.getSpecimen().size()));

		for (Specimen s : popul.getSpecimen()) {
			saveSpecimen(s);
		}

		out.writeEndElement();
	}

	private void saveSpecimen(Specimen s) throws XMLStreamException {
		out.writeStartElement("SPECIMEN");
		if (s.getName() != null)
			out.writeAttribute("name", s.getName());
		if (s.getSurname() != null)
			out.writeAttribute("surname", s.getSurname());
		if (s.getBirth() != null) {
			out.writeAttribute("birth_date", s.getBirth().toString());
		} else {
			out.writeAttribute("birth_date", "");
		}
		out.writeAttribute("sex", String.valueOf(s.getSex()));
		out.writeAttribute("hand", String.valueOf(s.getHand()));
		out.writeAttribute("brain", String.valueOf(s.getBrain()));
		if (s.getOperationType() != null) {
			out.writeAttribute("operation",
					String.valueOf(s.getOperationType()));
		}
		if (s.getAfter() != null) {
			out.writeAttribute("inputs_number", "2");
		} else {
			out.writeAttribute("inputs_number", "1");
		}

		if (s.getBefore() != null) {
			saveDrawing((Drawing) s.getBefore());
		}

		if (s.getAfter() != null) {
			saveDrawing((Drawing) s.getAfter());
		}

		out.writeEndElement();
	}

	private void saveDrawing(Drawing drawing) throws XMLStreamException {
		out.writeStartElement("INPUT");
		if (drawing.getName() != null)
			out.writeAttribute("id", drawing.getName());
		out.writeAttribute("figures",
				String.valueOf(drawing.getFigure().size()));
		out.writeAttribute("pressure_avoid",
				String.valueOf(drawing.getPressureAvoid()));
		out.writeAttribute("content", rectangleToString(drawing.getContent()));

		out.writeStartElement("RAW_DATA");
		if (drawing.getPacket() != null) {
			for (PacketData p : drawing.getPacket()) {
				out.writeCharacters(p.getPkX() + " ");
				out.writeCharacters(p.getPkY() + " ");
				out.writeCharacters(p.getPkPressure() + " ");
				out.writeCharacters(p.getPkTime() + " ");
				out.writeCharacters(p.getPkAzimuth() + " ");
				out.writeCharacters(p.getPkAltitude() + " ");
			}
		}
		out.writeEndElement();

		for (Figure ch : drawing.getFigure()) {
			saveFigure(ch);
		}

		out.writeEndElement();

	}

	private String rectangleToString(Rectangle r) {
		String content = "";
		if (r != null) {
			content += (int) r.getX();
			content += " " + (int) r.getY();
			content += " " + (int) r.getWidth();
			content += " " + (int) r.getHeight();
		}
		return content;
	}

	private void saveFigure(Figure fig) throws XMLStreamException {
		out.writeStartElement("FIGURE");
		out.writeAttribute("type", String.valueOf(fig.getType()));

		if (fig.getSegment() != null) {
			out.writeAttribute("segments",
					String.valueOf(fig.getSegment().size()));
		} else {
			out.writeAttribute("segments", "");
		}

		if (fig.getBounds() != null) {
			out.writeAttribute("bounds", rectangleToString(fig.getBounds()));
		} else {
			out.writeAttribute("bounds", "");
		}

		for (Segment s : fig.getSegment()) {
			saveSegment(s);
		}

		out.writeEndElement();
	}

	private void saveSegment(Segment c) throws XMLStreamException {
		out.writeStartElement("SEGMENT");
		if (c.getRange() != null) {
			String range = c.getRange().getLeft() + " "
					+ c.getRange().getRight();
			out.writeAttribute("range", range);
		} else {
			out.writeAttribute("range", "");
		}

		out.writeEndElement();
	}
}
