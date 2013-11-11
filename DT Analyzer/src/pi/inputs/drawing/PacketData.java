package pi.inputs.drawing;

public class PacketData
{
	private int pkTime = 0;
	private int pkX = 0;
	private int pkY = 0;
	private int pkPressure = 0;
	private int pkAzimuth = 0;
	private int pkAltitude = 0;
	private boolean broken = false;

	public PacketData getCopy()
	{
		PacketData result = new PacketData();
		result.setPkAltitude(this.pkAltitude);
		result.setPkAzimuth(this.pkAzimuth);
		result.setPkPressure(this.pkPressure);
		result.setPkTime(this.pkTime);
		result.setPkX(this.pkX);
		result.setPkY(this.pkY);
		result.setBroken(this.broken);
		return result;
	}

	public int getPkTime()
	{
		return pkTime;
	}

	public void setPkTime(int pkTime)
	{
		this.pkTime = pkTime;
	}

	public int getPkX()
	{
		return pkX;
	}

	public void setPkX(int pkX)
	{
		this.pkX = pkX;
	}

	public int getPkY()
	{
		return pkY;
	}

	public void setPkY(int pkY)
	{
		this.pkY = pkY;
	}

	public int getPkPressure()
	{
		return pkPressure;
	}

	public void setPkPressure(int pkPressure)
	{
		this.pkPressure = pkPressure;
	}

	public int getPkAzimuth()
	{
		return pkAzimuth;
	}

	public void setPkAzimuth(int pkAzimuth)
	{
		this.pkAzimuth = pkAzimuth;
	}

	public int getPkAltitude()
	{
		return pkAltitude;
	}

	public void setPkAltitude(int pkAltitude)
	{
		this.pkAltitude = pkAltitude;
	}

	public boolean isBroken()
	{
		return broken;
	}

	public void setBroken(boolean broken)
	{
		this.broken = broken;
	}

}
