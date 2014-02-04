package pi.gui.dependgraph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;

public class DependGraph extends JPanel
{
	private static final long serialVersionUID = 1L;

	private ArrayList<ArrayList<Double>> data = null;

	private final Color backgroundColor = new Color(255, 255, 255, 255);
	private final Color borderColor = new Color(0, 0, 0, 255);
	private final Color gridColor = new Color(170, 170, 170, 255);
	private final Color fontColor = new Color(0, 0, 0, 255);

	private final Color[] drawColor =
	{ new Color(255, 0, 0, 255), new Color(0, 255, 0, 255),
			new Color(0, 0, 255, 255), new Color(255, 0, 255, 255),
			new Color(255, 255, 0, 255), new Color(0, 255, 255, 255), };

	private final Color[] mixedColor =
	{ new Color(255, 200, 200, 255), new Color(200, 255, 200, 255),
			new Color(200, 200, 255, 255), new Color(255, 200, 255, 255),
			new Color(255, 255, 200, 255), new Color(200, 255, 255, 255), };

	public final static int LINE = 0;
	public final static int POINT = 1;
	public final static int MIXED = 2;

	private int type = DependGraph.LINE;

	private final double margin = 30.0d;
	private final double marginLeft = 60.0d;

	private double minValue = 0.0d;
	private double maxValue = 0.0d;

	private double minTime = 0.0d;
	private double maxTime = 0.0d;

	private double lockedMaxTime = 0.0d;
	private boolean loockedMax = false;

	public void recalculate()
	{
		if (getData() == null)
			return;

		this.minValue = 1000000.0d;
		this.maxValue = -1000000.0d;
		this.minTime = 1000000.0d;
		this.maxTime = -1000000.0d;

		for (int i = 0; i < data.size(); i++)
		{
			int size = data.get(i).size();
			for (int j = 0; j < size - 1; j += 2)
			{
				if (getData().get(i).get(j) > maxTime)
					maxTime = getData().get(i).get(j);
				if (getData().get(i).get(j) < minTime)
					minTime = getData().get(i).get(j);

				if (getData().get(i).get(j + 1) > maxValue)
					maxValue = getData().get(i).get(j + 1);
				if (getData().get(i).get(j + 1) < minValue)
					minValue = getData().get(i).get(j + 1);
			}
		}

		if (this.loockedMax)
		{
			this.maxTime = this.lockedMaxTime;
		}

	}

	@Override
	public void paintComponent(Graphics graphics)
	{

		this.drawBackground(graphics);
		this.drawBorder(graphics);

		if (data == null)
			return;

		if (this.maxTime - this.minTime == 0.0d)
			return;
		if (this.maxValue - this.minValue == 0.0d)
			return;

		this.drawGrid(graphics);

		double left = marginLeft;
		double bottom = this.getSize().height - margin;
		double height = (this.getSize().height - 2 * margin);
		double width = (this.getSize().width - margin - marginLeft);
		double posX, posY, tmp, prop;

		double prevX = 0.0d, prevY = 0.0d;

		for (int i = 0; i < data.size(); i++)
		{
			int size = data.get(i).size();

			graphics.setColor(this.drawColor[i]);

			for (int j = 0; j < size - 1; j += 2)
			{
				tmp = data.get(i).get(j);

				if (this.loockedMax)
				{
					if (tmp > this.lockedMaxTime)
						break;
				}

				prop = (tmp - this.minTime) / (this.maxTime - this.minTime);
				posX = 0.5d + left + width * prop;

				tmp = data.get(i).get(j + 1);
				prop = (tmp - this.minValue) / (this.maxValue - this.minValue);
				posY = bottom - height * prop;

				if (this.type == DependGraph.LINE)
				{
					if (j != 0)
					{
						graphics.drawLine((int) prevX, (int) prevY, (int) posX,
								(int) posY);
					}
				} else if (type == DependGraph.MIXED)
				{
					if (j != 0)
					{
						graphics.setColor(this.mixedColor[i]);
						graphics.drawLine((int) (prevX), (int) (prevY - 1),
								(int) (posX), (int) (posY + 1));
					}

					graphics.setColor(this.drawColor[i]);
					graphics.fillOval((int) (posX - 3), (int) (posY - 3), 5, 5);

				} else
				{
					graphics.drawLine((int) (posX - 1), (int) (posY),
							(int) (posX + 1), (int) (posY));
					graphics.drawLine((int) (posX), (int) (posY - 1),
							(int) (posX), (int) (posY + 1));
				}

				prevX = posX;
				prevY = posY;

			}
		}

	}

	public void drawGrid(Graphics graphics)
	{
		int divider = 10;

		double left = marginLeft;
		double bottom = this.getSize().height - margin;
		double width = (this.getSize().width - margin - marginLeft);
		double height = (this.getSize().height - 2 * margin);
		double divX = width / (double) divider;
		double divY = height / (double) divider;
		double posY = 0.0d;
		double posX = 0.0d;

		double dV = (this.maxValue - this.minValue) / (double) divider;
		double dT = (this.maxTime - this.minTime) / (double) divider;

		for (int i = 0; i <= divider; i++)
		{
			graphics.setColor(this.gridColor);

			posX = left + i * divX;
			graphics.drawLine((int) posX, (int) bottom, (int) posX,
					(int) (bottom - height));

			posY = bottom - i * divY;
			graphics.drawLine((int) left, (int) posY, (int) (left + width),
					(int) posY);

			graphics.setColor(this.fontColor);
			graphics.drawString(
					String.format("%f", this.minValue + (double) i * dV),
					(int) 5, (int) (posY));

			graphics.drawString(
					String.format("%f", this.minTime + (double) i * dT),
					(int) posX, (int) (bottom + 12 + 12 * (i % 2)));
		}

	}

	public void draw()
	{
		if (getData() == null)
			return;
		this.repaint();
	}

	public void drawBorder(Graphics graphics)
	{
		Rectangle frame = this.getBounds();
		graphics.setColor(this.borderColor);
		graphics.drawRect(0, 0, frame.width - 1, frame.height - 1);
	}

	public void drawBackground(Graphics graphics)
	{
		Rectangle frame = this.getBounds();
		graphics.setColor(this.backgroundColor);
		graphics.fillRect(0, 0, frame.width - 1, frame.height - 1);
	}

	public void setLockedMaxTime(double value)
	{
		this.loockedMax = true;
		this.lockedMaxTime = value;
	}

	public void disableLockedValue()
	{
		this.loockedMax = false;
	}

	public ArrayList<ArrayList<Double>> getData()
	{
		return data;
	}

	public void setData(ArrayList<ArrayList<Double>> data)
	{
		this.data = data;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

}