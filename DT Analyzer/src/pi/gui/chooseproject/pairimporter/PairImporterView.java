package pi.gui.chooseproject.pairimporter;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import pi.shared.SharedController;

public class PairImporterView extends JFrame
{

	private static final long serialVersionUID = 1L;
	private PairImporterController controller;

	private JLabel firstBefore = new JLabel("First: Before: ");
	private JLabel firstAfter = new JLabel("First: Before: ");
	
	private JTextField firstBeforeField = new JTextField();
	private JTextField firstAfterField = new JTextField();

	private JButton firstBeforeButton = new JButton("Select");
	private JButton firstAfterButton = new JButton("Select");

	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");

	private final JFileChooser fc = new JFileChooser();

	public PairImporterView()
	{
		this.controller = new PairImporterController(this);

		this.setTitle("Create Specimen: Pair");

		this.setSize(430, 140);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);

		this.setLocation(x, y);
		this.setResizable(false);
		this.setLayout(null);

		this.firstBefore.setBounds(15, 15, 150, 20);
		this.firstAfter.setBounds(15, 45, 150, 20);
		this.add(this.firstBefore);
		this.add(this.firstAfter);
		
		this.firstBeforeField.setBounds(120, 15, 200, 20);
		this.firstAfterField.setBounds(120, 45, 200, 20);
		this.add(this.firstBeforeField);
		this.add(this.firstAfterField);
		
		this.firstBeforeButton.setBounds(330, 15, 70, 20);
		this.firstAfterButton.setBounds(330, 45, 70, 20);
		this.add(this.firstBeforeButton);
		this.add(this.firstAfterButton);
		
		this.firstBeforeButton.setActionCommand("LOAD1");
		this.firstBeforeButton.addActionListener(this.controller);

		this.firstAfterButton.setActionCommand("LOAD2");
		this.firstAfterButton.addActionListener(this.controller);
		
		this.okButton.setBounds(300, 75, 100, 30);
		this.cancelButton.setBounds(17, 75, 100, 30);

		this.okButton.setActionCommand("OK");
		this.cancelButton.setActionCommand("CANCEL");

		this.okButton.addActionListener(this.controller);
		this.cancelButton.addActionListener(this.controller);

		this.add(this.okButton);
		this.add(this.cancelButton);

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosed(WindowEvent e)
			{
				SharedController.getInstance().getFrame().getMenuView()
						.setInChoose(false);
			}
		});

	}

	public JTextField getFirstBeforeField()
	{
		return firstBeforeField;
	}

	public void setFirstBeforeField(JTextField firstBeforeField)
	{
		this.firstBeforeField = firstBeforeField;
	}

	public JFileChooser getFc()
	{
		return fc;
	}

	public JTextField getFirstAfterField()
	{
		return firstAfterField;
	}

	public void setFirstAfterField(JTextField firstAfterField)
	{
		this.firstAfterField = firstAfterField;
	}
}
