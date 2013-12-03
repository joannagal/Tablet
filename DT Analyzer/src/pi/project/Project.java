package pi.project;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import pi.analyze.AnalysisResult;
import pi.analyze.Analyzer;
import pi.analyze.ECG.ECGAnalyzer;
import pi.inputs.drawing.Drawing;
import pi.population.Population;
import pi.population.Specimen;
import pi.project.saver.PopSaver;

//-------------------------------------------
/*
 TA KLASA TRZYMA WSZYSTKIE INFO O CALYM 
 POJEDYNCZYM PROJEKCIE 

 TA SMIESZNA STRUKTURA DRZEWKA JAK
 PAMIETACIE 
 */
//-------------------------------------------

public class Project
{
	// NAZWA PROJEKTU
	private String name;

	// SCIEZKA PLIKU PROJEKTU
	private String path;

	// DATA UTWORZENIA PROJEKTU
	private Date date;

	// PIERWSZA POPULACJA
	// ONA TWORZONA JEST OBOWIAZKOWO
	// NO BO NAWET W NAJPROSTSZYM PROJEKCIE
	// Z JEDNYM LUDKIEM
	// MUSI ON NALEZEC W KONCU DO POPULACJI
	// (KTORA ZAWIERA 1 OSOBNIKA)
	private Population firstPopulation;

	// DRUGA POPULACJA, WIADOMO
	// TWORZYMY JAK JEST PROJEKT Z DRUGA POPULACJA
	private Population secondPopulation;

	// KLASA PROJEKT JEST OWNEREM
	// MODULU STATYSTYCZNEGO
	private Analyzer analyzer;

	// TUTAJ PRZECHOWUJEMY WYNIKI ANALIZY
	private LinkedList<AnalysisResult> results;

	// PO WCZYTANIU USTALAMY TYP PROJEKTU
	// KOLEJNOSC JAK W TYM PLIKU Z ZALOZENIAMI
	private int type;
	public static final int SPECIMEN_SINGLE = 0;
	public static final int SPECIMEN_PAIR = 1;
	public static final int POPULATION_SINGLE = 2;
	public static final int POPULATION_PAIR = 3;

	public Project()
	{
		setAnalyzer(new ECGAnalyzer());
	}

	public boolean save(String path)
	{
		if (path == null)
			path = this.path;
		
		PopSaver ps = new PopSaver(this);
		try
		{
			ps.save(path);
			this.path = path;
			
		} catch (FileNotFoundException | UnsupportedEncodingException
				| XMLStreamException | FactoryConfigurationError e)
		{

			e.printStackTrace();
		}

		return true;
	}

	public boolean create(ArrayList<String> firstBefore,
			ArrayList<String> firstAfter, ArrayList<String> secondBefore,
			ArrayList<String> secondAfter)
	{
		if (firstBefore != null)
		{
			System.out.printf("A\n");
			int size = firstBefore.size();
			ArrayList<Specimen> specimenArray = new ArrayList<Specimen>(size);
			Specimen specimen;
			Drawing temp;
			for (int i = 0; i < size; i++)
			{
				try
				{
					temp = new Drawing(firstBefore.get(i));
					temp.setLabel("First Population: Before:");
					specimen = new Specimen();
					specimen.setBefore(temp);
					specimenArray.add(specimen);

				} catch (Exception e)
				{
					String msg = "Something goes wrong with: "
							+ firstBefore.get(i);
					JOptionPane.showMessageDialog(null, msg);
					return false;
				}
			}
			this.getFirstPopulation().setName("First Population");
			this.getFirstPopulation().setSpecimen(specimenArray);
		}

		if (firstAfter != null)
		{
			System.out.printf("B\n");
			int size = firstAfter.size();
			ArrayList<Specimen> specimenArray = this.getFirstPopulation()
					.getSpecimen();
			Drawing temp;
			for (int i = 0; i < size; i++)
			{
				try
				{
					temp = new Drawing(firstAfter.get(i));
					temp.setLabel("First Population: After:");
					specimenArray.get(i).setAfter(temp);

				} catch (Exception e)
				{
					String msg = "Something goes wrong with: "
							+ firstAfter.get(i);
					JOptionPane.showMessageDialog(null, msg);
					return false;
				}
			}
		}

		//

		if (secondBefore != null)
		{
			int size = secondBefore.size();
			ArrayList<Specimen> specimenArray = new ArrayList<Specimen>(size);
			Specimen specimen;
			Drawing temp;
			for (int i = 0; i < size; i++)
			{
				try
				{
					temp = new Drawing(secondBefore.get(i));
					temp.setLabel("Second Population: Before:");
					specimen = new Specimen();
					specimen.setBefore(temp);
					specimenArray.add(specimen);

				} catch (Exception e)
				{
					String msg = "Something goes wrong with: "
							+ secondBefore.get(i);
					JOptionPane.showMessageDialog(null, msg);
					return false;
				}
			}
			this.getSecondPopulation().setName("Second Population");
			this.getSecondPopulation().setSpecimen(specimenArray);
		}

		if (secondAfter != null)
		{
			int size = secondAfter.size();
			ArrayList<Specimen> specimenArray = this.getSecondPopulation()
					.getSpecimen();
			Drawing temp;
			for (int i = 0; i < size; i++)
			{
				try
				{
					temp = new Drawing(secondAfter.get(i));
					temp.setLabel("Second Population: After:");
					specimenArray.get(i).setAfter(temp);

				} catch (Exception e)
				{
					String msg = "Something goes wrong with: "
							+ secondAfter.get(i);
					JOptionPane.showMessageDialog(null, msg);
					return false;
				}
			}
		}

		return true;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public Population getFirstPopulation()
	{
		return firstPopulation;
	}

	public void setFirstPopulation(Population firstPopulation)
	{
		this.firstPopulation = firstPopulation;
	}

	public Population getSecondPopulation()
	{
		return secondPopulation;
	}

	public void setSecondPopulation(Population secondPopulation)
	{
		this.secondPopulation = secondPopulation;
	}

	public Analyzer getAnalyzer()
	{
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer)
	{
		this.analyzer = analyzer;
	}

	public LinkedList<AnalysisResult> getResults()
	{
		return results;
	}

	public void setResults(LinkedList<AnalysisResult> results)
	{
		this.results = results;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

}
