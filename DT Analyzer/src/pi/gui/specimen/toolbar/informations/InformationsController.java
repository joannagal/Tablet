package pi.gui.specimen.toolbar.informations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSplitPane;

import pi.population.Specimen;

public class InformationsController implements ActionListener
{
	private Specimen specimen;
	private InformationsView view; 
	
	public InformationsController(Specimen specimen, InformationsView view)
	{
		this.specimen = specimen;
		this.view = view;
	}
	
	
	public void actionPerformed(ActionEvent e)
	{
		String action = e.getActionCommand();

		if (action.equals("CANCEL"))
		{
			view.setVisible(false);
		}

		if (action.equals("OK"))
		{
			String name = this.view.getNameField().getText();
			String surname = this.view.getSurnameField().getText();
			
			specimen.setName(name);
			specimen.setSurname(surname);
			view.setVisible(false);
		}
		
	}



}
