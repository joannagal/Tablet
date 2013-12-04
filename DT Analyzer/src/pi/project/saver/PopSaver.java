package pi.project.saver;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
			savePopul(project.getFirstPopulation(), "1");
		}

		if (project.getSecondPopulation() != null) {
			savePopul(project.getSecondPopulation(), "2");
		}

		out.writeEndElement();
		out.writeEndDocument();

		out.close();
	}

	private void savePopul(Population popul, String id) throws XMLStreamException {
		out.writeStartElement("POPUL");
		
		if (popul.getName() != null) {
			out.writeAttribute("name", popul.getName());
		} 
		
		out.writeAttribute("id", id);
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
		if (s.getPesel() != null)
			out.writeAttribute("pesel", s.getPesel());
		if (s.getBirth() != null) 
		{
			Date date = s.getBirth();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			out.writeAttribute("birth_date", df.format(date));
		}
		if (s.getSex() != null)
			out.writeAttribute("sex", String.valueOf(s.getSex()));
		if (s.getHand() != null)
			out.writeAttribute("hand", String.valueOf(s.getHand()));
		if (s.getBrain() != null)
			out.writeAttribute("brain", String.valueOf(s.getBrain()));
		if (s.getOperationType() != null) 
			out.writeAttribute("operation",
					String.valueOf(s.getOperationType()));
		if (s.getFirstOperationNo() != null) 
			out.writeAttribute("firstoperationno",
					String.valueOf(s.getFirstOperationNo()));
		if (s.getSecondOperationNo() != null) 
			out.writeAttribute("secondoperationno",
					String.valueOf(s.getSecondOperationNo()));
		
		if (s.getAfter() != null) {
			out.writeAttribute("inputs_number", "2");
		} else {
			out.writeAttribute("inputs_number", "1");
		}

		if (s.getBefore() != null) {
			saveDrawing((Drawing) s.getBefore(), "1");
		}

		if (s.getAfter() != null) {
			saveDrawing((Drawing) s.getAfter(), "2");
		}

		out.writeEndElement();
	}

	private void saveDrawing(Drawing drawing, String id) throws XMLStreamException {
		out.writeStartElement("INPUT");
		

		out.writeAttribute("id", id);
		
		out.writeAttribute("figures",
				String.valueOf(drawing.getFigure().size()));
		out.writeAttribute("pressure_avoid",
				String.valueOf(drawing.getPressureAvoid()));
		out.writeAttribute("total_time",
				String.valueOf(drawing.getTotalTime()));
		out.writeAttribute("content", rectangleToString(drawing.getContent()));

		out.writeStartElement("RAW_DATA");
		out.writeAttribute("packets", Integer.toString(drawing.getPacket().size()));
		
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

		if (fig.getSegment() != null) 
			out.writeAttribute("segments",
					String.valueOf(fig.getSegment().size()));
		

		if (fig.getBounds() != null)
			out.writeAttribute("bounds", rectangleToString(fig.getBounds()));
		

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
