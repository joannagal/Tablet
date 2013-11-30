package pi.gui.statisticscomparator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import pi.inputs.drawing.Figure;
import pi.inputs.drawing.PacketData;
import pi.inputs.drawing.Segment;
import pi.population.Specimen;
import pi.statistics.logic.AttributeResult;
import pi.statistics.logic.DrawingResult;
import pi.statistics.logic.FigureResult;
import pi.statistics.logic.SpecimenResult;
import pi.statistics.logic.StatisticResult;

public class StatisticsComparatorController implements ActionListener
{
	public StatisticsComparatorView view;

	public StatisticsComparatorController(StatisticsComparatorView view)
	{
		this.view = view;
	}

	private ArrayList<ArrayList<Double>> toHist;
	private ArrayList<ArrayList<Double>> toDepend;
	private ArrayList<ArrayList<Double>> toFFT;
	
	public void set(String figure, String element, int columns)
	{

		Specimen[] specimen = view.getSpecimen();
		SpecimenResult[] specResult = new SpecimenResult[2];

		if (specimen[0] != null)
			specResult[0] = specimen[0].getResult();
		else
			specResult[0] = null;
		if (specimen[1] != null)
			specResult[1] = specimen[1].getResult();
		else
			specResult[1] = null;

		// GOING DOWN

		int pntr = 0;

		this.toHist = new ArrayList<ArrayList<Double>>(4);
		this.toDepend = new ArrayList<ArrayList<Double>>(4);
		this.toFFT = new ArrayList<ArrayList<Double>>(4);
		
		Map<String, DrawingResult> valueSpec = specResult[0].getValue();
		DrawingResult drawingResult = valueSpec.get("Before");
		if (drawingResult != null)
		{

			pntr++;
			this.fillColumn(drawingResult, figure, element, pntr);
		}

		drawingResult = valueSpec.get("After");
		if (drawingResult != null)
		{
			pntr++;
			this.fillColumn(drawingResult, figure, element, pntr);
		}

		if (specResult[1] != null)
		{
			valueSpec = specResult[1].getValue();

			drawingResult = valueSpec.get("Before");
			if (drawingResult != null)
			{
				pntr++;
				this.fillColumn(drawingResult, figure, element, pntr);
			}

			drawingResult = valueSpec.get("After");
			if (drawingResult != null)
			{
				pntr++;
				this.fillColumn(drawingResult, figure, element, pntr);
			}
		}

		this.view.getHistogram().setData(this.toHist);
		this.view.getHistogram().recalculate();
		this.view.getHistogram().draw();
		
		this.view.getdGraph().setData(this.toDepend);
		this.view.getdGraph().recalculate();
		this.view.getdGraph().draw();
		
		this.view.getFftGraph().setData(this.toFFT);
		this.view.getFftGraph().recalculate();
		this.view.getFftGraph().draw();
	}

	public void fillColumn(DrawingResult drawingResult, String figure,
			String element, int column)
	{

		Map<String, FigureResult> valueFig = drawingResult.getValue();
		FigureResult figureResult = valueFig.get(figure);
		if (figureResult != null)
		{
			// PRINT FOR CHECK
			/*
			 * System.out.printf("START\n"); ArrayList<PacketData> pck =
			 * figureResult.getFigure().getParent() .getPacket();
			 * 
			 * Figure fig = figureResult.getFigure(); Iterator<Segment> it =
			 * fig.getSegment().iterator(); while (it.hasNext()) {
			 * System.out.printf("SEGMENT\n"); Segment seg = it.next(); for (int
			 * i = seg.getRange().getLeft(); i <= seg.getRange() .getRight();
			 * i++) { System.out.printf("%d %d %d %d %d %d\n", pck.get(i)
			 * .getPkX(), pck.get(i).getPkY(), pck.get(i) .getPkTime(),
			 * pck.get(i).getPkPressure(), pck .get(i).getPkAltitude(),
			 * pck.get(i).getPkAzimuth()); } }
			 */

			// --------------

			Map<String, AttributeResult> valueAttr = figureResult.getValue();
			AttributeResult attributeResult = valueAttr.get(element);
			if (attributeResult != null)
			{

				Map<String, StatisticResult> valueStat = attributeResult
						.getValue();

				StatisticResult statistcResult = valueStat.get("Collector");
				if (statistcResult != null)
				{
					ArrayList<Double> value = statistcResult.getValue();
					this.toHist.add(value);
				}

				statistcResult = valueStat.get("Dependency Collector");
				if (statistcResult != null)
				{
					ArrayList<Double> value = statistcResult.getValue();
					this.toDepend.add(value);
				}
				
				statistcResult = valueStat.get("FFT");
				if (statistcResult != null)
				{
					ArrayList<Double> value = statistcResult.getValue();
					this.toFFT.add(value);
				}
				
				String[] standards =
				{ "Min", "Max", "Amplitude", "Average", "Variance",
						"StandardDev", "Drawing time", "Drawing length",
						"Avg Speed", "Breaks Amount" };

				int pos = 0;

				for (int i = 0; i < 10; i++)
				{
					statistcResult = valueStat.get(standards[i]);
					if (statistcResult != null)
					{
						Double value = statistcResult.getValue().get(0);
						this.view.getModel().setValueAt(Double.toString(value),
								pos, column);
						if (column == 1)
							this.view.getModel().setValueAt(standards[i], pos,
									0);
						pos++;
					}

				}

			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String action = e.getActionCommand();

		if (action.equals("CHANGE_FIGURE"))
		{
			view.prepare(view.getFigureCombo().getSelectedItem().toString(),
					view.getElementStr());
		}

		if (action.equals("CLOSE"))
		{
			view.setVisible(false);
		}

		if (action.equals("SAVE"))
		{

		}
	}

}
