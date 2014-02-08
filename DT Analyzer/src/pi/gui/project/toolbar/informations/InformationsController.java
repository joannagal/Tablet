package pi.gui.project.toolbar.informations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import pi.shared.SharedController;

public class InformationsController implements ActionListener
{
	private InformationsView view;

	public InformationsController(InformationsView view)
	{
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		String action = arg0.getActionCommand();

		if (action.equals("OK"))
		{
			String name = this.view.getProjectField().getText();
			if (name.equals(""))
				name = null;
			SharedController.getInstance().getProject().setName(name);

			String first = this.view.getFirstField().getText();
			if (first.equals(""))
				first = null;
			SharedController.getInstance().getProject().getFirstPopulation()
					.setName(first);

			if (SharedController.getInstance().getProject()
					.getSecondPopulation() != null)
			{
				String second = this.view.getSecondField().getText();
				if (second.equals(""))
					second = null;
				SharedController.getInstance().getProject()
						.getSecondPopulation().setName(second);
			}

			SharedController.getInstance().getProjectView().updateLabels();
			
			SharedController.getInstance().getFrame().setFrameTitle(name);

			this.view.setVisible(false);
		} else if (action.equals("CANCEL"))
		{
			this.view.setVisible(false);
		}
	}

}
