package pi.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import pi.gui.chooseproject.ChooseProjectController;
import pi.gui.chooseproject.ChooseProjectView;
import pi.project.Project;
import pi.project.importer.PopImporter;
import pi.shared.SharedController;

public class MenuController implements ActionListener
{
	MenuView menuView;

	public MenuController(MenuView view)
	{
		this.menuView = view;
		this.menuView.setMenuItemListener(this);
	}

	public void actionPerformed(ActionEvent ae)
	{

		String action = ae.getActionCommand();

		if (action.equals("EXIT"))
		{

			int response = JOptionPane.showConfirmDialog(null,
					"Do you want to exit?", "Confirm",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION)
			{
				menuView.closeParent();
			}
		}

		if (action.equals("ABOUT"))
		{

		}

		if (action.equals("CREATE_PROJECT"))
		{
			ChooseProjectView projectView = new ChooseProjectView();
			ChooseProjectController projectController = new ChooseProjectController(
					projectView);
			projectView.setVisible(true);

			this.menuView.setInChoose(true);
		}

		if (action.equals("OPEN_PROJECT"))
		{
			int returnVal = this.menuView.getFc().showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				File file = this.menuView.getFc().getSelectedFile();
			
				// create with importer

				PopImporter pi = new PopImporter();

				XMLReader p;
				try
				{
					p = XMLReaderFactory.createXMLReader();
					p.setContentHandler(pi);

					String path = file.getAbsolutePath();
					path = path.replace("\\", "/");
					String url = String.format("file:///%s", path);
					
					p.parse(url);

					Project project = pi.getProject();
					project.setPath(file.getAbsolutePath());
					SharedController.getInstance().getFrame()
							.initProjectView(project);

				} catch (SAXException e)
				{
					e.printStackTrace();
				} catch (IOException e)
				{
					e.printStackTrace();
				}

			}
		}
		if (action.equals("SAVE_PROJECT"))
		{
			if (SharedController.getInstance().getProject() == null)
				return;
			if (SharedController.getInstance().getProject().getPath() == null)
			{
				int returnVal = this.menuView.getFc().showSaveDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					File file = this.menuView.getFc().getSelectedFile();
					SharedController.getInstance().getProject()
							.save(file.getAbsolutePath());
				}
			} else
			{
				SharedController.getInstance().getProject().save(null);
			}
		}
		if (action.equals("SAVE_AS_PROJECT"))
		{
			if (SharedController.getInstance().getProject() == null)
				return;

			int returnVal = this.menuView.getFc().showSaveDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				File file = this.menuView.getFc().getSelectedFile();
				SharedController.getInstance().getProject()
						.save(file.getAbsolutePath());
			}

		}
		if (action.equals("CLOSE_PROJECT"))
		{
			int result = JOptionPane.showConfirmDialog(null, "Are you sure?",
					"Confirm", JOptionPane.YES_NO_OPTION);

			if (result == 0)
			{
				SharedController.getInstance().getFrame().closeProject();
			}
		}

	}

}
