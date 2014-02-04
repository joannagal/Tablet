package pi.inputs.drawing;

import java.util.ArrayList;

import pi.utilities.Range;

public class Segment
{
	private Range range;

	private ArrayList<PacketData> linearized;

	public Range getRange()
	{
		return range;
	}

	public void setRange(Range range)
	{
		this.range = range;
	}

	public ArrayList<PacketData> getLinearized()
	{
		return linearized;
	}

	public void setLinearized(ArrayList<PacketData> linearized)
	{
		this.linearized = linearized;
	}
}
