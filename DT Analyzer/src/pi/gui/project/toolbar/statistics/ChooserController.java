package pi.gui.project.toolbar.statistics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import pi.statistics.logic.StatMapper;

public class ChooserController implements ActionListener
{
	private ChooserView view;

	public ChooserController(ChooserView view)
	{
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		String action = arg0.getActionCommand();

		if (action.equals("OK"))
		{
			this.view.getStatisticsView().showWithData();
			this.view.setVisible(false);
		}

		if (action.equals("CLOSE"))
		{
			this.view.setVisible(false);
		}

		for (int i = 0; i < StatMapper.figureNames.length; i++)
			if (action.equals(StatMapper.figureNames[i]))
			{
				boolean sel = view.getFigureCombo()[i].isSelected();
				StatMapper.figureAvaible.put(StatMapper.figureNames[i], sel);
				return;
			}

		for (int i = 0; i < StatMapper.attributeNames.length; i++)
			if (action.equals(StatMapper.attributeNames[i]))
			{
				boolean sel = view.getAttributeCombo()[i].isSelected();

				if ((i == 3) && (sel == true))
				{
					view.getAttributeCombo()[2].setSelected(true);
					StatMapper.attributeAvaible.put(
							StatMapper.attributeNames[2], sel);
				}

				StatMapper.attributeAvaible.put(StatMapper.attributeNames[i],
						sel);
				return;
			}

		for (int i = 0; i < StatMapper.statisticNames.length; i++)
			if (action.equals(StatMapper.statisticNames[i]))
			{
				boolean sel = view.getStatisticsCombo()[i].isSelected();

				if ((i == 6) && (sel == true))
				{
					view.getStatisticsCombo()[4].setSelected(true);
					StatMapper.statisticAvaible.put(
							StatMapper.statisticNames[4], sel);

					view.getStatisticsCombo()[5].setSelected(true);
					StatMapper.statisticAvaible.put(
							StatMapper.statisticNames[5], sel);
				}

				if ((i == 9) && (sel == true))
				{
					view.getStatisticsCombo()[7].setSelected(true);
					StatMapper.statisticAvaible.put(
							StatMapper.statisticNames[7], sel);
				}

				if ((i == 10) && (sel == true))
				{
					view.getStatisticsCombo()[7].setSelected(true);
					StatMapper.statisticAvaible.put(
							StatMapper.statisticNames[7], sel);

					view.getStatisticsCombo()[9].setSelected(true);
					StatMapper.statisticAvaible.put(
							StatMapper.statisticNames[9], sel);
				}

				if ((i == 2) && (sel == true))
				{
					view.getStatisticsCombo()[0].setSelected(true);
					StatMapper.statisticAvaible.put(
							StatMapper.statisticNames[0], sel);

					view.getStatisticsCombo()[1].setSelected(true);
					StatMapper.statisticAvaible.put(
							StatMapper.statisticNames[1], sel);
				}

				StatMapper.statisticAvaible.put(StatMapper.statisticNames[i],
						sel);
				return;
			}

	}

}
