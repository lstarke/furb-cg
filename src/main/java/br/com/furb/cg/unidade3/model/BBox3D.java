package br.com.furb.cg.unidade3.model;

/**
 * Bound Box de um objeto grafico
 * 
 * Preparado para 2 e 3 dimensoes
 * Cada objeto grafico possui uma Bound Box individual
 */

import javax.media.opengl.GL;

import br.com.furb.cg.unidade3.model.auxiliar.ListaVertices;

public class BBox3D {
	private double xMin, xMax;
	private double yMin, yMax;
	private double zMin, zMax;
	private Ponto4D centro = new Ponto4D();
	
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
	 * Construtor da Bound Box para 3D
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
	 * Atualizar Bound Box para 3D
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
		//this.calcularCentro();
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glLineStipple(1, (short)0XAAAA);
		
		gl.glEnable(GL.GL_LINE_STIPPLE);
		gl.glBegin(GL.GL_LINE_LOOP);
		{	
			gl.glVertex2d(xMin, yMin);
			gl.glVertex2d(xMax, yMin);
			gl.glVertex2d(xMax, yMax);
			gl.glVertex2d(xMin, yMax);
		}
		gl.glEnd();
		gl.glDisable(GL.GL_LINE_STIPPLE);
		
		gl.glBegin(GL.GL_POINTS);
		{	
			gl.glVertex2d(xMin, yMin);
			gl.glVertex2d(xMax, yMin);
			gl.glVertex2d(xMax, yMax);
			gl.glVertex2d(xMin, yMax);
			
			gl.glVertex2d(xMin, centro.obterY());
			gl.glVertex2d(xMax, centro.obterY());
			gl.glVertex2d(centro.obterX(), yMax);
			gl.glVertex2d(centro.obterX(), yMin);
		}
		gl.glEnd();
	}

	public void setPontos(ListaVertices vertices) {
		
		double minX = Integer.MAX_VALUE;
		double maxX = Integer.MIN_VALUE;
		double minY = Integer.MAX_VALUE;
		double maxY = Integer.MIN_VALUE;

		for (int i = 0; i < vertices.size(); i++) {
			
			Ponto4D p = vertices.get(i);
			
			if (p.obterX() > maxX)
				maxX = p.obterX() + Ponto4D.DISTANCIA;
			
			if (p.obterX() < minX)
				minX = p.obterX() - Ponto4D.DISTANCIA;
			
			if (p.obterY() > maxY)
				maxY = p.obterY() + Ponto4D.DISTANCIA;
			
			if (p.obterY() < minY)
				minY = p.obterY() - Ponto4D.DISTANCIA;
		}
		
//		this.xMin = minX;
//		this.xMax = maxX;
//		this.yMin = minY;
//		this.yMax = maxY;
		this.atualizar(minX, maxX, minY, maxY);
	}
	
	public boolean pontoEstaDentro(Ponto4D ponto) {
		
		double x = ponto.obterX();
		double y = ponto.obterY();

		if (x > this.getxMin() && x < this.getxMax() && 
			y > this.getyMin() && y < this.getyMax()){
			return true;
		}
		
		return false;
	}

}