package pi.gui.project.population.specimen.drawingtest.toolbar;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pi.graph.drawing.Graph;
import pi.inputs.drawing.Drawing;
import pi.utilities.Range;

public class Timeline extends JPanel
{
	private static final long serialVersionUID = 1L;

	private JSlider timeSlider;
	private Graph graph;

	private JButton beginFigure = new JButton("Start Figure");
	private JButton endFigure = new JButton("Create Figure");;

	String[] figures =
	{ "ZigZag", "Circle-Left", "Circle-Right", "First Line", "Second Line",
			"Broken Line", "Spiral-In", "Spiral-Out" };

	JComboBox<String> figuresCombo = new JComboBox<String>(figures);

	
	public Timeline(Graph graph)
	{
		this.graph = graph;

		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		this.setMinimumSize(new Dimension(350, 50));

		this.setBorder(BorderFactory.createTitledBorder("Timeline"));

		this.timeSlider = new JSlider();
		this.timeSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				if (getGraph().getDrawing() != null)
				{
					int pck = timeSlider.getValue();
					int time = getGraph().getDrawing().getPacket().get(pck)
							.getPkTime();

					if (time <= getGraph().getSelection().getLeft())
					{
						getGraph().getSelection().setRight(
								getGraph().getSelection().getLeft() + 1);
					} else
						getGraph().getSelection().setRight(time);

					applyNewLabel();

					getGraph().setCurrentTime(time);
				}
			}
		});

		this.add(this.timeSlider);

		this.add(this.beginFigure);
		this.beginFigure.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				if (getGraph().getDrawing() != null)
				{
					int pck = timeSlider.getValue();
					int time = getGraph().getDrawing().getPacket().get(pck)
							.getPkTime();

					getGraph().getSelection().setRange(time, time + 1);

					getGraph().draw();
				}

			}
		});

		this.add(this.endFigure);
		this.endFigure.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				if (getGraph().getDrawing() == null)
					return;

				int type = figuresCombo.getSelectedIndex() + 1;
				Range range = new Range(getGraph().getSelection().getLeft(),
						getGraph().getSelection().getRight());

				if (range.getInterval() < 50)
					return;

				getGraph().getDrawing().createFigure(range, type);

				range = getGraph().getSelection();
				range.setRight(getGraph().getDrawing().getTotalTime() + 1);
				range.setLeft(getGraph().getDrawing().getTotalTime());

				getGraph().recalculate();
				getGraph().draw();
			}
		});

		this.add(this.figuresCombo);

		this.rebuild(graph.getDrawing());
	}

	public void rebuild(Drawing drawing)
	{
		this.timeSlider.setMinimum(0);

		if (drawing != null)
		{
			this.timeSlider.setMaximum(drawing.getPacket().size() - 1);
		} else
		{
			this.timeSlider.setMaximum(1);
		}

		this.timeSlider.setValue(this.timeSlider.getMaximum());

		this.applyNewLabel();
	}

	public void applyNewLabel()
	{
		if (getGraph().getDrawing() == null)
			return;

		int pck = timeSlider.getValue();
		int time = getGraph().getDrawing().getPacket().get(pck).getPkTime();
		pck = timeSlider.getMaximum();
		int max = getGraph().getDrawing().getPacket().get(pck).getPkTime();

		this.setBorder(BorderFactory.createTitledBorder(String.format(
				"Time %d/%d", time, max)));
	}

	public Graph getGraph()
	{
		return graph;
	}

	public void setGraph(Graph graph)
	{
		this.graph = graph;
	}
}
