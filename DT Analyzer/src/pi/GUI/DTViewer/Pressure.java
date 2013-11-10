package pi.GUI.DTViewer;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pi.graph.drawing.Graph;
import pi.inputs.drawing.Drawing;

public class Pressure extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private JSlider pressureSlider;
	private Graph graph;
	
	public Pressure(Dimension size, Graph graph)
	{
		this.setGraph(graph);

		this.setSize(size);
		this.setLayout(null);
		this.setBorder(BorderFactory.createTitledBorder("Pressure Ignore"));
		
		this.pressureSlider = new JSlider();
		this.pressureSlider.setSize(new Dimension(size.width - 20, 30));
		this.pressureSlider.setLocation(10, 20);
		this.pressureSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				getGraph().getDrawing().setPressureAvoid(pressureSlider.getValue());
				getGraph().recalculate();
				getGraph().draw();
			}
		});
		this.add(this.pressureSlider);
	
		this.rebuild(graph.getDrawing());
	}
	
	public void rebuild(Drawing drawing)
	{
		this.pressureSlider.setMinimum(0);
		this.pressureSlider.setMaximum(1024);
		this.pressureSlider.setValue(drawing.getPressureAvoid());
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
