package pi.gui.specimen.toolbar;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import pi.gui.specimen.SpecimenView;
import pi.gui.specimen.toolbar.informations.InformationsView;
import pi.gui.statisticscomparator.StatisticsComparatorView;

public class ToolbarView extends JPanel
{
	private static final long serialVersionUID = 1L;

	private JButton personalButton = new JButton("Informations");
	private JButton statisticsButton = new JButton("Statistics");

	private JButton splitButton = new JButton("Change Split");
	private JButton viewButton = new JButton("Change view");
	
	private ToolbarController controller;
	private SpecimenView specimenView;

	private InformationsView informationsView;
	private StatisticsComparatorView comparator;

	public ToolbarView(SpecimenView specimenView)
	{
		this.specimenView = specimenView;
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		this.setMinimumSize(new Dimension(40, 40));

		this.add(this.personalButton);
		this.add(this.statisticsButton);

		if (this.specimenView.getAfter() != null)
			this.add(this.splitButton);
		
		this.add(this.viewButton);

		controller = new ToolbarController(this.specimenView, this);

		this.viewButton.setActionCommand("CHANGE_VIEW");
		this.viewButton.addActionListener(controller);
		
		this.splitButton.setActionCommand("CHANGE_SPLIT");
		this.splitButton.addActionListener(controller);

		this.statisticsButton.setActionCommand("COMPARATOR");
		this.statisticsButton.addActionListener(controller);

		this.personalButton.setActionCommand("PERSONAL");
		this.personalButton.addActionListener(controller);

		informationsView = new InformationsView(this.specimenView.getSpecimen());
		comparator = new StatisticsComparatorView();
	}
	
	public SpecimenView getSpecimenView()
	{
		return this.specimenView;
	}

	public InformationsView getInformationsView()
	{
		return this.informationsView;
	}

	public StatisticsComparatorView getComparator()
	{
		return comparator;
	}

}
