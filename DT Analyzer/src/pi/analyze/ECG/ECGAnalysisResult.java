package pi.analyze.ECG;

import java.sql.Date;
import pi.analyze.AnalysisResult;

public class ECGAnalysisResult implements AnalysisResult
{
	private Date date;

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}
}
