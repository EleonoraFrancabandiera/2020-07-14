package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;
import it.polito.tdp.PremierLeague.model.Event.EventType;

public class Simulator {
	
	//Dati in ingresso
	private int N;
	private int x;
	
	//Dati in uscita
	
	private int critico;
	private int mediaReporter;
	
	//Modello del mondo
	private Map<Match,MatchReporter> mappaMatch;
	private Map<Team, Integer> reporterPerTeam; 
	private Graph<Team, DefaultWeightedEdge> grafo;
	private PremierLeagueDAO dao;
	private List<Match> matchDB;
	
	
	
	//coda degli eventi
	private PriorityQueue<Event> queue;
	
	public Simulator(Graph<Team, DefaultWeightedEdge> grafo) {		
		this.grafo=grafo;
		this.dao= new PremierLeagueDAO();
	}
	
	
	
	public void init(int n, int soglia) {
		this.N=n;
		this.x=soglia;
		
		this.matchDB=this.dao.listAllMatches();
		this.critico=-1;
		this.mediaReporter=-1;
		
		for(Team v : this.grafo.vertexSet()) {
			reporterPerTeam.put(v, N);
		}
		
		//pre-carico la coda
		for(Match m : this.matchDB) {
			int reporterHome = reporterPerTeam.get(m.teamHomeID);
			int reporterAway = reporterPerTeam.get(m.getTeamAwayID());
			
			mappaMatch.put(m, new MatchReporter(m, reporterHome, reporterAway));
			
			if(m.resultOfTeamHome!=0) {//non siamo in un pareggio
				int idSquadraVincente;
				int idSquadraPerdente;
				if(m.resultOfTeamHome==1){//ha vinto il teamHome
						idSquadraVincente = m.getTeamHomeID();
						idSquadraPerdente = m.getTeamAwayID();
					}
				else {
					idSquadraVincente = m.getTeamAwayID();
					idSquadraPerdente = m.getTeamHomeID();
				}
			
				double casoVincente = Math.random();
				double casoPerdente = Math.random();
				
				if(casoVincente<0.5) {
					this.queue.add(new Event(m, EventType.PROMOZIONE, idSquadraVincente ));
				}
				
				if(casoPerdente<0.2) {
					this.queue.add(new Event(m, EventType.BOCCIATURA, idSquadraPerdente));
				}
			
			}
		}
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
		}
	}



	private void processEvent(Event e) {
		switch(e.getType()) {
		
		case PROMOZIONE:
				if(this.reporterPerTeam.get(e.getTeamID())>0) {//controllo se ci sono ancora reporter
					
				}
			break;
			
		case BOCCIATURA:
			if(this.reporterPerTeam.get(e.getTeamID())>0) {//controllo se ci sono ancora reporter
				
			}
			break;
		
		}
		
	}
	
	
	public List<TeamClassifica> getSquadreMigliori(Team team){
		List<TeamClassifica> result = new ArrayList<>();
		
		for(Team t : this.grafo.vertexSet()) {
			int delta = t.getPuntiClassifica()-team.getPuntiClassifica();
			if(delta>0) {
				result.add(new TeamClassifica(t, delta));
			}
		}
		
		Collections.sort(result);
		return result;
	}
	
	public List<TeamClassifica> getSquadrePeggiori(Team team){
		List<TeamClassifica> result = new ArrayList<>();
		
		for(Team t : this.grafo.vertexSet()) {
			int delta = t.getPuntiClassifica()-team.getPuntiClassifica();
			if(delta<0) {
				result.add(new TeamClassifica(t, (-1)*delta));
			}
		}
		
		Collections.sort(result);
		return result;
	}
	
	private Team scegliTeam(List<TeamClassifica> lista){
		Team scelto=null;;
		
		int i = (int)Math.random()*lista.size();
		
		for(Team t: this.grafo.vertexSet()) {
			if(lista.get(i).getTeam().getTeamID()==t.teamID) {
				scelto=t;
				break;
			}
		}
		
		return scelto;		
	}
		
}
	
	
	
	
	
	

