package br.furb.cg.unidade4.model;

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
	 * Transformar a matriz para escala do objeto grafico 2d
	 */
	public void escalar2d(double multiplicador)
	{		
		this.matriz = escalarInterno2d(multiplicador, this.matriz);
	}
	
	public Transformacao4D escalarInterno2d(double multiplicador, Transformacao4D matrizOperacao) {
		getUniqueMatrizTransformacao().atribuirEscala(multiplicador, multiplicador, 1f);
		return matrizTransformacao.transformMatrix(matrizOperacao);
	}
	
	/**
	 * Transformar a matriz para escala do objeto grafico 3d
	 */
	public void escalar3d(double multiplicador)
	{		
		this.matriz = escalarInterno3d(multiplicador, this.matriz);
	}
	
	public Transformacao4D escalarInterno3d(double multiplicador, Transformacao4D matrizOperacao) {
		getUniqueMatrizTransformacao().atribuirEscala(multiplicador, multiplicador, multiplicador);
		return matrizTransformacao.transformMatrix(matrizOperacao);
	}
	
	/**
	 * Transformar a matriz para escala em ponto fixo 2d
	 */
	public void escalarFixo2d(double multiplicador, Ponto4D pontoFixo) {
		getUniqueMatrizGlobal().atribuirIdentidade();
		matrizGlobal = transladarInterno(pontoFixo.obterX(), pontoFixo.obterY(), 0, matrizGlobal, true);
		matrizGlobal = escalarInterno2d(multiplicador, matrizGlobal);
		matrizGlobal = transladarInterno(pontoFixo.obterX(), pontoFixo.obterY(), 0, matrizGlobal, false);

		matriz = matriz.transformMatrix(matrizGlobal);
	}
	
	/**
	 * Transformar a matriz para escala em ponto fixo 3d
	 */
	public void escalarFixo3d(double multiplicador, Ponto4D pontoFixo) {
		getUniqueMatrizGlobal().atribuirIdentidade();
		this.matrizGlobal.exibeMatriz();
		matrizGlobal = transladarInterno(pontoFixo.obterX(), pontoFixo.obterY(), pontoFixo.obterZ(), matrizGlobal, true);
		this.matrizGlobal.exibeMatriz();
		matrizGlobal = escalarInterno3d(multiplicador, matrizGlobal);
		this.matrizGlobal.exibeMatriz();
		matrizGlobal = transladarInterno(pontoFixo.obterX(), pontoFixo.obterY(), pontoFixo.obterZ(), matrizGlobal, false);
		this.matrizGlobal.exibeMatriz();

		matriz = matriz.transformMatrix(matrizGlobal);
	}
	
	/**
	 * Transformar a matriz para translacao do objeto grafico 2d
	 */
	public void transladar2d(double x, double y)
	{
		this.matriz = transladarInterno(x, y, 0, this.matriz, false);
	}
	
	/**
	 * Transformar a matriz para translacao do objeto grafico 3d
	 */
	public void transladar3d(double x, double y, double z)
	{
		this.matriz = transladarInterno(x, y, z, this.matriz, false);
	}
	
	private Transformacao4D transladarInterno(double x, double y, double z, Transformacao4D matrizOperacao, boolean negativo) {
		if (negativo)
			getUniqueMatrizTransformacao().atribuirTranslacao(-x, -y, -z);
		else
			getUniqueMatrizTransformacao().atribuirTranslacao(x, y, z);
		
		return matrizTransformacao.transformMatrix(matrizOperacao);
	}
	
	/**
	 * Transformar a matriz para rotacao 2d do objeto grafico em ponto fixo
	 */
	public void rotacionarFixo2d(double angulo, Ponto4D pontoFixo)
	{		
		getUniqueMatrizGlobal().atribuirIdentidade();
		matrizGlobal = transladarInterno(pontoFixo.obterX(), pontoFixo.obterY(), 0, matrizGlobal, true);
		
		matrizTransformacao.atribuirRotacaoZ(Transformacao4D.DEG_TO_RAD * angulo);
		matrizGlobal = matrizTransformacao.transformMatrix(matrizGlobal);
	
		matrizGlobal = transladarInterno(pontoFixo.obterX(), pontoFixo.obterY(), 0, matrizGlobal, false);

		matriz = matriz.transformMatrix(matrizGlobal);
	}
	
	/**
	 * Transformar a matriz para rotacao 3d em X do objeto grafico em ponto fixo
	 */
	public void rotacionarFixo3dX(double angulo, Ponto4D pontoFixo)
	{		
		getUniqueMatrizGlobal().atribuirIdentidade();
		matrizGlobal = transladarInterno(pontoFixo.obterX(), pontoFixo.obterY(), pontoFixo.obterZ(), matrizGlobal, true);
		
		matrizTransformacao.atribuirRotacaoX(Transformacao4D.DEG_TO_RAD * angulo);
		matrizGlobal = matrizTransformacao.transformMatrix(matrizGlobal);
	
		matrizGlobal = transladarInterno(pontoFixo.obterX(), pontoFixo.obterY(), pontoFixo.obterZ(), matrizGlobal, false);

		matriz = matriz.transformMatrix(matrizGlobal);
	}
	
	/**
	 * Transformar a matriz para rotacao 3d em Y do objeto grafico em ponto fixo
	 */
	public void rotacionarFixo3dY(double angulo, Ponto4D pontoFixo)
	{		
		getUniqueMatrizGlobal().atribuirIdentidade();
		matrizGlobal = transladarInterno(pontoFixo.obterX(), pontoFixo.obterY(), pontoFixo.obterZ(), matrizGlobal, true);
		
		matrizTransformacao.atribuirRotacaoY(Transformacao4D.DEG_TO_RAD * angulo);
		matrizGlobal = matrizTransformacao.transformMatrix(matrizGlobal);
	
		matrizGlobal = transladarInterno(pontoFixo.obterX(), pontoFixo.obterY(), pontoFixo.obterZ(), matrizGlobal, false);

		matriz = matriz.transformMatrix(matrizGlobal);
	}
	
	/**
	 * Transformar a matriz para rotacao 3d em Z do objeto grafico em ponto fixo
	 */
	public void rotacionarFixo3dZ(double angulo, Ponto4D pontoFixo)
	{		
		getUniqueMatrizGlobal().atribuirIdentidade();
		matrizGlobal = transladarInterno(pontoFixo.obterX(), pontoFixo.obterY(), pontoFixo.obterZ(), matrizGlobal, true);
		
		matrizTransformacao.atribuirRotacaoZ(Transformacao4D.DEG_TO_RAD * angulo);
		matrizGlobal = matrizTransformacao.transformMatrix(matrizGlobal);
	
		matrizGlobal = transladarInterno(pontoFixo.obterX(), pontoFixo.obterY(), pontoFixo.obterZ(), matrizGlobal, false);

		matriz = matriz.transformMatrix(matrizGlobal);
	}
	
	/**
	 * Imprimir no console o formato atual da matriz
	 */
	public void exibir() {
		this.matriz.exibeMatriz();
	}
}