package pi.gui.project.toolbar;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import pi.gui.project.toolbar.statistics.StatisticsView;
import pi.project.Project;

public class ToolbarView extends JPanel
{
	private static final long serialVersionUID = 1L;

	private Project project = null;
	private ToolbarController controller;

	private JButton informationsButton = new JButton("Informations");
	private JButton statisticsButton = new JButton("Statistics");
	private JButton compareButton = new JButton("Compare");
	
	private StatisticsView statisticsView = new StatisticsView();
	
	public ToolbarView(Project project)
	{
		controller = new ToolbarController(this);
		this.project = project;

		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		this.setMinimumSize(new Dimension(40, 40));

		this.informationsButton.setActionCommand("INFO");
		this.informationsButton.addActionListener(this.controller);

		this.statisticsButton.setActionCommand("STAT");
		this.statisticsButton.addActionListener(this.controller);

		this.compareButton.setActionCommand("COMP");
		this.compareButton.addActionListener(this.controller);
		
		this.add(this.informationsButton);
		this.add(this.statisticsButton);
	}

	public StatisticsView getStatisticsView()
	{
		return statisticsView;
	}

	public void setStatisticsView(StatisticsView statisticsView)
	{
		this.statisticsView = statisticsView;
	}

}
