package pi.gui.project.population.specimen.drawingtest;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

import pi.graph.drawing.Graph;
import pi.gui.project.population.specimen.drawingtest.toolbar.ToolbarView;
import pi.inputs.drawing.Drawing;
import pi.population.Specimen;

public class DrawingTestView extends JPanel
{
	private static final long serialVersionUID = 1L;

	private Graph graph;
	private Specimen specimen = null;
	private boolean before = false;
	private ToolbarView toolbarView;

	public DrawingTestView()
	{
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		this.graph = new Graph(new Dimension(0, 0));
		this.toolbarView = new ToolbarView(this);

		c.fill = GridBagConstraints.BOTH;
		c.gridy = 0;
		c.weightx = 1.0d;
		this.add(this.toolbarView, c);

		c.weighty = 1.0d;
		c.gridy = 1;
		;

		this.add(graph, c);

		this.addComponentListener(new ComponentAdapter()
		{
			public void componentResized(ComponentEvent e)
			{
				super.componentResized(e);
				recalculate();
			}
		});
	}

	public void recalculate()
	{

		this.graph.recalculate();
		this.graph.draw();
	}

	// ----------- GETTERS AND SETTERS

	public void setDrawing(Drawing drawing)
	{
		this.setDrawing(drawing, specimen, before);
	}
	
	public void setDrawing(Drawing drawing, Specimen specimen, boolean before)
	{
		//if (drawing != null) drawing.createStatus();
		
		this.setSpecimen(specimen);
		this.setBefore(before);
		
		this.graph.setDrawing(drawing);
		this.graph.recalculate();
		this.toolbarView.rebuild(drawing);
		if (drawing != null)
			this.graph.setCurrentTime(drawing.getTotalTime());
		else
		{
			this.graph.setCurrentTime(0);
		}
			
		this.graph.draw();

	}

	public void redraw()
	{
		this.graph.recalculate();
		this.graph.draw();
	}

	public Drawing getDrawing()
	{
		return this.graph.getDrawing();
	}

	public Graph getGraph()
	{
		return graph;
	}

	public void setGraph(Graph graph)
	{
		this.graph = graph;
	}

	public Specimen getSpecimen()
	{
		return specimen;
	}

	public void setSpecimen(Specimen specimen)
	{
		this.specimen = specimen;
	}

	public boolean isBefore()
	{
		return before;
	}

	public void setBefore(boolean before)
	{
		this.before = before;
	}

}
