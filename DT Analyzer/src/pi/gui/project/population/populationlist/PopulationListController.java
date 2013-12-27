package pi.gui.project.population.populationlist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import pi.gui.project.population.populationlist.importer.PopulationSingleView;
import pi.population.Specimen;
import pi.project.Project;
import pi.shared.SharedController;

public class PopulationListController
{
	private PopulationListView view;
	private ListSelectionListener listListener;
	private ActionListener actionListener;

	public PopulationListController(PopulationListView view)
	{
		this.view = view;
		this.listListener = this.getListSelectionListener();
		this.actionListener = this.getButtonListener();
	}

	private ActionListener getButtonListener()
	{
		ActionListener listener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String action = e.getActionCommand();

				if (action.equals("ADD"))
				{
					int type = SharedController.getInstance().getProject()
							.getType();
					
					PopulationSingleView importer = new PopulationSingleView();
					importer.showWithProjectType(type, view.getPopulationView().isFirstPopulation());


				} else if (action.equals("DEL"))
				{
					int type = SharedController.getInstance().getProject()
							.getType();

					int index = getView().getList().getSelectedIndex();
					if (index < 0)
						return;

					if ((type == Project.POPULATION_PAIR)
							|| (type == Project.POPULATION_SINGLE))
					{

						Project project = SharedController.getInstance()
								.getProject();
						
						String temp = null;
						String msg = null;
						
						boolean first = view.getPopulationView().isFirstPopulation();

						if (first) 
						{
							temp = project.getFirstPopulation().getName();
							if (temp == null)
								temp = "First Population";
							
							temp += ": " + String.format("%d. ", index + 1);
							msg = temp;
							temp = project.getFirstPopulation().getSpecimen()
									.get(index).getName();
							if (temp == null)
								temp = "Name";
							msg += temp;
							temp = project.getFirstPopulation().getSpecimen()
									.get(index).getSurname();
							if (temp == null)
								temp = "Surname";
							msg += " " + temp + "\n";
						}
						else 
						{
							temp = project.getSecondPopulation().getName();
							if (temp == null)
								temp = "Second Population";
							
							temp += ": " + String.format("%d. ", index + 1);
							msg = temp;
							temp = project.getSecondPopulation().getSpecimen()
									.get(index).getName();
							if (temp == null)
								temp = "Name";
							msg += temp;
							temp = project.getSecondPopulation().getSpecimen()
									.get(index).getSurname();
							if (temp == null)
								temp = "Surname";
							msg += " " + temp + "\n";
						}

						int result = JOptionPane.showConfirmDialog(null,
								"Delete:\n" + msg,
								"Delete specimen?", JOptionPane.YES_NO_OPTION);

						if (result == 0)
						{
							SharedController.getInstance().getProjectView()
									.deleteSpecimen(index, first);
						}
					} else
					{
						Project project = SharedController.getInstance()
								.getProject();

						String temp = project.getFirstPopulation().getName();
						if (temp == null)
							temp = "First Population";
						temp += ": " + String.format("%d. ", index + 1);
						String first = temp;
						temp = project.getFirstPopulation().getSpecimen()
								.get(index).getName();
						if (temp == null)
							temp = "Name";
						first += temp;
						temp = project.getFirstPopulation().getSpecimen()
								.get(index).getSurname();
						if (temp == null)
							temp = "Surname";
						first += " " + temp + "\n";

						int result = JOptionPane.showConfirmDialog(null,
								"Delete specimen:\n" + first,
								"Delete specimen?", JOptionPane.YES_NO_OPTION);

						if (result == 0)
						{
							SharedController.getInstance().getProjectView()
									.deleteSpecimen(index);
						}

					}

				} else if (action.equals("UP"))
				{
					int index = getView().getList().getSelectedIndex();
					if (index < 0)
						return;

					ArrayList<Specimen> specimen = getView()
							.getPopulationView().getPopulation().getSpecimen();

					if (index < 1)
						return;

					Specimen temp = specimen.get(index);
					specimen.set(index, specimen.get(index - 1));
					specimen.set(index - 1, temp);

					getView().getPopulationView().getListView().getList()
							.setSelectedIndex(index - 1);

					getView().getPopulationView().updateLabels();

				} else if (action.equals("DOWN"))
				{
					int index = getView().getList().getSelectedIndex();
					if (index < 0)
						return;

					ArrayList<Specimen> specimen = getView()
							.getPopulationView().getPopulation().getSpecimen();

					if (index >= specimen.size() - 1)
						return;

					Specimen temp = specimen.get(index);
					specimen.set(index, specimen.get(index + 1));
					specimen.set(index + 1, temp);

					getView().getPopulationView().getListView().getList()
							.setSelectedIndex(index + 1);

					getView().getPopulationView().updateLabels();
				}
			}

		};

		return listener;
	}

	private ListSelectionListener getListSelectionListener()
	{
		ListSelectionListener listener = new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				int index = getView().getList().getSelectedIndex();
				if (index < 0)
					return;

				Specimen specimen = getView().getPopulationView()
						.getPopulation().getSpecimen().get(index);

				getView().getPopulationView().setCurrentSpecimen(specimen);

			}
		};

		return listener;
	}

	public ListSelectionListener getListListener()
	{
		return listListener;
	}

	public void setListListener(ListSelectionListener listListener)
	{
		this.listListener = listListener;
	}

	public PopulationListView getView()
	{
		return view;
	}

	public void setView(PopulationListView view)
	{
		this.view = view;
	}

	public ActionListener getActionListener()
	{
		return actionListener;
	}

	public void setActionListener(ActionListener actionListener)
	{
		this.actionListener = actionListener;
	}

}
