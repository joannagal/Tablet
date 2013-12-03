package pi.project.importer;

import java.awt.Rectangle;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import javax.xml.crypto.Data;

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

public class PopImporter extends DefaultHandler
{
	private Project project;
	private Population popul;
	private ArrayList<Specimen> specimenList;
	private Specimen spec;
	private Drawing input;
	private Drawing currentDrawing = null;
	private ArrayList<Figure> figureList;
	private Figure figure;
	private LinkedList<Segment> segmentsList;
	private Segment segment;

	private int specimenIndex = 0;
	private int channelIndex = 0;

	private int rawSize = -1;

	boolean rawDataNode = false;

	LinkedList<String> toBuild;

	@Override
	public void startDocument()
	{
		System.out.println("Start project import");
	}

	@Override
	public void endDocument()
	{
		System.out.println("Project imported");
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException
	{

		if (qName.equalsIgnoreCase("PROJECT"))
		{
			initProject(attributes);
			rawDataNode = false;

		} else if (qName.equalsIgnoreCase("POPUL"))
		{
			initPopul(attributes);
			rawDataNode = false;
		} else if (qName.equalsIgnoreCase("SPECIMEN"))
		{
			initSpecimen(attributes);
			rawDataNode = false;

		} else if (qName.equalsIgnoreCase("INPUT"))
		{
			initInput(attributes);
		} else if (qName.equalsIgnoreCase("FIGURE"))
		{
			initFigure(attributes);
			rawDataNode = false;
			

		} else if (qName.equalsIgnoreCase("RAW_DATA"))
		{
			initRawData(attributes);
			rawDataNode = true;
			
		} else if (qName.equalsIgnoreCase("SEGMENT"))
		{
			initSegment(attributes);
			rawDataNode = false;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException
	{
		System.out.println("End Element :" + qName);
		if (rawDataNode)
		{
			this.finishRawData();
			rawDataNode = false;
		}
	}

	@Override
	public void characters(char ch[], int start, int length)
			throws SAXException
	{
		if (rawDataNode)
		{
			getRawData(ch, start, length);
		}
	}

	public void init()
	{

	}

	public void finalize()
	{

	}

	public void initProject(Attributes attributes)
	{
		project = new Project();
		project.setName(attributes.getValue("name"));
		project.setPath(attributes.getValue("path"));

		String type = attributes.getValue("type");
		if (type != "")
			project.setType(Integer.parseInt(type));
	}

	public void initPopul(Attributes attributes)
	{
		popul = new Population();
		int popId = Integer.parseInt(attributes.getValue("id"));
		String name = attributes.getValue("name");
		popul.setName(name);
		String specCount = attributes.getValue("specimens");
		if (specCount != "")
			specimenList = new ArrayList<>(Integer.parseInt(specCount));
		specimenIndex = 0;
		popul.setSpecimen(specimenList);

		if (popId == 1)
		{
			project.setFirstPopulation(popul);
		} else if (popId == 2)
		{
			project.setSecondPopulation(popul);
		}
	}

	public void initSpecimen(Attributes attributes)
	{
		spec = new Specimen();

		spec.setName(attributes.getValue("name"));
		spec.setSurname(attributes.getValue("surname"));
		spec.setPesel(attributes.getValue("pesel"));

		// birth_date
		String date = attributes.getValue("birth_date");
		if (date != null)
		{
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			try
			{
				Date d = df.parse(date);
				spec.setBirth(d);

			} catch (ParseException e1)
			{
				spec.setBirth(null);
			}
		} else
		{
			spec.setBirth(null);
		}

		String hand = attributes.getValue("hand");
		if (hand != null)
			spec.setHand(Boolean.parseBoolean(hand));
		else
			spec.setHand(null);

		String sex = attributes.getValue("sex");
		if (sex != null)
			spec.setSex(Boolean.parseBoolean(sex));
		else
			spec.setSex(null);

		String brain = attributes.getValue("brain");
		if (brain != null)
			spec.setBrain(Boolean.parseBoolean(brain));
		else
			spec.setBrain(null);

		String operation = attributes.getValue("operation");
		if (operation != null)
			spec.setOperationType(Boolean.parseBoolean(operation));
		else
			spec.setOperationType(null);

		String firstOperationNo = attributes.getValue("firstoperationno");
		if (firstOperationNo != null)
			spec.setFirstOperationNo(Integer.parseInt(firstOperationNo));
		else
			spec.setFirstOperationNo(null);

		String secondOperationNo = attributes.getValue("secondoperationno");
		if (secondOperationNo != null)
			spec.setSecondOperationNo(Integer.parseInt(secondOperationNo));
		else
			spec.setSecondOperationNo(null);

		popul.getSpecimen().add(specimenIndex, spec);
		specimenIndex++;
	}

	public void initInput(Attributes attributes)
	{
		input = new Drawing();
		this.currentDrawing = input;

		String id = attributes.getValue("id");
		String figures = attributes.getValue("figures");
		if (figures != null && figures != "")
		{
			System.out.println("ERR Figures: " + figures);
			figureList = new ArrayList<>(Integer.parseInt(figures));
		} else
		{
			figureList = new ArrayList<>();

		}
		channelIndex = 0;
		input.setFigure(figureList);

		String pressureAvoid = attributes.getValue("pressure_avoid");
		if (pressureAvoid != null && pressureAvoid != "")
			input.setPressureAvoid(Integer.parseInt(pressureAvoid));
		
		String totalTime = attributes.getValue("total_time");
		if (totalTime != null && totalTime != "")
			input.setTotalTime(Integer.parseInt(totalTime));

		String content = attributes.getValue("content");

		if (content != "")
		{
			String[] points = content.split(" ");
			if (points.length >= 4)
			{
				int x = Integer.parseInt(points[0]);
				int y = Integer.parseInt(points[1]);
				int w = Integer.parseInt(points[2]);
				int h = Integer.parseInt(points[3]);
				input.setContent(new Rectangle(x, y, w, h));
			}
		}

		String operation = attributes.getValue("operation");
		if ((operation != null) && (operation != ""))
		{
			spec.setOperationType(Boolean.parseBoolean(operation));
		}

		if (id.equals("1"))
		{
			spec.setBefore(input);
		} else if (id.equals("2"))
		{
			spec.setAfter(input);
		}

		input.createStatus();
	}

	public void initFigure(Attributes attributes)
	{
		figure = new Figure(input);

		String type = attributes.getValue("type");
		if (type != "")
			figure.setType(Integer.parseInt(type));

		String bounds = attributes.getValue("bounds");
		if (bounds != "")
		{
			String[] points = bounds.split(" ");
			if (points.length >= 4)
			{
				int x = Integer.parseInt(points[0]);
				int y = Integer.parseInt(points[1]);
				int w = Integer.parseInt(points[2]);
				int h = Integer.parseInt(points[3]);
				figure.setBounds(new Rectangle(x, y, w, h));
			}
		}

		segmentsList = new LinkedList<Segment>();
		figure.setSegment(segmentsList);

		figureList.add(channelIndex, figure);
		channelIndex++;

	}

	public void initRawData(Attributes attributes)
	{
		this.rawSize = Integer.parseInt(attributes.getValue("packets"));
		this.toBuild = new LinkedList<String>();
	}

	public void getRawData(char ch[], int start, int length)
	{
		String toAdd = new String(ch, start, length);
		this.toBuild.addLast(toAdd);
	}

	public void finishRawData()
	{
		
		System.out.printf("--- FINISH\n");
		Iterator <String> it = this.toBuild.iterator();
		String value;
		String sum = "";
		
		while (it.hasNext())
		{
			value = it.next();
			System.out.printf("L: %d\n", value.length());
			sum = sum + value;
		}
		String data[] = sum.split(" ");

		System.out.printf("--- %d %d %d\n", sum.length(), rawSize, data.length);

		ArrayList<PacketData> packets = new ArrayList<>(rawSize);

		for (int i = 0; i < data.length - 1; i += 6)
		{
			System.out.printf("%s ", data[i]);

			PacketData p = new PacketData();
			p = new PacketData();
			p.setPkX(Integer.parseInt(data[i]));
			p.setPkY(Integer.parseInt(data[i + 1]));
			p.setPkPressure(Integer.parseInt(data[i + 2]));
			p.setPkTime(Integer.parseInt(data[i + 3]));
			p.setPkAzimuth(Integer.parseInt(data[i + 4]));
			p.setPkAltitude(Integer.parseInt(data[i + 5]));
			packets.add(p);
		}

		if (this.currentDrawing != null)
		{
			this.currentDrawing.setPacket(packets);
		}
	}

	public void initSegment(Attributes attributes)
	{
		segment = new Segment();

		String range = attributes.getValue("range");
		String[] bounds = range.split(" ");
		if (bounds.length >= 2)
		{
			int a = Integer.parseInt(bounds[0]);
			int b = Integer.parseInt(bounds[1]);

			segment.setRange(new Range(a, b));
		}

		segmentsList.add(segment);
	}

	public Project getProject()
	{
		return project;
	}
}
