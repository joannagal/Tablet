package pi.inputs.drawing;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import pi.inputs.drawing.autofinder.FigureExtractor;
import pi.inputs.drawing.autofinder.FigureInterpreter;
import pi.utilities.Range;

public class Drawing
{
	private String label = "";
	private String origin = null;
	
	
	private Rectangle content;
	
	private ArrayList<PacketData> packet;
	private ArrayList<Figure> figure;

	private Figure[] completeFigure = new Figure[8];

	private boolean status = false;

	private boolean withExtract = false;

	private int pressureAvoid = 128;
	private int maxPressure = 1024;
	private int totalTime = 0;
	private int breakFigureDistance = 128;
	
	private int outOrgX = 0;
	private int outOrgY = 0;
	private int outExtX = 0;
	private int outExtY = 0;
	
	FigureExtractor extractor = new FigureExtractor();
	FigureInterpreter interpreter = new FigureInterpreter();

	public Drawing(String path) throws Exception
	{
		this.createFromFile(path);
		this.calculateBreakFigureDistance();
		this.recalculate(true);
	}
	
	public Drawing()
	{

	}

	public void recalculate(boolean bounds)
	{
		if ((!bounds) && (!this.isWithExtract()))
			return;
		this.clearStuff();
		
		if (bounds)
		{
			this.setBounds();
			double width = this.outOrgY - this.outOrgX;
			width = width / 5;
			this.setBreakFigureDistance((int)width);
		}
		
		extractor.extract(this);

		interpreter.interprate(this);
		this.createStatus();
	}

	public void createStatus()
	{
		if (this.figure == null)
			return;
		int size = this.figure.size();

		int[] tab = new int[9];
		this.status = true;

		for (int i = 0; i < 8; i++)
			this.getCompleteFigure()[i] = null;

		for (int i = 0; i < size; i++)
		{
			int fig = this.figure.get(i).getType();
			if (fig == -1)
				tab[8]++;
			else
			{
				tab[fig]++;
				this.getCompleteFigure()[fig] = this.figure.get(i);
			}
				
		}

		for (int i = 0; i < 8; i++)
		{
			if (tab[i] != 1)
			{
				this.status = false;
				break;
			}
		}

		if (tab[8] != 0)
			this.status = false;
	}

	public void createFigure(Range inTime, int type)
	{
		int left = -1, right = -1;
		int size = this.packet.size();

		for (int i = 0; i < size; i++)
		{
			if ((left == -1) && (packet.get(i).getPkTime() >= inTime.getLeft()))
				left = i;
			if ((right == -1)
					&& (packet.get(i).getPkTime() >= inTime.getRight()))
				right = i;
		}

		Figure newFigure = extractor.getFigure(this, new Range(left, right));
		newFigure.setType(type);
		this.figure.add(newFigure);
		
		this.createStatus();
	}

	public void createFromFile(String path) throws Exception
	{

		File file = new File(path);
		InputStream insputStream = new FileInputStream(file);
		long size = file.length();
		byte[] data = new byte[(int) size];

		this.origin = file.getName();
		
		insputStream.read(data);
		insputStream.close();

		int shift = 0;
		int fileNameLength = this.getInt(data, shift);

		//System.out.printf("-----------\n");
		//System.out.printf("%d ", fileNameLength);
		
		if (fileNameLength > 0)
		{
			shift += 4;
			byte[] fileName = new byte[fileNameLength];
			for (int i = 0; i < fileNameLength; i++)
				fileName[i] = data[shift + i];
			shift += fileNameLength;
			
			//String str = new String(fileName, "UTF-8");
			//System.out.printf("%s \n", str);
			
		} else
			shift++;

		int dateLength = this.getInt(data, shift);
		//System.out.printf("%d ", dateLength);
		
		if (dateLength > 0)
		{
			shift += 4;

			byte[] date = new byte[dateLength];
			for (int i = 0; i < dateLength; i++)
				date[i] = data[shift + i];
			shift += dateLength;
			
			//String str = new String(date, "UTF-8");
			//System.out.printf("%s \n", str);
			
		} else
			shift++;

		int memoLength = this.getInt(data, shift);
		//System.out.printf("%d ", memoLength);
		if (memoLength > 0)
		{
			shift += 4;
			byte[] memo = new byte[memoLength];
			for (int i = 0; i < memoLength; i++)
				memo[i] = data[shift + i];
			shift += memoLength;
			
			//String str = new String(memo, "UTF-8");
			//System.out.printf("%s \n", str);
		} else
			shift++;

		this.outOrgX = this.getInt(data, shift);
		shift += 4;
		this.outOrgY =this.getInt(data, shift);
		shift += 4;
		this.outExtX = this.getInt(data, shift);
		shift += 4;
		this.outExtY = this.getInt(data, shift);
		shift += 4;
		
		//System.out.printf("%d %d %d %d\n", outOrgX, outOrgY, outExtX,outExtY);

		int numPackages = this.getInt(data, shift);
		shift += 4;

		//System.out.printf("PACKAGES:  %d\n", numPackages);

		this.setPressureAvoid(128);
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
	}

	public void setBounds()
	{
		this.setContent(new Rectangle(this.outOrgX, this.outExtY, this.outOrgY, this.outExtX));
	}
	
	public void calculateBounds()
	{
		if (this.figure == null)
			return;

		int min_x = 1000000;
		int max_x = -1000000;
		int min_y = 1000000;
		int max_y = -1000000;
		int width = 0;
		int height = 0;
		int prop;

		for (int i = 0; i < this.figure.size(); i++)
		{

			Iterator<Segment> itSeg = this.figure.get(i).getSegment()
					.iterator();
			Segment seg;

			while (itSeg.hasNext())
			{
				seg = itSeg.next();
				Range range = seg.getRange();

				for (int j = range.getLeft(); j <= range.getRight(); j++)
				{
					if (packet.get(j).getPkX() > max_x)
						max_x = packet.get(j).getPkX();
					if (packet.get(j).getPkX() < min_x)
						min_x = packet.get(j).getPkX();

					if (packet.get(j).getPkY() > max_y)
						max_y = packet.get(j).getPkY();
					if (packet.get(j).getPkY() < min_y)
						min_y = packet.get(j).getPkY();
				}
			}
		}

		width = max_x - min_x;
		height = max_y - min_y;
		prop = width / 30;

		this.setContent(new Rectangle(min_x - prop, min_y - prop, width + 2
				* prop, height + 2 * prop));
		
		//System.out.printf("%d %d %d %d B\n", min_x, min_y, min_x + width,min_y + height);

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

	public void clearStuff()
	{
		if (figure == null)
			return;
		int size = this.figure.size();
		for (int i = 0; i < size; i++)
		{
			if (this.figure.get(i).getSegment() == null)
				continue;
			Iterator<Segment> segment = this.figure.get(i).getSegment()
					.iterator();
			Segment seg;

			while (segment.hasNext())
			{
				seg = segment.next();
				seg.setRange(null);
			}

			this.figure.get(i).getSegment().clear();
			this.figure.get(i).setSegment(null);
		}
		this.figure.clear();
		this.figure = null;
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

	public boolean isWithExtract()
	{
		return withExtract;
	}

	public void setWithExtract(boolean withExtract)
	{
		this.withExtract = withExtract;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public boolean isStatus()
	{
		return status;
	}

	public void setStatus(boolean status)
	{
		this.status = status;
	}

	public Figure[] getCompleteFigure()
	{
		return completeFigure;
	}

	public void setCompleteFigure(Figure[] completeFigure)
	{
		this.completeFigure = completeFigure;
	}


	public int getOutOrgX()
	{
		return outOrgX;
	}

	public void setOutOrgX(int outOrgX)
	{
		this.outOrgX = outOrgX;
	}

	public int getOutOrgY()
	{
		return outOrgY;
	}

	public void setOutOrgY(int outOrgY)
	{
		this.outOrgY = outOrgY;
	}

	public int getOutExtX()
	{
		return outExtX;
	}

	public void setOutExtX(int outExtX)
	{
		this.outExtX = outExtX;
	}

	public int getOutExtY()
	{
		return outExtY;
	}

	public void setOutExtY(int outExtY)
	{
		this.outExtY = outExtY;
	}

	public String getOrigin()
	{
		return origin;
	}

	public void setOrigin(String origin)
	{
		this.origin = origin;
	}
}
