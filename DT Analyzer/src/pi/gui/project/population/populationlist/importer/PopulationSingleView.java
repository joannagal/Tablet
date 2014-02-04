package pi.gui.project.population.populationlist.importer;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import pi.project.Project;

public class PopulationSingleView extends JFrame
{
	private static final long serialVersionUID = 1L;
	private PopulationSingleController controller;

	private JLabel firstLabel = new JLabel("First: Before: ");
	private JLabel secondLabel = new JLabel("Second: Before: ");

	private JTextField firstField = new JTextField();
	private JTextField secondField = new JTextField();

	private JButton firstButton = new JButton("Select");
	private JButton secondButton = new JButton("Select");

	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");

	private boolean firstPopulation = true;
	private int projectType = 0;

	private final JFileChooser fc = new JFileChooser();

	public PopulationSingleView()
	{
		this.controller = new PopulationSingleController(this);

		this.setTitle("Add Specimen Pair");

		this.setSize(430, 140);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);

		this.setLocation(x, y);
		this.setResizable(false);
		this.setLayout(null);

		this.firstLabel.setBounds(15, 15, 150, 20);
		this.secondLabel.setBounds(15, 45, 150, 20);

		this.add(this.firstLabel);
		this.add(this.secondLabel);

		this.firstField.setBounds(120, 15, 200, 20);
		this.secondField.setBounds(120, 45, 200, 20);

		this.add(this.firstField);
		this.add(this.secondField);

		this.firstButton.setBounds(330, 15, 70, 20);
		this.secondButton.setBounds(330, 45, 70, 20);

		this.firstButton.setActionCommand("LOAD1");
		this.secondButton.setActionCommand("LOAD2");

		this.firstButton.addActionListener(this.controller);
		this.secondButton.addActionListener(this.controller);

		this.add(this.firstButton);
		this.add(this.secondButton);

		this.okButton.setBounds(300, 75, 100, 30);
		this.cancelButton.setBounds(17, 75, 100, 30);

		this.okButton.setActionCommand("OK");
		this.cancelButton.setActionCommand("CANCEL");

		this.okButton.addActionListener(this.controller);
		this.cancelButton.addActionListener(this.controller);

		this.add(this.okButton);
		this.add(this.cancelButton);

	}

	public void showWithProjectType(int type, boolean firstPopulation)
	{
		this.setFirstPopulation(firstPopulation);
		this.setProjectType(type);

		if (type == Project.POPULATION_PAIR)
		{
			this.firstLabel.setText("Specimen: After: ");
			this.secondLabel.setText("Specimen: Before: ");
		} else if (type == Project.POPULATION_SINGLE)
		{
			this.firstLabel.setText("First: Before: ");
			this.secondLabel.setText("Second: Before: ");
		} else
			return;

		this.setVisible(true);
	}

	public JFileChooser getFc()
	{
		return fc;
	}

	public JTextField getFirstField()
	{
		return firstField;
	}

	public void setFirstField(JTextField firstField)
	{
		this.firstField = firstField;
	}

	public JTextField getSecondField()
	{
		return secondField;
	}

	public void setSecondField(JTextField secondField)
	{
		this.secondField = secondField;
	}

	public boolean isFirstPopulation()
	{
		return firstPopulation;
	}

	public void setFirstPopulation(boolean firstPopulation)
	{
		this.firstPopulation = firstPopulation;
	}

	public int getProjectType()
	{
		return projectType;
	}

	public void setProjectType(int projectType)
	{
		this.projectType = projectType;
	}

}
