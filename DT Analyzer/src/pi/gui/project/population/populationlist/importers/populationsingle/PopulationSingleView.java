package pi.gui.project.population.populationlist.importers.populationsingle;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class PopulationSingleView extends JFrame
{
	private static final long serialVersionUID = 1L;
	private PopulationSingleController controller;

	private JLabel firstBefore = new JLabel("First: Before: ");
	private JLabel secondBefore = new JLabel("Second: Before: ");

	private JTextField firstBeforeField = new JTextField();
	private JTextField secondBeforeField = new JTextField();

	private JButton firstBeforeButton = new JButton("Select");
	private JButton secondBeforeButton = new JButton("Select");

	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");

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

		this.firstBefore.setBounds(15, 15, 150, 20);
		this.secondBefore.setBounds(15, 45, 150, 20);

		this.add(this.firstBefore);
		this.add(this.secondBefore);

		this.firstBeforeField.setBounds(120, 15, 200, 20);
		this.secondBeforeField.setBounds(120, 45, 200, 20);

		this.add(this.firstBeforeField);
		this.add(this.secondBeforeField);

		this.firstBeforeButton.setBounds(330, 15, 70, 20);
		this.secondBeforeButton.setBounds(330, 45, 70, 20);

		this.firstBeforeButton.setActionCommand("LOAD1");
		this.secondBeforeButton.setActionCommand("LOAD2");

		this.firstBeforeButton.addActionListener(this.controller);
		this.secondBeforeButton.addActionListener(this.controller);

		this.add(this.firstBeforeButton);
		this.add(this.secondBeforeButton);

		this.okButton.setBounds(300, 75, 100, 30);
		this.cancelButton.setBounds(17, 75, 100, 30);

		this.okButton.setActionCommand("OK");
		this.cancelButton.setActionCommand("CANCEL");

		this.okButton.addActionListener(this.controller);
		this.cancelButton.addActionListener(this.controller);

		this.add(this.okButton);
		this.add(this.cancelButton);

	}

	public JTextField getFirstBeforeField()
	{
		return firstBeforeField;
	}

	public void setFirstBeforeField(JTextField firstBeforeField)
	{
		this.firstBeforeField = firstBeforeField;
	}

	public JTextField getSecondBeforeField()
	{
		return secondBeforeField;
	}

	public void setSecondBeforeField(JTextField secondBeforeField)
	{
		this.secondBeforeField = secondBeforeField;
	}

	public JFileChooser getFc()
	{
		return fc;
	}
}
