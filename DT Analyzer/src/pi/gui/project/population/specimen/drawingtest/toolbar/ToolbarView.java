package pi.gui.project.population.specimen.drawingtest.toolbar;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import pi.gui.project.population.specimen.drawingtest.DrawingTestView;
import pi.inputs.drawing.Drawing;

public class ToolbarView extends JPanel
{
	private static final long serialVersionUID = 1L;

	private Timeline timeline;
	private Pressure pressure;
	private LoadTest loadTest;
	
	public ToolbarView(DrawingTestView parent)
	{
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		this.setMinimumSize(new Dimension(50, 50));
		// --- TIME PANEL
		
		this.loadTest = new LoadTest(parent);
		this.add(this.loadTest);
		
		this.pressure = new Pressure(parent.getGraph());
		this.add(this.pressure);
		
		this.timeline = new Timeline(parent.getGraph());
		this.add(this.timeline);

	}
	
	public void rebuild(Drawing drawing)
	{
		this.timeline.rebuild(drawing);
		this.pressure.rebuild(drawing);
	}
}
