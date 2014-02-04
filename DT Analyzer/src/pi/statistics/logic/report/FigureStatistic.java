package pi.statistics.logic.report;

import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import pi.population.Specimen;
import pi.shared.SharedController;

public class FigureStatistic {

	// DANE DO NAG£ÓWKA:
	private String name;
	private String surname;
	private String personalID; // pesel
	private String sex; // {"male", "female"}
	private Date birth;
	private String hand; // {"right", "left"}
	private String brain; // {"right", "left"}
	private String type; // typ operacji/testu
	private int number; // numer operacji/testu

	// DANE KOLEJNYCH WIERSZY TABELI:
	private String statistic;
	private Double standards;
	private Double pressure;
	private Double speed;
	private Double acceleration;
	private Double change;
	private Double azimuth;
	private Double altitude;
	private Double dev;
	private String examination; // Nazwa sekcji {"before", "after"}
	private String figure; // Nazwa podsekcji {"All Figures", "Zig-Zag",
							// "Circle-Up"... }

	public static Collection getFigureStatistics() {
		Vector<FigureStatistic> statistics = new Vector<FigureStatistic>();

		// TODO Wype³niæ wektor "statistics" obiektami klasy "FigureStatistic"
		// reprezentuj¹cymi kolejne wiersze raportu. Dane do nag³ówka wystarczy
		// dodaæ tylko do pierwszego obiektu, w pozosta³ych mo¿na zostawiæ pola
		// puste.

		return statistics;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPersonalID() {
		return personalID;
	}

	public void setPersonalID(String personalID) {
		this.personalID = personalID;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getHand() {
		return hand;
	}

	public void setHand(String hand) {
		this.hand = hand;
	}

	public String getBrain() {
		return brain;
	}

	public void setBrain(String brain) {
		this.brain = brain;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getStatistic() {
		return statistic;
	}

	public void setStatistic(String statistic) {
		this.statistic = statistic;
	}

	public Double getStandards() {
		return standards;
	}

	public void setStandards(Double standards) {
		this.standards = standards;
	}

	public Double getPressure() {
		return pressure;
	}

	public void setPressure(Double pressure) {
		this.pressure = pressure;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Double getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Double acceleration) {
		this.acceleration = acceleration;
	}

	public Double getChange() {
		return change;
	}

	public void setChange(Double change) {
		this.change = change;
	}

	public Double getAzimuth() {
		return azimuth;
	}

	public void setAzimuth(Double azimuth) {
		this.azimuth = azimuth;
	}

	public Double getAltitude() {
		return altitude;
	}

	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}

	public Double getDev() {
		return dev;
	}

	public void setDev(Double dev) {
		this.dev = dev;
	}

	public String getExamination() {
		return examination;
	}

	public void setExamination(String examination) {
		this.examination = examination;
	}

	public String getFigure() {
		return figure;
	}

	public void setFigure(String figure) {
		this.figure = figure;
	}

}
