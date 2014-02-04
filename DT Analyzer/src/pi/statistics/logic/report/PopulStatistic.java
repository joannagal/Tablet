package pi.statistics.logic.report;

import java.util.Collection;
import java.util.Vector;

public class PopulStatistic {

	private int sampleSize;
	private String firstPopul;
	private String secondPopul;
	private String channelName;
	private String waveName;
	private String statisticName;
	private double firstAverage;
	private double firstDeviation;
	private double secondAverage;
	private double secondDeviation;
	private double p_value;
	
	@SuppressWarnings("rawtypes")
	public static Collection getPopulStatistics() {
		Vector<PopulStatistic> statistics = new Vector<PopulStatistic>();
		
		//TODO
		PopulStatistic ps1 = new PopulStatistic();
		ps1.channelName = "channel 1";
		ps1.statisticName = "Average";
		ps1.waveName = "P-wave";
		ps1.firstAverage = 0.4;
		ps1.secondAverage = 0.5;
		
		PopulStatistic ps2 = new PopulStatistic();
		ps2.channelName = "channel 1";
		ps2.statisticName = "Wariance";
		ps2.waveName = "P-wave";
		ps2.firstAverage = 0.9;
		ps2.secondAverage = 0.7;
		
		statistics.add(ps1);
		statistics.add(ps2);
		
		return statistics;
	}

	public int getSampleSize() {
		return sampleSize;
	}

	public void setSampleSize(int sampleSize) {
		this.sampleSize = sampleSize;
	}

	public String getFirstPopul() {
		return firstPopul;
	}

	public void setFirstPopul(String firstPopul) {
		this.firstPopul = firstPopul;
	}

	public String getSecondPopul() {
		return secondPopul;
	}

	public void setSecondPopul(String secondPopul) {
		this.secondPopul = secondPopul;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getWaveName() {
		return waveName;
	}

	public void setWaveName(String waveName) {
		this.waveName = waveName;
	}

	public String getStatisticName() {
		return statisticName;
	}

	public void setStatisticName(String statisticName) {
		this.statisticName = statisticName;
	}

	public double getFirstAverage() {
		return firstAverage;
	}

	public void setFirstAverage(double firstAverage) {
		this.firstAverage = firstAverage;
	}

	public double getFirstDeviation() {
		return firstDeviation;
	}

	public void setFirstDeviation(double firstDeviation) {
		this.firstDeviation = firstDeviation;
	}

	public double getSecondAverage() {
		return secondAverage;
	}

	public void setSecondAverage(double secondAverage) {
		this.secondAverage = secondAverage;
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
