package br.com.furb.cg.unidade3.model;

import javax.media.opengl.GL;

import br.com.furb.cg.unidade3.model.auxiliar.ListaObjetosGraficos;

public class Caneta {

	private Ponto4D pontoOrigem;
	private Ponto4D pontoDestino;
	private ObjetoGrafico objeto;
	private ListaObjetosGraficos grafoCenaTmp;
	private Mundo mundo;

//	public ObjetoGrafico novoPonto(Ponto4D p) {
//		if (this.objeto == null) {
//			this.objeto = new ObjetoGrafico();
//			this.mundo.getListaObjetoGrafico().add(objeto);
//		}
//		
//		if (objeto.getVertices().isEmpty()) {
//			this.objeto.getVertices().add(p);
//		} else {
//			this.objeto.getVertices().add(p);
//		}		
//		
//		return this.objeto;		
//	}
//	
//	public void atualizaUltimoVertice(Ponto4D p) {
//		if (this.objeto != null) {
//			this.destino.atribuirX(p.obterX());
//			this.destino.atribuirY(p.obterY());
//		}		
//	}
	
	public void inserirNovoPonto(Ponto4D ponto) {
		
		if(this.objeto == null) {
			this.objeto = new ObjetoGrafico(GL.GL_LINE_STRIP);
			this.objeto.setDesenhando(true);
			this.pontoOrigem = this.objeto.addVertice(ponto.obterX(), ponto.obterY());
			this.pontoDestino = this.objeto.addVertice(ponto.obterX(), ponto.obterY());
			this.getMundo().adicionarObjetoGrafico(objeto);
		} else {
			this.pontoDestino = this.objeto.addVertice(ponto.obterX(), ponto.obterY());
		}		
		
	}
	
	/**
	 * Atualiza o ultimo ponto para fazer o rastro ao mover o mouse.
	 * @param ponto
	 */
	public void atualizarUltimoVertice(Ponto4D ponto) {
		if (this.pontoOrigem != null) {
			this.pontoDestino.atribuirX(ponto.obterX());
			this.pontoDestino.atribuirY(ponto.obterY());
		}
	}

	/**
	 * Pega o mundo(folha) em que a caneta está desenhando. 
	 * @return
	 */
	public Mundo getMundo() {
		return mundo;
	}

	/**
	 * Define o mundo(folha) em que a caneta deve desenhar. 
	 * @param mundo
	 */
	public void setMundo(Mundo mundo) {
		this.mundo = mundo;
	}

	public void finalizar() {
		if (this.objeto != null) {
			this.objeto.removerUltimoVertice();
			this.objeto.setDesenhando(false);
			this.objeto.setPrimitiva(GL.GL_LINE_LOOP);
			this.setNullAttributes();
		}
		
	}

	/**
	 * Iniciar um novo desenho.
	 * Seta null para as variáveis do objeto gráfico.
	 */
	private void setNullAttributes() {
		this.objeto = null;
		this.pontoOrigem = null;
		this.pontoDestino = null;		
	}
	
}