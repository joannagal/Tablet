package pi.gui.project.toolbar.informations;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import pi.project.Project;
import pi.shared.SharedController;

public class InformationsView extends JFrame
{
	private static final long serialVersionUID = 1L;

	private InformationsController controller;

	private JButton cancelButton = new JButton("Cancel");
	private JButton okButton = new JButton("Ok");

	private JLabel projectLabel = new JLabel("Project name");
	private JLabel firstLabel = new JLabel("First popul. name");
	private JLabel secondLabel = new JLabel("Second popul. name");

	private JTextField projectField = new JTextField();
	private JTextField firstField = new JTextField();
	private JTextField secondField = new JTextField();

	public InformationsView()
	{
		this.setTitle("Project Informations");
		
		controller = new InformationsController(this);

		int type = SharedController.getInstance().getProject().getType();

		if ((type == Project.POPULATION_PAIR)
				|| (type == Project.POPULATION_SINGLE))
			this.setSize(340, 200);
		else
			this.setSize(340, 140);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);

		this.setLocation(x, y);
		this.setResizable(false);
		this.setLayout(null);

		this.projectLabel.setBounds(15, 15, 130, 20);
		this.add(this.projectLabel);

		this.projectField.setBounds(140, 13, 180, 20);
		this.add(this.projectField);

		this.firstLabel.setBounds(15, 45, 130, 20);
		this.add(this.firstLabel);

		this.firstField.setBounds(140, 43, 180, 20);
		this.add(this.firstField);

		if ((type == Project.POPULATION_PAIR)
				|| (type == Project.POPULATION_SINGLE))
		{
			this.secondLabel.setBounds(15, 75, 130, 20);
			this.add(this.secondLabel);

			this.secondField.setBounds(140, 73, 180, 20);
			this.add(this.secondField);

			this.cancelButton.setBounds(15, 115, 100, 20);
			this.okButton.setBounds(215, 115, 100, 20);

		} else
		{
			this.cancelButton.setBounds(15, 75, 100, 20);
			this.okButton.setBounds(215, 75, 100, 20);
		}

		this.cancelButton.setActionCommand("CANCEL");
		this.cancelButton.addActionListener(this.controller);

		this.okButton.setActionCommand("OK");
		this.okButton.addActionListener(this.controller);

		this.add(this.cancelButton);
		this.add(this.okButton);
	}

	public void showWithData()
	{
		String name = SharedController.getInstance().getProject().getName();
		if (name != null)
			this.projectField.setText(name);

		String first = SharedController.getInstance().getProject()
				.getFirstPopulation().getName();
		if (first != null)
			this.firstField.setText(first);

		if (SharedController.getInstance().getProject().getSecondPopulation() != null)
		{
			String second = SharedController.getInstance().getProject()
					.getSecondPopulation().getName();
			if (second != null)
				this.secondField.setText(second);
		}

		this.setVisible(true);
	}

	public JTextField getProjectField()
	{
		return projectField;
	}

	public void setProjectField(JTextField projectField)
	{
		this.projectField = projectField;
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
}
