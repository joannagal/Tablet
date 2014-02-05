package pi.statistics.logic.report;

import java.util.Collection;
import java.util.Vector;

public class PopulStatistic
{

	private int sampleSize;
	private String firstSample;
	private String secondSample;
	
	private String tableName;
	private String figureName;
	private String attributeName;
	
	private String statisticName;
	private double firstVariance;
	private double firstDeviation;
	private double secondVariance;
	private double secondDeviation;
	private double p_value;

	@SuppressWarnings("rawtypes")
	public static Collection getPopulStatistics()
	{
		Vector<PopulStatistic> statistics = new Vector<PopulStatistic>();


		PopulStatistic ps = new PopulStatistic();
		ps.setFirstSample("Próba 1");
		ps.setSecondSample("Próba 2");
		ps.setFirstVariance(0.3);
		ps.setFirstDeviation(0.001);
		ps.setTableName("Tabela 1");
		ps.setFigureName("Zig-zag");
		ps.setAttributeName("Attr 1");
		statistics.add(ps);


		PopulStatistic ps1 = new PopulStatistic();
		ps1.setFirstSample("Próba 1");
		ps1.setSecondSample("Próba 2");
		ps1.setFirstVariance(0.3);
		ps1.setFirstDeviation(0.0201);
		ps1.setTableName("Tabela 1");
		ps1.setFigureName("Zig-zag");
		ps1.setAttributeName("Attr 1");
		statistics.add(ps1);
		

		PopulStatistic ps2 = new PopulStatistic();
		ps2.setFirstSample("Próba 1");
		ps2.setSecondSample("Próba 2");
		ps2.setFirstVariance(0.38);
		ps2.setFirstDeviation(0.0031);
		ps2.setTableName("Tabela 1");
		ps2.setFigureName("Line");
		ps2.setAttributeName("Attr 1");
		statistics.add(ps2);
		

		PopulStatistic ps3 = new PopulStatistic();
		ps3.setFirstSample("Próba 1");
		ps3.setSecondSample("Próba 2");
		ps3.setFirstVariance(0.3);
		ps3.setFirstDeviation(0.301);
		ps3.setTableName("Tabela 1");
		ps3.setFigureName("Line");
		ps3.setAttributeName("Attr 1");
		statistics.add(ps3);
		

		PopulStatistic ps4 = new PopulStatistic();
		ps4.setFirstSample("Próba 1");
		ps4.setSecondSample("Próba 2");
		ps4.setFirstVariance(0.13);
		ps4.setFirstDeviation(0.011);
		ps4.setTableName("Tabela 1");
		ps4.setFigureName("Line");
		ps4.setAttributeName("Attr 12");
		statistics.add(ps4);
		
		return statistics;
	}

	public int getSampleSize() {
		return sampleSize;
	}

	public void setSampleSize(int sampleSize) {
		this.sampleSize = sampleSize;
	}

	public String getFirstSample() {
		return firstSample;
	}

	public void setFirstSample(String firstSample) {
		this.firstSample = firstSample;
	}

	public String getSecondSample() {
		return secondSample;
	}

	public void setSecondSample(String secondSample) {
		this.secondSample = secondSample;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFigureName() {
		return figureName;
	}

	public void setFigureName(String figureName) {
		this.figureName = figureName;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getStatisticName() {
		return statisticName;
	}

	public void setStatisticName(String statisticName) {
		this.statisticName = statisticName;
	}

	public double getFirstVariance() {
		return firstVariance;
	}

	public void setFirstVariance(double firstVariance) {
		this.firstVariance = firstVariance;
	}

	public double getFirstDeviation() {
		return firstDeviation;
	}

	public void setFirstDeviation(double firstDeviation) {
		this.firstDeviation = firstDeviation;
	}

	public double getSecondVariance() {
		return secondVariance;
	}

	public void setSecondVariance(double secondVariance) {
		this.secondVariance = secondVariance;
	}

	public double getSecondDeviation() {
		return secondDeviation;
	}

	public void setSecondDeviation(double secondDeviation) {
		this.secondDeviation = secondDeviation;
	}

	public double getP_value() {
		return p_value;
	}

	public void setP_value(double p_value) {
		this.p_value = p_value;
	}
}

