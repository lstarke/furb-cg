package br.com.furb.cg.unidade3.model;

/**
 * Objeto grafico
 * 
 * Cada poligono eh um objeto grafico
 * Aqui econtram-se todas as informacoes necessarias
 */

import java.awt.Color;
import java.util.List;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import br.com.furb.cg.unidade3.model.auxiliar.ListaObjetosGraficos;
import br.com.furb.cg.unidade3.model.auxiliar.ListaVertices;

public final class ObjetoGrafico {
	
	// Caracteristicas do objeto grafico
	private int primitiva;
	private ListaVertices vertices;
	private Color cor;
	private BBox3D bbox;
	
	// Parametros Auxiliares
	private boolean selecionado;
	private boolean desenhando;	
	
	// Transformacao
	private Matriz matriz;
	
	// Grafo de cena
	private ListaObjetosGraficos filhos;
	
	// Variaveis de configurações das linhas
	private float tamanho = 1.0f;

	/**
	 * Contrutor
	 * 
	 * @param primitiva = primitiva grafica (lines)
	 */
	public ObjetoGrafico(int primitiva)
	{
		// Caracteristicas
		this.setPrimitiva(primitiva);
		this.vertices = new ListaVertices();
		this.cor = Color.BLACK;
		this.bbox = new BBox3D();
		
		this.selecionado = false;
		this.desenhando = false;
		
		// Transformacao
		this.matriz = new Matriz();
		
		// Grafo de cena
		this.filhos = new ListaObjetosGraficos();
	}
	
	public List<Ponto4D> getVertices() {
		return vertices.getVertices();
	}

	/**
	 * Atribuir/Alterar primitiva gráfica do objeto gráfico
	 * 
	 * @param primitiva = primitiva grafica (GL_LINE_STRIP ou GL_LINE_LOOP)
	 */
	public void setPrimitiva(int primitiva)
	{
		if (primitiva == javax.media.opengl.GL.GL_LINE_STRIP ||
			primitiva == javax.media.opengl.GL.GL_LINE_LOOP)
			this.primitiva = primitiva;
		else
			throw new java.lang.RuntimeException("Objeto grafico nao preparado para esta primitva, utilize: GL_LINE_STRIP ou GL_LINE_LOOP");
	}
	
	/**
	 * Atribuir/Alterar cor do objeto grafico
	 * Se nenhuma cor for atribuida o padrao sera preto
	 * 
	 * @param cor = java.awt.Color
	 */
	public void setCor(Color cor)
	{
		if (cor == null)
			this.cor = Color.BLACK;
		else	
			this.cor = cor;
	}
	
	/**
	 * Retornar Bound Box do objeto gráfico
	 */
	public BBox3D getBbox()
	{	
		return bbox;
	}
	
	/**
	 * Informar se o objeto grafico esta selecionado 
	 */
	public boolean isSelecionado()
	{
		return selecionado;
	}
	
	/**
	 * Atribuir selecao do objeto grafico
	 * 
	 * @param selecionar
	 */
	public void setSelecionado(boolean selecionar)
	{
		this.selecionado = selecionar;
	}
	
	/**
	 * Informar se o objeto grafico esta sendo desenhado
	 */
	public boolean isDesenhando()
	{
		return desenhando;
	}

	/**
	 * Indicar se o objeto grafico esta sendo desenhado
	 * 
	 * @param desenhando
	 */
	public void setDesenhando(boolean desenhando)
	{
		this.desenhando = desenhando;
	}
	
	/**
	 * Desenhar objeto grafico
	 * 
	 * @param gl = OpenGl gl
	 * @param glu = OpenGl glu
	 */
	public void desenhar(GL gl, GLU glu)
	{
	    // obedecer a cor escolhida pelo usuario
		gl.glColor3ub((byte)cor.getRed(), (byte)cor.getGreen(), (byte)cor.getBlue());

		gl.glLineWidth(tamanho);
		gl.glPointSize(tamanho);

		gl.glPushMatrix();
			gl.glMultMatrixd(matriz.getMatriz().GetDate(), 0);
			gl.glBegin(primitiva);
				for (byte i=0; i < vertices.size(); i++) {
					gl.glVertex2d(vertices.get(i).obterX(), vertices.get(i).obterY());
				}
			gl.glEnd();

			//////////// ATENCAO: chamar desenho dos filhos...
			
			if (this.isSelecionado()) {				
				this.bbox.desenhar(gl);
			}

		gl.glPopMatrix();
	}

	/**
	 * Inserir vertice na lista de vertices
	 */
	public Ponto4D addVertice(double x, double y) {
		
		this.calcularBbox();
		
		Ponto4D vertice = new Ponto4D(x, y, 0, 1);
		
		this.vertices.add(vertice);
		
		return vertice;
	}
	
	/**
	 * Calcular os vertices e o centro da Bound Box
	 */
	public void calcularBbox() {
		this.bbox.calcular(this.vertices);
	}

	/**
	 * Remover ultimo vertice da lista de vertices
	 */
	public void removerUltimoVertice() {
		this.vertices.removerUltimo();
		this.calcularBbox();
	}
	
	/**
	 * Remover vertice selecionado
	 */
	public boolean removerVerticeSelecionado() {
		boolean b = this.vertices.removerSelecionado(); 
		this.calcularBbox();
		return b;
	}

	/**
	 * Procurar ponto na lista de pontos do vertice
	 */
	public Ponto4D localizarVertice(Ponto4D pontoComparado) {
		return this.vertices.getPontoVerticeMaisPertoSelecionado(pontoComparado);		
	}
	
	/**
	 * Mover Objeto Grafico para os lados (translacao)
	 */
	public void mover(double x, double y) {
		this.matriz.transladar(x, y);
	}
	
	/**
	 * Ampliar/reduzir Objeto Grafico (escala) 
	 */
	public void escalonar(double multiplicador) {
		this.matriz.escalar(multiplicador);
	}
	
	/**
	 * Ampliar/reduzir Objeto Grafico (escala) e mantendo no mesmo local 
	 */
	public void escalonarFixado(double multiplicador) {
		this.matriz.escalarFixo(multiplicador, 
								new Ponto4D(this.bbox.getCentro().obterX(), 
										    this.bbox.getCentro().obterY(), 
										    0f, 0f));
	}
	
	/**
	 * Rotacionar o Objeto Grafico em Z
	 */
	public void rotacionar() {
		this.matriz.rotacionarFixo(10f, 
								   new Ponto4D(this.bbox.getCentro().obterX(), 
										   	   this.bbox.getCentro().obterY(), 
										       0f, 0f));
	}
	
	/**
	 * Imprimir todos os pontos (vertices e centro) da bound box no console
	 */
	public void exibirBbox() {
		System.out.println("Bound Box:");
		this.bbox.exibir();
	}
	
	/**
	 * Imprimir a matriz de um objeto grafico no console
	 */
	public void exibirMatriz()
	{
		System.out.println("Matriz:");
		this.matriz.exibir();
	}
	
	/**
	 * Imprimir todos os vertices (pontos) no console
	 */
	public void exibirVertices()
	{
		System.out.println("Vertices:");
		this.vertices.exibir();
	}
}