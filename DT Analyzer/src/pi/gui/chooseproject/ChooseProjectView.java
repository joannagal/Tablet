package pi.gui.chooseproject;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import pi.gui.menu.MenuView;
import pi.shared.SharedController;

public class ChooseProjectView extends JDialog
{

	private static final long serialVersionUID = 1L;

	private JRadioButton oneSignalButton;
	private JRadioButton twoSignalsButton;
	private JRadioButton twoPopulationsButton;
	private JRadioButton diffrencesPopulationButton;
	private ButtonGroup projectGroup;
	private JButton cancelButton;
	private JButton proceedButton;
	private JPanel buttonPanel;
	private JLabel projectLabel;
	private JRadioButton[] radioButtonsArray;
	private JButton[] buttonsArray;
	private String[] radioEvent = new String[]
	{ "SINGLE_SIGNAL", "TWO_SIGNALS", "TWO_POPULATIONS",
			"POPULATION_DIFFERENCE" };
	private String[] buttonEvent = new String[]
	{ "CANCEL", "NEXT" };

	
	public ChooseProjectView()
	{
		
		setVisible(true);
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setVisible(true);
		this.add(panel);
		panel.setPreferredSize(new Dimension(350, 220));
		this.setResizable(false);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
	
		projectLabel = new JLabel("New project");
		projectLabel.setFont(new Font("Arial", Font.BOLD, 24));
		panel.add(projectLabel);

		oneSignalButton = new JRadioButton("Single singal");
		oneSignalButton.setFont(new Font("Arial", Font.BOLD, 14));
		twoSignalsButton = new JRadioButton("Signal pair analyzis");
		twoSignalsButton.setFont(new Font("Arial", Font.BOLD, 14));
		twoPopulationsButton = new JRadioButton("Two populations");
		twoPopulationsButton.setFont(new Font("Arial", Font.BOLD, 14));
		diffrencesPopulationButton = new JRadioButton(
				"Diffrences between populations");
		diffrencesPopulationButton.setFont(new Font("Arial", Font.BOLD, 14));

		radioButtonsArray = new JRadioButton[]
		{ oneSignalButton, twoSignalsButton, twoPopulationsButton,
				diffrencesPopulationButton };

		projectGroup = new ButtonGroup();
		projectGroup.add(oneSignalButton);
		projectGroup.add(twoPopulationsButton);
		projectGroup.add(twoSignalsButton);
		projectGroup.add(diffrencesPopulationButton);

		projectLabel.setVisible(true);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		panel.add(oneSignalButton, constraints);

		oneSignalButton.setVisible(true);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		panel.add(oneSignalButton, constraints);

		twoSignalsButton.setVisible(true);
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		panel.add(twoSignalsButton, constraints);

		twoPopulationsButton.setVisible(true);
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		panel.add(twoPopulationsButton, constraints);

		diffrencesPopulationButton.setVisible(true);
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		panel.add(diffrencesPopulationButton, constraints);

		buttonPanel = new JPanel();
		cancelButton = new JButton(buttonEvent[0]);
		proceedButton = new JButton(buttonEvent[1]);
		buttonsArray = new JButton[]
		{ cancelButton, proceedButton };

		buttonPanel.add(cancelButton);
		buttonPanel.add(proceedButton);

		buttonPanel.setVisible(true);
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		panel.add(buttonPanel, constraints);

		getContentPane().add(panel, BorderLayout.CENTER);

		pack();
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		this.setLocation(x, y);

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosed(WindowEvent e) {
		    	SharedController.getInstance().getFrame().getMenuView().setInChoose(false);
		    }
		});
		
	}

	public void setButtonsListener(ActionListener al)
	{

		for (int i = 0; i < buttonsArray.length; i++)
		{
			buttonsArray[i].setActionCommand(buttonEvent[i]);
			buttonsArray[i].addActionListener(al);
		}
	}

	public String findSelectedRadio()
	{
		String selected = "";
		for (int i = 0; i < radioButtonsArray.length; i++)
		{
			if (radioButtonsArray[i].isSelected())
			{
				selected = radioEvent[i];
			}
		}
		return selected;
	}



}
