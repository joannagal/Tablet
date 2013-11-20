package michal;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;


import javax.swing.JFrame;
import javax.swing.JOptionPane;

import pi.gui.specimen.SpecimenView;
import pi.gui.specimen.drawingtest.DrawingTestView;
import pi.inputs.drawing.Drawing;
import pi.population.Specimen;


public class Dummy
{	
	private JFrame frame;
	private SpecimenView viewer;
	
	public Dummy()
	{
		frame = new JFrame("DT Prototype");
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(0, 0, dimension.width, dimension.height);

		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0d;
		c.weighty = 1.0d;
		
		Drawing before, after;
		try
		{
			before = new Drawing("src/michal/test6.mtb");
			before.setLabel("ABC: Before");
			
			after = new Drawing("src/michal/test5.mtb");
			after.setLabel("ABC: After");
			
			Specimen spec = new Specimen();
			spec.setBefore(before);
			spec.setAfter(after);
			spec.setName(new String("Ala"));
			spec.setSurname(new String("MaKota"));
			
			this.viewer = new SpecimenView(spec);
			frame.add(this.viewer, c);

			
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "UPS!");
		}

		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setVisible(true);
		
	}
	
}


