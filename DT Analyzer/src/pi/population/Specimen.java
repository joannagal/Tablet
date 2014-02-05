package pi.population;

import java.util.Date;

import pi.inputs.drawing.Drawing;
import pi.statistics.logic.SpecimenResult;

public class Specimen
{
	private String name = null;
	private String surname = null;

	private String pesel = null;
	private Boolean sex = null;
	private Boolean hand = null;
	private Boolean brain = null;
	private Boolean operationType = null;
	private int age;

	private Integer operationTestNo = null;

	private Date birth = null;

	private Drawing before = null;
	private Drawing after = null;

	private SpecimenResult result = null;

	public void calculateStatistic(boolean projectLevel)
	{
		result = new SpecimenResult(this);
		result.calculateResult(projectLevel);
	}

	public String getNamedSex()
	{
		if (this.sex == null)
			return "";
		if (this.sex == true)
			return "Male";
		else
			return "Female";
	}

	public String getNamedHand()
	{
		if (this.hand == null)
			return "";
		if (this.hand == true)
			return "Right";
		else
			return "Left";
	}

	public String getNamedBrain()
	{
		if (this.brain == null)
			return "";
		if (this.brain == true)
			return "Right";
		else
			return "Left";
	}

	public String getNamedOperationType()
	{
		if (this.operationType == null)
			return "";
		if (this.operationType == true)
			return "P";
		else
			return "T";
	}

	public void clearMemory()
	{
		if (this.result != null)
		{
			this.result.clearMemory();
			this.result = null;
		}
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

	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
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
