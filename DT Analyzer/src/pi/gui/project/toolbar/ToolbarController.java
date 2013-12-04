package pi.gui.project.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import pi.gui.project.toolbar.informations.InformationsView;


public class ToolbarController implements ActionListener
{
	private ToolbarView view;
	
	public ToolbarController(ToolbarView view)
	{
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		String action = arg0.getActionCommand();

		if (action.equals("INFO"))
		{
			InformationsView info = new InformationsView();
			info.showWithData();
		}
		else if (action.equals("STAT"))
		{
			
		}
	}
	
	

}
