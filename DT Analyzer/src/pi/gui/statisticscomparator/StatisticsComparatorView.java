package pi.gui.statisticscomparator;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;

import pi.gui.histogram.Histogram;
import pi.inputs.drawing.Drawing;
import pi.population.Specimen;

public class StatisticsComparatorView extends JFrame
{
	private static final long serialVersionUID = 1L;

	private Specimen[] specimen = new Specimen[2];
	private StatisticsComparatorController controller;

	public JLabel figureLabel = new JLabel("Figure");

	String[] figureStrings =
	{ "All", "ZigZag", "Circle-Left", "Circle-Right", "First-Line", "Second-Line",
			"Broken-Line", "Spiral-In", "Spiral-Out" };
	
	private JComboBox figureCombo = new JComboBox(figureStrings);;

	String[] elementsStrings = { "Pressure" };
	JList elementsList = new JList(elementsStrings);
	
	private JTextArea report = new JTextArea();
	
	private JButton closeButton = new JButton("Close");
	private JButton saveButton = new JButton("Save");
	private Histogram histogram = new Histogram();
	
	
	public StatisticsComparatorView(Specimen first, Specimen second)
	{
		this.setLayout(null);
		this.setSize(new Dimension(1000, 500));
		this.setResizable(false);

		specimen[0] = first;
		specimen[1] = second;

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		this.setLocation(x, y);

		controller = new StatisticsComparatorController(this);

		this.figureCombo.setSelectedIndex(0);
		
		this.figureLabel.setBounds(15, 20, 100, 15);
		this.add(this.figureLabel);
		
		this.figureCombo.setBounds(55, 18, 100, 19);
		this.add(this.figureCombo);
		
		this.elementsList.setBounds(15, 45, 140, 390);
		this.add(this.elementsList);
		
		this.report.setEditable(false);
		this.report.setBounds(165, 18, 300, 417);
		this.add(this.report);
		
		this.closeButton.setActionCommand("CLOSE");
		this.closeButton.addActionListener(controller);
		this.closeButton.setBounds(15, 440, 140, 25);
		this.add(this.closeButton);
		
		this.saveButton.setActionCommand("SAVE");
		this.saveButton.addActionListener(controller);
		this.saveButton.setBounds(325, 440, 140, 25);
		this.add(this.saveButton);
		
		this.histogram.setBounds(470, 15, 520, 450);
		this.add(this.histogram);
		this.histogram.recalculate();
		this.histogram.draw();
	}

	public void setSpecimen(Specimen first, Specimen second)
	{
		specimen[0] = first;
		specimen[1] = second;
	}

	public void showWithData()
	{
		controller.set("All", "Pressure");

		this.setVisible(true);
	}

}
