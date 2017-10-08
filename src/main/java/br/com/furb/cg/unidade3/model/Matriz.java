package br.com.furb.cg.unidade3.model;

/**
 * Matriz de um objeto grafico
 * 
 * Cada objeto possui uma matriz individual
 * Este objeto e responsavel por todas as transformacoes nesta matriz
 */

public final class Matriz {

	private Transformacao4D matriz;
	
	/**
	 * Construtor
	 */
	public Matriz() {
		matriz = new Transformacao4D();
		matriz.atribuirIdentidade();
	}

	public Transformacao4D getMatriz() {
		return matriz;
	}

	/**
	 * Identificar se a matriz eh uma matriz identidade (sem transformacao)
	 */
	public boolean isIdentidade() {
		return matriz.isIdentidade();
	}

	public void escalar(double proporcao)
	{		
		// transformacao escalar na matriz global
		
		// Seguir o exemplo do professor
		
		// Leia o arquivo Dicas.txt
	}
	
	public void transladar(double x, double y)
	{
		// transformaca de translacao na matriz global
		
		// Seguir o exemplo do professor
		
		// Leia o arquivo Dicas.txt
	}
	
	public void rotacionar(double graus)
	{		
		// transformacao de rotacao na matriz global
		
		// Seguir o exemplo do professor
		
		// Leia o arquivo Dicas.txt
	}
	
	/**
	 * Imprimir no console o formato atual da matriz
	 */
	public void exibirMatriz() {
		this.matriz.exibeMatriz();
	}
}