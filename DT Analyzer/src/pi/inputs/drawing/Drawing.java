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
import java.util.Random;

public class Drawing
{
	private Rectangle content;

	private ArrayList<PacketData> packet;
	private ArrayList<Figure> figure;

	private int pressureAvoid = 0;
	private int maxPressure = 1024;
	private int totalTime = 0;
	private int breakFigureDistance = 300;

	public Drawing(String path)
	{
		this.createFromFile(path);

		FigureExtractor extractor = new FigureExtractor();
		extractor.extract(this);

		System.out.printf("::: %d\n", this.figure.size());
		
		Linearize linearize = new Linearize();
		linearize.linearize(this);
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
				//System.out.printf("File name length: %d\n", fileNameLength);

				byte[] fileName = new byte[fileNameLength];
				for (int i = 0; i < fileNameLength; i++)
					fileName[i] = data[shift + i];
				String str = new String(fileName);
				shift += fileNameLength;
				//System.out.printf("File name: %s\n", str);
			} else
				shift++;

			int dateLength = this.getInt(data, shift);
			if (dateLength > 0)
			{
				shift += 4;
				//System.out.printf("Date length: %d\n", dateLength);

				byte[] date = new byte[dateLength];
				for (int i = 0; i < dateLength; i++)
					date[i] = data[shift + i];
				String str = new String(date);
				shift += dateLength;
				//System.out.printf("Date: %s\n", str);
			} else
				shift++;

			int memoLength = this.getInt(data, shift);
			if (memoLength > 0)
			{
				shift += 4;
				//System.out.printf("Memo length: %d\n", memoLength);

				byte[] memo = new byte[memoLength];
				for (int i = 0; i < memoLength; i++)
					memo[i] = data[shift + i];
				String str = new String(memo);
				shift += memoLength;
				//System.out.printf("Memo: %s\n", str);
			} else
				shift++;

			long outOrgX = this.getInt(data, shift);
			shift += 4;
			//System.out.printf("OUT_ORG_X: %d\n", outOrgX);

			long outOrgY =this.getInt(data, shift);
			shift += 4;
			//System.out.printf("OUT_ORG_Y: %d\n", outOrgY);

			long outExtX = this.getInt(data, shift);
			shift += 4;
			//System.out.printf("OUT_EXT_X: %d\n", outExtX);

			long outExtY = this.getInt(data, shift);
			shift += 4;
			//System.out.printf("OUT_EXT_Y: %d\n", outExtY);

			int numPackages = this.getInt(data, shift);
			shift += 4;
			//System.out.printf("Number of packages: %d\n", numPackages);

			this.setPressureAvoid(0);
			this.setMaxPressure(1024);
			this.setContent(new Rectangle(1500, 2000, 16000, 13000));

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
				temp.setPkTime( this.getInt(data, shift));
				temp.setPkX( this.getInt(data, shift + 4));
				temp.setPkY( this.getInt(data, shift + 8));
				temp.setPkPressure( this.getInt(data, shift + 12));
				temp.setPkAzimuth( this.getInt(data, shift + 16));
				temp.setPkAltitude( this.getInt(data, shift + 20));
				packet.add(temp);

				/*if ((cnt > 200) && (cnt < 450)) System.out.printf("::  %d %d %d %d %d %d\n",
				temp.getPkTime(),temp.getPkX(), temp.getPkY(),
				temp.getPkPressure(), temp.getPkAzimuth() ,temp.getPkAltitude());*/

				shift += 24;
			}

			//System.out.printf("%d %d\n", cnt, numPackages);
			//System.out.printf("%d %d\n", shift, size);

			this.setTotalTime(maxTime);

		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
}
