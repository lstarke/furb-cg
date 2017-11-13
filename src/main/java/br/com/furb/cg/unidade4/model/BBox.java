package br.furb.cg.unidade4.model;

/**
 * Bounding Box de um objeto grafico
 * 
 * Preparado para 2 e 3 dimensoes
 * Necessario herdar e especificar (generico)
 */

import br.furb.cg.unidade4.model.auxiliar.ListaVertices;

public class BBox {
	
	protected final String STRCENTRO = "Centro: %s";

	private double xMin, xMax;
	private double yMin, yMax;
	private double zMin, zMax;
	private Ponto4D centro;
	
	/**
	 * Construtor da Bounding Box
	 */
	public BBox() {
		this.centro = new Ponto4D();
		this.atualizar(0, 0, 0, 0, 0, 0);
	}
	
	public double getXmin() {
		return xMin;
	}

	public void setXmin(double xMin) {
		this.xMin = xMin;
	}

	public double getXmax() {
		return xMax;
	}

	public void setXmax(double xMax) {
		this.xMax = xMax;
	}

	public double getYmin() {
		return yMin;
	}

	public void setYmin(double yMin) {
		this.yMin = yMin;
	}

	public double getYmax() {
		return yMax;
	}

	public void setYmax(double yMax) {
		this.yMax = yMax;
	}

	public double getZmin() {
		return zMin;
	}

	public void setZmin(double zMin) {
		this.zMin = zMin;
	}

	protected double getZmax() {
		return zMax;
	}

	protected void setZmax(double zMax) {
		this.zMax = zMax;
	}
	
	public Ponto4D getCentro() {
		return centro;
	}
	
	/**
	 * Atualizar Bounding Box
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
	 * Calcular o tamanho da Bounding Box a partir dos vertices do objeto grafico
	 * 
	 * @param ListaVertices
	 * @param boolean 3D
	 */
	public void calcular(ListaVertices vertices, boolean calcular3D) {
		
		double minX = Integer.MAX_VALUE;
		double maxX = Integer.MIN_VALUE;
		double minY = Integer.MAX_VALUE;
		double maxY = Integer.MIN_VALUE;
		double minZ = calcular3D ? Integer.MAX_VALUE : 0;
		double maxZ = calcular3D ? Integer.MIN_VALUE : 0;
		Ponto4D p;

		for (int i = 0; i < vertices.size(); i++) {
			p = vertices.get(i);
			
			if (p.obterX() > maxX)
				maxX = p.obterX() + Ponto4D.DISTANCIA;
			
			if (p.obterX() < minX)
				minX = p.obterX() - Ponto4D.DISTANCIA;
			
			if (p.obterY() > maxY)
				maxY = p.obterY() + Ponto4D.DISTANCIA;
			
			if (p.obterY() < minY)
				minY = p.obterY() - Ponto4D.DISTANCIA;
			
			if (calcular3D) { 
				if (p.obterZ() > maxZ)
					maxZ = p.obterZ() + Ponto4D.DISTANCIA;
				
				if (p.obterZ() < minZ)
					minZ = p.obterZ() - Ponto4D.DISTANCIA;
			}
		}

		this.atualizar(minX, maxX, minY, maxY, minZ, maxZ);
	}
	
	/**
	 * Calcular o centro do Bounding Box
	 */
	private void calcularCentro() {
		centro.atribuirX((xMax + xMin) / 2);
		centro.atribuirY((yMax + yMin) / 2);
		centro.atribuirZ((zMax + zMin) / 2);
	}
}