package pi.gui.project.population.populationlist.importers.populationsingle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import pi.inputs.drawing.Drawing;
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
			if (
					( ! this.view.getFirstBeforeField().getText().isEmpty() ) && 
					( ! this.view.getSecondBeforeField().getText().isEmpty() ) )
			{
				
				// 1 --------
				Drawing [] drawing = new Drawing[2];
				try
				{
					drawing[0] = new Drawing(this.view.getFirstBeforeField().getText());
				} catch (Exception e)
				{
					JOptionPane.showMessageDialog(view, "Something goes wrong with First Before!");
					return;
				}
				
				try
				{
					drawing[1] = new Drawing(this.view.getSecondBeforeField().getText());
				} catch (Exception e)
				{
					JOptionPane.showMessageDialog(view, "Something goes wrong with Second Before!");
					return;
				}
				
				SharedController
						.getInstance()
						.getProjectView()
						.addSpecimenPair(drawing[0], null, drawing[1], null);
				
				view.setVisible(false);
			}
			else
			{
				JOptionPane.showMessageDialog(view, "All fields should be filled");
			}
			
			
			
		}
		else if (action.equals("CANCEL"))
		{
			view.setVisible(false);
		}
		else if (action.equals("LOAD1"))
		{
			 int returnVal = this.view.getFc().showOpenDialog(null);

		     if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = this.view.getFc().getSelectedFile();
		            this.view.getFirstBeforeField().setText(file.getAbsolutePath());
		     } 
		}
	    else if (action.equals("LOAD2"))
		{
			 int returnVal = this.view.getFc().showOpenDialog(null);

		     if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = this.view.getFc().getSelectedFile();
		            this.view.getSecondBeforeField().setText(file.getAbsolutePath());
		     } 
		}
			
		
	}

}