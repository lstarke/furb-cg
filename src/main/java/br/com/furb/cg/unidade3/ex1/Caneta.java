package br.com.furb.cg.unidade3.ex1;

public class Caneta {
	
	private ObjetoGrafico objeto;
	private Ponto4D origem;
	private Ponto4D destino;
	
	
	public ObjetoGrafico novoPonto(Ponto4D p) {
		if (this.objeto == null) {
			this.objeto = new ObjetoGrafico();
		}
		
		if(this.objeto.getVertices().isEmpty()) {			
			this.objeto.addVertice(p);		
			this.origem = p;
			this.destino = p;			
		} else {
			this.destino = p;
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
