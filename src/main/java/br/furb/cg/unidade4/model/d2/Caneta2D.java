package br.furb.cg.unidade4.model.d2;

/**
 * Objeto responsavel por desenhar os poligonos na cena grafica
 */

import javax.media.opengl.GL;

import br.furb.cg.unidade4.model.ObjetoGrafico;
import br.furb.cg.unidade4.model.Ponto4D;
import br.furb.cg.unidade4.model.auxiliar.ListaObjetosGraficos;

public final class Caneta2D {

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
	
	/**
	 * Desenhar um quadrado magicamente no mundo 2d...
	 */
	public void desenharQuadrado2d() {
		Ponto4D p1 = new Ponto4D(50, 50, 0, 1);
		Ponto4D p2 = new Ponto4D(50, 150, 0, 1);
		Ponto4D p3 = new Ponto4D(150, 150, 0, 1);
		Ponto4D p4 = new Ponto4D(150, 50, 0, 1);
		Ponto4D p5 = new Ponto4D(50, 50, 0, 1);
		
		this.atualizarUltimoVertice(p1);
		this.inserirNovoPonto(p1);
		
		this.atualizarUltimoVertice(p2);
		this.inserirNovoPonto(p2);
		
		this.atualizarUltimoVertice(p3);
		this.inserirNovoPonto(p3);
		
		this.atualizarUltimoVertice(p4);
		this.inserirNovoPonto(p4);
		
		this.atualizarUltimoVertice(p5);
		this.inserirNovoPonto(p5);
	}
	
	// Duplicar objeto desenhado em 2d e acrescentar ligacoes;
	public void calcular2Dto3D(float profundidade) {
		if (objetoTmp != null && objetoTmp.isSelecionado()) {
			
		}
	}
}