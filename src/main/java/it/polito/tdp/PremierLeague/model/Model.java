package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private PremierLeagueDAO dao;
	private Graph<Team, DefaultWeightedEdge> grafo;
	private List<Team> vertici;
	
	
	public Model() {
		this.dao= new PremierLeagueDAO();
	}
	
	public void creaGrafo() {
		
		//creo il grafo
		this.grafo= new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		//vertici
		this.vertici= new ArrayList<>(this.dao.listAllTeams());
		for(Team t : this.vertici) {
			this.dao.getPuntiSquadra(t);
		}		
		Graphs.addAllVertices(this.grafo, this.vertici);
		
		//archi
		for(Team t1 : this.vertici) {
			for(Team t2: this.vertici) {
				if(t1.getTeamID()<t2.getTeamID()) {
					int peso = t1.getPuntiClassifica()-t2.getPuntiClassifica();
					if(peso>0) {
						//t1 ha + punti di t2
						Graphs.addEdge(this.grafo, t1, t2, peso);
					}
					else if(peso<0) {
						//t2 ha + punti di t1
						Graphs.addEdge(this.grafo, t2, t1, peso*(-1));
					}
				}
			}
		}
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public boolean grafoCreato() {
		if(this.grafo==null)
			return false;
		return true;
	}
	
	public List<Team> getVertici(){
		Collections.sort(this.vertici);
		return vertici;
	}
	
	public List<TeamClassifica> getSquadreMigliori(Team team){
		List<TeamClassifica> result = new ArrayList<>();
		
		for(Team t : this.vertici) {
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
		
		for(Team t : this.vertici) {
			int delta = t.getPuntiClassifica()-team.getPuntiClassifica();
			if(delta<0) {
				result.add(new TeamClassifica(t, (-1)*delta));
			}
		}
		
		Collections.sort(result);
		return result;
	}
	
	
	
	
	
}
