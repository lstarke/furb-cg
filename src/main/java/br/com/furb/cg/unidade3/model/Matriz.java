package br.com.furb.cg.unidade3.model;

/**
 * Matriz de um objeto grafico
 * 
 * Cada objeto possui uma matriz individual
 * Este objeto e responsavel por todas as transformacoes nesta matriz
 */

public final class Matriz {

	private Transformacao4D matriz;
	private static Transformacao4D matrizGlobal;
	private static Transformacao4D matrizTransformacao;
	
	/**
	 * Construtor
	 */
	public Matriz() {
		this.matriz = new Transformacao4D();
		this.matriz.atribuirIdentidade();
		
		// Criada somente quando houver a necessidade
		matrizGlobal = null;
		matrizTransformacao = null;
	}
	
	/**
	 * Get matriz double
	 * @return double[]
	 */
	public double[] getData() {
		return matriz.GetDate();
	}

	/**
	 * Get matriz principal
	 * @return Transformacao4D
	 */
	public Transformacao4D getMatriz() {
		return this.matriz;
	}
	
	// Get interno para a matriz global
	// Cria a matriz somente quando for necessario
	// Por ser uma matriz auxiliar, eh apenas uma matriz para todos os objetos graficos
	private static Transformacao4D getUniqueMatrizGlobal() {
		if (matrizGlobal == null)
			matrizGlobal = new Transformacao4D();
		
		return matrizGlobal;
	}
	
	// Get interno para a matriz de transfromacoes
	// Cria a matriz somente quando for necessario
	// Por ser uma matriz auxiliar, eh apenas uma matriz para todos os objetos graficos
	private static Transformacao4D getUniqueMatrizTransformacao() {
		if (matrizTransformacao == null)
			matrizTransformacao = new Transformacao4D();
		
		return matrizTransformacao;
	}

	/**
	 * Identificar se a matriz eh uma matriz identidade (sem transformacao)
	 */
	public boolean isIdentidade() {
		return this.matriz.isIdentidade();
	}

	/**
	 * Transformar a matriz para escala do objeto grafico
	 */
	public void escalar(double multiplicador)
	{		
		this.matriz = escalarInterno(multiplicador, this.matriz);
	}
	
	public Transformacao4D escalarInterno(double multiplicador, Transformacao4D matrizOperacao) {
		getUniqueMatrizTransformacao().atribuirEscala(multiplicador, multiplicador, 1f);
		return matrizTransformacao.transformMatrix(matrizOperacao);
	}
	
	/**
	 * Transformar a matriz para escala em ponto fixo
	 */
	public void escalarFixo(double multiplicador, Ponto4D pontoFixo) {
		getUniqueMatrizGlobal().atribuirIdentidade();
		matrizGlobal = transladarInterno(pontoFixo.obterX(), pontoFixo.obterY(), matrizGlobal, true);
		matrizGlobal = escalarInterno(multiplicador, matrizGlobal);
		matrizGlobal = transladarInterno(pontoFixo.obterX(), pontoFixo.obterY(), matrizGlobal, false);

		matriz = matriz.transformMatrix(matrizGlobal);
	}
	
	/**
	 * Transformar a matriz para translacao do objeto grafico
	 */
	public void transladar(double x, double y)
	{
		this.matriz = transladarInterno(x, y, this.matriz, false);
	}
	
	private Transformacao4D transladarInterno(double x, double y, Transformacao4D matrizOperacao, boolean negativo) {
		if (negativo)
			getUniqueMatrizTransformacao().atribuirTranslacao(-x, -y, 0f);
		else
			getUniqueMatrizTransformacao().atribuirTranslacao(x, y, 0f);
		
		return matrizTransformacao.transformMatrix(matrizOperacao);
	}
	
	/**
	 * Transformar a matriz para rotacao do objeto grafico em ponto fixo
	 */
	public void rotacionarFixo(double angulo, Ponto4D pontoFixo)
	{		
		getUniqueMatrizGlobal().atribuirIdentidade();
		matrizGlobal = transladarInterno(pontoFixo.obterX(), pontoFixo.obterY(), matrizGlobal, true);
		
		matrizTransformacao.atribuirRotacaoZ(Transformacao4D.DEG_TO_RAD * angulo);
		matrizGlobal = matrizTransformacao.transformMatrix(matrizGlobal);
	
		matrizGlobal = transladarInterno(pontoFixo.obterX(), pontoFixo.obterY(), matrizGlobal, false);

		matriz = matriz.transformMatrix(matrizGlobal);
	}
	
	/**
	 * Imprimir no console o formato atual da matriz
	 */
	public void exibir() {
		this.matriz.exibeMatriz();
	}
}