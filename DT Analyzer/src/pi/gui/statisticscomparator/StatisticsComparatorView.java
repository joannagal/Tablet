package pi.gui.statisticscomparator;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

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
	{ "ZigZag", "Circle-Left", "Circle-Right", "First Line", "Second Line",
			"Broken Line", "Spiral-In", "Spiral-Out" };

	private JComboBox figureCombo = new JComboBox(figureStrings);;

	String[] elementsStrings =
	{ "Pressure" };
	JList elementsList = new JList(elementsStrings);

	// private JTextArea report = new JTextArea();

	private JButton closeButton = new JButton("Close");
	private JButton saveButton = new JButton("Save");
	private Histogram histogram = new Histogram();

	private JTable report = new JTable();
	private DefaultTableModel model = new DefaultTableModel();
	private JScrollPane reportPane = new JScrollPane(report);

	public StatisticsComparatorView()
	{
		this.setLayout(null);
		this.setSize(new Dimension(1000, 500));
		this.setResizable(false);

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

		this.report.setBounds(165, 18, 500, 417);
		this.reportPane.setBounds(165, 18, 500, 417);
		this.add(this.reportPane);

		this.closeButton.setActionCommand("CLOSE");
		this.closeButton.addActionListener(controller);
		this.closeButton.setBounds(15, 440, 140, 25);
		this.add(this.closeButton);

		this.saveButton.setActionCommand("SAVE");
		this.saveButton.addActionListener(controller);
		this.saveButton.setBounds(325, 440, 140, 25);
		this.add(this.saveButton);

		this.getHistogram().setBounds(670, 15, 320, 450);
		this.add(this.getHistogram());
		this.getHistogram().recalculate();
		this.getHistogram().draw();
	}

	public void setSpecimen(Specimen first, Specimen second)
	{
		specimen[0] = first;
		specimen[1] = second;
	}

	public void showWithData(Specimen first, Specimen second)
	{
		specimen[0] = first;
		specimen[1] = second;

		int size = 1;

		if (first != null)
		{
			first.calculateStatistic();
			size++;
			if (first.getAfter() != null)
				size++;
		}

		if (second != null)
		{
			second.calculateStatistic();
			size++;
			if (second.getAfter() != null)
				size++;
		}

		String[] columns = new String[size];
		columns[0] = "Element";

		int pntr = 0;
		if (first != null)
		{
			columns[++pntr] = String.format("%s %s: Before", first.getName(),
					first.getSurname());
			if (first.getAfter() != null)
				columns[++pntr] = "After";
		}

		if (second != null)
		{
			columns[++pntr] = String.format("%s %s: Before", second.getName(),
					second.getSurname());
			if (second.getAfter() != null)
				columns[++pntr] = "After";
		}

		getModel().setDataVector(null, columns);

		for (int i = 0; i <= pntr; i++)
			columns[i] = "";
		for (int i = 0; i < 10; i++)
			this.model.addRow(columns);

		this.report.setModel(this.getModel());

		controller.set("ZigZag", "Pressure", pntr);
		this.setVisible(true);
	}

	public Specimen[] getSpecimen()
	{
		return specimen;
	}

	public void setSpecimen(Specimen[] specimen)
	{
		this.specimen = specimen;
	}

	public JTable getReport()
	{
		return report;
	}

	public void setReport(JTable report)
	{
		this.report = report;
	}

	public Histogram getHistogram()
	{
		return histogram;
	}

	public void setHistogram(Histogram histogram)
	{
		this.histogram = histogram;
	}

	public DefaultTableModel getModel()
	{
		return model;
	}

	public void setModel(DefaultTableModel model)
	{
		this.model = model;
	}

}
