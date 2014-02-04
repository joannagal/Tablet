package pi.shared;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class ProgressView extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JProgressBar progress = new JProgressBar();

	public ProgressView()
	{
		this.setTitle("Progress...");
		this.setSize(300, 100);
		this.setLayout(null);
		this.setResizable(false);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		this.setLocation(x, y);

		progress.setMinimum(0);
		progress.setMaximum(1);
		progress.setBounds(15, 15, 270, 40);
		this.add(this.progress);
	}

	public void init(int max)
	{
		this.progress.setMaximum(max);
		this.progress.setValue(0);
		this.setVisible(true);
	}

	public void increase()
	{
		this.progress.setValue(this.progress.getValue() + 1);
		if (this.progress.getValue() == this.progress.getMaximum())
			this.setVisible(false);
	}

	public void close()
	{
		this.setVisible(false);
	}

}
