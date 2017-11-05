package br.furb.cg.unidade4.model.camera;

import javax.media.opengl.GL;

public class CameraFrustum {
	
	private float xMin  = -400f;
	private float xMax  = 400f;
	private float yMin  = -400f;
	private float yMax  = 400f;
	private float zNear = 0.00001f;
	private float zFar  = 400f;

	public float getXmin() {
		return xMin;
	}
	
	public float getXmax() {
		return xMax;
	}
	
	public float getYmin() {
		return yMin;
	}
	
	public float getYmax() {
		return yMax;
	}
	
	public float getNear() {
		return zNear;
	}
	
	public float getFar() {
		return zFar;
	}
	
	public float getTamX() {
		return xMax - xMin;
	}
	
	public float getTamY() {
		return yMax - yMin;
	}
	
	public float getTamZ() {
		return zFar - zNear;
	}
	
	/**
	 * Posicionar a camera
	 * 
	 * @param gl
	 */
	public void posicionar(GL gl)
	{
		gl.glFrustum(this.xMin, this.xMax, this.yMin, this.yMax, this.zNear, this.zFar);
	}
}