package pi.project.saver;

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

import pi.inputs.signal.Channel;
import pi.inputs.signal.Cycle;
import pi.inputs.signal.ECG;
import pi.inputs.signal.Probe;
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
		out.writeAttribute("surname", s.getSurname());
		if (s.getBirth() != null) {
			out.writeAttribute("birth_date", s.getBirth());
		} else {
			out.writeAttribute("birth_date", "");
		}
		out.writeAttribute("age", String.valueOf(s.getAge()));
		out.writeAttribute("weight", String.valueOf(s.getWeight()));
		out.writeAttribute("activity_duration",
				String.valueOf(s.getActivityDuration()));
		out.writeAttribute("hiv", String.valueOf(s.getHiv()));
		out.writeAttribute("metadon", String.valueOf(s.getMetadon()));
		out.writeAttribute("metadon_time_application",
				String.valueOf(s.getMetadonTimeApplication()));
		out.writeAttribute("good_mood_duration",
				String.valueOf(s.getTimeToGoodMood()));
		if (s.getAfter() != null) {
			out.writeAttribute("inputs_number", "2");
		} else {
			out.writeAttribute("inputs_number", "1");
		}

		if (s.getBefore() != null) {
			saveECG((ECG) s.getBefore());
		}

		if (s.getAfter() != null) {
			saveECG((ECG) s.getAfter());
		}

		out.writeEndElement();
	}

	private void saveECG(ECG ecg) throws XMLStreamException {
		out.writeStartElement("INPUT");
		if (ecg.getName() != null)
			out.writeAttribute("id", ecg.getName());
		out.writeAttribute("channels", String.valueOf(ecg.getChannel().size()));

		for (Channel ch : ecg.getChannel()) {
			saveChannel(ch);
		}

		out.writeEndElement();

	}

	private void saveChannel(Channel ch) throws XMLStreamException {
		out.writeStartElement("CHANNEL");
		if (ch.getName() != null)
			out.writeAttribute("name", ch.getName());
		if (ch.getInterval() != null) {
			out.writeAttribute("interval", String.valueOf(ch.getInterval()));
		} else {
			out.writeAttribute("interval", "");
		}
		if (ch.getProbe() != null) {
			out.writeAttribute("samples", String.valueOf(ch.getProbe().size()));
		} else {
			out.writeAttribute("samples", "");
		}
		out.writeAttribute("translations", String.valueOf(ch.getTranslation()));

		out.writeStartElement("RAW_DATA");
		if (ch.getProbe() != null) {
			for (Probe p : ch.getProbe()) {
				out.writeCharacters(p.getValue() + " ");
			}
		}
		out.writeEndElement();

		out.writeStartElement("CYCLES");
		out.writeAttribute("number", String.valueOf(ch.getCycle().size()));
		for (Cycle c : ch.getCycle()) {
			saveCycle(c);
		}

		out.writeEndElement();

		out.writeEndElement();
	}

	private void saveCycle(Cycle c) throws XMLStreamException {
		out.writeStartElement("CYCLE");
		if (c.getRange() != null) {
			out.writeAttribute("range", c.getRange().toString());
		} else {
			out.writeAttribute("range", "");
		}
		if (c.getP_wave() != null) {
			out.writeAttribute("p_wave", c.getP_wave().toString());
		} else {
			out.writeAttribute("p_wave", "");
		}
		if (c.getPr_segment() != null) {
			out.writeAttribute("pr_segment", c.getPr_segment().toString());
		} else {
			out.writeAttribute("pr_segment", "");
		}
		if (c.getQrs_complex() != null) {
			out.writeAttribute("qrs_complex", c.getQrs_complex().toString());
		} else {
			out.writeAttribute("qrs_complex", "");
		}

		if (c.getT_wave() != null) {
			out.writeAttribute("t_wave", c.getT_wave().toString());
		} else {
			out.writeAttribute("t_wave", "");
		}
		if (c.getU_wave() != null) {
			out.writeAttribute("u_wave", c.getU_wave().toString());
		} else {
			out.writeAttribute("u_wave", "");
		}
		if (c.getMarkered() != null) {
			out.writeAttribute("markered", c.getMarkered().toString());
		} else {
			out.writeAttribute("markered", Boolean.FALSE.toString());
		}

		out.writeEndElement();
	}
}
