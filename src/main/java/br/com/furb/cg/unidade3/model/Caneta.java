package br.com.furb.cg.unidade3.model;

/**
 * Objeto responsavel por desenhar os poligonos na cena grafica
 */

import javax.media.opengl.GL;
import br.com.furb.cg.unidade3.model.auxiliar.ListaObjetosGraficos;

public final class Caneta {

	private Ponto4D pontoOrigem;
	private Ponto4D pontoDestino;
	private ObjetoGrafico objeto;
	private ListaObjetosGraficos objetosTmp;
	
	/**
	 * Inserir/adicionar novo ponto na tela
	 * @param ponto
	 */
	public void inserirNovoPonto(Ponto4D ponto) {
		
		if (this.objeto == null) {
			this.objeto = new ObjetoGrafico(GL.GL_LINE_STRIP);
			this.objeto.setDesenhando(true);
			this.pontoOrigem = this.objeto.addVertice(ponto.obterX(), ponto.obterY());
			this.pontoDestino = this.objeto.addVertice(ponto.obterX(), ponto.obterY());
			this.objetosTmp.add(objeto);
		} else
			if (this.pontoOrigem != null) {
				if (this.pontoOrigem.estaPerto(ponto))
					this.finalizar(true);
				else
					this.pontoDestino = this.objeto.addVertice(ponto.obterX(), ponto.obterY());
			}
	}
	
	/**
	 * Atualizar o ultimo ponto para fazer o rastro ao mover o mouse.
	 * @param ponto
	 */
	public void atualizarUltimoVertice(Ponto4D ponto) {
		if (this.pontoOrigem != null) {
			this.pontoDestino.atribuirX(ponto.obterX());
			this.pontoDestino.atribuirY(ponto.obterY());
		}
	}
	
	public void setObjetosGraficos(ListaObjetosGraficos objetos) {
		this.objetosTmp = objetos;
	}

	public void finalizar(boolean poligonoFechado) {
		if (this.objeto != null) {
			this.objeto.removerUltimoVertice();
			this.objeto.setDesenhando(false);			
			
			if (poligonoFechado)
				this.objeto.setPrimitiva(GL.GL_LINE_LOOP);
			
			this.setNullAttributes();			
		}
	}

	/**
	 * Iniciar um novo desenho.
	 * Seta null para as variaveis do objeto grafico.
	 */
	private void setNullAttributes() {
		this.objeto = null;
		this.pontoOrigem = null;
		this.pontoDestino = null;		
	}
}