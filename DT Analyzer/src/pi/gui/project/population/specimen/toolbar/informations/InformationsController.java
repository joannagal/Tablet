package pi.gui.project.population.specimen.toolbar.informations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pi.gui.project.population.specimen.SpecimenView;
import pi.population.Specimen;

public class InformationsController implements ActionListener
{
	private SpecimenView specimenView;
	private InformationsView view;

	public InformationsController(SpecimenView specimenView,
			InformationsView view)
	{
		this.specimenView = specimenView;
		this.view = view;
	}

	public void actionPerformed(ActionEvent e)
	{
		String action = e.getActionCommand();

		if (action.equals("CANCEL"))
		{
			view.setVisible(false);
		}

		else if (action.equals("OK"))
		{
			if (this.specimenView.getSpecimen() == null)
				return;
			Specimen specimen = this.specimenView.getSpecimen();

			String name = this.view.getNameField().getText();
			String surname = this.view.getSurnameField().getText();
			String pesel = this.view.getPeselField().getText();
			String sex = this.view.getSexField().getSelectedItem().toString();
			String birth = this.view.getBirthField().getText();
			String hand = this.view.getHandField().getSelectedItem().toString();
			String brain = this.view.getBrainField().getSelectedItem()
					.toString();
			String operation = this.view.getOperationField().getSelectedItem()
					.toString();
			String firstOperation = this.view.getFirstOperationField()
					.getText();
			String secondOperation = this.view.getSecondOperationField()
					.getText();

			if ((name.equals("")) || (name.isEmpty()))
				specimen.setName(null);
			else
				specimen.setName(name);

			if ((surname.equals("")) || (surname.isEmpty()))
				specimen.setSurname(null);
			else
				specimen.setSurname(surname);

			if ((pesel.equals("")) || (pesel.isEmpty()))
				specimen.setPesel(null);
			else
			{
				try
				{
					specimen.setPesel(pesel);
				} catch (NumberFormatException nfe)
				{
					specimen.setPesel(null);
				}
			}

			if ((sex.equals("")) || (sex.isEmpty()))
				specimen.setSex(null);
			else if (sex.equals("Male"))
				specimen.setSex(true);
			else if (sex.equals("Female"))
				specimen.setSex(false);

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			try
			{
				Date date = df.parse(birth);
				specimen.setBirth(date);
			} catch (ParseException e1)
			{
				specimen.setBirth(null);
			}

			if ((hand.equals("")) || (hand.isEmpty()))
				specimen.setHand(null);
			else if (hand.equals("Right"))
				specimen.setHand(true);
			else if (hand.equals("Left"))
				specimen.setHand(false);

			if ((brain.equals("")) || (brain.isEmpty()))
				specimen.setBrain(null);
			else if (brain.equals("Right"))
				specimen.setBrain(true);
			else if (brain.equals("Left"))
				specimen.setBrain(false);

			if ((operation.equals("")) || (operation.isEmpty()))
				specimen.setOperationType(null);
			else if (operation.equals("P"))
				specimen.setOperationType(true);
			else if (operation.equals("T"))
				specimen.setOperationType(false);

			if ((firstOperation.equals("")) || (firstOperation.isEmpty()))
				specimen.setFirstOperationNo(null);
			else
			{
				try
				{
					Integer i = Integer.parseInt(firstOperation);
					specimen.setFirstOperationNo(i);
				} catch (NumberFormatException nfe)
				{
					specimen.setFirstOperationNo(null);
				}
			}

			if ((secondOperation.equals("")) || (secondOperation.isEmpty()))
				specimen.setSecondOperationNo(null);
			else
			{
				try
				{
					Integer i = Integer.parseInt(secondOperation);
					specimen.setSecondOperationNo(i);
				} catch (NumberFormatException nfe)
				{
					specimen.setSecondOperationNo(null);
				}
			}

			view.getSpecimenView().getPopulationView().updateLabels();
			view.setVisible(false);
		}

	}

}
