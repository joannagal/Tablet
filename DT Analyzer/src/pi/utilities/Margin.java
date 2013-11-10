package pi.utilities;

//-------------------------------------------
/*
	KLASA MARGIN
	
	TRZYMAM TU WARTOSCI MARGINESOW, POTRZEBNE DO RYSOWANIA
	WYKRESU ECG
*/
//-------------------------------------------

public class Margin
{
	private double left;
	private double right;
	private double top;
	private double bottom;
	
	public Margin(double left, double right, double top, double bottom)
	{
		set(left, right, top, bottom);
	}
	
	public void set(double left, double right, double top, double bottom)
	{
		this.setLeft(left);
		this.setRight(right);
		this.setTop(top);
		this.setBottom(bottom);
	}

	public double getLeft()
	{
		return left;
	}

	public void setLeft(double left)
	{
		this.left = left;
	}

	public double getRight()
	{
		return right;
	}

	public void setRight(double right)
	{
		this.right = right;
	}

	public double getTop()
	{
		return top;
	}

	public void setTop(double top)
	{
		this.top = top;
	}

	public double getBottom()
	{
		return bottom;
	}

	public void setBottom(double bottom)
	{
		this.bottom = bottom;
	}
	
}
