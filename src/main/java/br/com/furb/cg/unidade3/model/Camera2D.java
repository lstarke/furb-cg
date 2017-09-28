package br.com.furb.cg.unidade3.model;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;


public class Camera2D {
	
	private final double DESLOCAMENTO = 20.0; // pode-se utilizar o valor que achar interessante

	private float ortho2D_minX = -400.0f;
	private float ortho2D_maxX =  400.0f;
	private float ortho2D_minY = -400.0f;
	private float ortho2D_maxY =  400.0f;
	
	public float getXmin() {
		return ortho2D_minX;
	}
	
	public float getXmax() {
		return ortho2D_maxX;
	}
	
	public float getYmin() {
		return ortho2D_minY;
	}
	
	public float getYmax() {
		return ortho2D_maxY;
	}
	
	public void posicionar(GL gl, GLU glu)
	{			
		glu.gluOrtho2D(this.ortho2D_minX, this.ortho2D_maxX, this.ortho2D_minY, this.ortho2D_maxY);
	}
	
	public Ponto4D convertePontoTela(double xPonto, double yPonto, double xTela, double yTela)
	{
		double xTotal = ortho2D_maxX - ortho2D_minX;
		double yTotal = ortho2D_maxY - ortho2D_minY;
		
		double escalaX = xTotal / xTela;
		double escalaY = yTotal / yTela;

		double x = ((xPonto * escalaX) + ortho2D_minX);
		double y = ((yPonto * escalaY) + ortho2D_minY) * -1;
		
		return new Ponto4D(x, y, 0.0, 1.0);
	}

	/**
	 * Zoom In da camera
	 * valor de movimento fixo
	 */
	public void zoomIn() {
		if (ortho2D_maxX - ortho2D_minX > 100) {
			ortho2D_minX += DESLOCAMENTO;
			ortho2D_maxX -= DESLOCAMENTO;
			ortho2D_minY += DESLOCAMENTO;
			ortho2D_maxY -= DESLOCAMENTO;
		} else
			System.out.println("Limite maximo do Zoom!");
	}
	
	/**
	 * Zoom Out da camera
	 * valor de movimento fixo
	 */
	public void zoomOut() {
		if (ortho2D_maxX - ortho2D_minX < 2200) {
			ortho2D_minX -= DESLOCAMENTO;
			ortho2D_maxX += DESLOCAMENTO;
			ortho2D_minY -= DESLOCAMENTO;
			ortho2D_maxY += DESLOCAMENTO;
		} else
			System.out.println("Limite minimo do Zoom!");
	}
	
	/**
	 * Movimentar a camera para baixo
	 * valor de movimento fixo
	 */
	public void panBaixo() {
		ortho2D_minY += DESLOCAMENTO;
		ortho2D_maxY += DESLOCAMENTO;
	}
	
	/**
	 * Movimentar a camera para cima
	 * valor de movimento fixo
	 */
	public void panCima() {
		ortho2D_minY -= DESLOCAMENTO;
		ortho2D_maxY -= DESLOCAMENTO;
	}
	
	/**
	 * Movimentar a camera para a direita
	 * valor de movimento fixo
	 */
	public void panDireita() {
		ortho2D_minX -= DESLOCAMENTO;
		ortho2D_maxX -= DESLOCAMENTO;
	}
	
	/**
	 * Movimentar a camera para a esquerda
	 * valor de movimento fixo
	 */
	public void panEsquerda() {
		ortho2D_minX += DESLOCAMENTO;
		ortho2D_maxX += DESLOCAMENTO;
	}
}