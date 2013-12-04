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
	private String name = null;
	private String surname = null;
	
	private String pesel = null;
	private Boolean sex = null; // true - male
	private Boolean hand = null; // true - right
	private Boolean brain = null; // true - right
	private Boolean operationType = null; // true - P
	private int age;

	private Integer operationTestNo = null;
	
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

	public String getPesel()
	{
		return pesel;
	}

	public void setPesel(String pesel)
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Integer getOperationTestNo()
	{
		return operationTestNo;
	}

	public void setOperationTestNo(Integer operationTestNo)
	{
		this.operationTestNo = operationTestNo;
	}



	
}
