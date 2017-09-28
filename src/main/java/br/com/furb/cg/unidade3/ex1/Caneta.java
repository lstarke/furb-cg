package br.com.furb.cg.unidade3.ex1;

public class Caneta {
	
	private Mundo mundo;
	
	private ObjetoGrafico objeto;
	private Ponto4D origem;
	private Ponto4D destino;	
	
	
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

	public Mundo getMundo() {
		return mundo;
	}

	public void setMundo(Mundo mundo) {
		this.mundo = mundo;
	}
	
	

}
