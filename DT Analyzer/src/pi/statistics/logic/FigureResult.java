package pi.statistics.logic;

import java.util.HashMap;
import java.util.Map;

import pi.inputs.drawing.Figure;
import pi.statistics.logic.extenders.AccelerationResult;
import pi.statistics.logic.extenders.AltitudeResult;
import pi.statistics.logic.extenders.AzimuthResult;
import pi.statistics.logic.extenders.ChangeDirectionResult;
import pi.statistics.logic.extenders.DFAverage;
import pi.statistics.logic.extenders.FigureStandardsResult;
import pi.statistics.logic.extenders.MSpeedResult;
import pi.statistics.logic.extenders.PressureResult;

public class FigureResult
{
	private Map<String, AttributeResult> value;
	private Figure figure;

	public FigureResult(Figure figure)
	{
		this.setFigure(figure);
	}

	public void calculateResult()
	{
		this.value = new HashMap<String, AttributeResult>();

		if (this.figure.getTotalPoints() < 5)
			return;

		if (StatMapper.attributeAvaible.get("Pressure"))
		{
			PressureResult pressure = new PressureResult(this.figure
					.getParent().getPacket(), this.figure.getSegment());
			pressure.calculateResult();
			this.value.put("Pressure", pressure);
		}

		MSpeedResult mSpeed = new MSpeedResult(this.figure.getParent()
				.getPacket(), this.figure.getSegment());
		
		if (StatMapper.attributeAvaible.get("Momentary Speed"))
		{
			mSpeed.calculateResult();
			this.value.put("Momentary Speed", mSpeed);
		}

		if (
			(StatMapper.attributeAvaible.get("Momentary Speed")) &&
			(StatMapper.attributeAvaible.get("Acceleration")) )
		{
			AccelerationResult acceleration = new AccelerationResult(
					mSpeed.getForAcceleration(), mSpeed.getFreq());
			acceleration.calculateResult();
			this.value.put("Acceleration", acceleration);
		}

		if (StatMapper.attributeAvaible.get("Figure Standards"))
		{
			FigureStandardsResult standards = new FigureStandardsResult(
					this.figure.getParent().getPacket(),
					this.figure.getSegment());
			standards.calculateResult();
			this.value.put("Figure Standards", standards);
		}

		if (StatMapper.attributeAvaible.get("Direction Change (f'')"))
		{
			ChangeDirectionResult direction = new ChangeDirectionResult(
					this.figure.getParent().getPacket(),
					this.figure.getSegment());
			direction.calculateResult();
			this.value.put("Direction Change (f'')", direction);
		}

		if (StatMapper.attributeAvaible.get("Azimuth"))
		{
			AzimuthResult azimuth = new AzimuthResult(this.figure.getParent()
					.getPacket(), this.figure.getSegment());
			azimuth.calculateResult();
			this.value.put("Azimuth", azimuth);
		}

		if (StatMapper.attributeAvaible.get("Altitude"))
		{
			AltitudeResult altitude = new AltitudeResult(this.figure
					.getParent().getPacket(), this.figure.getSegment());
			altitude.calculateResult();
			this.value.put("Altitude", altitude);
		}

		if (StatMapper.attributeAvaible.get("Dev. from Average"))
		{
			DFAverage dfaverage = new DFAverage(this.figure.getParent()
					.getPacket(), this.figure.getSegment());
			dfaverage.calculateResult();
			this.value.put("Dev. from Average", dfaverage);
		}
	}

	public Map<String, AttributeResult> getValue()
	{
		return value;
	}

	public void setValue(Map<String, AttributeResult> value)
	{
		this.value = value;
	}

	public Figure getFigure()
	{
		return figure;
	}

	public void setFigure(Figure figure)
	{
		this.figure = figure;
	}

}
