package br.furb.cg.unidade3.model;

import java.util.ArrayList;
import java.util.List;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import br.furb.cg.unidade3.model.auxiliar.ListaObjetosGraficos;

public class Mundo {
	// Propriedades do Mundo/Universo
	private float tamEixoXsru;
	private float tamEixoYsru;
	private Camera2D camera;
	private ListaObjetosGraficos grafoCena;

	public Mundo() {
		this.tamEixoXsru = 200f;
		this.tamEixoYsru = 200f;
		camera = new Camera2D();
		grafoCena = new ListaObjetosGraficos();
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
		return grafoCena;
	}
	
	public void posicionaCamera(GL gl, GLU glu) {
		this.camera.posicionar(gl, glu);
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
	
	public void desenharObjetos(GL gl, GLU glu) {
		for (ObjetoGrafico obj : this.grafoCena) {
			obj.desenhar(gl, glu);
		}
	}
}