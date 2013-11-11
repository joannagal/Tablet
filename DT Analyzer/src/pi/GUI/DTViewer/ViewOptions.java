package pi.GUI.DTViewer;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import pi.graph.drawing.Graph;

public class ViewOptions extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private JButton signalOn;
	private JButton signalOff;
	
	private JButton thickOn;
	private JButton thickOff;
	
	private JButton angleOn;
	private JButton angleOff;
	
	private JButton linearOn;
	private JButton linearOff;
	
	private Graph graph;
	
	public ViewOptions(Dimension size, Graph graph)
	{
		this.setGraph(graph);
		
		this.setSize(size);
		this.setLayout(null);
		this.setBorder(BorderFactory.createTitledBorder("View Options"));
		
		this.thickOn = new JButton("Signal ON");
		this.thickOn.setSize(new Dimension(90, 30));
		this.thickOn.setLocation(5, 20);
		this.thickOn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				getGraph().setSignalShow(true);
				getGraph().recalculate();
				getGraph().draw();
			}
		});
		this.add(thickOn);
		
		this.thickOff = new JButton("Signal Off");
		this.thickOff.setSize(new Dimension(90, 30));
		this.thickOff.setLocation(95, 20);
		thickOff.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				getGraph().setSignalShow(false);
				getGraph().recalculate();
				getGraph().draw();
			}
		});
		this.add(thickOff);
		
		this.linearOn = new JButton("Linear On");
		this.linearOn.setSize(new Dimension(90, 30));
		this.linearOn.setLocation(5, 60);
		this.linearOn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				getGraph().setLinearShow(true);
				getGraph().recalculate();
				getGraph().draw();
			}
		});
		this.add(linearOn);
		
		this.linearOff = new JButton("Linear Off");
		this.linearOff.setSize(new Dimension(90, 30));
		this.linearOff.setLocation(95, 60);
		this.linearOff.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				getGraph().setLinearShow(false);
				getGraph().recalculate();
				getGraph().draw();
			}
		});
		this.add(linearOff);
		
		this.thickOn = new JButton("Thick ON");
		this.thickOn.setSize(new Dimension(90, 30));
		this.thickOn.setLocation(5, 100);
		this.thickOn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				getGraph().setThicknessShow(true);
				getGraph().recalculate();
				getGraph().draw();
			}
		});
		this.add(thickOn);
		
		this.thickOff = new JButton("Thick Off");
		this.thickOff.setSize(new Dimension(90, 30));
		this.thickOff.setLocation(95, 100);
		thickOff.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				getGraph().setThicknessShow(false);
				getGraph().recalculate();
				getGraph().draw();
			}
		});
		this.add(thickOff);
	
		this.angleOn = new JButton("Angle ON");
		this.angleOn.setSize(new Dimension(90, 30));
		this.angleOn.setLocation(5, 140);
		this.angleOn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				getGraph().setDrawingAngleShow(true);
				getGraph().recalculate();
				getGraph().draw();
			}
		});
		this.add(angleOn);
		
		this.angleOff = new JButton("Angle Off");
		this.angleOff.setSize(new Dimension(90, 30));
		this.angleOff.setLocation(95, 140);
		this.angleOff.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				getGraph().setDrawingAngleShow(false);
				getGraph().recalculate();
				getGraph().draw();
			}
		});
		this.add(angleOff);
		
		
	}

	public Graph getGraph()
	{
		return graph;
	}

	public void setGraph(Graph graph)
	{
		this.graph = graph;
	}
}
