package pi.gui.project.population.specimen.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSplitPane;

import pi.gui.project.population.specimen.SpecimenView;
import pi.shared.SharedController;

public class ToolbarController implements ActionListener
{
	SpecimenView specimenView;
	ToolbarView toolbarView;

	public ToolbarController(SpecimenView specimenView, ToolbarView toolbarView)
	{
		this.specimenView = specimenView;
		this.toolbarView = toolbarView;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String action = e.getActionCommand();

		if (action.equals("CHANGE_SPLIT"))
		{
			int current = this.specimenView.getSplitPane().getOrientation();
			if (current == JSplitPane.HORIZONTAL_SPLIT)
				this.specimenView.getSplitPane().setOrientation(
						JSplitPane.VERTICAL_SPLIT);
			else
				this.specimenView.getSplitPane().setOrientation(
						JSplitPane.HORIZONTAL_SPLIT);

			this.specimenView.getSplitPane().setDividerLocation(0.5d);
		}

		if (action.equals("CHANGE_VIEW"))
		{
			SharedController.getInstance().switchScheme();
			this.specimenView.redraw();

		}

		if (action.equals("PERSONAL"))
		{
			if (this.specimenView.getSpecimen() != null)
			{
				this.toolbarView.getInformationsView().showWithData();	
			}
			
		}

		if (action.equals("COMPARATOR"))
		{
			if (this.specimenView.getSpecimen() != null)
			{
				this.toolbarView.getComparator().showWithData(
						this.specimenView.getSpecimen(), null);
			}
		}

	}

}
