package br.com.furb.cg.unidade3.model;

/**
 * Objeto responsavel por desenhar os poligonos na cena grafica
 */

import javax.media.opengl.GL;
import br.com.furb.cg.unidade3.model.auxiliar.ListaObjetosGraficos;

public final class Caneta {

	private Ponto4D pontoOrigem;
	private Ponto4D pontoDestino;
	private ObjetoGrafico objetoTmp;
	private ListaObjetosGraficos listaObjetosTmp;

	public ObjetoGrafico getObjeto() {
		return objetoTmp;
	}

	public void setObjeto(ObjetoGrafico objetoTmp) {
		this.objetoTmp = objetoTmp;
	}

	public void setObjetosGraficos(ListaObjetosGraficos objetos) {
		this.listaObjetosTmp = objetos;
	}

	/**
	 * Iniciar um novo desenho.
	 * Atribuir null para as variaveis do objetoTmp grafico.
	 */
	private void setNullAttributes() {
		this.objetoTmp = null;
		this.pontoOrigem = null;
		this.pontoDestino = null;		
	}

	/**
	 * Inserir/adicionar novo ponto na tela
	 * @param ponto
	 */
	public void inserirNovoPonto(Ponto4D ponto) {
		
		if (this.objetoTmp == null) {
			this.objetoTmp = new ObjetoGrafico(GL.GL_LINE_STRIP);
			this.objetoTmp.setSelecionado(false);
			this.pontoOrigem = this.objetoTmp.addVertice(ponto.obterX(), ponto.obterY());
			this.pontoDestino = this.objetoTmp.addVertice(ponto.obterX(), ponto.obterY());
			this.listaObjetosTmp.add(objetoTmp);
		} else
			if (this.pontoOrigem != null) {
				if (this.pontoOrigem.estaPerto(ponto))
					this.finalizar(true);
				else
					this.pontoDestino = this.objetoTmp.addVertice(ponto.obterX(), ponto.obterY());
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

	/**
	 * Finalizar o desenho do objeto grafico
	 * @param poligonoFechado
	 */
	public void finalizar(boolean poligonoFechado) {
		if (this.objetoTmp != null) {
			this.objetoTmp.removerUltimoVertice();
			this.objetoTmp.setSelecionado(true);
			
			if (poligonoFechado)
				this.objetoTmp.setPrimitiva(GL.GL_LINE_LOOP);
			
			this.setNullAttributes();			
		}
	}
	
	/**
	 * Duplicar o objeto grafico selecionado
	 */
	public void duplicarObjeto() {
		if (objetoTmp != null && objetoTmp.isSelecionado())
			objetoTmp.gerarFilho();
	}
}