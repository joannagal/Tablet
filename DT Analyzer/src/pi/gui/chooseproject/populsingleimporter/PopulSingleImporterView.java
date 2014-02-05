package pi.gui.chooseproject.populsingleimporter;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import pi.shared.SharedController;

public class PopulSingleImporterView extends JFrame
{

	private static final long serialVersionUID = 1L;
	private PopulSingleImporterController controller;

	private JTabbedPane tabbedPane = new JTabbedPane();

	private DefaultListModel<String> firstBeforeListModel = new DefaultListModel<String>();
	private JList<String> firstBeforeList = new JList<String>(
			firstBeforeListModel);
	private JScrollPane firstBeforePane = new JScrollPane(firstBeforeList,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	private LinkedList<File> firstBeforeFiles = new LinkedList<File>();

	private DefaultListModel<String> secondBeforeListModel = new DefaultListModel<String>();
	private JList<String> secondBeforeList = new JList<String>(
			secondBeforeListModel);
	private JScrollPane secondBeforePane = new JScrollPane(secondBeforeList,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	private LinkedList<File> secondBeforeFiles = new LinkedList<File>();

	private JButton addButton = new JButton("Select");
	private JButton delButton = new JButton("Delete");
	private JButton upButton = new JButton("Up");
	private JButton downButton = new JButton("Down");

	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");

	private final JFileChooser fc = new JFileChooser();

	public PopulSingleImporterView()
	{
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"htd and mtb files (*.htd, *.mtb)", "htd", "mtb");
		fc.setFileFilter(filter);

		this.controller = new PopulSingleImporterController(this);

		this.setTitle("Create Two Populations: Single");

		this.setSize(400, 500);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		this.setLocation(x, y);

		URL iconURL = getClass().getResource("../../images/Logo.png");
		ImageIcon icon = new ImageIcon(iconURL);
		this.setIconImage(icon.getImage());
		
		this.setResizable(false);
		this.setLayout(null);

		this.tabbedPane.setBounds(0, 0, 400, 380);

		JPanel firstPanel = new JPanel();
		firstPanel.setLayout(null);
		firstPanel.add(this.firstBeforePane);
		this.firstBeforePane.setBounds(0, 0, 390, 345);
		this.tabbedPane.addTab("First population", firstPanel);

		JPanel secondPanel = new JPanel();
		secondPanel.setLayout(null);
		secondPanel.add(this.secondBeforePane);
		this.secondBeforePane.setBounds(0, 0, 390, 345);
		this.tabbedPane.addTab("Second population", secondPanel);

		this.add(this.tabbedPane);

		this.addButton.setBounds(17, 385, 80, 23);
		this.addButton.setActionCommand("ADD");
		this.addButton.addActionListener(this.controller);
		this.add(this.addButton);

		this.delButton.setBounds(97, 385, 80, 23);
		this.delButton.setActionCommand("DEL");
		this.delButton.addActionListener(this.controller);
		this.add(this.delButton);

		this.upButton.setBounds(177, 385, 80, 23);
		this.upButton.setActionCommand("UP");
		this.upButton.addActionListener(this.controller);
		this.add(this.upButton);

		this.downButton.setBounds(257, 385, 80, 23);
		this.downButton.setActionCommand("DOWN");
		this.downButton.addActionListener(this.controller);
		this.add(this.downButton);

		this.okButton.setBounds(283, 435, 100, 30);
		this.cancelButton.setBounds(17, 435, 100, 30);

		this.okButton.setActionCommand("OK");
		this.cancelButton.setActionCommand("CANCEL");

		this.okButton.addActionListener(this.controller);
		this.cancelButton.addActionListener(this.controller);

		this.add(this.okButton);
		this.add(this.cancelButton);

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosed(WindowEvent e)
			{
				SharedController.getInstance().getFrame().getMenuView()
						.setInChoose(false);
			}
		});

	}

	public ArrayList<ArrayList<String>> getPaths()
	{
		ArrayList<ArrayList<String>> paths = new ArrayList<ArrayList<String>>(2);

		int size = this.firstBeforeFiles.size();
		ArrayList<String> firstBefore = new ArrayList<String>(size);
		Iterator<File> it = this.firstBeforeFiles.iterator();
		File file;

		while (it.hasNext())
		{
			file = it.next();
			System.out.println(file.getAbsolutePath());
			firstBefore.add(file.getAbsolutePath());
		}
		paths.add(firstBefore);

		size = this.secondBeforeFiles.size();
		ArrayList<String> secondBefore = new ArrayList<String>(size);
		it = this.secondBeforeFiles.iterator();

		while (it.hasNext())
		{
			file = it.next();
			System.out.println(file.getAbsolutePath());
			secondBefore.add(file.getAbsolutePath());
		}
		paths.add(secondBefore);

		return paths;
	}

	public DefaultListModel<String> getCurrentListModel()
	{
		int tab = this.tabbedPane.getSelectedIndex();
		if (tab == -1)
			return null;
		else if (tab == 0)
			return this.firstBeforeListModel;
		else
			return this.secondBeforeListModel;
	}

	public LinkedList<File> getCurrentFileList()
	{
		int tab = this.tabbedPane.getSelectedIndex();
		if (tab == -1)
			return null;
		else if (tab == 0)
			return this.firstBeforeFiles;
		else
			return this.secondBeforeFiles;
	}

	public JList<String> getCurrentList()
	{
		int tab = this.tabbedPane.getSelectedIndex();
		if (tab == -1)
			return null;
		else if (tab == 0)
			return this.firstBeforeList;
		else
			return this.secondBeforeList;
	}

	public JFileChooser getFc()
	{
		return fc;
	}

	public LinkedList<File> getFirstBeforeFiles()
	{
		return firstBeforeFiles;
	}

	public void setFirstBeforeFiles(LinkedList<File> firstBeforeFiles)
	{
		this.firstBeforeFiles = firstBeforeFiles;
	}

	public LinkedList<File> getSecondBeforeFiles()
	{
		return secondBeforeFiles;
	}

	public void setSecondBeforeFiles(LinkedList<File> secondBeforeFiles)
	{
		this.secondBeforeFiles = secondBeforeFiles;
	}

}
