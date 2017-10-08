package br.com.furb.cg.unidade3.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import br.com.furb.cg.unidade3.model.auxiliar.ListaObjetosGraficos;

public class Mundo {
	// Propriedades do Mundo/Universo
	private float tamEixoXsru;
	private float tamEixoYsru;
	private Camera2D camera;
	private ListaObjetosGraficos objetos;

	public Mundo() {
		this.tamEixoXsru = 200f;
		this.tamEixoYsru = 200f;
		this.camera = new Camera2D();
		this.objetos = new ListaObjetosGraficos();
	}
	
	public float getTamEixoXSru() {
		return this.tamEixoXsru;
	}
	
	public void setTamEixoXSru(float tamX) {
		this.tamEixoXsru = tamX;
	}
	
	public float getTamEixoYStru() {
		return this.tamEixoYsru;
	}
	
	public void setTamEixoYSru(float tamY) {
		this.tamEixoYsru = tamY;
	}
	
	public Camera2D getCamera() {
		return camera;
	}
	
	public ListaObjetosGraficos getListaObjetoGrafico(){
		return objetos;
	}
	
	public void posicionaCamera(GL gl, GLU glu) {
		this.camera.posicionar(gl, glu);
	}
	
	public void adicionarObjetoGrafico(ObjetoGrafico objeto) {
		this.objetos.add(objeto);
	}
	
	/**
	 * Desenha os eixos X e Y
	 * @param gl
	 * @param glu
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
	 * Desenhar todos os objetos graficos contidos
	 * 
	 * @param gl
	 * @param glu
	 */
	public void desenharObjetos(GL gl, GLU glu) {
		this.objetos.desenharObjetos(gl, glu);
//		for (Iterator<ObjetoGrafico> it = this.objetos.iterador(); it.hasNext();) 
//			it.next().desenhar(gl, glu);
	}

	/**
	 * Procura Ponto selecionado na lista de objeto contidos no mundo.
	 * @param Ponto4D
	 * @return Ponto4D
	 */
	public Ponto4D selecionaPonto(Ponto4D p) {
		return this.objetos.localizarPonto(p);		
	}
}