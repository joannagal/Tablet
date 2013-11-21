package pi.gui.specimen;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import pi.gui.specimen.drawingtest.DrawingTestView;
import pi.gui.specimen.toolbar.ToolbarView;
import pi.inputs.drawing.Drawing;
import pi.population.Specimen;

public class SpecimenView extends JPanel
{
	private static final long serialVersionUID = 1L;

	private SpecimenController controller;
	private Specimen specimen;

	private ToolbarView toolbar;

	private DrawingTestView before;
	private DrawingTestView after;

	private JSplitPane splitPane;

	public SpecimenView(Specimen specimen)
	{
		this.specimen = specimen;
		this.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;
		c.gridy = 1;
		c.weighty = 1.0d;

		if (specimen.getAfter() == null)
		{
			this.before = new DrawingTestView();
			before.setDrawing(specimen.getBefore());
			this.add(this.before, c);
		} else
		{
			this.before = new DrawingTestView();
			before.setDrawing(specimen.getBefore());

			this.after = new DrawingTestView();
			after.setDrawing(specimen.getAfter());

			this.splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
					this.before, this.after);

			this.splitPane.setOneTouchExpandable(true);
			
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			this.splitPane.setDividerLocation(dimension.height);
			this.add(this.splitPane, c);
		}

		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0d;
		c.weighty = 0.0d;

		this.toolbar = new ToolbarView(this);
		this.add(this.toolbar, c);

	}

	public void recalculate()
	{
		if (this.splitPane != null)
			this.splitPane.setDividerLocation(0.5d);
	}

	public void redraw()
	{
		this.before.redraw();
		if (this.after != null)
		{
			this.after.redraw();
		}
	}
	
	public JSplitPane getSplitPane()
	{
		return this.splitPane;
	}

	public DrawingTestView getBefore()
	{
		return before;
	}

	public void setBefore(DrawingTestView before)
	{
		this.before = before;
	}

	public DrawingTestView getAfter()
	{
		return after;
	}

	public void setAfter(DrawingTestView after)
	{
		this.after = after;
	}

	public Specimen getSpecimen()
	{
		return specimen;
	}

	public void setSpecimen(Specimen specimen)
	{
		this.specimen = specimen;
	}

	public SpecimenController getController()
	{
		return controller;
	}

	public void setController(SpecimenController controller)
	{
		this.controller = controller;
	}

}
