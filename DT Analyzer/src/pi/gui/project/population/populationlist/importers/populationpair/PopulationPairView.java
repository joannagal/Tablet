package pi.gui.project.population.populationlist.importers.populationpair;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class PopulationPairView extends JFrame
{
	private static final long serialVersionUID = 1L;
	private PopulationPairController controller;

	private JLabel firstBefore = new JLabel("First: Before: ");
	private JLabel firstAfter = new JLabel("First: After: ");
	private JLabel secondBefore = new JLabel("Second: Before: ");
	private JLabel secondAfter = new JLabel("Second: After: ");

	private JTextField firstBeforeField = new JTextField();
	private JTextField firstAfterField = new JTextField();
	private JTextField secondBeforeField = new JTextField();
	private JTextField secondAfterField = new JTextField();

	private JButton firstBeforeButton = new JButton("Select");
	private JButton firstAfterButton = new JButton("Select");
	private JButton secondBeforeButton = new JButton("Select");
	private JButton secondAfterButton = new JButton("Select");

	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");

	private final JFileChooser fc = new JFileChooser();

	public PopulationPairView()
	{
		this.controller = new PopulationPairController(this);

		this.setTitle("Add Specimen Pair");

		this.setSize(430, 200);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);

		this.setLocation(x, y);
		this.setResizable(false);
		this.setLayout(null);

		this.firstBefore.setBounds(15, 15, 150, 20);
		this.firstAfter.setBounds(15, 45, 150, 20);
		this.secondBefore.setBounds(15, 75, 150, 20);
		this.secondAfter.setBounds(15, 105, 150, 20);

		this.add(this.firstBefore);
		this.add(this.firstAfter);
		this.add(this.secondBefore);
		this.add(this.secondAfter);

		this.firstBeforeField.setBounds(120, 15, 200, 20);
		this.firstAfterField.setBounds(120, 45, 200, 20);
		this.secondBeforeField.setBounds(120, 75, 200, 20);
		this.secondAfterField.setBounds(120, 105, 200, 20);

		this.add(this.firstBeforeField);
		this.add(this.firstAfterField);
		this.add(this.secondBeforeField);
		this.add(this.secondAfterField);

		this.firstBeforeButton.setBounds(330, 15, 70, 20);
		this.firstAfterButton.setBounds(330, 45, 70, 20);
		this.secondBeforeButton.setBounds(330, 75, 70, 20);
		this.secondAfterButton.setBounds(330, 105, 70, 20);

		this.firstBeforeButton.setActionCommand("LOAD1");
		this.firstAfterButton.setActionCommand("LOAD2");
		this.secondBeforeButton.setActionCommand("LOAD3");
		this.secondAfterButton.setActionCommand("LOAD4");

		this.firstBeforeButton.addActionListener(this.controller);
		this.firstAfterButton.addActionListener(this.controller);
		this.secondBeforeButton.addActionListener(this.controller);
		this.secondAfterButton.addActionListener(this.controller);

		this.add(this.firstBeforeButton);
		this.add(this.firstAfterButton);
		this.add(this.secondBeforeButton);
		this.add(this.secondAfterButton);

		this.okButton.setBounds(300, 135, 100, 30);
		this.cancelButton.setBounds(17, 135, 100, 30);

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

	public JTextField getFirstAfterField()
	{
		return firstAfterField;
	}

	public void setFirstAfterField(JTextField firstAfterField)
	{
		this.firstAfterField = firstAfterField;
	}

	public JTextField getSecondBeforeField()
	{
		return secondBeforeField;
	}

	public void setSecondBeforeField(JTextField secondBeforeField)
	{
		this.secondBeforeField = secondBeforeField;
	}

	public JTextField getSecondAfterField()
	{
		return secondAfterField;
	}

	public void setSecondAfterField(JTextField secondAfterField)
	{
		this.secondAfterField = secondAfterField;
	}

	public JFileChooser getFc()
	{
		return fc;
	}

}
