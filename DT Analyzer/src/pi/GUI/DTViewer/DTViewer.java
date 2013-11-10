package pi.GUI.DTViewer;

import java.awt.Dimension;

import javax.swing.JPanel;

import pi.graph.drawing.Graph;
import pi.inputs.drawing.Drawing;
import pi.utilities.Margin;

public class DTViewer extends JPanel
{
	private static final long serialVersionUID = 1L;

	private Margin margin = new Margin(200.0d, 20.0d, 20.0d, 20.0d);
	private Graph graph;
	
	private Timeline timeline;
	private Pressure pressure;
	private ViewOptions voptions;

	public DTViewer(Dimension size, Drawing drawing)
	{
		this.setLayout(null);
		
		this.graph = new Graph(new Dimension(0, 0), drawing);
		this.graph.setLocation((int) margin.getLeft(), (int) margin.getTop());
		
		if (drawing != null)
		{
			this.graph.setCurrentTime(drawing.getTotalTime());
		}
		
		// --- TIME PANEL
		
		this.timeline = new Timeline(new Dimension((int)this.margin.getLeft() - 10, 90), graph);
		this.timeline.setLocation(5, (int)this.margin.getTop());
		this.add(timeline);
		
		this.pressure = new Pressure(new Dimension((int)this.margin.getLeft() - 10, 60), graph);
		this.pressure.setLocation(5, (int)this.margin.getTop() + 100);
		this.add(pressure);
		
		this.voptions = new ViewOptions(new Dimension((int)this.margin.getLeft() - 10, 150), graph);
		this.voptions.setLocation(5, (int)this.margin.getTop() + 170);
		this.add(voptions);

		this.setSize(size);
		this.add(graph);

	}

	// Sztuczka ze zmiana size
	// odpalamy setSize klasy nadrzednej
	// i dodajemy recalculate
	@Override
	public void setSize(Dimension d)
	{
		super.setSize(d);
		this.recalculate();
	}

	public void recalculate()
	{
		double width = this.getSize().width
				- (this.margin.getLeft() + this.margin.getRight());
		double height = this.getSize().height
				- (this.margin.getTop() + this.margin.getBottom());

		if (width < 0.0d)
			width = 0.0d;
		else if (height < 0.0d)
			height = 0.0d;

		this.graph.setSize((int)width, (int) height);
		this.graph.recalculate();
	}

	// ----------- GETTERS AND SETTERS

	public Drawing getDrawing()
	{
		return this.graph.getDrawing();
	}

	public void setDrawing(Drawing drawing)
	{
		if (drawing != null)
		{
			this.graph.setDrawing(drawing);
			this.graph.setCurrentTime(drawing.getTotalTime());
		}
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
