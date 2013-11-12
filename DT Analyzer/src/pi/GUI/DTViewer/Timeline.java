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

public class Timeline extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private JSlider timeSlider;
	private JLabel currentTime;
	
	private Graph graph;
	
	public Timeline(Dimension size, Graph graph)
	{
		this.graph = graph;
		
		this.setSize(size);
		this.setLayout(null);
		this.setBorder(BorderFactory.createTitledBorder("Timeline"));
		
		this.timeSlider = new JSlider();
		this.timeSlider.setSize(new Dimension(size.width - 20, 30));
		this.timeSlider.setLocation(10, 20);
		this.timeSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				if (getGraph().getDrawing() != null)
				{
					applyNewLabel();
					getGraph().setCurrentTime(timeSlider.getValue());
				}
			}
		});
		this.add(this.timeSlider);
		
		this.currentTime = new JLabel();
		this.currentTime.setLocation(15, 45);
		this.currentTime.setSize(new Dimension(size.width, 20));
		this.add(this.currentTime);
		
		this.rebuild(graph.getDrawing());
	}
	
	public void rebuild(Drawing drawing)
	{
		this.timeSlider.setMinimum(0);
		
		if (drawing != null)
		{
			this.timeSlider.setMaximum(drawing.getTotalTime());
		}
		else
		{
			this.timeSlider.setMaximum(1);
		}
		
		this.timeSlider.setValue(this.timeSlider.getMaximum());
	
		this.applyNewLabel();
	}


	public void applyNewLabel()
	{
		this.currentTime.setText(String.format("Time %d/%d", this.timeSlider.getValue(), this.timeSlider.getMaximum()));
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
