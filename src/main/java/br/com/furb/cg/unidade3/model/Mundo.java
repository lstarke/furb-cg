package br.com.furb.cg.unidade3.model;

/**
 * Cena grafica
 */

import java.util.Iterator;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import br.com.furb.cg.unidade3.model.auxiliar.ListaObjetosGraficos;

public class Mundo {
	// Propriedades do Mundo/Universo
	private float tamEixoXsru;
	private float tamEixoYsru;
	private Camera2D camera;
	private ListaObjetosGraficos objetos;
	private ObjetoGrafico objSelecionado;
	private Ponto4D ptoSelecionado;
	private boolean desenhando;

	/// Construtor
	public Mundo() {
		this.tamEixoXsru = 200f;
		this.tamEixoYsru = 200f;
		this.camera = new Camera2D();
		this.objetos = new ListaObjetosGraficos();
		this.objSelecionado = null;
		this.ptoSelecionado = null;
		this.desenhando = false;
	}

//	public float getTamEixoXSru() {
//		return this.tamEixoXsru;
//	}
//	
//	public void setTamEixoXSru(float tamX) {
//		this.tamEixoXsru = tamX;
//	}
//	
//	public float getTamEixoYStru() {
//		return this.tamEixoYsru;
//	}
//	
//	public void setTamEixoYSru(float tamY) {
//		this.tamEixoYsru = tamY;
//	}
	
	public Camera2D getCamera() {
		return camera;
	}
	
	public ObjetoGrafico getObjetoSelecionado() {
		return this.objSelecionado;
	}

	public ListaObjetosGraficos getObjetosGraficos(){
		return this.objetos;
	}
	
	public Ponto4D getVerticeSelecionado() {
		return this.ptoSelecionado;
	}
	
	/**
	 * Indicar se existe objeto grafico selecionado no mundo
	 * @return boolean
	 */
	public boolean hasObjetoSelecionado() {
		return objSelecionado != null;
	}
	
	/**
	 * Indicar se existe ponto selecionado no mundo
	 * @return boolean
	 */
	public boolean hasVerticeSelecionado() {
		return ptoSelecionado != null;
	}
	
	/**
	 * Indicar se esta em tempo de edicao
	 * Por padrao, se nao estiver desenhando estara selecionando
	 * @return boolean
	 */
	public boolean isDesenhando() {
		return this.desenhando;
	}
	
	/**
	 * Atribuir se esta em tempo de edicao
	 * @param boolean
	 */
	public void setDesenhando(boolean desenhando) {
		this.desenhando = desenhando;
		
		if (this.desenhando) {
			this.objSelecionado = null;
			this.ptoSelecionado = null;
		} else
			this.selecionarUltimoObjeto();
	}
	
	/**
	 * Indicar se estq em tempo de selecao
	 * Por padrao se nao estiver desenando estara selecionando
	 * @return boolean
	 */
	public boolean isSelecionando() {
		return !this.desenhando;
	}
	
	/**
	 * Desenhar os eixos X e Y na cena grafica
	 */	
	public void SRU(GL gl, GLU glu) {
		gl.glLineWidth(1.0f);
	
		// eixo X
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
			gl.glVertex2f(-tamEixoXsru, 0.0f);
			gl.glVertex2f(tamEixoXsru, 0.0f);
		gl.glEnd();
		
		// eixo Y
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
			gl.glVertex2f(0.0f, -tamEixoYsru);
			gl.glVertex2f(0.0f, tamEixoYsru);
		gl.glEnd();
	}
	
	/**
	 * Posicionar a camera em um ponto especifico da cena
	 * @param gl
	 * @param glu
	 */
	public void posicionarCamera(GL gl, GLU glu) {
		this.camera.posicionar(gl, glu);
	}
	
	/**
	 * Desenhar todos os objetos graficos contidos
	 * @param gl
	 * @param glu
	 */
	public void desenharObjetos(GL gl, GLU glu) {
		for (Iterator<ObjetoGrafico> it = this.objetos.iterador(); it.hasNext();)
			it.next().desenhar(gl, glu);		
	}
	
	/**
	 * Inserir um objeto grafico na cena (lista de objetos)
	 */
	public void addObjeto(ObjetoGrafico objeto) {
		this.objetos.add(objeto);
	}
	
	/**
	 * Selecionar um objeto em relacao a um ponto no mundo
	 * @param Ponto4D
	 * @return ObjetoGrafico
	 */
	public ObjetoGrafico selecionarObjeto(Ponto4D p) {
		this.objSelecionado = this.objetos.localizarObjeto(p);
		return this.objSelecionado;
	}
	
	/**
	 * Selecionar um vertice especifico na lista de objetos do mundo
	 * @param Ponto4D
	 * @return Ponto4D se existe na lista de objetos graficos
	 */
	public Ponto4D selecionarVertice(Ponto4D p) {
		this.ptoSelecionado = this.objetos.localizarPonto(p);
		return this.ptoSelecionado;
	}
	
	/**
	 * Selecionar ultimo objeto grafico criado
	 */
	public void selecionarUltimoObjeto() {
		this.objSelecionado = this.objetos.getUltimo();
	}
	
	/**
	 * Calcular bound box do objeto grafico selecionado
	 */
	public void calcularBoundBox() {
		this.objSelecionado.calcularBbox();
	}
	
	/**
	 * Alterar escala do objeto selecionado
	 * @param double escala
	 * @param boolean manterPontoFixo = em relacao a sua Bound Box se true
	 */
	public void escalonarObjeto(double escala, boolean manterPontoFixo) {
		if (! this.desenhando) {
			if (manterPontoFixo)
				this.objSelecionado.escalonarFixado(escala);
			else
				this.objSelecionado.escalonar(escala);
		}
	}
	
	/**
	 * Rotacionar objeto grafico em relacao a sua Bound Box
	 */
	public void rotacionarObjeto() {
		if (! this.desenhando)
			this.objSelecionado.rotacionar();
	}
	
	/**
	 * Movimentar objeto grafico selecionado
	 * @param x
	 * @param y
	 */
	public void moverObjeto(double x, double y) {
		if (! this.desenhando)
			this.objSelecionado.mover(x, y);
	}
	
	/**
	 * Remover vertice selecionado do objeto selecionado
	 */
	public void removerVerticeObjeto() {
		this.objSelecionado.removerVerticeSelecionado();
	}
	
	/**
	 * Imprimir no console a Bound Box do objeto selecionado
	 */
	public void exibirBboxObjeto() {
		if (! this.desenhando)
			this.objSelecionado.exibirBbox();
	}
	
	/**
	 * Imprimir no console a Matriz objeto selecionado
	 */
	public void exibirMatrizObjeto() {
		if (! this.desenhando)
			this.objSelecionado.exibirMatriz();
	}
	
	/**
	 * Imprimir no console os vertices do objeto selecionado
	 */
	public void exibirVerticesObjeto() {
		if (! this.desenhando)
			this.objSelecionado.exibirVertices();
	}
}