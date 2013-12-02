package pi.gui.chooseproject.singleimporter;

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

public class SingleImporterView extends JFrame
{

	private static final long serialVersionUID = 1L;
	private SingleImporterController controller;

	private JLabel firstBefore = new JLabel("First: Before: ");

	private JTextField firstBeforeField = new JTextField();

	private JButton firstBeforeButton = new JButton("Select");

	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");

	private final JFileChooser fc = new JFileChooser();

	public SingleImporterView()
	{
		this.controller = new SingleImporterController(this);

		this.setTitle("Create Specimen: Single");

		this.setSize(430, 110);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);

		this.setLocation(x, y);
		this.setResizable(false);
		this.setLayout(null);

		this.firstBefore.setBounds(15, 15, 150, 20);

		this.add(this.firstBefore);

		this.firstBeforeField.setBounds(120, 15, 200, 20);

		this.add(this.firstBeforeField);

		this.firstBeforeButton.setBounds(330, 15, 70, 20);

		this.firstBeforeButton.setActionCommand("LOAD1");

		this.firstBeforeButton.addActionListener(this.controller);

		this.add(this.firstBeforeButton);

		this.okButton.setBounds(300, 45, 100, 30);
		this.cancelButton.setBounds(17, 45, 100, 30);

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
}
