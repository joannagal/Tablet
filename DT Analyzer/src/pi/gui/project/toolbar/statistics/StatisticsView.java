package pi.gui.project.toolbar.statistics;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import pi.project.Project;
import pi.shared.SharedController;

public class StatisticsView extends JFrame
{
	private static final long serialVersionUID = 1L;

	private StatisticsController controller;

	public JLabel figureLabel = new JLabel("Figure");

	String[] figureStrings =
	{ "ZigZag", "Circle-Left", "Circle-Right", "First Line", "Second Line",
			"Broken Line", "Spiral-In", "Spiral-Out" };

	private JComboBox<String> figureCombo = new JComboBox<String>(figureStrings);;

	String[] elementsStrings =
	{ "Figure Standards", "Pressure", "Momentary Speed", "Acceleration",
			"Direction Change (f'')" };
	private JList<String> elementsList = new JList<String>(elementsStrings);

	private String figureStr = "ZigZag";
	private String elementStr = "Figure Standards";

	private JButton closeButton = new JButton("Close");
	private JButton saveButton = new JButton("Save");

	private JTable report = new JTable();
	private DefaultTableModel model = new DefaultTableModel();
	private JScrollPane reportPane = new JScrollPane(report);

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

		this.reportPane.setBounds(165, 18, 820, 417);
		this.add(this.reportPane);
	}


	class ShowThread implements Runnable {
		
		StatisticsView view;
		
		public ShowThread(StatisticsView view)
		{
			this.view = view;
		}
		
		public void run() 
		{
			Project project = SharedController.getInstance().getProject();
			int specimens = project.getFirstPopulation().getSpecimen().size();
			if (project.getSecondPopulation() != null)
			{
				specimens += project.getSecondPopulation().getSpecimen().size();
			}

			System.out.printf("SPEC: %d\n", specimens);
			SharedController.getInstance().getProgressView().init(specimens);

			project.calculateStatistic();

			view.prepare(view.getFigureStr(), view.getElementStr());
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

		if (type == Project.POPULATION_SINGLE)
		{
			columns = new String[2];
			Project project = SharedController.getInstance().getProject();
			columns[0] = project.getFirstPopulation().getName();
			columns[1] = project.getSecondPopulation().getName();

			this.getModel().setDataVector(null, columns);

			for (int i = 0; i < 2; i++)
				columns[i] = "";
			for (int i = 0; i < 10; i++)
				this.model.addRow(columns);

			// controller.set(figure, element, 2);

		} else if (type == Project.POPULATION_PAIR)
		{
			columns = new String[5];
			Project project = SharedController.getInstance().getProject();
			columns[0] = project.getFirstPopulation().getName() + ": B with A";
			columns[1] = project.getSecondPopulation().getName() + ": B with A";
			columns[2] = "B with B";
			columns[3] = "A with A";
			columns[4] = "(A - B) with (A - B)";

			this.getModel().setDataVector(null, columns);

			for (int i = 0; i < 5; i++)
				columns[i] = "";
			for (int i = 0; i < 10; i++)
				this.model.addRow(columns);

			// controller.set(figure, element, 5);
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
}
