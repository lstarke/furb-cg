package br.furb.cg.unidade3.model;

public class Matriz {

	private Transformacao4D matriz;
	
	/**
	 * Construtor
	 */
	public Matriz() {
		matriz = new Transformacao4D();
		matriz.atribuirIdentidade();
	}
	
	/**
	 * 
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
	
	public void exibirMatriz() {
		this.matriz.exibeMatriz();
	}
}