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

		Drawing drawing = new Drawing("src/michal/test6.mtb");

		this.viewer = new DTViewer(new Dimension(1300, 800));
		this.viewer.setLocation(0, 0);
		this.viewer.setDrawing(drawing);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		frame.add(this.viewer);
		
		frame.setVisible(true);
	}
	
}


