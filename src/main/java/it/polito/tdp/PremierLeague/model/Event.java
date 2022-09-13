package it.polito.tdp.PremierLeague.model;

public class Event implements Comparable <Event>{

	public enum EventType{
		PROMOZIONE,
		BOCCIATURA
	}
	
	
	private EventType type;
	private int teamID;
	private Match match;
	
	
	
	public Event(Match match, EventType type, int teamID) {
		super();
		this.match = match;
		this.type = type;
		this.teamID = teamID;
	}



	public EventType getType() {
		return type;
	}




	public int getTeamID() {
		return teamID;
	}




	public Match getMatch() {
		return match;
	}




	@Override
	public int compareTo(Event o) {
		return this.match.getMatchID()-o.getMatch().getMatchID();
	}
	
	

}
