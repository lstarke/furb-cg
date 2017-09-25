package br.com.furb.cg.unidade3.ex1;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

public class Mundo {
	
	public float sruX = 200f;
	public float sruY = 200f;
	
	private Camera2D camera;
	private List<ObjetoGrafico> listaObjetos;

	public Mundo() {
		camera = new Camera2D();
		listaObjetos = new ArrayList<ObjetoGrafico>();
	}
	
	public Camera2D getCamera() {
		return camera;
	}
	
	public List<ObjetoGrafico> getListaObjetoGrafico(){
		return listaObjetos;
	}
	
	public void posicionaCamera(GL gl, GLU glu) {
		this.camera.posicionar(gl, glu);
	}
	
	/**
	 * Desenha os eixos X e Y
	 * @param gl
	 * @param glu
	 */	
	public void desenhaSRU(GL gl, GLU glu) {
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		
		gl.glBegin(GL.GL_LINES);
			// eixo X
			gl.glVertex2f(-sruX, 0.0f);
			gl.glVertex2f(sruX, 0.0f);
		gl.glEnd();
		
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		
		gl.glBegin(GL.GL_LINES);
			// eixo Y
			gl.glVertex2f(0.0f, -sruY);
			gl.glVertex2f(0.0f, sruY);
		gl.glEnd();
	}
	
	public void desenhaObjetos(GL gl, GLU glu) {
		for (ObjetoGrafico obj : this.listaObjetos) {
			obj.desenha(gl, glu);
		}
	}
}