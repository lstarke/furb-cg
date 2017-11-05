package br.furb.cg.unidade4.model.camera;

import javax.media.opengl.GL;

public class CameraOrtho {
	private final double DESLOCAMENTO = 20.0; // pode-se utilizar o valor que achar interessante

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
		gl.glOrtho(this.xMin, this.xMax, this.yMin, this.yMax, this.zNear, this.zFar);
	}

	/**
	 * Zoom In da camera
	 * valor de movimento fixo
	 */
	public void zoomIn() {
		if (xMax - xMin > 100) {
			xMin += DESLOCAMENTO;
			xMax -= DESLOCAMENTO;
			yMin += DESLOCAMENTO;
			yMax -= DESLOCAMENTO;
		} else
			System.out.println("Limite maximo do Zoom!");
	}
	
	/**
	 * Zoom Out da camera
	 * valor de movimento fixo
	 */
	public void zoomOut() {
		if (xMax - xMin < 2200) {
			xMin -= DESLOCAMENTO;
			xMax += DESLOCAMENTO;
			yMin -= DESLOCAMENTO;
			yMax += DESLOCAMENTO;
		} else
			System.out.println("Limite minimo do Zoom!");
	}
	
	/**
	 * Movimentar a camera para baixo
	 * valor de movimento fixo
	 */
	public void panBaixo() {
		yMin += DESLOCAMENTO;
		yMax += DESLOCAMENTO;
	}
	
	/**
	 * Movimentar a camera para cima
	 * valor de movimento fixo
	 */
	public void panCima() {
		yMin -= DESLOCAMENTO;
		yMax -= DESLOCAMENTO;
	}
	
	/**
	 * Movimentar a camera para a direita
	 * valor de movimento fixo
	 */
	public void panDireita() {
		xMin -= DESLOCAMENTO;
		xMax -= DESLOCAMENTO;
	}
	
	/**
	 * Movimentar a camera para a esquerda
	 * valor de movimento fixo
	 */
	public void panEsquerda() {
		xMin += DESLOCAMENTO;
		xMax += DESLOCAMENTO;
	}
}