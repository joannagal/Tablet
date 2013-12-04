package pi.gui.project.toolbar.statistics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatisticsController implements ActionListener
{
	StatisticsView view;
	
	public StatisticsController(StatisticsView view)
	{
		this.view = view;
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
			
		}

		if (com.equals("CLOSE"))
		{
			view.setVisible(false);
		}
		
	}

}
