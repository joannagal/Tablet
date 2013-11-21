package pi.gui.statisticscomparator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

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
	
	private ArrayList <ArrayList <Double> > toHist;
	
	public void set(String figure, String element, int columns)
	{
		Specimen [] specimen = view.getSpecimen();
		SpecimenResult [] specResult = new SpecimenResult[2];
		
		if (specimen[0] != null) specResult[0] = specimen[0].getResult();
		else specResult[0] = null;
		if (specimen[1] != null) specResult[1] = specimen[1].getResult();
		else specResult[1] = null;
		
		// GOING DOWN
		
		int pntr = 0;
		
		toHist = new ArrayList <ArrayList <Double> > (4);
		
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
	}
	
	public void fillColumn(DrawingResult drawingResult, String figure, String element, int column)
	{
		Map <String, FigureResult> valueFig = drawingResult.getValue();
		FigureResult figureResult = valueFig.get(figure);
		if (figureResult != null)
		{
			
			Map <String, AttributeResult> valueAttr = figureResult.getValue();
			AttributeResult attributeResult = valueAttr.get(element);
			if (attributeResult != null)
			{
				
				Map <String, StatisticResult> valueStat = attributeResult.getValue();
				
				StatisticResult statistcResult = valueStat.get("Collector");
				if (statistcResult != null)
				{
					ArrayList <Double> value = statistcResult.getValue();
					this.toHist.add(value);
				}	
				
				String [] standards = {"Min", "Max", "Amplitude", "Average", "Variance", "StandardDev"};
				
				for (int i = 0; i < 6; i++)
				{
					statistcResult = valueStat.get(standards[i]);
					if (statistcResult != null)
					{
						Double value = statistcResult.getValue().get(0);
						this.view.getModel().setValueAt(Double.toString(value), i, column);
						if (column == 1) this.view.getModel().setValueAt(standards[i], i, 0);	
					}	
				}
				
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String action = e.getActionCommand();

		if (action.equals("CLOSE"))
		{
			view.setVisible(false);
		}

		if (action.equals("SAVE"))
		{
			
		}
	}

}
