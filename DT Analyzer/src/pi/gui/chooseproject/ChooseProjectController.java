package pi.gui.chooseproject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import pi.gui.chooseproject.pairimporter.PairImporterView;
import pi.gui.chooseproject.populpairimporter.PopulPairImporterView;
import pi.gui.chooseproject.populsingleimporter.PopulSingleImporterView;
import pi.gui.chooseproject.singleimporter.SingleImporterView;
import pi.shared.SharedController;

public class ChooseProjectController implements ActionListener
{
	ChooseProjectView view;

	public ChooseProjectController(ChooseProjectView view)
	{
		this.view = view;
		view.setButtonsListener(this);
	}

	public void actionPerformed(ActionEvent ae)
	{

		String action = ae.getActionCommand();
		if (action.equals("CANCEL"))
		{
			view.dispose();
		}
		if (action.equals("NEXT"))
		{

			String selected = view.findSelectedRadio();

			if (selected.equals("SINGLE_SIGNAL"))
			{
				SingleImporterView importer = new SingleImporterView();
				importer.setVisible(true);

				this.view.setVisible(false);

				SharedController.getInstance().getFrame().getMenuView()
						.setInChoose(true);
			}

			if (selected.equals("TWO_SIGNALS"))
			{
				PairImporterView importer = new PairImporterView();
				importer.setVisible(true);

				this.view.setVisible(false);

				SharedController.getInstance().getFrame().getMenuView()
						.setInChoose(true);
			}

			if (selected.equals("TWO_POPULATIONS"))
			{
				PopulSingleImporterView importer = new PopulSingleImporterView();
				importer.setVisible(true);

				this.view.setVisible(false);

				SharedController.getInstance().getFrame().getMenuView()
						.setInChoose(true);
			}
			if (selected.equals("POPULATION_DIFFERENCE"))
			{
				PopulPairImporterView importer = new PopulPairImporterView();
				importer.setVisible(true);

				this.view.setVisible(false);

				SharedController.getInstance().getFrame().getMenuView()
						.setInChoose(true);
			}
		}

	}

}