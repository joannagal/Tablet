package pi.gui.specimen.drawingtest;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

import pi.graph.drawing.Graph;
import pi.gui.specimen.drawingtest.toolbar.ToolbarView;
import pi.inputs.drawing.Drawing;
import pi.utilities.Margin;

public class DrawingTestView extends JPanel
{
	private static final long serialVersionUID = 1L;

	private Margin margin = new Margin(200.0d, 20.0d, 20.0d, 20.0d);
	private Graph graph;
	
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
		c.gridy = 1;;
	
		this.add(graph, c);
		
		this.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent e) {
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
		if (drawing != null)
		{
			this.graph.setDrawing(drawing);
			this.graph.recalculate();
			this.toolbarView.rebuild(drawing);
			this.graph.setCurrentTime(drawing.getTotalTime());
			this.graph.draw();
		}
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

}
