package br.com.furb.cg.unidade3.model;
/// \file Ponto4D.java
/// \version $Revision: 1.7 $

/**
 * Esta classe foi fornecida pelo professor
 *
 *
 * Classe que define ponto no espaco 3D
 * Pontos e vetores usando coordenadas homogeneas
 *
 * A classe Ponto4D fornece uma forma unificada de representar objetos com pontos e vetores, facilitando as operacoes entre estes "dois" tipos de entidade,
 * juntamente com a integracao com a classe Transformacao4D. O ponto 4D homogeneo eh representado por um vector ( x , y , z, w ).
 * A coordenada W eh 0 para vetores e 1 para pontos normalizados.
 */

public final class Ponto4D {
	// Distacia aceita para considerar que um ponto esta proximo
	public static final double DISTANCIA = 5.0;
	
	// Propriedades do Ponto
	private double x; /// valor X.
	private double y; /// valor Y.
	private double z; /// valor Z.
	private double w; /// valor W.

	 /// Cria o ponto (0,0,0,1).
	public Ponto4D() {
		this(0, 0, 0, 1);
	}
	
	 /// Cria o ponto (0,0,0,1).
	public Ponto4D(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Ponto4D inverterSinal(Ponto4D pto) {
		pto.atribuirX(pto.obterX()*-1);
		pto.atribuirY(pto.obterY()*-1);
		pto.atribuirZ(pto.obterZ()*-1);
		return pto;
	}
	
	/// Obter valor X do ponto.
	public double obterX() {
		return x;
	}
	
	/// Obter valor Y do ponto.
	public double obterY() {
		return y;
	}
	
	/// Obter valor Z do ponto.
	public double obterZ() {
		return z;
	}
	
	/// Obter valor W do ponto.
	public double obterW() {
		return w;
	}

	/// Atribuir valor X do ponto.
	public void atribuirX(double x) {
		this.x = x;
	}
	
	/// Atribuir valor Y do ponto.
	public void atribuirY(double y) {
		this.y = y;
	}
	
	/// Atribuir valor Z do ponto.
	public void atribuirZ(double z) {
		this.z = z;
	}

	/// Atribuir valor W do ponto.
//	public void AtribuirW(double w) {
//		this.w = w;
//	}
	
	/**
	 * Indicar o ponto comparado esta proximo ao ponto atual
	 * 
	 * @param pontoComparado
	 * @return boolean
	 */
	public boolean estaPerto(Ponto4D pontoComparado)
	{
//		ISSO AQUI NÃO FUNCIONOU		
//		return pontoComparado.obterX() <= (this.obterX() + DISTANCIA) ||
//				pontoComparado.obterX() >= (this.obterX() - DISTANCIA) || 
//				pontoComparado.obterY() <= (this.obterY() + DISTANCIA) || 
//				pontoComparado.obterY() >= (this.obterY() - DISTANCIA) ||
//				pontoComparado.obterZ() <= (this.obterZ() + DISTANCIA) ||
//				pontoComparado.obterZ() >= (this.obterZ() - DISTANCIA);
		
		double xMax = this.obterX() + DISTANCIA;
		double xMin = this.obterX() - DISTANCIA;
		double yMax = this.obterY() + DISTANCIA;
		double yMin = this.obterY() - DISTANCIA;
		
		return pontoComparado.obterX() <= xMax && 
			pontoComparado.obterX() >= xMin && 
			pontoComparado.obterY() <= yMax && 
			pontoComparado.obterY() >= yMin;
	}

	@Override
	public String toString() {
		return "x = " + x + "; y = " + y;
	}
}
