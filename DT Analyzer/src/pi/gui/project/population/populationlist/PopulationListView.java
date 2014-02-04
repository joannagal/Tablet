package pi.gui.project.population.populationlist;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import pi.gui.project.population.PopulationView;
import pi.population.Specimen;

public class PopulationListView extends JPanel
{
	private static final long serialVersionUID = 1L;

	private PopulationListController controller;
	private JList<String> list;

	private JButton addButton = new JButton("Add");
	private JButton delButton = new JButton("Del");
	private JButton upButton = new JButton("Up");
	private JButton downButton = new JButton("Down");

	private PopulationView populationView;

	public PopulationListView(PopulationView populationView)
	{
		this.populationView = populationView;
		this.controller = new PopulationListController(this);

		this.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;
		c.gridy = 0;
		c.gridwidth = 4;
		c.weighty = 1.0d;
		c.weightx = 1.0d;

		this.list = new JList<String>();
		this.list.addListSelectionListener(this.controller.getListListener());

		this.add(this.list, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.0d;
		c.gridwidth = 1;
		c.gridy = 1;
		c.gridx = 0;

		this.addButton.setActionCommand("ADD");
		this.addButton.addActionListener(this.controller.getActionListener());
		this.add(this.addButton, c);

		c.gridx = 1;
		this.delButton.setActionCommand("DEL");
		this.delButton.addActionListener(this.controller.getActionListener());
		this.add(this.delButton, c);

		c.gridx = 2;
		this.upButton.setActionCommand("UP");
		this.upButton.addActionListener(this.controller.getActionListener());
		this.add(this.upButton, c);

		c.gridx = 3;
		this.downButton.setActionCommand("DOWN");
		this.downButton.addActionListener(this.controller.getActionListener());
		this.add(this.downButton, c);

		// -----------------------------

		this.setLabels();
	}

	public void setLabels()
	{
		int prev = this.list.getSelectedIndex();

		ArrayList<Specimen> specimen = this.populationView.getPopulation()
				.getSpecimen();

		String[] labels = new String[specimen.size()];
		for (int i = 0; i < specimen.size(); i++)
		{
			String number = String.format("%d. ", i + 1);
			String name;
			if (specimen.get(i).getName() == null)
				name = "Name";
			else
				name = specimen.get(i).getName();

			String surname;
			if (specimen.get(i).getSurname() == null)
				surname = "Surname";
			else
				surname = specimen.get(i).getSurname();

			labels[i] = number + name + " " + surname;
		}

		this.list.setListData(labels);

		if (prev < 0)
			this.list.setSelectedIndex(0);
		else
			this.list.setSelectedIndex(prev);
	}

	public JList<String> getList()
	{
		return list;
	}

	public void setList(JList<String> list)
	{
		this.list = list;
	}

	public PopulationView getPopulationView()
	{
		return populationView;
	}

	public void setPopulationView(PopulationView populationView)
	{
		this.populationView = populationView;
	}

}
