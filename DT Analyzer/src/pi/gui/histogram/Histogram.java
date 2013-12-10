package pi.gui.histogram;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Histogram extends JPanel
{
	private static final long serialVersionUID = 1L;

	private ArrayList<ArrayList<Double>> data = null;
	private ArrayList<ArrayList<Integer>> counter = null;

	private final Color backgroundColor = new Color(255, 255, 255, 255);
	private final Color borderColor = new Color(0, 0, 0, 255);
	private final Color gridColor = new Color(170, 170, 170, 255);
	private final Color fontColor = new Color(0, 0, 0, 255);

	private final Color[] drawColor =
	{ new Color(255, 0, 0, 255), new Color(0, 255, 0, 255),
			new Color(0, 0, 255, 255), new Color(255, 0, 255, 255),
			new Color(255, 255, 0, 255), new Color(0, 255, 255, 255), };

	private int maxCounter = 0;
	private int ranges = 5;

	private final double innerScale = 0.9d;
	private final double margin = 30.0d;
	private final double marginLeft = 60.0d;
	private int divider = 0;

	private double minValue = 0.0d;
	private double maxValue = 0.0d;

	private JButton addSegmentButton = new JButton("+");
	private JButton delSegmentButton = new JButton("-");

	public void recalculate()
	{
		if (getData() == null)
			return;

		System.out.printf("DEEEBUG: %d %d\n", data.size(), ranges);

		counter = new ArrayList<ArrayList<Integer>>(data.size());
		for (int i = 0; i < data.size(); i++)
		{
			counter.add(new ArrayList<Integer>(ranges));
		}

		this.minValue = 1000000.0d;
		this.maxValue = -1000000.0d;
		this.maxCounter = 0;

		for (int i = 0; i < data.size(); i++)
		{
			int size = data.get(i).size();
			for (int j = 0; j < size; j++)
			{
				if (getData().get(i).get(j) > maxValue)
					maxValue = getData().get(i).get(j);
				if (getData().get(i).get(j) < minValue)
					minValue = getData().get(i).get(j);
			}
			
			size = counter.get(i).size();

			for (int j = 0; j < ranges; j++)
			{
				counter.get(i).add(0);
			}

		}

		double range = (double) (maxValue - minValue) / (double) ranges;

		for (int i = 0; i < data.size(); i++)
		{
			int size = data.get(i).size();
			double value = 0.0d;
			ArrayList<Integer> cnt = counter.get(i);

			for (int j = 0; j < size; j++)
			{
				value = getData().get(i).get(j);
				for (int k = 1; k <= ranges; k++)
				{
					if ((value < minValue + (double) k * range)
							|| (k == ranges))
					{
						int tmp = cnt.get(k - 1);
						tmp++;
						if (tmp > maxCounter)
							maxCounter = tmp;
						cnt.set(k - 1, tmp);
						break;
					}
				}
			}
		}

		if (maxCounter <= 3)
			divider = maxCounter;
		else if (maxCounter <= 100)
		{
			int max = -1;
			int half = maxCounter / 2 + 1;
			for (int i = 3; i <= half; i++)
			{
				if (maxCounter % i == 0)
				{
					if (i > max)
						max = i;
				}
			}

			if (max == -1)
				divider = 2;
			else
				divider = max;
		} else
			divider = 10;

	}

	@Override
	public void paintComponent(Graphics graphics)
	{

		this.drawBackground(graphics);
		this.drawBorder(graphics);

		if (data == null)
			return;

		this.drawGrid(graphics);

		double left = marginLeft;
		double bottom = this.getSize().height - margin;
		double width = (this.getSize().width - margin - marginLeft)
				/ (double) ranges;
		double height = (this.getSize().height - 2 * margin);

		double innerShift = width * (1.0d - this.innerScale) * 0.5d;

		double smallWidth = width / data.size();
		double posX, posY, prop;

		double dV = (this.maxValue - this.minValue) / (double) this.ranges;

		for (int i = 0; i < data.size(); i++)
		{
			ArrayList<Integer> cnt = counter.get(i);
			for (int j = 0; j < ranges; j++)
			{
				posX = 0.5d + left + innerShift + (width) * (double) j;
				posX += i * smallWidth * this.innerScale;

				prop = (double) (cnt.get(j)) / (double) (this.maxCounter);
				prop *= (double) height;
				posY = bottom - prop;

				graphics.setColor(this.drawColor[i]);
				graphics.fillRect((int) posX, (int) (posY), (int) (smallWidth
						* this.innerScale + 0.5d), (int) prop);

				graphics.setColor(this.fontColor);
				graphics.drawString(String.format("%d", cnt.get(j)),
						(int) posX, (int) (posY - 6));

				if (i == 0)
				{
					graphics.setColor(this.fontColor);
					graphics.drawString(
							String.format("%f", minValue + (double) j * dV),
							(int) posX, (int) (bottom + 10 + 10 * (j % 2)));

					if (j == ranges - 1)
					{
						posX += width;

						graphics.drawString(
								String.format("%f", minValue + (double) (j + 1)
										* dV), (int) posX,
								(int) (bottom + 10 + 10 * ((j + 1) % 2)));
					}
				}

			}

		}

	}

	public void drawGrid(Graphics graphics)
	{
		double left = marginLeft;
		double bottom = this.getSize().height - margin;
		double width = (this.getSize().width - margin - marginLeft);
		double height = (this.getSize().height - 2 * margin) / (double) divider;
		double posY = 0.0d;

		graphics.setColor(this.gridColor);

		for (int i = 1; i <= divider; i++)
		{
			posY = bottom - i * height;
			graphics.drawLine((int) left, (int) posY, (int) (left + width),
					(int) posY);
		}

	}

	public Histogram()
	{
		this.setLayout(null);

		this.addSegmentButton.setBounds(5, 5, 45, 40);
		this.addSegmentButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				addSegment();
			}

		});
		this.add(this.addSegmentButton);

		this.delSegmentButton.setBounds(5, 45, 45, 40);
		this.delSegmentButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				delSegment();
			}

		});
		this.add(this.delSegmentButton);
	}

	public void draw()
	{
		if (getData() == null)
			return;
		this.repaint();
	}

	// ------------------------------------------
	// SIMPLE DRAWING BORDER
	public void drawBorder(Graphics graphics)
	{
		Rectangle frame = this.getBounds();
		graphics.setColor(this.borderColor);
		graphics.drawRect(0, 0, frame.width - 1, frame.height - 1);
	}

	// ------------------------------------------
	// SIMPLE DRAWING BACKGROUND
	public void drawBackground(Graphics graphics)
	{
		Rectangle frame = this.getBounds();
		graphics.setColor(this.backgroundColor);
		graphics.fillRect(0, 0, frame.width - 1, frame.height - 1);
	}

	public void addSegment()
	{
		if (this.ranges < 10)
		{
			this.ranges++;
			this.recalculate();
			this.draw();
		}
	}

	public void delSegment()
	{
		if (this.ranges > 3)
		{
			this.ranges--;
			this.recalculate();
			this.draw();
		}
	}

	public int getRanges()
	{
		return ranges;
	}

	public void setRanges(int ranges)
	{
		this.ranges = ranges;
	}

	public ArrayList<ArrayList<Double>> getData()
	{
		return data;
	}

	public void setData(ArrayList<ArrayList<Double>> data)
	{
		this.data = data;
	}

}
