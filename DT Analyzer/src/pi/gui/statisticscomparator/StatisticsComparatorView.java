package pi.gui.statisticscomparator;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import pi.gui.dependgraph.DependGraph;
import pi.gui.histogram.Histogram;
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

	private JComboBox<String> figureCombo = new JComboBox<String>(figureStrings);;

	String[] elementsStrings =
	{ "Figure Standards", "Pressure", "Momentary Speed", "Acceleration" };
	private JList<String> elementsList = new JList<String>(elementsStrings);

	private String figureStr = "ZigZag";
	private String elementStr = "Figure Standards";

	private JButton closeButton = new JButton("Close");
	private JButton saveButton = new JButton("Save");
	private Histogram histogram = new Histogram();
	private DependGraph dGraph = new DependGraph();
	private DependGraph fftGraph = new DependGraph();

	
	JTabbedPane tabbedPane = new JTabbedPane();
	
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
		this.figureCombo.setActionCommand("CHANGE_FIGURE");
		this.figureCombo.addActionListener(controller);
		
		this.figureLabel.setBounds(15, 20, 100, 15);
		this.add(this.figureLabel);

		this.figureCombo.setBounds(55, 18, 100, 19);
		this.add(this.figureCombo);

		this.elementsList.setBounds(15, 45, 140, 390);
		this.elementsList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0)
			{
				prepare(getFigureStr(), getElementsList().getSelectedValue());
			}
        });
		this.add(this.elementsList);

		
		this.closeButton.setActionCommand("CLOSE");
		this.closeButton.addActionListener(controller);
		this.closeButton.setBounds(15, 440, 140, 25);
		this.add(this.closeButton);

		this.saveButton.setActionCommand("SAVE");
		this.saveButton.addActionListener(controller);
		this.saveButton.setBounds(850, 440, 140, 25);
		this.add(this.saveButton);
	
		this.tabbedPane.addTab("Data", this.reportPane);
		this.tabbedPane.addTab("Histogram", this.histogram);
		this.tabbedPane.addTab("Dependency Graph", this.dGraph);
		this.tabbedPane.addTab("FFT", this.fftGraph);
		this.tabbedPane.setBounds(165, 18, 820, 417);
		this.add(this.tabbedPane);
		
		this.histogram.recalculate();
		this.histogram.draw();
		
		this.dGraph.setType(DependGraph.MIXED);
		this.dGraph.recalculate();
		this.dGraph.draw();
		
		this.fftGraph.recalculate();
		this.fftGraph.draw();
	}

	public void setSpecimen(Specimen first, Specimen second)
	{
		this.specimen[0] = first;
		this.specimen[1] = second;
	}

	public void showWithData(Specimen first, Specimen second)
	{
		this.setSpecimen(first, second);

		this.specimen[0].calculateStatistic();
		if (this.specimen[1] != null)
			specimen[1].calculateStatistic();

		this.prepare(this.figureStr, this.elementStr);
	}

	public void prepare(String figure, String element)
	{
		this.figureStr = figure;
		this.elementStr = element;
		
		int size = 1;

		if (this.specimen[0] != null)
		{
			size++;
			if (this.specimen[0].getAfter() != null)
				size++;
		}

		if (this.specimen[1] != null)
		{
			size++;
			if (this.specimen[1].getAfter() != null)
				size++;
		}

		String[] columns = new String[size];
		columns[0] = "Element";

		int pntr = 0;
		if (specimen[0] != null)
		{
			columns[++pntr] = String.format("%s %s: Before",
					this.specimen[0].getName(), this.specimen[0].getSurname());
			if (this.specimen[0].getAfter() != null)
				columns[++pntr] = "After";
		}

		if (specimen[1] != null)
		{
			columns[++pntr] = String.format("%s %s: Before",
					this.specimen[1].getName(), this.specimen[1].getSurname());
			if (this.specimen[1].getAfter() != null)
				columns[++pntr] = "After";
		}

		getModel().setDataVector(null, columns);

		for (int i = 0; i <= pntr; i++)
			columns[i] = "";
		for (int i = 0; i < 10; i++)
			this.model.addRow(columns);

		this.report.setModel(this.getModel());

		controller.set(figure, element, pntr);
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

	public JComboBox<String> getFigureCombo()
	{
		return figureCombo;
	}

	public void setFigureCombo(JComboBox<String> figureCombo)
	{
		this.figureCombo = figureCombo;
	}

	public String getFigureStr()
	{
		return figureStr;
	}

	public void setFigureStr(String figureStr)
	{
		this.figureStr = figureStr;
	}

	public String getElementStr()
	{
		return elementStr;
	}

	public void setElementStr(String elementStr)
	{
		this.elementStr = elementStr;
	}

	public JList<String> getElementsList()
	{
		return elementsList;
	}

	public void setElementsList(JList<String> elementsList)
	{
		this.elementsList = elementsList;
	}

	public DependGraph getdGraph()
	{
		return dGraph;
	}

	public void setdGraph(DependGraph dGraph)
	{
		this.dGraph = dGraph;
	}

	public DependGraph getFftGraph()
	{
		return fftGraph;
	}

	public void setFftGraph(DependGraph fftGraph)
	{
		this.fftGraph = fftGraph;
	}

}
