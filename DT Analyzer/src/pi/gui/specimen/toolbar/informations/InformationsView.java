package pi.gui.specimen.toolbar.informations;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import pi.population.Specimen;

public class InformationsView extends JFrame
{
	private static final long serialVersionUID = 1L;

	private JLabel name = new JLabel("Name");
	private JLabel surname = new JLabel("Surname");

	private JTextField nameField = new JTextField();
	private JTextField surnameField = new JTextField();

	private JButton cancelButton = new JButton("Cancel");
	private JButton okButton = new JButton("OK");

	private InformationsController controller;
	private Specimen specimen;
	
	public InformationsView(Specimen specimen)
	{
		this.setLayout(null);
		this.setSize(new Dimension(275, 140));
		this.setResizable(false);

		this.name.setBounds(15, 15, 150, 15);
		this.add(this.name);

		this.surname.setBounds(15, 45, 150, 15);
		this.add(this.surname);

		this.nameField.setBounds(80, 13, 170, 19);
		this.add(this.nameField);

		this.surnameField.setBounds(80, 43, 170, 19);
		this.add(this.surnameField);

		this.cancelButton.setBounds(15, 70, 80, 25);
		this.add(this.cancelButton);

		this.okButton.setBounds(170, 70, 80, 25);
		this.add(this.okButton);
		
		controller = new InformationsController(specimen, this);
		
		this.cancelButton.setActionCommand("CANCEL");
		this.cancelButton.addActionListener(controller);
		
		this.okButton.setActionCommand("OK");
		this.okButton.addActionListener(controller);
		
		this.specimen = specimen;
	}

	public void showWithData()
	{
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		this.setLocation(x, y);
		
		String name = this.specimen.getName();
		String surname = this.specimen.getSurname();
		
		if (name == null) name = new String("");
		if (surname == null) surname = new String("");
		
		this.getNameField().setText(name);
		this.surnameField.setText(surname);
		
		this.setVisible(true);
	}

	public JTextField getNameField()
	{
		return nameField;
	}

	public JTextField getSurnameField()
	{
		return surnameField;
	}



}
