package br.furb.cg.unidade4.model.camera;

import javax.media.opengl.glu.GLU;

public class CameraPerspective {
	
	private float fovy;
	private float aspect;
	private float zNear;
	private float zFar;
	
	public float getFovy() {
		return fovy;
	}
	
	public float getAspect() {
		return aspect;
	}
	
	public float getzNear() {
		return zNear;
	}
	
	public float getzFar() {
		return zFar;
	}

	/**
	 * Posicionar a camera
	 * 
	 * @param gl
	 */
	public void posicionar(GLU glu) {
		glu.gluPerspective(this.fovy, this.aspect, this.zNear, this.zFar);
	}
}