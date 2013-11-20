package pi.gui.statisticscomparator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatisticsComparatorController implements ActionListener
{
	public StatisticsComparatorView view;
	
	public StatisticsComparatorController(StatisticsComparatorView view)
	{
		this.view = view;
	}
	
	public void set(String figure, String element)
	{
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String action = e.getActionCommand();

		if (action.equals("CLOSE"))
		{
			view.setVisible(false);
		}

		if (action.equals("SAVE"))
		{
			
		}
	}

}
