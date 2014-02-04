package pi.graph.drawing.popup;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;

import pi.graph.drawing.Graph;
import pi.inputs.drawing.Figure;

public class PopUp extends JPopupMenu
{
	private static final long serialVersionUID = 1L;

	private Graph graph;
	private Point point;

	private JMenuItem delete = new JMenuItem("Delete");
	private JMenuItem all = new JRadioButtonMenuItem("All Signal");
	private JMenuItem thick = new JRadioButtonMenuItem("Thickness");
	private JMenuItem angle = new JRadioButtonMenuItem("Angle");

	private JMenu changeType = new JMenu("Change Type");
	private JMenuItem zigzag = new JMenuItem("ZigZag");
	private JMenuItem circleLeft = new JMenuItem("Circle-Left");
	private JMenuItem circleRight = new JMenuItem("Circle-Right");
	private JMenuItem firstLine = new JMenuItem("First Line");
	private JMenuItem secondLine = new JMenuItem("Second Line");
	private JMenuItem brokenLine = new JMenuItem("Broken Line");
	private JMenuItem spiralIn = new JMenuItem("Spiral In");
	private JMenuItem spiralOut = new JMenuItem("Spiral Out");

	public PopUp(Graph graph)
	{
		this.all.setSelected(false);
		this.thick.setSelected(false);
		this.angle.setSelected(false);

		this.graph = graph;

		this.add(this.all);
		this.all.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				if (getGraph().getDrawing() == null)
					return;
				getGraph().setSignalShow(all.isSelected());
				getGraph().recalculate();
				getGraph().draw();
			}
		});

		this.add(this.thick);
		this.thick.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				if (getGraph().getDrawing() == null)
					return;
				getGraph().setThicknessShow(thick.isSelected());
				getGraph().recalculate();
				getGraph().draw();
			}
		});

		this.add(this.angle);
		this.angle.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				if (getGraph().getDrawing() == null)
					return;
				getGraph().setDrawingAngleShow(angle.isSelected());
				getGraph().recalculate();
				getGraph().draw();
			}
		});

		this.add(new JSeparator());

		this.add(this.changeType);
		this.changeType.add(this.zigzag);
		this.zigzag.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				if (point == null)
					return;
				getGraph().changeFigure(point.x, point.y, Figure.ZIGZAG);
			}
		});

		this.changeType.add(this.circleLeft);
		this.circleLeft.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				if (point == null)
					return;
				getGraph().changeFigure(point.x, point.y, Figure.CIRCLELEFT);
			}
		});

		this.changeType.add(this.circleRight);
		this.circleRight.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				if (point == null)
					return;
				getGraph().changeFigure(point.x, point.y, Figure.CIRCLERIGHT);
			}
		});

		this.changeType.add(this.firstLine);
		this.firstLine.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				if (point == null)
					return;
				getGraph().changeFigure(point.x, point.y, Figure.FIRSTLINE);
			}
		});

		this.secondLine.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				if (point == null)
					return;
				getGraph().changeFigure(point.x, point.y, Figure.SECONDLINE);
			}
		});

		this.brokenLine.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				if (point == null)
					return;
				getGraph().changeFigure(point.x, point.y, Figure.BROKENLINE);
			}
		});

		this.spiralIn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				if (point == null)
					return;
				getGraph().changeFigure(point.x, point.y, Figure.SPIRALIN);
			}
		});

		this.spiralOut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				if (point == null)
					return;
				getGraph().changeFigure(point.x, point.y, Figure.SPIRALOUT);
			}
		});

		this.changeType.add(this.secondLine);
		this.changeType.add(this.brokenLine);
		this.changeType.add(this.spiralIn);
		this.changeType.add(this.spiralOut);

		this.add(new JSeparator());

		this.add(this.delete);
		this.delete.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				if (point == null)
					return;
				getGraph().deleteFigure(point.x, point.y);
			}
		});

	}

	public Graph getGraph()
	{
		return this.graph;
	}

	public Point getPoint()
	{
		return point;
	}

	public void setPoint(Point point)
	{
		this.point = point;
	}

}
