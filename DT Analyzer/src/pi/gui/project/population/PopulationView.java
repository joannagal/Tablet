package pi.gui.project.population;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import pi.gui.project.population.populationlist.PopulationListView;
import pi.gui.project.population.specimen.SpecimenView;
import pi.inputs.drawing.Drawing;
import pi.population.Population;
import pi.population.Specimen;
import pi.project.Project;

public class PopulationView extends JPanel
{
	private static final long serialVersionUID = 1L;

	private PopulationController controller;
	private Population population;

	private JSplitPane splitPane;

	private SpecimenView specimenView;
	private PopulationListView listView;

	private boolean firstPopulation = true;

	public PopulationView(Population population, int type, boolean first)
	{
		this.firstPopulation = first;
		this.controller = new PopulationController(this);
		this.population = population;

		this.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;
		c.gridy = 0;
		c.weighty = 1.0d;
		c.weightx = 1.0d;

		if ((type == Project.SPECIMEN_PAIR)
				|| (type == Project.SPECIMEN_SINGLE))
		{
			this.specimenView = new SpecimenView(this, type);
			
			if (population.getSpecimen().size() == 0) this.specimenView.setSpecimen(null);
			else this.specimenView.setSpecimen(population.getSpecimen().get(0));
			
			this.add(this.specimenView, c);
		} else
		{	
			this.specimenView = new SpecimenView(this, type);
			
			if (population.getSpecimen().size() == 0) this.specimenView.setSpecimen(null);
			else this.specimenView.setSpecimen(population.getSpecimen().get(0));

			this.listView = new PopulationListView(this);

			this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
					this.listView, this.specimenView);

			this.splitPane.setOneTouchExpandable(true);

			this.splitPane.setDividerLocation(250);
			this.add(this.splitPane, c);
		}
	}

	public void updateLabels()
	{
		if (this.listView != null) this.listView.setLabels();

		ArrayList<Specimen> specimen = this.population.getSpecimen();

		String pref, name, surname;
		if (this.population.getName() != null)
			pref = this.population.getName();
		else
		{
			if (this.firstPopulation)
				pref = "First population";
			else
				pref = "Second population";
		}

		for (int i = 0; i < specimen.size(); i++)
		{
			Drawing drawing = specimen.get(i).getBefore();
			
			//drawing.recalculate(true);
			
			if (specimen.get(i).getName() == null)
				name = "";
			else
				name = specimen.get(i).getName();

			if (specimen.get(i).getSurname() == null)
				surname = "";
			else
				surname = specimen.get(i).getSurname();

			if (drawing != null)
			{
				drawing.setLabel(pref + ": Before: " + name + " " + surname);
			}
			drawing = specimen.get(i).getAfter();
			if (drawing != null)
			{
				drawing.setLabel(pref + ": After: " + name + " " + surname);
				//drawing.recalculate(true);
			}
		}
		
		this.specimenView.redraw();

	}

	public void addSpecimen(Drawing before, Drawing after)
	{
		Specimen specimen = new Specimen();
		specimen.setBefore(before);
		specimen.setAfter(after);
		this.population.getSpecimen().add(specimen);
		this.updateLabels();
	}

	public void deleteSpecimen(int index)
	{
		this.population.getSpecimen().remove(index);
		if (this.population.getSpecimen().size() > 0)
		{
			this.listView.getList().setSelectedIndex(0);
			this.specimenView.setSpecimen(this.population.getSpecimen().get(0));
		} else
		{
			this.listView.getList().setSelectedIndex(-1);
			this.specimenView.setSpecimen(null);
		}

		this.updateLabels();
	}

	public void setCurrentSpecimen(Specimen specimen)
	{
		this.specimenView.setSpecimen(specimen);
	}

	public Population getPopulation()
	{
		return population;
	}

	public void setPopulation(Population population)
	{
		this.population = population;
	}

	public PopulationListView getListView()
	{
		return listView;
	}

	public void setListView(PopulationListView listView)
	{
		this.listView = listView;
	}

}
