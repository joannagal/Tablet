package pi.gui.project.population.specimen.drawingtest.loader;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import pi.gui.project.population.specimen.drawingtest.DrawingTestView;
import pi.inputs.drawing.Drawing;

public class LoadView extends JFrame
{
	private static final long serialVersionUID = 1L;

	private JButton cancelButton = new JButton("Cancel");
	private JButton loadButton = new JButton("Load");
	DrawingTestView parent; 
	
	private JTextField editPath = new JTextField();
	private JButton chooseFile = new JButton("Choose");
	private JProgressBar progressBar = new JProgressBar();
	private final JFileChooser fc = new JFileChooser();
	
	public LoadView(DrawingTestView parent)
	{
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "htd and mtb files (*.htd, *.mtb)", "htd", "mtb");
	    fc.setFileFilter(filter);
		
		this.setLayout(null);
		this.parent = parent;
		this.setTitle("Load Test");
		this.setResizable(false);
		this.setSize(new Dimension(370, 130));
		
		this.editPath.setBounds(10, 10, 240, 22);
		this.add(this.editPath);
		
		this.chooseFile.setBounds(255, 10, 100, 22);
		this.add(this.chooseFile);
		this.chooseFile.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				 int returnVal = fc.showOpenDialog(null);

			     if (returnVal == JFileChooser.APPROVE_OPTION) {
			            File file = fc.getSelectedFile();
			            editPath.setText(file.getAbsolutePath());
			     } 
			}
		});
		
		
		
		this.progressBar.setBounds(10, 40, 345, 22);
		this.add(this.progressBar);
		
		this.cancelButton.setBounds(10, 70, 100, 22);
		this.cancelButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				editPath.setText("");
				setVisible(false);
			}
		});
		this.add(this.cancelButton);
		
		this.loadButton.setBounds(255, 70, 100, 22);
		this.loadButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				Drawing drawing;
				try
				{
					drawing = new Drawing(editPath.getText());
					getParent().setDrawing(drawing);

				} catch (Exception e)
				{
					JOptionPane.showMessageDialog(null, "Something goes wrong!");
				}
			}
		});
		this.add(this.loadButton);

	}
	
	public DrawingTestView getParent()
	{
		return this.parent;
	}
}
