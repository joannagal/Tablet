package pi.gui.project.population.populationlist.importer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import pi.inputs.drawing.Drawing;
import pi.project.Project;
import pi.shared.SharedController;

public class PopulationSingleController implements ActionListener
{
	private PopulationSingleView view;

	public PopulationSingleController(PopulationSingleView view)
	{
		this.view = view;

	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		String action = arg0.getActionCommand();

		if (action.equals("OK"))
		{
			if ((!this.view.getFirstField().getText().isEmpty())
					&& (!this.view.getSecondField().getText().isEmpty()))
			{
				Drawing[] drawing = new Drawing[2];
				try
				{
					drawing[0] = new Drawing(this.view.getFirstField()
							.getText());
				} catch (Exception e)
				{
					JOptionPane.showMessageDialog(view,
							"Something goes wrong with first signal!");
					return;
				}

				try
				{
					drawing[1] = new Drawing(this.view.getSecondField()
							.getText());
				} catch (Exception e)
				{
					JOptionPane.showMessageDialog(view,
							"Something goes wrong with second signal!");
					return;
				}

				if (view.getProjectType() == Project.POPULATION_PAIR)
				{
					if (view.isFirstPopulation())
					{
						SharedController
								.getInstance()
								.getProjectView()
								.addSpecimenPair(drawing[0], drawing[1], null,
										null);
					} else
					{
						SharedController
								.getInstance()
								.getProjectView()
								.addSpecimenPair(null, null, drawing[0],
										drawing[1]);

					}

				} else if (view.getProjectType() == Project.POPULATION_SINGLE)
				{
					SharedController
							.getInstance()
							.getProjectView()
							.addSpecimenPair(drawing[0], null, drawing[1], null);
				}

				view.setVisible(false);
			} else
			{
				JOptionPane.showMessageDialog(view,
						"All fields should be filled");
			}

		} else if (action.equals("CANCEL"))
		{
			view.setVisible(false);
		} else if (action.equals("LOAD1"))
		{
			int returnVal = this.view.getFc().showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				File file = this.view.getFc().getSelectedFile();
				this.view.getFirstField().setText(file.getAbsolutePath());
			}
		} else if (action.equals("LOAD2"))
		{
			int returnVal = this.view.getFc().showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				File file = this.view.getFc().getSelectedFile();
				this.view.getSecondField().setText(file.getAbsolutePath());
			}
		}

	}

}