package pi.graph.drawing.popup;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;

import pi.graph.drawing.Graph;


public class PopUp extends JPopupMenu
{
	private static final long serialVersionUID = 1L;

	private Graph graph;
	private Point point;
	
	private JMenuItem delete = new JMenuItem("Delete");
	private JMenuItem all = new JRadioButtonMenuItem("All Signal");
	private JMenuItem thick = new JRadioButtonMenuItem("Thickness");
	private JMenuItem angle = new JRadioButtonMenuItem("Angle");
	
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
				if (getGraph().getDrawing() == null) return;
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
				if (getGraph().getDrawing() == null) return;
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
				if (getGraph().getDrawing() == null) return;
				getGraph().setDrawingAngleShow(angle.isSelected());
				getGraph().recalculate();
				getGraph().draw();	
			}
		});
		
		this.add(new JSeparator());
		
		this.add(this.delete);
		this.delete.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				if (point == null) return;
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
