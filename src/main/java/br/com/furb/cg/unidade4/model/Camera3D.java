package br.furb.cg.unidade4.model;

/**
 * Camera da cena grafica
 * 
 * Preparada para trabalhar em cenas 3D Perspectiva
 */

import javax.media.opengl.glu.GLU;

public class Camera3D {
	
	private float fovy;
	private float aspect;
	private float near;
	private float far;
	
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
	 * 
	 * @param gl
	 */
	public void posicionar(GLU glu) {
		glu.gluPerspective(this.fovy, this.aspect, this.near, this.far);
	}
}