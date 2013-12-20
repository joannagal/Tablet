package pi.statistics.logic;

import java.util.HashMap;
import java.util.Map;

import pi.inputs.drawing.Figure;
import pi.statistics.logic.extenders.AccelerationResult;
import pi.statistics.logic.extenders.AltitudeResult;
import pi.statistics.logic.extenders.AzimuthResult;
import pi.statistics.logic.extenders.ChangeDirectionResult;
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
		
		PressureResult pressure = new PressureResult(this.figure.getParent().getPacket(), this.figure.getSegment());
		pressure.calculateResult();
		this.value.put("Pressure", pressure);
		
		MSpeedResult mSpeed = new MSpeedResult(this.figure.getParent().getPacket(), this.figure.getSegment());
		mSpeed.calculateResult();
		this.value.put("Momentary Speed", mSpeed);
		
		AccelerationResult acceleration = new AccelerationResult(mSpeed.getForAcceleration(), mSpeed.getFreq());
		acceleration.calculateResult();
		this.value.put("Acceleration", acceleration);
		
		FigureStandardsResult standards = new FigureStandardsResult(this.figure.getParent().getPacket(), this.figure.getSegment());
		standards.calculateResult();
		this.value.put("Figure Standards", standards);
		
		ChangeDirectionResult direction = new ChangeDirectionResult(this.figure.getParent().getPacket(), this.figure.getSegment());
		direction.calculateResult();
		this.value.put("Direction Change (f'')", direction);
		
		AzimuthResult azimuth = new AzimuthResult(this.figure.getParent().getPacket(), this.figure.getSegment());
		azimuth.calculateResult();
		this.value.put("Azimuth", azimuth);
		
		AltitudeResult altitude = new AltitudeResult(this.figure.getParent().getPacket(), this.figure.getSegment());
		altitude.calculateResult();
		this.value.put("Altitude", altitude);
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
