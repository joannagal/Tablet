package pi.gui.project.toolbar.statistics;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import net.sf.jasperreports.engine.JRException;

import pi.gui.histogram.Histogram;
import pi.project.Project;
import pi.shared.SharedController;
import pi.statistics.logic.StatMapper;
import pi.statistics.logic.report.PopulReportMngr;

public class StatisticsView extends JFrame
{
	private static final long serialVersionUID = 1L;

	private StatisticsController controller;

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

	public class MyTableModel extends DefaultTableModel
	{
		private static final long serialVersionUID = 1L;

		@Override
		public boolean isCellEditable(int row, int column)
		{
			return false;
		}
	}

	private JTabbedPane tabbedPane = new JTabbedPane();

	private JTable report = new JTable();
	private DefaultTableModel model = new MyTableModel();
	private JScrollPane reportPane = new JScrollPane(report);

	private JPanel detailPanel = new JPanel();

	private JLabel hypoLeftStatLabel = new JLabel("P1 Avg+-SD");
	private JLabel hypoRightStatLabel = new JLabel("P2 Avg+-SD");
	private JLabel hypoTestLabel = new JLabel("Test performed");
	private JLabel hypoEqualLabel = new JLabel("P-Value");
	private JLabel hypoRightLabel = new JLabel("Right sided test");
	private JLabel hypoLeftLabel = new JLabel("Left sided test");

	private JTextField hypoLeftStatEdit = new JTextField();
	private JTextField hypoRightStatEdit = new JTextField();
	private JTextField hypoTestEdit = new JTextField();
	private JTextField hypoEqualEdit = new JTextField();
	private JTextField hypoRightEdit = new JTextField();
	private JTextField hypoLeftEdit = new JTextField();

	public StatisticsView()
	{
		this.setTitle("Statistics");

		this.setLayout(null);
		this.setSize(new Dimension(1000, 500));
		this.setResizable(false);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		this.setLocation(x, y);

		this.controller = new StatisticsController(this);

		this.figureLabel.setBounds(15, 20, 100, 15);
		this.add(this.figureLabel);

		this.figureCombo.setBounds(55, 18, 100, 19);
		this.figureCombo.setActionCommand("CHANGE_FIGURE");
		this.figureCombo.addActionListener(controller);
		this.add(this.figureCombo);

		this.elementsList.setBounds(15, 45, 140, 390);
		this.elementsList.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent arg0)
			{
				prepare(getFigureStr(), getElementsList().getSelectedValue());
				report.changeSelection(0, 1, false, false);
				changeSelection();
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

		this.reportButton.setActionCommand("DISPLAY");
		this.reportButton.addActionListener(controller);
		this.reportButton.setBounds(700, 440, 140, 25);
		this.add(this.reportButton);

		this.report.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.report.setCellSelectionEnabled(true);
		this.report.setDragEnabled(false);

		this.report.addMouseListener(new java.awt.event.MouseAdapter()
		{
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt)
			{
				changeSelection();
			}
		});

		this.detailPanel.setBounds(165, 18, 820, 100);
		this.add(this.detailPanel);

		this.detailPanel.setLayout(null);
		this.detailPanel.setBorder(BorderFactory.createTitledBorder("Details"));

		this.hypoTestLabel.setBounds(10, 25, 110, 15);
		this.detailPanel.add(this.hypoTestLabel);
		this.hypoTestEdit.setBounds(110, 23, 290, 20);
		this.detailPanel.add(this.hypoTestEdit);

		this.hypoLeftStatLabel.setBounds(10, 50, 90, 15);
		this.detailPanel.add(this.hypoLeftStatLabel);
		this.hypoLeftStatEdit.setBounds(110, 48, 290, 20);
		this.detailPanel.add(this.hypoLeftStatEdit);

		this.hypoRightStatLabel.setBounds(10, 75, 90, 15);
		this.detailPanel.add(this.hypoRightStatLabel);
		this.hypoRightStatEdit.setBounds(110, 73, 290, 20);
		this.detailPanel.add(this.hypoRightStatEdit);

		this.hypoEqualLabel.setBounds(410, 25, 110, 15);
		this.detailPanel.add(this.hypoEqualLabel);
		this.hypoEqualEdit.setBounds(520, 23, 290, 20);
		this.detailPanel.add(this.hypoEqualEdit);

		this.hypoRightLabel.setBounds(410, 50, 110, 15);
		this.detailPanel.add(this.hypoRightLabel);
		this.hypoRightEdit.setBounds(520, 48, 290, 20);
		this.detailPanel.add(this.hypoRightEdit);

		this.hypoLeftLabel.setBounds(410, 75, 110, 15);
		this.detailPanel.add(this.hypoLeftLabel);
		this.hypoLeftEdit.setBounds(520, 73, 290, 20);
		this.detailPanel.add(this.hypoLeftEdit);

		this.reportPane.setBounds(165, 120, 820, 317);
		this.report.getTableHeader().setReorderingAllowed(false);

		this.histogram.setBounds(165, 120, 820, 317);
		this.histogram.recalculate();
		this.histogram.draw();

		this.tabbedPane.setBounds(165, 120, 820, 317);
		this.tabbedPane.add("Results", this.reportPane);
		this.tabbedPane.add("Histogram", this.histogram);
		this.add(this.tabbedPane);

		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				SharedController.getInstance().getProject().clearMemory();
			}
		});
	}

	public void changeSelection()
	{
		int row = report.getSelectedRow();
		int column = report.getSelectedColumn();

		if ((row == -1) || (column == -1))
			return;

		String figure = getFigureStr();
		String attribute = getElementStr();

		String statistics = model.getValueAt(row, 0).toString();

		controller.setDetails(column, figure, attribute, statistics);
	}

	class ShowThread implements Runnable
	{

		StatisticsView view;

		public ShowThread(StatisticsView view)
		{
			this.view = view;
		}

		public void run()
		{
			Project project = SharedController.getInstance().getProject();
			int specimens = project.getFirstPopulation().getSpecimen().size();

			int columns = 1;

			if (project.getSecondPopulation() != null)
			{
				columns = 5;
				specimens += project.getSecondPopulation().getSpecimen().size();
			}

			int figures = StatMapper.getFigureAvaibles();
			int attributes = StatMapper.getAttributeAvaibles();
			int total = figures * attributes * columns;

			SharedController.getInstance().getProgressView()
					.init(specimens + total);
			project.calculateStatistic();

			view.prepare(view.getFigureStr(), view.getElementStr());
			view.report.changeSelection(0, 1, false, false);

			try
			{
				SharedController.getInstance().setPopulMgr(
						new PopulReportMngr());
			} catch (JRException e)
			{
				e.printStackTrace();
			}

			
			SharedController.getInstance().getProgressView().close();
			view.setVisible(true);

		}
	}

	public void showWithData()
	{
		ShowThread runnable = new ShowThread(this);
		Thread thread = new Thread(runnable);
		thread.start();

	}

	public void prepare(String figure, String element)
	{
		int type = SharedController.getInstance().getProject().getType();

		String[] columns;

		this.figureStr = figure;
		this.elementStr = element;

		if (type == Project.POPULATION_SINGLE)
		{
			columns = new String[3];
			Project project = SharedController.getInstance().getProject();
			columns[0] = "";
			columns[1] = project.getFirstPopulation().getName();
			columns[2] = project.getSecondPopulation().getName();

			this.getModel().setDataVector(null, columns);

			for (int i = 0; i < 3; i++)
				columns[i] = "";
			for (int i = 0; i < 10; i++)
				this.model.addRow(columns);

			controller.set(figure, element);


		} else if (type == Project.POPULATION_PAIR)
		{
			columns = new String[6];
			Project project = SharedController.getInstance().getProject();
			columns[0] = "";
			columns[1] = project.getFirstPopulation().getName() + ": B with A";
			columns[2] = project.getSecondPopulation().getName() + ": B with A";
			columns[3] = "B with B";
			columns[4] = "A with A";
			columns[5] = "(A - B) with (A - B)";

			this.getModel().setDataVector(null, columns);

			for (int i = 0; i < 6; i++)
				columns[i] = "";
			for (int i = 0; i < 10; i++)
				this.model.addRow(columns);

			controller.set(figure, element);
		}

		this.report.setModel(this.getModel());
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

	public JComboBox<String> getFigureCombo()
	{
		return figureCombo;
	}

	public void setFigureCombo(JComboBox<String> figureCombo)
	{
		this.figureCombo = figureCombo;
	}

	public DefaultTableModel getModel()
	{
		return model;
	}

	public void setModel(DefaultTableModel model)
	{
		this.model = model;
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

	public JTextField getHypoTestEdit()
	{
		return hypoTestEdit;
	}

	public void setHypoTestEdit(JTextField hypoTestEdit)
	{
		this.hypoTestEdit = hypoTestEdit;
	}

	public JTextField getHypoEqualEdit()
	{
		return hypoEqualEdit;
	}

	public void setHypoEqualEdit(JTextField hypoEqualEdit)
	{
		this.hypoEqualEdit = hypoEqualEdit;
	}

	public JTextField getHypoRightEdit()
	{
		return hypoRightEdit;
	}

	public void setHypoRightEdit(JTextField hypoRightEdit)
	{
		this.hypoRightEdit = hypoRightEdit;
	}

	public JTextField getHypoLeftEdit()
	{
		return hypoLeftEdit;
	}

	public void setHypoLeftEdit(JTextField hypoLeftEdit)
	{
		this.hypoLeftEdit = hypoLeftEdit;
	}

	public JTextField getHypoLeftStatEdit()
	{
		return hypoLeftStatEdit;
	}

	public void setHypoLeftStatEdit(JTextField hypoLeftStatEdit)
	{
		this.hypoLeftStatEdit = hypoLeftStatEdit;
	}

	public JTextField getHypoRightStatEdit()
	{
		return hypoRightStatEdit;
	}

	public void setHypoRightStatEdit(JTextField hypoRightStatEdit)
	{
		this.hypoRightStatEdit = hypoRightStatEdit;
	}
}
