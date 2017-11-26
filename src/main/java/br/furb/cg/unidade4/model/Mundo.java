package br.furb.cg.unidade4.model;

/**
 * Cena grafica
 */

import java.util.Iterator;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import br.furb.cg.unidade4.model.auxiliar.ListaObjetosGraficos;
import br.furb.cg.unidade4.model.d2.Camera2D;

public class Mundo {
	// Propriedades do Mundo/Universo
	private float tamEixoXsru;
	private float tamEixoYsru;
	private float tamEixoZsru;
	private Camera2D camera2d;
//	private Camera3D camera3d;
	private ListaObjetosGraficos objetos;
	
	// Auxiliares
	private ObjetoGrafico objSelecionado;
	private Ponto4D ptoSelecionado;
	private boolean desenhando;
	private boolean is2D;

	/// Construtor
	public Mundo(boolean is2D) {
		this.tamEixoXsru = 200f;
		this.tamEixoYsru = 200f;
		this.tamEixoZsru = 200f;
		
		this.camera2d = new Camera2D();
//		this.camera3d = new Camera3D(o.gl, o.glu);
		this.objetos = new ListaObjetosGraficos();
		this.objSelecionado = null;
		this.ptoSelecionado = null;
		this.desenhando = false;
		this.is2D = is2D;
	}
	
	public boolean is2D() {
		return is2D;
	}
	
	public void set3D() {
		this.is2D = false;
	}

	public Camera2D getCamera2d() {
		return camera2d;
	}
	
	public float getTamX() {
		return this.tamEixoXsru;
	}
	
	public float getTamY() {
		return this.tamEixoYsru;
	}
	
	public float getTamZ() {
		return this.tamEixoZsru;
	}
	
	public ObjetoGrafico getObjetoSelecionado() {
		return this.objSelecionado;
	}

	public ListaObjetosGraficos getObjetosGraficos(){
		return this.objetos;
	}
	
	public void removerObjetosGraficos() {
		this.objetos.removerObjetos();
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
	public void Sru2d(GL gl, GLU glu) {
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
	 * Desenhar os eixos X, Y e Z na cena grafica
	 */	
	public void Sru3d(GL gl) {
		gl.glLineWidth(1.0f);

		// eixo X - Red
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
			gl.glVertex3f(0.0f, 0.0f, 0.0f);
			gl.glVertex3f(tamEixoXsru, 0.0f, 0.0f);
		gl.glEnd();

		// eixo Y - Green
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
			gl.glVertex3f(0.0f, 0.0f, 0.0f);
			gl.glVertex3f(0.0f, tamEixoYsru, 0.0f);
		gl.glEnd();

		// eixo Z - Blue
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glBegin(GL.GL_LINES);
			gl.glVertex3f(0.0f, 0.0f, 0.0f);
			gl.glVertex3f(0.0f, 0.0f, tamEixoZsru);
		gl.glEnd();
	}
	
	/**
	 * Posicionar a camera2d em um ponto especifico da cena
	 * @param gl
	 * @param glu
	 */
	public void posicionarCamera2d(GL gl, GLU glu) {
		this.camera2d.posicionar(gl, glu);
	}
	
	/**
	 * Desenhar todos os objetos graficos contidos
	 * @param gl
	 * @param glu
	 */
	public void desenharObjetos(GL gl, GLU glu) {
		if (is2D()) {
			for (Iterator<ObjetoGrafico> it = this.objetos.iterador(); it.hasNext();)
				it.next().desenhar(gl, glu);
		} else {
			for (Iterator<ObjetoGrafico> it = this.objetos.iterador(); it.hasNext();)
				it.next().desenhar3D(gl, glu);
		}
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
		if (p == null)
			this.objSelecionado = null;
		else
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
	 * Alterar escala do objeto selecionado 2d
	 * @param double escala
	 * @param boolean manterPontoFixo = em relacao a sua Bound Box se true
	 */
	public void escalonarObjeto2d(double escala, boolean manterPontoFixo) {
		if (this.isSelecionando()) {
			if (manterPontoFixo)
				this.objSelecionado.escalonarFixado2d(escala);
			else
				this.objSelecionado.escalonar2d(escala);
		}
	}
	
	/**
	 * Alterar escala do objeto selecionado 3d
	 * @param double escala
	 * @param boolean manterPontoFixo = em relacao a sua Bound Box se true
	 */
	public void escalonarObjeto3d(double escala, boolean manterPontoFixo) {
		if (this.isSelecionando()) {
			if (manterPontoFixo)
				this.objSelecionado.escalonarFixado3d(escala);
			else
				this.objSelecionado.escalonar3d(escala);
		}
	}
	
	/**
	 * Rotacionar objeto grafico 2d em relacao a sua Bound Box
	 */
	public void rotacionarObjeto2d() {
		if (this.isSelecionando() && this.hasObjetoSelecionado())
			this.objSelecionado.rotacionar2d();
	}
	
	/**
	 * Rotacionar objeto grafico 3d em X em relacao a sua Bound Box
	 */
	public void rotacionarObjeto3dx() {
		if (this.isSelecionando() && this.hasObjetoSelecionado())
			this.objSelecionado.rotacionar3dx();
	}
	
	/**
	 * Rotacionar objeto grafico 3d em Y em relacao a sua Bound Box
	 */
	public void rotacionarObjeto3dy() {
		if (this.isSelecionando() && this.hasObjetoSelecionado())
			this.objSelecionado.rotacionar3dy();
	}
	
	/**
	 * Rotacionar objeto grafico 3d em X em relacao a sua Bound Box
	 */
	public void rotacionarObjeto3dz() {
		if (this.isSelecionando() && this.hasObjetoSelecionado())
			this.objSelecionado.rotacionar3dz();
	}
	
	/**
	 * Movimentar objeto grafico selecionado
	 * @param x
	 * @param y
	 */
	public void moverObjeto2d(double x, double y) {
		if (this.isSelecionando() && this.hasObjetoSelecionado())
			this.objSelecionado.mover2d(x, y);
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
		if (this.isSelecionando() && this.hasObjetoSelecionado())
			this.objSelecionado.exibirBbox();
	}
	
	/**
	 * Imprimir no console a Matriz objeto selecionado
	 */
	public void exibirMatrizObjeto() {
		if (this.isSelecionando() && this.hasObjetoSelecionado())
			this.objSelecionado.exibirMatriz();
	}
	
	/**
	 * Imprimir no console os vertices do objeto selecionado
	 */
	public void exibirVerticesObjeto() {
		if (this.isSelecionando() && this.hasObjetoSelecionado())
			this.objSelecionado.exibirVertices();
	}
}