package pi.gui.statisticscomparator;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import net.sf.jasperreports.engine.JRException;

import pi.gui.dependgraph.DependGraph;
import pi.gui.histogram.Histogram;
import pi.population.Specimen;
import pi.shared.SharedController;
import pi.statistics.logic.StatMapper;
import pi.statistics.logic.report.ReportManager;

public class StatisticsComparatorView extends JFrame
{
	private static final long serialVersionUID = 1L;

	private Specimen[] specimen = new Specimen[2];
	private StatisticsComparatorController controller;

	public JLabel figureLabel = new JLabel("Figure");

	private JComboBox<String> figureCombo = new JComboBox<String>(
			StatMapper.figureNames);

	private JList<String> elementsList = new JList<String>(
			StatMapper.attributeNames);

	private String figureStr = "All Figures";
	private String elementStr = "Figure Standards";

	private JButton closeButton = new JButton("Close");

	private JButton reportButton = new JButton("Display report");
	private JButton saveButton = new JButton("Save report");

	private Histogram histogram = new Histogram();
	private DependGraph dGraph = new DependGraph();
	private DependGraph fftGraph = new DependGraph();

	private JTabbedPane tabbedPane = new JTabbedPane();

	private JTable report = new JTable();
	private DefaultTableModel model = new DefaultTableModel();
	private JScrollPane reportPane = new JScrollPane(report);

	public StatisticsComparatorView()
	{
		this.setTitle("Statistics");

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
		this.elementsList.addListSelectionListener(new ListSelectionListener()
		{
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

		this.reportButton.setActionCommand("DISPLAY");
		this.reportButton.addActionListener(controller);
		this.reportButton.setBounds(700, 440, 140, 25);
		this.add(this.reportButton);

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

		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{

				if (specimen[0] != null)
				{
					specimen[0].clearMemory();
				}

				if (specimen[1] != null)
				{
					specimen[1].clearMemory();
				}
			}
		});

	}

	public void setSpecimen(Specimen first, Specimen second)
	{
		this.specimen[0] = first;
		this.specimen[1] = second;
	}

	class ShowThread implements Runnable
	{
		private StatisticsComparatorView view;
		private Specimen first;
		private Specimen second;

		public ShowThread(StatisticsComparatorView view, Specimen first,
				Specimen second)
		{
			this.view = view;
			this.first = first;
			this.second = second;
		}

		public void run()
		{
			SharedController.getInstance().getProgressView().init(2);

			view.setSpecimen(first, second);

			view.specimen[0].calculateStatistic(false);
			if (view.specimen[1] != null)
				specimen[1].calculateStatistic(false);

			try
			{
				SharedController.getInstance().setReportMgr(
						new ReportManager(first));
			} catch (JRException e)
			{
				e.printStackTrace();
			}

			SharedController.getInstance().getProgressView().increase();

			view.prepare(view.getFigureStr(), view.getElementStr());
		}
	}

	public void showWithData(Specimen first, Specimen second)
	{
		ShowThread runnable = new ShowThread(this, first, second);
		Thread thread = new Thread(runnable);
		thread.start();

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
		columns[0] = "";

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
