package pi.gui.project;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import pi.gui.project.population.PopulationView;
import pi.gui.project.toolbar.ToolbarView;
import pi.inputs.drawing.Drawing;
import pi.project.Project;

public class ProjectView extends JPanel
{
	private static final long serialVersionUID = 1L;

	private ProjectController controller;

	private ToolbarView toolbar;

	private PopulationView first;
	private PopulationView second;

	private JSplitPane splitPane;

	public ProjectView(Project project)
	{
		controller = new ProjectController(this);

		this.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;
		c.gridy = 1;
		c.weighty = 1.0d;

		int type = project.getType();

		if ((type == Project.SPECIMEN_PAIR)
				|| (type == Project.SPECIMEN_SINGLE))
		{
			this.first = new PopulationView(project.getFirstPopulation(), type,
					true);
			this.add(this.first, c);
		} else
		{
			this.first = new PopulationView(project.getFirstPopulation(), type,
					true);
			this.second = new PopulationView(project.getSecondPopulation(),
					type, false);

			this.splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
					this.first, this.second);

			this.splitPane.setOneTouchExpandable(true);

			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			this.splitPane.setDividerLocation(dimension.height / 2);
			this.add(this.splitPane, c);
		}

		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0d;
		c.weighty = 0.0d;

		this.toolbar = new ToolbarView(project);
		this.add(this.toolbar, c);
	}

	public void updateLabels()
	{
		this.first.updateLabels();
		if (this.second != null)
			this.second.updateLabels();
	}

	public void addSpecimenPair(Drawing firstBefore, Drawing firstAfter,
			Drawing secondBefore, Drawing secondAfter)
	{
		if (firstBefore != null)
			this.first.addSpecimen(firstBefore, firstAfter);
		if (secondBefore != null)
			this.second.addSpecimen(secondBefore, secondAfter);
	}

	public void deleteSpecimen(int index)
	{
		this.first.deleteSpecimen(index);
	}

	public void deleteSpecimen(int index, boolean first)
	{
		if (first)
			this.first.deleteSpecimen(index);
		else
			this.second.deleteSpecimen(index);
	}

	public void deletePair(int index)
	{
		this.first.deleteSpecimen(index);
		this.second.deleteSpecimen(index);
	}

}
