package pi.gui.project.population.specimen.drawingtest.toolbar;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
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
	private JCheckBox figureExtract = new JCheckBox("Extract");
	private Graph graph;
	
	public Pressure(Graph graph)
	{
		this.graph = graph;

		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		this.setMinimumSize(new Dimension(50, 50));
		
		this.setBorder(BorderFactory.createTitledBorder("Pressure Ignore"));

		this.pressureSlider = new JSlider();
		this.pressureSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				if (getGraph().getDrawing() != null)
				{
					getGraph().getDrawing().setPressureAvoid(pressureSlider.getValue());
					getGraph().getDrawing().recalculate(false);
					getGraph().recalculate();
					getGraph().draw();
					setBorder(BorderFactory.createTitledBorder(String.format("Pressure %d/1024", pressureSlider.getValue())));
				}
			}
		});
		this.add(this.pressureSlider);
		
		this.pressureSlider.setMinimum(1);
		this.pressureSlider.setMaximum(1024);
		this.pressureSlider.setValue(128);

		this.add(this.figureExtract);
		this.figureExtract.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				if (getGraph().getDrawing() != null)
				{
					getGraph().getDrawing().setWithExtract(figureExtract.isSelected());
				}
			}
		});
		

	}
	
	public void rebuild(Drawing drawing)
	{
		if (drawing != null)
		{
			this.pressureSlider.setValue(graph.getDrawing().getPressureAvoid());	
			this.setBorder(BorderFactory.createTitledBorder(String.format("Pressure %d/1024", pressureSlider.getValue())));
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
