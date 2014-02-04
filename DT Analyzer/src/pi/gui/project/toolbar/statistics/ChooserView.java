package pi.gui.project.toolbar.statistics;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import pi.statistics.logic.StatMapper;

public class ChooserView extends JFrame
{
	private static final long serialVersionUID = 1L;

	private ChooserController controller;

	private JButton closeButton = new JButton("Close");
	private JButton okButton = new JButton("OK");

	private JPanel figurePanel = new JPanel();
	private JPanel attributePanel = new JPanel();
	private JPanel statisticsPanel = new JPanel();

	private JCheckBox figureCombo[];
	private JCheckBox attributeCombo[];
	private JCheckBox statisticsCombo[];

	private StatisticsView statisticsView = new StatisticsView();

	public ChooserView()
	{
		controller = new ChooserController(this);

		this.setTitle("Choose Statistics");

		this.setLayout(null);
		this.setSize(new Dimension(650, 400));
		this.setResizable(false);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		this.setLocation(x, y);

		this.closeButton.setActionCommand("CLOSE");
		this.closeButton.addActionListener(this.controller);
		this.closeButton.setBounds(10, 340, 140, 25);
		this.add(this.closeButton);

		this.okButton.setActionCommand("OK");
		this.okButton.addActionListener(this.controller);
		this.okButton.setBounds(490, 340, 140, 25);
		this.add(this.okButton);

		this.figurePanel.setBorder(BorderFactory.createTitledBorder("Figures"));
		this.figurePanel.setLayout(new GridLayout(
				StatMapper.figureNames.length, 0));
		this.figurePanel.setBounds(10, 10, 200, 320);
		this.add(this.figurePanel);

		this.attributePanel.setBorder(BorderFactory
				.createTitledBorder("Attributes"));
		this.attributePanel.setLayout(new GridLayout(
				StatMapper.attributeNames.length, 0));
		this.attributePanel.setBounds(220, 10, 200, 320);
		this.add(this.attributePanel);

		this.statisticsPanel.setBorder(BorderFactory
				.createTitledBorder("Statistics"));
		this.statisticsPanel.setLayout(new GridLayout(
				StatMapper.statisticNames.length, 0));
		this.statisticsPanel.setBounds(430, 10, 200, 320);
		this.add(this.statisticsPanel);

		figureCombo = new JCheckBox[StatMapper.figureNames.length];
		for (int i = 0; i < StatMapper.figureNames.length; i++)
		{
			this.figureCombo[i] = new JCheckBox(StatMapper.figureNames[i]);
			boolean av = StatMapper.figureAvaible
					.get(StatMapper.figureNames[i]);
			this.figureCombo[i].setSelected(av);
			this.figureCombo[i].setActionCommand(StatMapper.figureNames[i]);
			this.figureCombo[i].addActionListener(this.controller);
			this.figurePanel.add(this.figureCombo[i]);
		}

		attributeCombo = new JCheckBox[StatMapper.attributeNames.length];
		for (int i = 0; i < StatMapper.attributeNames.length; i++)
		{
			this.attributeCombo[i] = new JCheckBox(StatMapper.attributeNames[i]);
			boolean av = StatMapper.attributeAvaible
					.get(StatMapper.attributeNames[i]);
			this.attributeCombo[i].setSelected(av);
			this.attributeCombo[i]
					.setActionCommand(StatMapper.attributeNames[i]);
			this.attributeCombo[i].addActionListener(this.controller);
			this.attributePanel.add(this.attributeCombo[i]);
		}

		statisticsCombo = new JCheckBox[StatMapper.statisticNames.length];
		for (int i = 0; i < StatMapper.statisticNames.length; i++)
		{
			this.statisticsCombo[i] = new JCheckBox(
					StatMapper.statisticNames[i]);
			boolean av = StatMapper.statisticAvaible
					.get(StatMapper.statisticNames[i]);
			this.statisticsCombo[i].setSelected(av);
			this.statisticsCombo[i]
					.setActionCommand(StatMapper.statisticNames[i]);
			this.statisticsCombo[i].addActionListener(this.controller);
			this.statisticsPanel.add(this.statisticsCombo[i]);
		}
	}

	public JCheckBox[] getFigureCombo()
	{
		return figureCombo;
	}

	public void setFigureCombo(JCheckBox figureCombo[])
	{
		this.figureCombo = figureCombo;
	}

	public JCheckBox[] getAttributeCombo()
	{
		return attributeCombo;
	}

	public void setAttributeCombo(JCheckBox attributeCombo[])
	{
		this.attributeCombo = attributeCombo;
	}

	public JCheckBox[] getStatisticsCombo()
	{
		return statisticsCombo;
	}

	public void setStatisticsCombo(JCheckBox statisticsCombo[])
	{
		this.statisticsCombo = statisticsCombo;
	}

	public StatisticsView getStatisticsView()
	{
		return statisticsView;
	}

	public void setStatisticsView(StatisticsView statisticsView)
	{
		this.statisticsView = statisticsView;
	}

}
