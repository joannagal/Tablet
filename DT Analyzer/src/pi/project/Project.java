package pi.project;

import java.sql.Date;
import java.util.LinkedList;

import pi.analyze.AnalysisResult;
import pi.analyze.Analyzer;
import pi.analyze.ECG.ECGAnalyzer;
import pi.population.Population;

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

	// OGOLNIE PONIZEJ SA GLOWNIE SETTERY I GETTERY
	// NO ALE TO W MIARE POTRZEB, BEDZIE SIE TA KLASE ROZBUDOWYWALO
	// NP. TRZEBA NAPISAC KLASE LOAD, SAVE
	
	public boolean load(String path)
	{
		return true;
	}

	public boolean save(String path)
	{
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
