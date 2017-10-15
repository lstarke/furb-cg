package br.com.furb.cg.unidade3.model;

/**
 * Matriz de um objeto grafico
 * 
 * Cada objeto possui uma matriz individual
 * Este objeto e responsavel por todas as transformacoes nesta matriz
 */

public final class Matriz {

	private Transformacao4D matriz;
	private Transformacao4D matrizEscala;
	private Transformacao4D matrizTranslacao;
	
	/**
	 * Construtor
	 */
	public Matriz() {
		this.matriz = new Transformacao4D();
		this.matriz.atribuirIdentidade();
		
		// Criadas somente quando houver a necessidade
		this.matrizEscala = null;
		this.matrizTranslacao = null;
	}

	/**
	 * Get matriz principal
	 */
	public Transformacao4D getMatriz() {
		return this.matriz;
	}
	
	/**
	 * Get interno para a matriz de escala
	 */
	// Criar a matriz de escala somente quanto for chamada pela primeira vez
	private Transformacao4D getMatrizEscala() {
		if (this.matrizEscala == null)
			this.matrizEscala = new Transformacao4D();
		
		return this.matrizEscala;
	}
	
	/**
	 * Get interno para a matriz de translacao
	 */
	// Criar a matriz de translacao somente quanto for chamada pela primeira vez
	private Transformacao4D getMatrizTranslacao() {
		if (this.matrizTranslacao == null)
			this.matrizTranslacao = new Transformacao4D();
		
		return this.matrizTranslacao;
	}

	/**
	 * Identificar se a matriz eh uma matriz identidade (sem transformacao)
	 */
	public boolean isIdentidade() {
		return this.matriz.isIdentidade();
	}

	public void escalar(double proporcao)
	{		
		getMatrizEscala().atribuirEscala(proporcao, proporcao, 1f);
		this.matriz = getMatrizEscala().transformMatrix(this.matriz);
	}
	
	/**
	 * Transformar a matriz para translacao do objeto grafico
	 */
	public void transladar(double x, double y)
	{
		getMatrizTranslacao().atribuirTranslacao(x, y, 0f);
		this.matriz = getMatrizTranslacao().transformMatrix(this.matriz);
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