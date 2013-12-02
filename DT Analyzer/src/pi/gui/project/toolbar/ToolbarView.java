package pi.gui.project.toolbar;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import pi.project.Project;

public class ToolbarView extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private Project project = null;
	private ToolbarController controller;
	
	private JButton informationsButton = new JButton("Informations");
	private JButton statisticsButton = new JButton("Statistics");
	
	public ToolbarView(Project project)
	{
		controller = new ToolbarController(this);
		this.project = project;
		
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		this.setMinimumSize(new Dimension(40, 40));

		this.add(this.informationsButton);
		this.add(this.statisticsButton);
	}





}
