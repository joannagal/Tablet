package michal;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;

import pi.gui.project.ProjectView;
import pi.population.Population;
import pi.project.Project;
import pi.shared.SharedController;


public class Dummy
{	
	private JFrame frame;
	//private SpecimenView viewer;
	private ProjectView projectView;
	
	public Dummy()
	{
		Project project = this.getDummyProject2();
		SharedController.getInstance().setProject(project);
		
		frame = new JFrame("DT Prototype");
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(0, 0, dimension.width, dimension.height);

		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0d;
		c.weighty = 1.0d;
		
		if (project != null)
		{
			this.projectView = new ProjectView(project);
			SharedController.getInstance().setProjectView(this.projectView);
			
			frame.add(this.projectView, c);
		}

		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setVisible(true);
	}
	
	public Project getDummyProject1()
	{
		Project project = new Project();
		project.setName("Dummy Project");
		project.setPath(null);
		project.setType(Project.SPECIMEN_SINGLE);
		
		project.setFirstPopulation(new Population());
		project.setSecondPopulation(new Population());
		
		ArrayList <String> firstBefore = new ArrayList <String> (1);
		firstBefore.add("src/michal/przed1.mtb");
		
		if (!project.create(firstBefore, null, null, null))
		{
			project = null;
			System.out.printf("OMG SMTH WRONG\n");
		}
		else System.out.printf("OK\n"); 
		
		return project;
	}
	
	public Project getDummyProject2()
	{
		Project project = new Project();
		project.setName("Dummy Project");
		project.setPath(null);
		project.setType(Project.SPECIMEN_PAIR);
		
		project.setFirstPopulation(new Population());
		project.setSecondPopulation(new Population());
		
		ArrayList <String> firstBefore = new ArrayList <String> (1);
		firstBefore.add("src/michal/test2.mtb");
		
		ArrayList <String> firstAfter = new ArrayList <String> (1);
		firstAfter.add("src/michal/po2.mtb");
		
		if (!project.create(firstBefore, firstAfter, null, null))
		{
			project = null;
			System.out.printf("OMG SMTH WRONG\n");
		}
		else System.out.printf("OK\n"); 
		
		return project;
	}
	
	public Project getDummyProject3()
	{
		Project project = new Project();
		project.setName("Dummy Project");
		project.setPath(null);
		project.setType(Project.POPULATION_SINGLE);
		
		project.setFirstPopulation(new Population());
		project.setSecondPopulation(new Population());
		
		ArrayList <String> firstBefore = new ArrayList <String> (3);
		firstBefore.add("src/michal/przed1.mtb");
		firstBefore.add("src/michal/przed2.mtb");
		firstBefore.add("src/michal/przed1.mtb");
		
		ArrayList <String> secondBefore = new ArrayList <String> (3);
		secondBefore.add("src/michal/przed3.mtb");
		secondBefore.add("src/michal/przed1.mtb");
		secondBefore.add("src/michal/przed3.mtb");
		
		if (!project.create(firstBefore, null, secondBefore, null))
		{
			project = null;
			System.out.printf("OMG SMTH WRONG\n");
		}
		else System.out.printf("OK\n"); 
		
		return project;
	}
	
	public Project getDummyProject4()
	{
		Project project = new Project();
		project.setName("Dummy Project");
		project.setPath(null);
		project.setType(Project.POPULATION_PAIR);
		
		project.setFirstPopulation(new Population());
		project.setSecondPopulation(new Population());
		
		ArrayList <String> firstBefore = new ArrayList <String> (3);
		firstBefore.add("src/michal/przed1.mtb");
		firstBefore.add("src/michal/przed2.mtb");
		firstBefore.add("src/michal/przed1.mtb");
		
		ArrayList <String> firstAfter = new ArrayList <String> (3);
		firstAfter.add("src/michal/po1.mtb");
		firstAfter.add("src/michal/po2.mtb");
		firstAfter.add("src/michal/po1.mtb");
		
		ArrayList <String> secondBefore = new ArrayList <String> (3);
		secondBefore.add("src/michal/przed3.mtb");
		secondBefore.add("src/michal/przed1.mtb");
		secondBefore.add("src/michal/przed3.mtb");
		
		ArrayList <String> secondAfter = new ArrayList <String> (3);	
		secondAfter.add("src/michal/po3.mtb");
		secondAfter.add("src/michal/po1.mtb");
		secondAfter.add("src/michal/po3.mtb");
		
		if (!project.create(firstBefore, firstAfter, secondBefore, secondAfter))
		{
			project = null;
			System.out.printf("OMG SMTH WRONG\n");
		}
		else System.out.printf("OK\n"); 
		
		return project;
	}
	
}


