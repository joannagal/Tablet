package pi.population;

import java.sql.Date;

import pi.inputs.drawing.Drawing;
import pi.statistics.logic.SpecimenResult;


//-------------------------------------------
/*
	KLASA - OSOBNIK
*/
//-------------------------------------------

public class Specimen
{
	// IMIE, NAZWISKO OSOBNIKA
	// W PRANIU MOZE WYJSC ZE LACZYMY TO W JEDNEGO
	// STRINGA I ZOSTAWIAMY PO PROSTU NAME
	private String name = null;
	private String surname = null;
	
	// DATA URODZENIA
	private Date birth = null;
	// WIEK OSOBNIKA
	private Integer age = null;
	
	private Drawing before = null;
	private Drawing after = null;
	
	private SpecimenResult result = null;

	public void calculateStatistic()
	{
		result = new SpecimenResult(this);
		result.calculateResult();
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getSurname()
	{
		return surname;
	}

	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	public Date getBirth()
	{
		return birth;
	}

	public void setBirth(Date birth)
	{
		this.birth = birth;
	}

	public Integer getAge()
	{
		return age;
	}

	public void setAge(Integer age)
	{
		this.age = age;
	}

	public Drawing getBefore()
	{
		return before;
	}

	public void setBefore(Drawing before)
	{
		this.before = before;
	}

	public Drawing getAfter()
	{
		return after;
	}

	public void setAfter(Drawing after)
	{
		this.after = after;
	}

	public SpecimenResult getResult()
	{
		return result;
	}

	public void setResult(SpecimenResult result)
	{
		this.result = result;
	}

	
}
