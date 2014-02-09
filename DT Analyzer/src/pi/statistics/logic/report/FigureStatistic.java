package pi.statistics.logic.report;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

import pi.population.Specimen;
import pi.shared.SharedController;
import pi.statistics.logic.AttributeResult;
import pi.statistics.logic.DrawingResult;
import pi.statistics.logic.FigureResult;
import pi.statistics.logic.StatMapper;
import pi.statistics.logic.StatisticResult;

public class FigureStatistic
{

	private String name;
	private String surname;
	private String personalID;
	private String sex;
	private Date birth;
	private String hand;
	private String brain;
	private String type;
	private int number;

	private String statistic;
	private Double standards;
	private Double pressure;
	private Double speed;
	private Double acceleration;
	private Double change;
	private Double azimuth;
	private Double altitude;
	private Double dev;
	private String examination;
	private String figure;

	
	public static Double getValue(StatisticResult result)
	{
		if (result == null)
			return null;
		else
			if (result.getValue() == null) return null;
		else
			return result.getValue().get(0);
	}
	
	@SuppressWarnings("rawtypes")
	public static Collection getFigureStatistics()
	{
		Vector<FigureStatistic> statistics = new Vector<FigureStatistic>(2
				* StatMapper.figureNames.length
				* StatMapper.statisticNames.length);
		
		Specimen specimen = SharedController.getInstance().getCurrentSpecimen();
		if (specimen == null) return statistics;
	

		if (specimen.getResult() == null)
			return statistics;

		// Po Examination
		for (Map.Entry<String, DrawingResult> examEntry : specimen.getResult()
				.getValue().entrySet())
		{
			// po figurach uszeregowane
			for (int f = 0; f < StatMapper.figureNames.length; f++)
			{
				FigureResult figure = examEntry.getValue().getValue().get(StatMapper.figureNames[f]);
				if (figure == null) continue;
				
				// wyciagamy dla kazdej statystyki...
				for (int i = 0; i < StatMapper.statisticNames.length; i++)
				{
					FigureStatistic figureStatistic = new FigureStatistic();
					figureStatistic.setExamination(examEntry.getKey());
					figureStatistic.setFigure(StatMapper.figureNames[f]);
					
					figureStatistic.setStatistic(StatMapper.statisticNames[i]);
					figureStatistic.name = specimen.getName();
					figureStatistic.surname = specimen.getSurname();
					figureStatistic.personalID = specimen.getPesel();
					figureStatistic.sex = specimen.getNamedSex();
					figureStatistic.birth = specimen.getBirth();
					figureStatistic.hand = specimen.getNamedHand();
					figureStatistic.brain = specimen.getNamedBrain();
					figureStatistic.type = specimen.getNamedOperationType();
					figureStatistic.number = specimen.getNamedOperationNo();

					// ... kazdy atrybut ...
					for (Map.Entry<String, AttributeResult> attributeEntry : figure.getValue().entrySet())
					{
						// ... o danej statystyce
						StatisticResult statisticResult = attributeEntry
								.getValue().getValue()
								.get(StatMapper.statisticNames[i]);
						if (attributeEntry.getKey().equals("Figure Standards"))
							figureStatistic.standards = getValue(statisticResult);
						else if (attributeEntry.getKey().equals("Pressure"))
							figureStatistic.pressure = getValue(statisticResult);
						else if (attributeEntry.getKey().equals(
								"Momentary Speed"))
							figureStatistic.speed = getValue(statisticResult);
						else if (attributeEntry.getKey().equals("Acceleration"))
							figureStatistic.acceleration = getValue(statisticResult);
						else if (attributeEntry.getKey().equals(
								"Direction Change (f'')"))
							figureStatistic.change = getValue(statisticResult);
						else if (attributeEntry.getKey().equals("Azimuth"))
							figureStatistic.azimuth = getValue(statisticResult);
						else if (attributeEntry.getKey().equals("Altitude"))
							figureStatistic.altitude = getValue(statisticResult);
						else if (attributeEntry.getKey().equals(
								"Dev. from Average"))
							figureStatistic.dev = getValue(statisticResult);
					}

					statistics.add(figureStatistic);
				}
			}
			
			
			// Po figurach
			/*for (Map.Entry<String, FigureResult> figureEntry : examEntry
					.getValue().getValue().entrySet())
			{
				

			}*/
		}

		return statistics;
	}
	

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getSurname()
	{
		return surname;
	}

	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	public String getPersonalID()
	{
		return personalID;
	}

	public void setPersonalID(String personalID)
	{
		this.personalID = personalID;
	}

	public String getSex()
	{
		return sex;
	}

	public void setSex(String sex)
	{
		this.sex = sex;
	}

	public Date getBirth()
	{
		return birth;
	}

	public void setBirth(Date birth)
	{
		this.birth = birth;
	}

	public String getHand()
	{
		return hand;
	}

	public void setHand(String hand)
	{
		this.hand = hand;
	}

	public String getBrain()
	{
		return brain;
	}

	public void setBrain(String brain)
	{
		this.brain = brain;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public int getNumber()
	{
		return number;
	}

	public void setNumber(int number)
	{
		this.number = number;
	}

	public String getStatistic()
	{
		return statistic;
	}

	public void setStatistic(String statistic)
	{
		this.statistic = statistic;
	}

	public Double getStandards()
	{
		return standards;
	}

	public void setStandards(Double standards)
	{
		this.standards = standards;
	}

	public Double getPressure()
	{
		return pressure;
	}

	public void setPressure(Double pressure)
	{
		this.pressure = pressure;
	}

	public Double getSpeed()
	{
		return speed;
	}

	public void setSpeed(Double speed)
	{
		this.speed = speed;
	}

	public Double getAcceleration()
	{
		return acceleration;
	}

	public void setAcceleration(Double acceleration)
	{
		this.acceleration = acceleration;
	}

	public Double getChange()
	{
		return change;
	}

	public void setChange(Double change)
	{
		this.change = change;
	}

	public Double getAzimuth()
	{
		return azimuth;
	}

	public void setAzimuth(Double azimuth)
	{
		this.azimuth = azimuth;
	}

	public Double getAltitude()
	{
		return altitude;
	}

	public void setAltitude(Double altitude)
	{
		this.altitude = altitude;
	}

	public Double getDev()
	{
		return dev;
	}

	public void setDev(Double dev)
	{
		this.dev = dev;
	}

	public String getExamination()
	{
		return examination;
	}

	public void setExamination(String examination)
	{
		this.examination = examination;
	}

	public String getFigure()
	{
		return figure;
	}

	public void setFigure(String figure)
	{
		this.figure = figure;
	}

}
