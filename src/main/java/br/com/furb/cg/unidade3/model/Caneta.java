package br.furb.cg.unidade3.model;

import br.furb.cg.unidade3.model.auxiliar.ListaObjetosGraficos;

public class Caneta {

	private Ponto4D origem;
	private Ponto4D destino;
	private ObjetoGrafico objeto;
	private ListaObjetosGraficos grafoCenaTmp;

	public ObjetoGrafico novoPonto(Ponto4D p) {
		if (this.objeto == null) {
			this.objeto = new ObjetoGrafico();
			this.mundo.getListaObjetoGrafico().add(objeto);
		}
		
		if (objeto.getVertices().isEmpty()) {
			this.objeto.getVertices().add(p);
		} else {
			this.objeto.getVertices().add(p);
		}		
		
		return this.objeto;		
	}
	
	public void atualizaUltimoVertice(Ponto4D p) {
		if (this.objeto != null) {
			this.destino.atribuirX(p.obterX());
			this.destino.atribuirY(p.obterY());
		}		
	}
}