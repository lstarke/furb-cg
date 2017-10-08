package br.com.furb.cg.unidade3.model;

import java.awt.Color;
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
	
	
	
	public ListaVertices getVertices() {
		return vertices;
	}

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
		this.desenhando = false; // serah ???
		
		// Transformacao
		this.matriz = new Matriz();
		
		// Grafo de cena
		this.filhos = new ListaObjetosGraficos();
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
	
	public boolean isSelecionavel()
	{		
		return matriz.isIdentidade();
	}
	
	/**
	 * Retornar matriz global do objeto grafico
	 */
	public Matriz getMatriz()
	{
		return matriz;
	}
	
	/**
	 * Desenhar objeto grafico
	 * 
	 * @param gl = OpenGl gl
	 * @param glu = OpenGl glu
	 */
	public void desenhar(GL gl, GLU glu)
	{		
		gl.glColor3f(0.0f, 0.0f, 0.0f);
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

		gl.glPopMatrix();
	}
	
	public void exibirVertices()
	{
		this.vertices.exibir();
	}
	
	public void exibirMatriz()
	{
		this.matriz.exibirMatriz();
	}

	public Ponto4D addVertice(double x, double y) {
		
		Ponto4D vertice = new Ponto4D(x, y, 0, 1);
		
		this.vertices.add(vertice);
		
		return vertice;
	}

	public void removerUltimoVertice() {
		this.vertices.removerUltimo();
	}

	/**
	 * Procura ponto na lista de pontos do vertice.
	 * @param Ponto4D
	 * @return Ponto4D
	 */
	public Ponto4D localizaVertice(Ponto4D p) {		
		return this.vertices.localizaVertice(p);
	}
}