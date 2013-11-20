package pi.gui.specimen.drawingtest.toolbar;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pi.gui.specimen.drawingtest.DrawingTestView;
import pi.gui.specimen.drawingtest.loader.LoadView;

public class LoadTest extends JPanel
{
	private JButton loadTest = new JButton("Load Test");

	private static final long serialVersionUID = 1L;
	private DrawingTestView parent;
	private LoadView loadView;

	public LoadTest(DrawingTestView parent)
	{
		this.parent = parent;
		this.loadView = new LoadView(parent);

		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		this.setMinimumSize(new Dimension(50, 50));

		this.setBorder(BorderFactory.createTitledBorder("Change Test"));

		this.loadTest.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
				int x = (int) ((dimension.getWidth() - loadView.getWidth()) / 2);
				int y = (int) ((dimension.getHeight() - loadView.getHeight()) / 2);
				loadView.setLocation(x, y);
				
				loadView.setVisible(true);
			}
		});

		this.add(this.loadTest);
	}

	public DrawingTestView getParent()
	{
		return this.parent;
	}

}
