package br.com.furb.cg.unidade3.model;

import javax.media.opengl.GL;

import br.com.furb.cg.unidade3.model.auxiliar.ListaObjetosGraficos;

public class Caneta {

	private Ponto4D pontoOrigem;
	private Ponto4D pontoDestino;
	private ObjetoGrafico objeto;
	private ListaObjetosGraficos grafoCenaTmp;
	private Mundo mundo;
	
	/**
	 * Insere/adiciona novo ponto na tela
	 * @param ponto
	 */
	public void inserirNovoPonto(Ponto4D ponto) {
		
		if(this.objeto == null) {
			this.objeto = new ObjetoGrafico(GL.GL_LINE_STRIP);
			this.objeto.setDesenhando(true);
			this.pontoOrigem = this.objeto.addVertice(ponto.obterX(), ponto.obterY());
			this.pontoDestino = this.objeto.addVertice(ponto.obterX(), ponto.obterY());
			this.getMundo().adicionarObjetoGrafico(objeto);
		} else {
			if (this.pontoOrigem != null) {
				if (this.pontoOrigem.estaPerto(ponto)) {
					this.finalizar(true);
				} else {
					this.pontoDestino = this.objeto.addVertice(ponto.obterX(), ponto.obterY());
				}
			}
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
	 * @return Mundo
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

	public void finalizar(boolean poligonoFechado) {
		if (this.objeto != null) {
			this.objeto.removerUltimoVertice();
			this.objeto.setDesenhando(false);			
			
			if (poligonoFechado) {
				this.objeto.setPrimitiva(GL.GL_LINE_LOOP);
			}
			
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