package pi.project.importer;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import pi.inputs.drawing.Drawing;
import pi.inputs.drawing.Figure;
import pi.inputs.drawing.PacketData;
import pi.inputs.drawing.Segment;
import pi.population.Population;
import pi.population.Specimen;
import pi.project.Project;
import pi.utilities.Range;

public class PopImporter extends DefaultHandler {

	private Project project;
	private Population popul;
	private ArrayList<Specimen> specimenList;
	private Specimen spec;
	private Drawing input;
	private ArrayList<Figure> figureList;
	private Figure figure;
	private LinkedList<Segment> segmentsList;
	private Segment segment;

	private int specimenIndex = 0;
	private int channelIndex = 0;

	boolean rawDataNode = false;

	@Override
	public void startDocument() {
		System.out.println("Start project import");
	}

	@Override
	public void endDocument() {
		System.out.println("Project imported");
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		if (qName.equalsIgnoreCase("PROJECT")) {
			initProject(attributes);
		} else if (qName.equalsIgnoreCase("POPUL")) {
			initPopul(attributes);
		} else if (qName.equalsIgnoreCase("SPECIMEN")) {
			initSpecimen(attributes);
		} else if (qName.equalsIgnoreCase("INPUT")) {
			initInput(attributes);
		} else if (qName.equalsIgnoreCase("FIGURE")) {
			initFigure(attributes);
		} else if (qName.equalsIgnoreCase("RAW_DATA")) {
			rawDataNode = true;
		} else if (qName.equalsIgnoreCase("SEGMENT")) {
			initSegment(attributes);
		} else if (qName.equalsIgnoreCase("CYCLE")) {
			initSegment(attributes);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		System.out.println("End Element :" + qName);
	}

	@Override
	public void characters(char ch[], int start, int length)
			throws SAXException {

		if (rawDataNode) {
			getRawData(ch, start, length);
			rawDataNode = false;
		}
	}

	public void init() {

	}

	public void finalize() {

	}

	public void initProject(Attributes attributes) {
		project = new Project();
		project.setName(attributes.getValue("name"));
		project.setPath(attributes.getValue("path"));
		// TODO:
		// Date date = new Date(attributes.getValue("date"));
		// project.setDate(date);
		String type = attributes.getValue("type");
		if (type != "")
			project.setType(Integer.parseInt(type));
	}

	public void initPopul(Attributes attributes) {
		popul = new Population();
		int popId = Integer.parseInt(attributes.getValue("id"));
		String specCount = attributes.getValue("specimens");
		if (specCount != "")
			specimenList = new ArrayList<>(Integer.parseInt(specCount));
		specimenIndex = 0;
		popul.setSpecimen(specimenList);

		if (popId == 1) {
			project.setFirstPopulation(popul);
		} else if (popId == 2) {
			project.setSecondPopulation(popul);
		}
	}

	public void initSpecimen(Attributes attributes) {
		spec = new Specimen();
		spec.setName(attributes.getValue("name"));
		spec.setSurname(attributes.getValue("surname"));
		spec.setPesel(attributes.getValue("pesel"));
		Date d = new Date();
		// TODO attributes.getValue("birth_date")
		// spec.setBirth((java.sql.Date) d);

		String hand = attributes.getValue("hand");
		if (hand != "")
			spec.setHand(Boolean.parseBoolean(hand));

		String sex = attributes.getValue("sex");
		if (sex != "") {
			spec.setSex(Boolean.parseBoolean(sex));
		}

		String brain = attributes.getValue("brain");
		if (brain != "") {
			spec.setBrain(Boolean.parseBoolean(brain));
		}

		String operation = attributes.getValue("operation");
		if (operation != "") {
			spec.setOperationType(Boolean.parseBoolean(operation));
		}

		// TODO Inputs number?

		popul.getSpecimen().add(specimenIndex, spec);
		specimenIndex++;
	}

	public void initInput(Attributes attributes) {
		input = new Drawing();
		input.setName(attributes.getValue("id"));
		String figures = attributes.getValue("figures");
		if (figures != null && figures != "") {
			System.out.println("ERR Figures: " + figures);
			figureList = new ArrayList<>(Integer.parseInt(figures));
		} else {
			figureList = new ArrayList<>();
		}
		channelIndex = 0;
		input.setFigure(figureList);

		String pressureAvoid = attributes.getValue("pressure_avoid");
		if (pressureAvoid != null && pressureAvoid != "")
			input.setPressureAvoid(Integer.parseInt(pressureAvoid));

		String content = attributes.getValue("content");

		if (content != "") {
			// TODO Rectangle?
			String[] points = content.split(" ");
			if (points.length >= 4) {
				Rectangle r = new Rectangle();
				// ???
			}
		}

		String operation = attributes.getValue("operation");
		if ((operation != null) && (operation != "")) {
			// TODO Operation missing?
		}


		if (input.getName().equals("0")) {
			spec.setBefore(input);
		} else if (input.getName().equals("1")) {
			spec.setAfter(input);
		}
	}

	public void initFigure(Attributes attributes) {
		figure = new Figure(input);

		String type = attributes.getValue("type");
		if (type != "")
			figure.setType(Integer.parseInt(type));

		String bounds = attributes.getValue("bounds");
		// TODO
		// if (bounds != "")
		// figure.setBounds(Integer.valueOf(bounds));

		segmentsList = new LinkedList<Segment>();
		figure.setSegment(segmentsList);

		// TODO Nie wiem czy atrybuyt segments jest w ogóle potrzebny

		figureList.add(channelIndex, figure);
		channelIndex++;

	}

	public void getRawData(char ch[], int start, int length) {
		String data[] = (new String(ch, start, length)).split(" ");
		ArrayList<PacketData> packets = new ArrayList<>(data.length);
		int packetsNumber = 0;
		for (int i = 0; i < data.length; i++) {
			PacketData p = new PacketData();
			int pd = i % 6;
			switch (pd) {
			case 0:
				p = new PacketData();
				p.setPkX(Integer.parseInt(data[i]));
				break;
			case 1:
				p.setPkY(Integer.parseInt(data[i]));
				break;
			case 2:
				p.setPkPressure(Integer.parseInt(data[i]));
				break;
			case 3:
				p.setPkTime(Integer.parseInt(data[i]));
				break;
			case 4:
				p.setPkAzimuth(Integer.parseInt(data[i]));
				break;
			case 5:
				p.setPkAltitude(Integer.parseInt(data[i]));
				packetsNumber++;
				break;
			}

			packets.add(packetsNumber, p);
		}

	}

	public void initSegment(Attributes attributes) {
		segment = new Segment();

		String range = attributes.getValue("range");
		String[] bounds = range.split(" ");
		if (bounds.length >= 2) {
			int a = Integer.parseInt(bounds[0]);
			int b = Integer.parseInt(bounds[1]);

			segment.setRange(new Range(a, b));
		}

		segmentsList.add(segment);
	}

	public Project getProject() {
		return project;
	}
}
