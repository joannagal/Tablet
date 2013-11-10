package pi.population;

import java.util.ArrayList;;

//-------------------------------------------
/*
	KLASA - POPULACJA
*/
//-------------------------------------------

public class Population
{
	// NAZWA POPULACJI
	// NIE WIEM CZY SIE PRZYDA, RACZEJ WZGLEDY ARTYSTYCZNE
	private String name;
	
	// WIADOMO - WEKTOR OSOBNIKOW
	private ArrayList <Specimen> specimen;
	
	
	// SETTERY I GETTERY
	public ArrayList <Specimen> getSpecimen()
	{
		return specimen;
	}

	public void setSpecimen(ArrayList <Specimen> specimen)
	{
		this.specimen = specimen;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	

}
