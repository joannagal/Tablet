package pi.population;

import java.util.ArrayList;


public class Population
{
	private String name;

	private ArrayList<Specimen> specimen;

	public ArrayList<Specimen> getSpecimen()
	{
		return specimen;
	}

	public void setSpecimen(ArrayList<Specimen> specimen)
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
