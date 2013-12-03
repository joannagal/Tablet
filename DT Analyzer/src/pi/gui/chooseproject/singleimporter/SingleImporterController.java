package pi.gui.chooseproject.singleimporter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import pi.population.Population;
import pi.project.Project;
import pi.shared.SharedController;

public class SingleImporterController implements ActionListener
{
	private SingleImporterView view;

	public SingleImporterController(SingleImporterView view)
	{
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		String action = arg0.getActionCommand();

		if (action.equals("OK"))
		{
			if (!this.view.getFirstBeforeField().getText().isEmpty())
			{
				Project project = new Project();
				project.setName("New Project");
				project.setPath(null);
				project.setType(Project.SPECIMEN_SINGLE);
				
				project.setFirstPopulation(new Population());
				project.setSecondPopulation(null);
				
				ArrayList <String> firstBefore = new ArrayList <String> (1);
				firstBefore.add(this.view.getFirstBeforeField().getText());
				
				if (!project.create(firstBefore, null, null, null))
				{
					project = null;
				}
				else
				{
					SharedController.getInstance().getFrame().initProjectView(project);
					this.view.setVisible(false);
				}

			}
			else JOptionPane.showMessageDialog(null, "All fields Should be filled!");

		} else if (action.equals("LOAD1"))
		{
			int returnVal = this.view.getFc().showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				File file = this.view.getFc().getSelectedFile();
				this.view.getFirstBeforeField().setText(file.getAbsolutePath());
			}
		} else if (action.equals("CANCEL"))
		{
			SharedController.getInstance().getFrame().getMenuView()
			.setInChoose(false);
			view.setVisible(false);
		}
	}
}
