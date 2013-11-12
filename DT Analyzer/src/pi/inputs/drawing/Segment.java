package pi.inputs.drawing;

import java.util.ArrayList;

public class Segment
{
	private ArrayList<PacketData> packet;
	private ArrayList<PacketData> linearized;

	public ArrayList<PacketData> getPacket()
	{
		return packet;
	}

	public void setPacket(ArrayList<PacketData> packet)
	{
		this.packet = packet;
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
