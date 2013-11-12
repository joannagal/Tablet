package pi.GUI.DTViewer;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
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
	private JLabel pressureLabel;
	private Graph graph;
	
	public Pressure(Dimension size, Graph graph)
	{
		this.graph = graph;

		this.setSize(size);
		this.setLayout(null);
		this.setBorder(BorderFactory.createTitledBorder("Pressure Ignore"));
		
		this.pressureLabel = new JLabel();
		this.pressureLabel.setSize(new Dimension(size.width - 20, 30));
		this.pressureLabel.setLocation(10, 40);
		this.add(pressureLabel);
		
		this.pressureSlider = new JSlider();
		this.pressureSlider.setSize(new Dimension(size.width - 20, 30));
		this.pressureSlider.setLocation(10, 20);
		this.pressureSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				if (getGraph().getDrawing() != null)
				{
					getGraph().getDrawing().setPressureAvoid(pressureSlider.getValue());
					getGraph().getDrawing().recalculate();
					getGraph().recalculate();
					getGraph().draw();
					pressureLabel.setText(String.format("Pressure %d/1024", pressureSlider.getValue()));
				}
			}
		});
		this.add(this.pressureSlider);
		
		this.pressureSlider.setMinimum(1);
		this.pressureSlider.setMaximum(1024);
		this.pressureSlider.setValue(128);
	}
	
	public void rebuild(Drawing drawing)
	{
		if (drawing != null)
		{
			this.pressureSlider.setValue(graph.getDrawing().getPressureAvoid());	
			pressureLabel.setText(String.format("Pressure %d/1024", pressureSlider.getValue()));
			System.out.printf("ASDASDAS %d\n", graph.getDrawing().getPressureAvoid());
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
