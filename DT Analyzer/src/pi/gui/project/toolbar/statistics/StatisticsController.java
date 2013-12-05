package pi.gui.project.toolbar.statistics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Map;

import pi.project.Project;
import pi.shared.SharedController;
import pi.statistics.logic.ProjectResult;
import pi.statistics.logic.StatMapper;

public class StatisticsController implements ActionListener
{
	StatisticsView view;

	public StatisticsController(StatisticsView view)
	{
		this.view = view;
	}

	public void set(String figure, String element)
	{
		boolean after = false;
		int type = SharedController.getInstance().getProject().getType();
		if ((type == Project.POPULATION_PAIR)
				|| (type == Project.SPECIMEN_PAIR))
			after = true;

		if (after)
		{
			String[] maps =
			{ "P1AB", "P2AB", "BB", "AA", "dAB", };

			for (int i = 0; i < 5; i++)
			{
				this.fillPopulation(maps[i], figure, element, i + 1);
			}
		} else
		{
			String[] maps =
			{ "BB" };

			for (int i = 0; i < 1; i++)
			{
				this.fillPopulation(maps[i], figure, element, i + 1);
			}
		}
	}

	public void fillPopulation(String source, String figure, String element,
			int column)
	{
		ProjectResult pResult = SharedController.getInstance().getProject()
				.getResult();
		Map<String, Map<String, Map<String, LinkedList<Double>>>> map = pResult
				.getTestResult().get(source);
		
		LinkedList <Double> result;
		
		int where = 0;
		
		for (int i = 0; i < 12; i++)
		{
			System.out.printf("%s %s %s %s ", source, figure, element, StatMapper.statisticNames[i]);
			
			result = map.get(figure).get(element).get(StatMapper.statisticNames[i]);
			if (result.isEmpty()) {
				System.out.printf(" -\n");
				continue;
			}
			else System.out.printf(" +\n");
			where++;
			String label = Double.toString(result.get(0));

			view.getModel().setValueAt(label, where, column);
			view.getModel().setValueAt(StatMapper.statisticNames[i], where, 0);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		String com = arg0.getActionCommand();

		if (com.equals("SAVE"))
		{

		}

		if (com.equals("CHANGE_FIGURE"))
		{
			view.prepare(view.getFigureCombo().getSelectedItem().toString(),
					view.getElementStr());
		}

		if (com.equals("CLOSE"))
		{
			view.setVisible(false);
		}

	}

}
