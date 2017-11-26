package br.furb.cg.unidade4.model;

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
import br.furb.cg.unidade4.model.auxiliar.ListaObjetosGraficos;
import br.furb.cg.unidade4.model.auxiliar.ListaVertices;

public final class ObjetoGrafico {
	
	// Caracteristicas do objeto grafico
	private int primitiva;
	private ListaVertices vertices;
	private Color cor;
	private BBox3D bbox;
	private Matriz matriz;
	private ListaObjetosGraficos filhos;
	private String strArquivo = "";
	
	// Auxiliares
	private boolean selecionado;

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
		this.matriz = new Matriz();
		this.filhos = new ListaObjetosGraficos();
		
		// Auxiliares
		this.selecionado = false;
	}
	
	public int getPrimitiva() {
		return this.primitiva;
	}
	
	

	public String getStrArquivo() {
		return strArquivo;
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
	 * Lista de vertices em pontos
	 * @return List<Ponto4D>
	 */
	public List<Ponto4D> getVertices() {
		return vertices.getVertices();
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
	 * Desenhar objeto grafico
	 * 
	 * @param gl = OpenGl gl
	 * @param glu = OpenGl glu
	 */
	public void desenhar(GL gl, GLU glu)
	{
		gl.glPushMatrix();
		{
			gl.glMultMatrixd(this.matriz.getData(), 0);
			{
				// Obedecer a cor escolhida pelo usuario
				gl.glColor3ub((byte)cor.getRed(), (byte)cor.getGreen(), (byte)cor.getBlue());
				
				// Desenhar objeto
				gl.glBegin(this.primitiva);
				for (byte i = 0; i < this.vertices.size(); i++)
					gl.glVertex2d(this.vertices.get(i).obterX(), 
							      this.vertices.get(i).obterY());
				gl.glEnd();
				
				// Desenhar filhos
				for(int i = 0; i < this.filhos.size(); i++)
					this.filhos.get(i).desenhar(gl, glu);
				 
				// Desenhar bound box quando selecionado
				if (this.isSelecionado())
					this.bbox.desenhar(gl);
			}
		}
		gl.glPopMatrix();
	}
	
	public void desenhar3D(GL gl, GLU glu) {
		
		
//		gl.glPushMatrix();
//		{
//			gl.glMultMatrixd(this.matriz.getData(), 0);
//			{
				// Obedecer a cor escolhida pelo usuario
				gl.glColor4ub((byte)cor.getRed(), (byte)cor.getGreen(), (byte)cor.getBlue(), (byte)cor.getAlpha());
				strArquivo += "gl.glColor4ub(" + (byte)cor.getRed() +", " + (byte)cor.getGreen() + ", " + (byte)cor.getBlue() + ", " + (byte)cor.getAlpha() + ");\n";				
				
				// Desenhar objeto
				int iSize = this.vertices.meio();
				
				// Frente
				gl.glBegin(GL.GL_POLYGON);
				strArquivo += "gl.glBegin(" + GL.GL_POLYGON + ");\n"; 
					//gl.glNormal3d(0f, 0f, -1f);
					for (byte i = 0; i < iSize; i++) {
						gl.glVertex3d(this.vertices.get(i).obterX(), this.vertices.get(i).obterY(), this.vertices.get(i).obterZ());
						strArquivo += "gl.glVertex3d(" + this.vertices.get(i).obterX() + ", " + this.vertices.get(i).obterY() + ", " + this.vertices.get(i).obterZ() + ");\n"; 
					}						
				gl.glEnd();
				strArquivo += "gl.glEnd();\n"; 
				
				// Lateral
				//gl.glNormal3f(0f, 0f, -1f);
				for (byte i = 0; i < iSize; i++) {
					gl.glBegin(GL.GL_TRIANGLE_STRIP);
					strArquivo += "gl.glBegin(" + GL.GL_TRIANGLE_STRIP + ");\n";
					gl.glVertex3d(this.vertices.get(i).obterX(), this.vertices.get(i).obterY(), this.vertices.get(i).obterZ());
					strArquivo += "gl.glVertex3d(" + this.vertices.get(i).obterX() + ", " + this.vertices.get(i).obterY() + ", " + this.vertices.get(i).obterZ() + ");\n";
					gl.glVertex3d(this.vertices.get(i+1).obterX(), this.vertices.get(i+1).obterY(), this.vertices.get(i+1).obterZ());
					strArquivo += "gl.glVertex3d(" + this.vertices.get(i+1).obterX() + ", " + this.vertices.get(i+1).obterY() + ", " + this.vertices.get(i+1).obterZ() + ");\n";
					gl.glVertex3d(this.vertices.get(i+iSize).obterX(), this.vertices.get(i+iSize).obterY(), this.vertices.get(i+iSize).obterZ());
					strArquivo += "gl.glVertex3d(" + this.vertices.get(i+iSize).obterX() + ", " + this.vertices.get(i+iSize).obterY() + ", " + this.vertices.get(i+iSize).obterZ() + ");\n";
					gl.glEnd();
					strArquivo += "gl.glEnd();\n";
				}				
				
				for (byte i = (byte) iSize; i < iSize*2; i++) {
					gl.glBegin(GL.GL_TRIANGLE_STRIP);
					strArquivo += "gl.glBegin(" + GL.GL_TRIANGLE_STRIP + ");\n";
					gl.glVertex3d(this.vertices.get(i).obterX(), this.vertices.get(i).obterY(), this.vertices.get(i).obterZ());
					strArquivo += "gl.glVertex3d(" + this.vertices.get(i).obterX() + ", " + this.vertices.get(i).obterY() + ", " + this.vertices.get(i).obterZ() + ");\n";
					gl.glVertex3d(this.vertices.get(i-1).obterX(), this.vertices.get(i-1).obterY(), this.vertices.get(i-1).obterZ());
					strArquivo += "gl.glVertex3d(" + this.vertices.get(i-1).obterX() + ", " + this.vertices.get(i-1).obterY() + ", " + this.vertices.get(i-1).obterZ() + ");\n";
					gl.glVertex3d(this.vertices.get(i-iSize).obterX(), this.vertices.get(i-iSize).obterY(), this.vertices.get(i-iSize).obterZ());
					strArquivo += "gl.glVertex3d(" + this.vertices.get(i-iSize).obterX() + ", " + this.vertices.get(i-iSize).obterY() + ", " + this.vertices.get(i-iSize).obterZ() + ");\n";
					gl.glEnd();
					strArquivo += "gl.glEnd();\n";
				}
				
				// Traseira
				gl.glBegin(GL.GL_POLYGON);
				strArquivo += "gl.glBegin(" + GL.GL_POLYGON + ");\n";
					//gl.glNormal3d(0f, 0f, 1f);
					for (byte i = (byte) iSize; i < iSize*2; i++) {
						gl.glVertex3d(this.vertices.get(i).obterX(), this.vertices.get(i).obterY(), this.vertices.get(i).obterZ());
						strArquivo += "gl.glVertex3d(" + this.vertices.get(i).obterX() + ", " + this.vertices.get(i).obterY() + ", " + this.vertices.get(i).obterZ() + ");\n";
					}
					
				gl.glEnd();
				strArquivo += "gl.glEnd();\n";
//			}
//		}		
//		gl.glPopMatrix();
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
	 * Criar objeto grafico 3d
	 */
	public void gerar3d(float profundidade) {
		int iSize = vertices.size();
		Ponto4D p = null;
		
		for (int i = 0; i < iSize; i++) {
			p = new Ponto4D(vertices.get(i).obterX(), vertices.get(i).obterY(), profundidade, 1);
			vertices.add(p);
		}
	}
	
	/**
	 * Criar objeto grafico filho
	 */
	public void gerarFilho() {
		List<Ponto4D> verticesAux = this.getVertices();
		ObjetoGrafico objAux = new ObjetoGrafico(GL.GL_LINE_STRIP);
		
		for (int i = 0; i < verticesAux.size(); i++)
			objAux.addVertice(verticesAux.get(i).obterX() + Ponto4D.DISTANCIA * 5,
							  verticesAux.get(i).obterY() + Ponto4D.DISTANCIA * 5);
		
		objAux.setPrimitiva(this.getPrimitiva());
		this.filhos.add(objAux);
	}
	
	// Duplicar os vertices
	public void duplicarVertices(float distancia) {
		
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