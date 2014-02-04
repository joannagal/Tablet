package pi.statistics.logic.report;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
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
			return result.getValue().get(0);
	}

	@SuppressWarnings("rawtypes")
	public static Collection getFigureStatistics(Specimen specimen)
	{

		Vector<FigureStatistic> statistics = new Vector<FigureStatistic>(2
				* StatMapper.figureNames.length
				* StatMapper.statisticNames.length);

		if (specimen.getResult() == null)
			return statistics;

		// Po Examination
		for (Map.Entry<String, DrawingResult> examEntry : specimen.getResult()
				.getValue().entrySet())
		{
			// Po figurach
			for (Map.Entry<String, FigureResult> figureEntry : examEntry
					.getValue().getValue().entrySet())
			{
				// wyciagamy dla kazdej statystyki...
				for (int i = 0; i < StatMapper.statisticNames.length; i++)
				{
					FigureStatistic figure = new FigureStatistic();
					figure.setExamination(examEntry.getKey());
					figure.setFigure(figureEntry.getKey());
					figure.setStatistic(StatMapper.statisticNames[i]);
					figure.name = specimen.getName();
					figure.surname = specimen.getSurname();
					figure.personalID = specimen.getPesel();
					figure.sex = specimen.getNamedSex();
					figure.birth = specimen.getBirth();
					figure.hand = specimen.getNamedHand();
					figure.brain = specimen.getNamedBrain();
					figure.type = specimen.getNamedOperationType();
					figure.number = specimen.getOperationTestNo();

					// ... kazdy atrybut ...
					for (Map.Entry<String, AttributeResult> attributeEntry : figureEntry
							.getValue().getValue().entrySet())
					{
						// ... o danej statystyce
						StatisticResult statisticResult = attributeEntry
								.getValue().getValue()
								.get(StatMapper.statisticNames[i]);
						if (attributeEntry.getKey().equals("Figure Standards"))
							figure.standards = getValue(statisticResult);
						else if (attributeEntry.getKey().equals("Pressure"))
							figure.pressure = getValue(statisticResult);
						else if (attributeEntry.getKey().equals(
								"Momentary Speed"))
							figure.speed = getValue(statisticResult);
						else if (attributeEntry.getKey().equals("Acceleration"))
							figure.acceleration = getValue(statisticResult);
						else if (attributeEntry.getKey().equals(
								"Direction Change (f'')"))
							figure.change = getValue(statisticResult);
						else if (attributeEntry.getKey().equals("Azimuth"))
							figure.azimuth = getValue(statisticResult);
						else if (attributeEntry.getKey().equals("Altitude"))
							figure.altitude = getValue(statisticResult);
						else if (attributeEntry.getKey().equals(
								"Dev. from Average"))
							figure.dev = getValue(statisticResult);
					}

					statistics.add(figure);
				}

			}
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
