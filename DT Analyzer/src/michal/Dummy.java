package michal;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import pi.GUI.DTViewer.DTViewer;
import pi.inputs.drawing.Drawing;
import pi.inputs.drawing.PacketData;

public class Dummy
{	
	private JFrame frame;
	private DTViewer viewer;
	
	public Dummy()
	{
		frame = new JFrame("DT Prototype");
		frame.setBounds(200, 100, 1400, 900);
		frame.setLayout(null);

		this.viewer = new DTViewer(new Dimension(1300, 800), new Drawing("src/michal/test.mtb"));
		this.viewer.setLocation(0, 0);
		
		frame.add(this.viewer);
	
		
		frame.setVisible(true);
	}
	
}


