package br.com.furb.cg.unidade3.model;

import javax.media.opengl.GL;

public class BBox3D {
	private double xMin, xMax;
	private double yMin, yMax;
	private double zMin, zMax;
	private Ponto4D centro = new Ponto4D();
	private double corR, corG, corB;
	
	/**
	 * Construtor da Bound Box sem parametros
	 */
	public BBox3D() {
		this.atualizar(0, 0, 0, 0, 0, 0);
	}
	
	/**
	 * Construtor da Bound Box para 2D
	 * 
	 * @param xMin
	 * @param yMin
	 * @param xMax
	 * @param yMax
	 */
	public BBox3D(double xMin, double yMin, double zMin, double xMax) {
		this.atualizar(xMin, xMax, yMin, yMax, 0, 0);
	}

	/**
	 * Construtor da Bound Box
	 * 
	 * @param xMin
	 * @param yMin
	 * @param zMin
	 * @param xMax
	 * @param yMax
	 * @param zMax
	 */
	public BBox3D(double xMin, double yMin, double zMin, double xMax, double yMax, double zMax) {
		this.atualizar(xMin, xMax, yMin, yMax, zMin, zMax);
	}
	
	public double getxMin() {
		return xMin;
	}

	public void setxMin(double xMin) {
		this.xMin = xMin;
	}

	public double getxMax() {
		return xMax;
	}

	public void setxMax(double xMax) {
		this.xMax = xMax;
	}

	public double getyMin() {
		return yMin;
	}

	public void setyMin(double yMin) {
		this.yMin = yMin;
	}

	public double getyMax() {
		return yMax;
	}

	public void setyMax(double yMax) {
		this.yMax = yMax;
	}

	public double getzMin() {
		return zMin;
	}

	public void setzMin(double zMin) {
		this.zMin = zMin;
	}

	public double getzMax() {
		return zMax;
	}

	public void setzMax(double zMax) {
		this.zMax = zMax;
	}
	
	public Ponto4D getCentro() {
		return centro;
	}
	
	/**
	 * Atualizar Bound Box para 2D
	 * 
	 * @param xMin
	 * @param yMin
	 * @param xMax
	 * @param yMax
	 */
	public void atualizar(double xMin, double xMax, double yMin, double yMax) {
		this.xMin = xMin;
		this.yMin = yMin;
		this.zMin = 0;
		this.xMax = xMax;
		this.yMax = yMax;
		this.zMax = 0;
		calcularCentro();
	}

	/**
	 * Atualizar Bound Box
	 * 
	 * @param xMin
	 * @param yMin
	 * @param zMin
	 * @param xMax
	 * @param yMax
	 * @param zMax
	 */
	public void atualizar(double xMin, double xMax, double yMin, double yMax, double zMin, double zMax) {
		this.xMin = xMin;
		this.yMin = yMin;
		this.zMin = zMin;
		this.xMax = xMax;
		this.yMax = yMax;
		this.zMax = zMax;
		calcularCentro();
	}

	/**
	 * Calcular o centro do Bound Box
	 */
	private void calcularCentro() {
		centro.atribuirX((xMax + xMin) / 2);
		centro.atribuirY((yMax + yMin) / 2);
		centro.atribuirZ((zMax + zMin) / 2);
	}
	
	/**
	 * desenha Bound Box
	 * @param gl
	 */
	public void desenhar(GL gl) {
		gl.glColor3d(corR, corG, corB);

		gl.glBegin(GL.GL_LINE_LOOP);
			gl.glVertex3d(xMin, yMax, zMin);
			gl.glVertex3d(xMax, yMax, zMin);
			gl.glVertex3d(xMax, yMin, zMin);
			gl.glVertex3d(xMin, yMin, zMin);
		gl.glEnd();
		
		gl.glBegin(GL.GL_LINE_LOOP);
			gl.glVertex3d(xMin, yMin, zMin);
			gl.glVertex3d(xMin, yMin, zMax);
			gl.glVertex3d(xMin, yMax, zMax);
			gl.glVertex3d(xMin, yMax, zMin);
		gl.glEnd();
		
		gl.glBegin(GL.GL_LINE_LOOP);
			gl.glVertex3d(xMax, yMax, zMax);
			gl.glVertex3d(xMin, yMax, zMax);
			gl.glVertex3d(xMin, yMin, zMax);
			gl.glVertex3d(xMax, yMin, zMax);
		gl.glEnd();
		
		gl.glBegin(GL.GL_LINE_LOOP);
			gl.glVertex3d(xMax, yMin, zMin);
			gl.glVertex3d(xMax, yMax, zMin);
			gl.glVertex3d(xMax, yMax, zMax);
			gl.glVertex3d(xMax, yMin, zMax);
		gl.glEnd();
	}
}