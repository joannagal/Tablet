package pi.inputs.drawing;

import java.awt.Rectangle;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import pi.inputs.drawing.autofinder.FigureExtractor;
import pi.inputs.drawing.autofinder.FigureInterpreter;
import pi.inputs.drawing.autofinder.Linearize;

public class Drawing
{
	private Rectangle content;

	private ArrayList<PacketData> packet;
	private ArrayList<Figure> figure;

	private Figure zigZag;
	private Figure circleUp;
	private Figure circleDown;
	private Figure firstLine;
	private Figure secondLine;
	private Figure brokenLine;
	private Figure spiralOut;
	private Figure spiralIn;

	private int pressureAvoid = 0;
	private int maxPressure = 1024;
	private int totalTime = 0;
	private int breakFigureDistance = 128;

	public Drawing(String path)
	{
		this.createFromFile(path);
		this.calculateBreakFigureDistance();

		this.recalculate();
	}

	public void recalculate()
	{
		FigureExtractor extractor = new FigureExtractor();
		extractor.extract(this);

		this.calculateBounds();

		Linearize linearize = new Linearize();
		linearize.linearize(this);

		// FigureInterpreter interpreter = new FigureInterpreter();
		// interpreter.interprate(this);
	}

	public void createFromFile(String path)
	{
		try
		{
			File file = new File(path);
			InputStream insputStream = new FileInputStream(file);
			long size = file.length();
			byte[] data = new byte[(int) size];

			insputStream.read(data);
			insputStream.close();

			int shift = 0;
			int fileNameLength = this.getInt(data, shift);

			if (fileNameLength > 0)
			{
				shift += 4;
				byte[] fileName = new byte[fileNameLength];
				for (int i = 0; i < fileNameLength; i++)
					fileName[i] = data[shift + i];
				shift += fileNameLength;
			} else
				shift++;

			int dateLength = this.getInt(data, shift);
			if (dateLength > 0)
			{
				shift += 4;

				byte[] date = new byte[dateLength];
				for (int i = 0; i < dateLength; i++)
					date[i] = data[shift + i];
				shift += dateLength;
			} else
				shift++;

			int memoLength = this.getInt(data, shift);
			if (memoLength > 0)
			{
				shift += 4;
				byte[] memo = new byte[memoLength];
				for (int i = 0; i < memoLength; i++)
					memo[i] = data[shift + i];
				shift += memoLength;
			} else
				shift++;

			// long outOrgX = this.getInt(data, shift);
			shift += 4;
			// long outOrgY =this.getInt(data, shift);
			shift += 4;
			// long outExtX = this.getInt(data, shift);
			shift += 4;
			// long outExtY = this.getInt(data, shift);
			shift += 4;

			int numPackages = this.getInt(data, shift);
			shift += 4;

			this.setPressureAvoid(0);
			this.setMaxPressure(1024);

			this.packet = new ArrayList<PacketData>(numPackages);
			PacketData temp;

			int maxTime = 0;
			int cnt = 0;

			while (cnt < numPackages)
			{
				cnt++;
				temp = new PacketData();
				if (this.getInt(data, shift) > maxTime)
					maxTime = this.getInt(data, shift);
				temp.setPkTime(this.getInt(data, shift));
				temp.setPkX(this.getInt(data, shift + 4));
				temp.setPkY(this.getInt(data, shift + 8));
				temp.setPkPressure(this.getInt(data, shift + 12));
				temp.setPkAzimuth(this.getInt(data, shift + 16));
				temp.setPkAltitude(this.getInt(data, shift + 20));
				packet.add(temp);
				shift += 24;
			}
			this.setTotalTime(maxTime);

		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void calculateBounds()
	{
		Iterator<Figure> itFig = this.figure.iterator();
		Figure fig;

		int min_x = 1000000;
		int max_x = -1000000;
		int min_y = 1000000;
		int max_y = -1000000;
		int width = 0;
		int height = 0;
		int prop;

		while (itFig.hasNext())
		{
			fig = itFig.next();

			Iterator<Segment> itSeg = fig.getSegment().iterator();
			Segment seg;

			while (itSeg.hasNext())
			{
				seg = itSeg.next();

				ArrayList<PacketData> packet = seg.getPacket();
				int size = packet.size();

				for (int i = 0; i < size; i++)
				{
					if (packet.get(i).getPkX() > max_x)
						max_x = packet.get(i).getPkX();
					if (packet.get(i).getPkX() < min_x)
						min_x = packet.get(i).getPkX();

					if (packet.get(i).getPkY() > max_y)
						max_y = packet.get(i).getPkY();
					if (packet.get(i).getPkY() < min_y)
						min_y = packet.get(i).getPkY();
				}
			}
		}
		width = max_x - min_x;
		height = max_y - min_y;
		prop = width / 30;

		this.setContent(new Rectangle(min_x - prop, min_y - prop, width + 2
				* prop, height + 2 * prop));

	}

	public void calculateBreakFigureDistance()
	{
		int size = this.packet.size();
		int min_x = 1000000;
		int max_x = -1000000;
		for (int i = 0; i < size; i++)
		{
			if (packet.get(i).getPkPressure() < 512)
				continue;
			if (packet.get(i).getPkX() > max_x)
				max_x = packet.get(i).getPkX();
			if (packet.get(i).getPkX() < min_x)
				min_x = packet.get(i).getPkX();
		}
		int width = max_x - min_x;
		this.setBreakFigureDistance(width / 5);
	}

	public int getInt(byte[] data, int position)
	{
		byte[] four = new byte[(int) 4];
		four[3] = data[position + 0];
		four[2] = data[position + 1];
		four[1] = data[position + 2];
		four[0] = data[position + 3];
		return java.nio.ByteBuffer.wrap(four).getInt();
	}

	public Rectangle getContent()
	{
		return content;
	}

	public void setContent(Rectangle content)
	{
		this.content = content;
	}

	public ArrayList<PacketData> getPacket()
	{
		return packet;
	}

	public void setPacket(ArrayList<PacketData> packet)
	{
		this.packet = packet;
	}

	public int getPressureAvoid()
	{
		return pressureAvoid;
	}

	public void setPressureAvoid(int pressureAvoid)
	{
		this.pressureAvoid = pressureAvoid;
	}

	public int getMaxPressure()
	{
		return maxPressure;
	}

	public void setMaxPressure(int maxPressure)
	{
		this.maxPressure = maxPressure;
	}

	public int getTotalTime()
	{
		return totalTime;
	}

	public void setTotalTime(int totalTime)
	{
		this.totalTime = totalTime;
	}

	public int getBreakFigureDistance()
	{
		return breakFigureDistance;
	}

	public void setBreakFigureDistance(int breakShapeDistance)
	{
		this.breakFigureDistance = breakShapeDistance;
	}

	public ArrayList<Figure> getFigure()
	{
		return figure;
	}

	public void setFigure(ArrayList<Figure> figure)
	{
		this.figure = figure;
	}

	public Figure getZigZag()
	{
		return zigZag;
	}

	public void setZigZag(Figure zigZag)
	{
		this.zigZag = zigZag;
	}

	public Figure getCircleUp()
	{
		return circleUp;
	}

	public void setCircleUp(Figure circleUp)
	{
		this.circleUp = circleUp;
	}

	public Figure getCircleDown()
	{
		return circleDown;
	}

	public void setCircleDown(Figure circleDown)
	{
		this.circleDown = circleDown;
	}

	public Figure getFirstLine()
	{
		return firstLine;
	}

	public void setFirstLine(Figure firstLine)
	{
		this.firstLine = firstLine;
	}

	public Figure getSecondLine()
	{
		return secondLine;
	}

	public void setSecondLine(Figure secondLine)
	{
		this.secondLine = secondLine;
	}

	public Figure getBrokenLine()
	{
		return brokenLine;
	}

	public void setBrokenLine(Figure brokenLine)
	{
		this.brokenLine = brokenLine;
	}

	public Figure getSpiralOut()
	{
		return spiralOut;
	}

	public void setSpiralOut(Figure spiralOut)
	{
		this.spiralOut = spiralOut;
	}

	public Figure getSpiralIn()
	{
		return spiralIn;
	}

	public void setSpiralIn(Figure spiralIn)
	{
		this.spiralIn = spiralIn;
	}
}
