package pi.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;

import pi.gui.menu.MenuController;
import pi.gui.menu.MenuView;
import pi.gui.project.ProjectView;
import pi.project.Project;
//import pi.gui.menu.MenuController;
//import pi.gui.menu.MenuView;
import pi.shared.SharedController;

public class OurFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	private MenuView menuBar;
	private MenuController menuController;
	private GridBagConstraints constraints;

	private ProjectView projectView;
	
	public OurFrame()
	{
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(0, 0, dimension.width, dimension.height);

		this.setLayout(new GridBagLayout());
		this.constraints = new GridBagConstraints();

		this.constraints.fill = GridBagConstraints.BOTH;
		this.constraints.weightx = 1.0d;
		this.constraints.weighty = 1.0d;

		SharedController.getInstance().setFrame(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.getContentPane().setBackground(Color.DARK_GRAY);

		this.menuBar = new MenuView(this);
		this.menuController = new MenuController(this.menuBar);
		this.setJMenuBar(this.menuBar);

		this.setVisible(true);
	}
	
	public void initProjectView(Project project)
	{
		this.menuBar.setInProject(true);
		this.menuBar.setInChoose(false);
		this.projectView = new ProjectView(project);
		SharedController.getInstance().setProjectView(this.projectView);
		SharedController.getInstance().setProject(project);
		this.add(this.projectView, this.constraints);
		
		this.pack();
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(0, 0, dimension.width, dimension.height);
	}
	
	public void closeProject()
	{
		this.menuBar.setInProject(false);
		this.remove(this.projectView);
		this.projectView = null;
		SharedController.getInstance().setProject(null);
		SharedController.getInstance().setProjectView(null);
		this.pack();
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(0, 0, dimension.width, dimension.height);
	}
	
	public MenuView getMenuView()
	{
		return this.menuBar;
	}

	public GridBagConstraints getConstraints()
	{
		return constraints;
	}

	public void setConstraints(GridBagConstraints constraints)
	{
		this.constraints = constraints;
	}

}
