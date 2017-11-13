package br.furb.cg.unidade4.model.d3;

/**
 * Camera da cena grafica
 * 
 * Preparada para trabalhar em cenas 3D Perspectiva
 */

import javax.media.opengl.glu.GLU;

public class Camera3D {
	
	private GLU glu;
	private float fovy;
	private float aspect;
	private float near;
	private float far;
	
	public Camera3D(GLU glu) {
		this.glu = glu;
		this.fovy = 0;
		this.aspect = 0;
		this.near = 0;
		this.far = 0;
	}
	
	public float getFovy() {
		return fovy;
	}
	
	public float getAspect() {
		return aspect;
	}
	
	public float getNear() {
		return near;
	}
	
	public float getFar() {
		return far;
	}

	/**
	 * Posicionar a camera
	 */
	public void posicionar() {
		glu.gluPerspective(this.fovy, this.aspect, this.near, this.far);
	}
	
	/**
	 * Indicar objetivo para a camera seguir (LookAt)
	 * 
	 * @param xOrigem
	 * @param yOrigem
	 * @param zOrigem
	 * @param xObjetivo
	 * @param yObjetivo
	 * @param zObjetivo
	 */
	public void fixaObjetivo(float xOrigem, float yOrigem, float zOrigem, 
							 float xObjetivo, float yObjetivo, float zObjetivo) {

		glu.gluLookAt(xOrigem, yOrigem, zOrigem, xObjetivo, yObjetivo, zObjetivo, 0, 1, 0);
	}
}