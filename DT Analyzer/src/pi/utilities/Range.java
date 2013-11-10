package pi.utilities;

//-------------------------------------------
/*
	KLASA RANGE - PRZEDZIAL
	
	TRZYMA ONA DWA INTY : LEFT , RIGHT
	ODPOWIADAJACE NUMEROM PROBEK BEDACYCH
	KONCAMI PRZEDZIALU.
*/
//-------------------------------------------

public class Range
{
	private int left;
	private int right;
	private int interval;


	public Range(int left, int right)
	{
		this.left = left;
		this.right = right;

		this.recalculate();
	}

	private void recalculate()
	{
		if ( (this.right < this.left) ||
			(this.left < 0) || (this.right < 0) )
		{
			throw new IllegalArgumentException();
		}

		interval = right - left;
	}
	
	public boolean isIntersecting(Range range)
	{
		if (range == null) return false;
		
		if ( ((this.left >= range.left) && (this.left < range.right))
				|| ( (this.right > range.left) && (this.right <= range.right)) ) return true;
			
		return false;
	}
	
	public boolean isInside(Range range)
	{
		if (range == null) return false;
		if ((this.left >= range.left) && (this.right <= range.right)) return true;
		return false;
	}

	public void setRange(int left, int right)
	{
		this.left = left;
		this.right = right;
		this.recalculate();
	}
	
	public void shiftLeft(int value)
	{
		this.left += value;
		this.recalculate();
	}

	public void shiftRight(int value)
	{
		this.right += value;
		this.recalculate();
	}

	public void shift(int value)
	{
		this.left += value;
		this.right += value;
	}

	public void scale(int value)
	{

		if (2 * value > this.interval)
		{
			throw new IllegalArgumentException();
		}

		this.left -= value;
		this.right += value;
		this.recalculate();
	}

	public int getLeft()
	{
		return left;
	}

	public void setLeft(int m_left)
	{
		this.left = m_left;
		this.recalculate();
	}

	public int getRight()
	{
		return right;
	}

	public void setRight(int m_right)
	{
		this.right = m_right;
		this.recalculate();
	}

	public int getInterval()
	{
		return interval;
	}

}
