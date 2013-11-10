package pi.inputs.drawing;

import java.util.ArrayList;

public class Figure
{
	private ArrayList <PacketData> packet;
	private ArrayList <PacketData> linearized;

	public ArrayList <PacketData> getLinearized()
	{
		return linearized;
	}

	public void setLinearized(ArrayList <PacketData> linearized)
	{
		this.linearized = linearized;
	}

	public ArrayList <PacketData> getPacket()
	{
		return packet;
	}

	public void setPacket(ArrayList <PacketData> packet)
	{
		this.packet = packet;
	}
	
	
}
