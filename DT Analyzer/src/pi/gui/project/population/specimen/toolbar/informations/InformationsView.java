package pi.gui.project.population.specimen.toolbar.informations;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import pi.gui.project.population.specimen.SpecimenView;
import pi.population.Specimen;

public class InformationsView extends JFrame
{
	private static final long serialVersionUID = 1L;

	private JLabel name = new JLabel("Name");
	private JLabel surname = new JLabel("Surname");
	private JLabel pesel = new JLabel("ID");
	private JLabel sex = new JLabel("Sex");
	private JLabel birth = new JLabel("Birth dd/MM/yyyy");
	private JLabel hand = new JLabel("Hand");
	private JLabel brain = new JLabel("Brain");
	private JLabel operation = new JLabel("Operation/Test Type");
	private JLabel firstOperation = new JLabel("Operation/Test No.");

	private JTextField nameField = new JTextField();
	private JTextField surnameField = new JTextField();
	private JTextField peselField = new JTextField();

	String[] sexStrings =
	{ "", "Male", "Female" };
	private JComboBox<String> sexField = new JComboBox<String>(sexStrings);

	private JTextField birthField = new JTextField();

	String[] handStrings =
	{ "", "Right", "Left" };
	private JComboBox<String> handField = new JComboBox<String>(handStrings);

	String[] brainStrings =
	{ "", "Right", "Left" };
	private JComboBox<String> brainField = new JComboBox<String>(brainStrings);

	String[] operationStrings =
	{ "", "P/P", "T/H" };
	private JComboBox<String> operationField = new JComboBox<String>(operationStrings);

	private JTextField firstOperationField = new JTextField();
	
	private JLabel beforeOriginLabel = new JLabel("Origin Before");
	private JTextField beforeOriginField = new JTextField();

	private JLabel afterOriginLabel = new JLabel("Origin After");
	private JTextField afterOriginField = new JTextField();
	
	private JButton cancelButton = new JButton("Cancel");
	private JButton okButton = new JButton("OK");

	private InformationsController controller;
	private SpecimenView specimenView;

	public InformationsView(SpecimenView specimenView)
	{
		this.specimenView = specimenView;
		
		this.setTitle("Informations");
		this.setLayout(null);
		this.setSize(new Dimension(275, 410));
		this.setResizable(false);

		this.name.setBounds(15, 15, 150, 15);
		this.add(this.name);

		this.surname.setBounds(15, 45, 150, 15);
		this.add(this.surname);

		this.pesel.setBounds(15, 75, 150, 15);
		this.add(this.pesel);

		this.sex.setBounds(15, 105, 150, 15);
		this.add(this.sex);

		this.birth.setBounds(15, 135, 150, 15);
		this.add(this.birth);

		this.hand.setBounds(15, 165, 150, 15);
		this.add(this.hand);

		this.brain.setBounds(15, 195, 150, 15);
		this.add(this.brain);

		this.operation.setBounds(15, 225, 150, 15);
		this.add(this.operation);

		this.firstOperation.setBounds(15, 255, 150, 15);
		this.add(this.firstOperation);


		this.nameField.setBounds(80, 13, 170, 19);
		this.add(this.nameField);

		this.surnameField.setBounds(80, 43, 170, 19);
		this.add(this.surnameField);

		this.peselField.setBounds(80, 73, 170, 19);
		this.add(this.peselField);

		this.sexField.setBounds(80, 103, 170, 19);
		this.add(this.sexField);

		this.birthField.setBounds(140, 133, 110, 19);
		this.add(this.birthField);

		this.handField.setBounds(80, 163, 170, 19);
		this.add(this.handField);

		this.brainField.setBounds(80, 193, 170, 19);
		this.add(this.brainField);

		this.operationField.setBounds(140, 223, 110, 19);
		this.add(this.operationField);

		this.firstOperationField.setBounds(140, 253, 110, 19);
		this.add(this.firstOperationField);

		this.beforeOriginField.setBounds(100, 283, 150, 19);
		this.beforeOriginField.setEditable(false);
		this.add(this.beforeOriginField);
		
		this.beforeOriginLabel.setBounds(15, 283, 150, 15);
		this.add(this.beforeOriginLabel);
		
		this.afterOriginField.setBounds(100, 313, 150, 19);
		this.afterOriginField.setEditable(false);
		this.add(this.afterOriginField);
		
		this.afterOriginLabel.setBounds(15, 313, 150, 15);
		this.add(this.afterOriginLabel);
		
		this.cancelButton.setBounds(15, 350, 80, 25);
		this.add(this.cancelButton);

		this.okButton.setBounds(170, 350, 80, 25);
		this.add(this.okButton);

		controller = new InformationsController(this.specimenView, this);

		this.cancelButton.setActionCommand("CANCEL");
		this.cancelButton.addActionListener(controller);

		this.okButton.setActionCommand("OK");
		this.okButton.addActionListener(controller);

	}

	public void showWithData()
	{
		if (this.specimenView.getSpecimen() == null) return;
		Specimen specimen = this.specimenView.getSpecimen();
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		this.setLocation(x, y);

		String name = specimen.getName();
		String surname = specimen.getSurname();
		String pesel = specimen.getPesel();
		Boolean sex = specimen.getSex();
		Date birth = specimen.getBirth();
		Boolean hand = specimen.getHand();
		Boolean brain = specimen.getBrain();
		Boolean operation = specimen.getOperationType();
		Integer firstOperation = specimen.getOperationTestNo();
		String beforeOrigin = specimen.getBefore().getOrigin();
		String afterOrigin = null;
		if (specimen.getAfter() != null) afterOrigin = specimen.getAfter().getOrigin();
		
		if (name != null)
			this.nameField.setText(name);
		else
			this.nameField.setText("");

		if (surname != null)
			this.surnameField.setText(surname);
		else
			this.surnameField.setText("");

		if (pesel != null)
			this.peselField.setText(pesel);
		else
			this.peselField.setText("");

		if (sex != null)
		{
			if (sex == true)
				this.sexField.setSelectedIndex(1);
			else
				this.sexField.setSelectedIndex(2);
		} else
			this.sexField.setSelectedIndex(0);

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		if (birth != null)
			this.birthField.setText(df.format(birth));
		else
			this.birthField.setText("");

		if (hand != null)
		{
			if (hand == true)
				this.handField.setSelectedIndex(1);
			else
				this.handField.setSelectedIndex(2);
		} else
			this.handField.setSelectedIndex(0);

		if (brain != null)
		{
			if (brain == true)
				this.brainField.setSelectedIndex(1);
			else
				this.brainField.setSelectedIndex(2);
		} else
			this.brainField.setSelectedIndex(0);

		if (operation != null)
		{
			if (operation == true)
				this.operationField.setSelectedIndex(1);
			else
				this.operationField.setSelectedIndex(2);
		} else
			this.operationField.setSelectedIndex(0);

		if (firstOperation != null)
			this.firstOperationField.setText(Integer.toString(firstOperation));
		else
			this.firstOperationField.setText("");
		
		if (beforeOrigin != null)
			this.beforeOriginField.setText(beforeOrigin);

		if (afterOrigin != null)
			this.afterOriginField.setText(afterOrigin);
		
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

	public JTextField getPeselField()
	{
		return peselField;
	}

	public JComboBox<String> getSexField()
	{
		return sexField;
	}

	public JTextField getBirthField()
	{
		return birthField;
	}


	public JComboBox<String> getHandField()
	{
		return handField;
	}


	public JComboBox<String> getBrainField()
	{
		return brainField;
	}


	public JComboBox<String> getOperationField()
	{
		return operationField;
	}


	public JTextField getFirstOperationField()
	{
		return firstOperationField;
	}


	public SpecimenView getSpecimenView()
	{
		return specimenView;
	}

	public void setSpecimenView(SpecimenView specimenView)
	{
		this.specimenView = specimenView;
	}




}
