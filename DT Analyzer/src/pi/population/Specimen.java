package pi.population;


import java.util.Date;

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
	
	private Integer pesel = null;
	private Boolean sex = null; // true - male
	private Boolean hand = null; // true - right
	private Boolean brain = null; // true - right
	private Boolean operationType = null; // true - P
	
	private Integer firstOperation = null;
	private Integer secondOperation = null;
	
	
	// DATA URODZENIA
	private Date birth = null;

	
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

	public void setBirth(Date date)
	{
		this.birth = date;
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

	public Integer getPesel()
	{
		return pesel;
	}

	public void setPesel(Integer pesel)
	{
		this.pesel = pesel;
	}

	public Boolean getSex()
	{
		return sex;
	}

	public void setSex(Boolean sex)
	{
		this.sex = sex;
	}

	public Boolean getHand()
	{
		return hand;
	}

	public void setHand(Boolean hand)
	{
		this.hand = hand;
	}

	public Boolean getBrain()
	{
		return brain;
	}

	public void setBrain(Boolean brain)
	{
		this.brain = brain;
	}

	public Boolean getOperationType()
	{
		return operationType;
	}

	public void setOperationType(Boolean operationType)
	{
		this.operationType = operationType;
	}

	public Integer getFirstOperation()
	{
		return firstOperation;
	}

	public void setFirstOperation(Integer firstOperation)
	{
		this.firstOperation = firstOperation;
	}

	public Integer getSecondOperation()
	{
		return secondOperation;
	}

	public void setSecondOperation(Integer secondOperation)
	{
		this.secondOperation = secondOperation;
	}

	
}
