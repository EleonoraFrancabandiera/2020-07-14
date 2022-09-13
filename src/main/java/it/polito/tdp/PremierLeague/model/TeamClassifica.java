package it.polito.tdp.PremierLeague.model;

public class TeamClassifica implements Comparable<TeamClassifica>{
	
	private Team team;
	private int deltaPunti;
	
	
	public TeamClassifica(Team team, int deltaPunti) {
		super();
		this.team = team;
		this.deltaPunti = deltaPunti;
	}


	public Team getTeam() {
		return team;
	}


	public int getDeltaPunti() {
		return deltaPunti;
	}


	@Override
	public String toString() {
		return team.getName() + " (" + deltaPunti + ")";
	}


	@Override
	public int compareTo(TeamClassifica o) {
		return this.deltaPunti-o.getDeltaPunti();
	}
	
	
	
	

}
