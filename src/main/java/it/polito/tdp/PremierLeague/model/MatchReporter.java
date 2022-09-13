package it.polito.tdp.PremierLeague.model;

public class MatchReporter {
	
	private Match match;
	private int reporterHome;
	private int reporterAway;
	
	
	public MatchReporter(Match match, int reporterHome, int reporterAway) {
		super();
		this.match = match;
		this.reporterHome = reporterHome;
		this.reporterAway = reporterAway;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public int getReporterHome() {
		return reporterHome;
	}

	public void setReporterHome(int reporterHome) {
		this.reporterHome = reporterHome;
	}

	public int getReporterAway() {
		return reporterAway;
	}

	public void setReporterAway(int reporterAway) {
		this.reporterAway = reporterAway;
	}

	public int getReporterTotali() {
		return this.reporterHome+this.reporterAway;
	}
	
	

}
