package pi.gui.chooseproject.populpairimporter;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
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

public class PopulPairImporterView extends JFrame
{

	private static final long serialVersionUID = 1L;
	private PopulPairImporterController controller;

	private JTabbedPane tabbedPane = new JTabbedPane();

	private DefaultListModel<String> firstBeforeListModel = new DefaultListModel<String>();
	private JList<String> firstBeforeList = new JList<String>(
			firstBeforeListModel);
	private JScrollPane firstBeforePane = new JScrollPane(firstBeforeList,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	private LinkedList<File> firstBeforeFiles = new LinkedList<File>();

	private DefaultListModel<String> firstAfterListModel = new DefaultListModel<String>();
	private JList<String> firstAfterList = new JList<String>(
			firstAfterListModel);
	private JScrollPane firstAfterPane = new JScrollPane(firstAfterList,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	private LinkedList<File> firstAfterFiles = new LinkedList<File>();

	private DefaultListModel<String> secondBeforeListModel = new DefaultListModel<String>();
	private JList<String> secondBeforeList = new JList<String>(
			secondBeforeListModel);
	private JScrollPane secondBeforePane = new JScrollPane(secondBeforeList,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	private LinkedList<File> secondBeforeFiles = new LinkedList<File>();

	private DefaultListModel<String> secondAfterListModel = new DefaultListModel<String>();
	private JList<String> secondAfterList = new JList<String>(
			secondAfterListModel);
	private JScrollPane secondAfterPane = new JScrollPane(secondAfterList,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	private LinkedList<File> secondAfterFiles = new LinkedList<File>();

	private JButton addButton = new JButton("Select");
	private JButton delButton = new JButton("Delete");
	private JButton upButton = new JButton("Up");
	private JButton downButton = new JButton("Down");

	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");

	private final JFileChooser fc = new JFileChooser();

	public PopulPairImporterView()
	{
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"htd and mtb files (*.htd, *.mtb)", "htd", "mtb");
		fc.setFileFilter(filter);

		this.controller = new PopulPairImporterController(this);

		this.setTitle("Create Two Populations: Single");

		this.setSize(550, 500);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);

		this.setLocation(x, y);
		this.setResizable(false);
		this.setLayout(null);

		this.tabbedPane.setBounds(0, 0, 550, 380);

		JPanel firstBeforePanel = new JPanel();
		firstBeforePanel.setLayout(null);
		firstBeforePanel.add(this.firstBeforePane);
		this.firstBeforePane.setBounds(0, 0, 540, 345);
		this.tabbedPane.addTab("First Popul: Before", firstBeforePanel);

		JPanel firstAfterPanel = new JPanel();
		firstAfterPanel.setLayout(null);
		firstAfterPanel.add(this.firstAfterPane);
		this.firstAfterPane.setBounds(0, 0, 540, 345);
		this.tabbedPane.addTab("First Popul: After", firstAfterPanel);

		JPanel secondBeforePanel = new JPanel();
		secondBeforePanel.setLayout(null);
		secondBeforePanel.add(this.secondBeforePane);
		this.secondBeforePane.setBounds(0, 0, 540, 345);
		this.tabbedPane.addTab("Second Popul: Before", secondBeforePanel);

		JPanel secondAfterPanel = new JPanel();
		secondAfterPanel.setLayout(null);
		secondAfterPanel.add(this.secondAfterPane);
		this.secondAfterPane.setBounds(0, 0, 540, 345);
		this.tabbedPane.addTab("Second Popul: After", secondAfterPanel);

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
		ArrayList<ArrayList<String>> paths = new ArrayList<ArrayList<String>>(4);

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

		size = this.firstAfterFiles.size();
		ArrayList<String> firstAfter = new ArrayList<String>(size);
		it = this.firstAfterFiles.iterator();

		while (it.hasNext())
		{
			file = it.next();
			System.out.println(file.getAbsolutePath());
			firstAfter.add(file.getAbsolutePath());
		}
		paths.add(firstAfter);

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

		size = this.secondAfterFiles.size();
		ArrayList<String> secondAfter = new ArrayList<String>(size);
		it = this.secondAfterFiles.iterator();

		while (it.hasNext())
		{
			file = it.next();
			System.out.println(file.getAbsolutePath());
			secondAfter.add(file.getAbsolutePath());
		}
		paths.add(secondAfter);

		return paths;
	}

	public DefaultListModel<String> getCurrentListModel()
	{
		int tab = this.tabbedPane.getSelectedIndex();
		if (tab == -1)
			return null;
		else if (tab == 0)
			return this.firstBeforeListModel;
		else if (tab == 1)
			return this.firstAfterListModel;
		else if (tab == 2)
			return this.secondBeforeListModel;
		else
			return this.secondAfterListModel;
	}

	public LinkedList<File> getCurrentFileList()
	{
		int tab = this.tabbedPane.getSelectedIndex();
		if (tab == -1)
			return null;
		else if (tab == 0)
			return this.firstBeforeFiles;
		else if (tab == 1)
			return this.firstAfterFiles;
		else if (tab == 2)
			return this.secondBeforeFiles;
		else
			return this.secondAfterFiles;
	}

	public JList<String> getCurrentList()
	{
		int tab = this.tabbedPane.getSelectedIndex();
		if (tab == -1)
			return null;
		else if (tab == 0)
			return this.firstBeforeList;
		else if (tab == 1)
			return this.firstAfterList;
		else if (tab == 2)
			return this.secondBeforeList;
		else
			return this.secondAfterList;
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

	public LinkedList<File> getFirstAfterFiles()
	{
		return firstAfterFiles;
	}

	public void setFirstAfterFiles(LinkedList<File> firstAfterFiles)
	{
		this.firstAfterFiles = firstAfterFiles;
	}

	public LinkedList<File> getSecondBeforeFiles()
	{
		return secondBeforeFiles;
	}

	public void setSecondBeforeFiles(LinkedList<File> secondBeforeFiles)
	{
		this.secondBeforeFiles = secondBeforeFiles;
	}

	public LinkedList<File> getSecondAfterFiles()
	{
		return secondAfterFiles;
	}

	public void setSecondAfterFiles(LinkedList<File> secondAfterFiles)
	{
		this.secondAfterFiles = secondAfterFiles;
	}

}
