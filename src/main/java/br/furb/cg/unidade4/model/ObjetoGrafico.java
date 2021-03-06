package br.furb.cg.unidade4.model;

/**
 * Objeto grafico
 * 
 * Cada poligono eh um objeto grafico
 * Aqui econtram-se todas as informacoes necessarias
 */

import java.awt.Color;
import java.util.List;
import java.util.Locale;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import br.furb.cg.unidade4.model.auxiliar.ListaObjetosGraficos;
import br.furb.cg.unidade4.model.auxiliar.ListaVertices;

public final class ObjetoGrafico {
	
	// Caracteristicas do objeto grafico
	private int primitiva;
	private ListaVertices vertices;
	private Color cor;
	private float corLuz[] = { 1.0f, 0.0f, 0.0f, 1.0f };
	private BBox3D bbox;
	private Matriz matriz;
	private ListaObjetosGraficos filhos;
	private StringBuilder strArquivo;
	private boolean usaLuz;
	
	// Auxiliares
	private boolean selecionado;

	/**
	 * Contrutor
	 * 
	 * @param primitiva = primitiva grafica (lines)
	 */
	public ObjetoGrafico(int primitiva2d)
	{
		// Caracteristicas
		this.setPrimitiva2d(primitiva2d);
		this.vertices = new ListaVertices();
		this.cor = Color.BLACK;
		this.bbox = new BBox3D();
		this.matriz = new Matriz();
		this.filhos = new ListaObjetosGraficos();
		
		// Auxiliares
		this.selecionado = false;
		this.usaLuz = true;
	}
	
	public int getPrimitiva2d() {
		return this.primitiva;
	}
	
	public String getStrArquivo() {
		if (strArquivo != null)
			return strArquivo.toString();
		return "";
	}
	
	public void setPercebeLuz(boolean usaLuz) {
		this.usaLuz = usaLuz;
	}

	/**
	 * Atribuir/Alterar primitiva gráfica do objeto gráfico
	 * 
	 * @param primitiva = primitiva grafica (GL_LINE_STRIP ou GL_LINE_LOOP)
	 */
	public void setPrimitiva2d(int primitiva)
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
		else {
			this.cor = cor;
			
			corLuz[0] = cor.getRed()   / 255f;
			corLuz[1] = cor.getGreen() / 255f;
			corLuz[2] = cor.getBlue()  / 255f;
			corLuz[3] = cor.getAlpha() / 255f;
		}
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
	 * Desenhar objeto grafico 2d
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
	
	/**
	 * Desenhar objeto grafico 3d
	 * 
	 * @param gl = OpenGl gl
	 * @param glu = OpenGl glu
	 */
	public void desenhar3D(GL gl, GLU glu) {
		gl.glPushMatrix();
		{
			gl.glMultMatrixd(this.matriz.getData(), 0);
			{
				if (usaLuz) {
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, corLuz, 0);  // iluminacao
					gl.glEnable(GL.GL_LIGHTING);
				}
				{
					if (!usaLuz) {
						// Obedecer a cor escolhida pelo usuario
						gl.glColor4ub((byte)cor.getRed(), (byte)cor.getGreen(), (byte)cor.getBlue(), (byte)cor.getAlpha());
					}
					
					// Desenhar objeto
					int iSize = this.vertices.meio();
					
					// Frente
					gl.glBegin(GL.GL_POLYGON); 
					for (byte i = 0; i < iSize; i++)
						gl.glVertex3d(this.vertices.get(i).obterX(), this.vertices.get(i).obterY(), this.vertices.get(i).obterZ()); 
					gl.glEnd();
					
					// Laterais (frente para traz)
					for (byte i = 0; i < iSize; i++) {
						gl.glBegin(GL.GL_TRIANGLE_STRIP);
						gl.glVertex3d(this.vertices.get(i).obterX(), this.vertices.get(i).obterY(), this.vertices.get(i).obterZ());
						gl.glVertex3d(this.vertices.get(i+1).obterX(), this.vertices.get(i+1).obterY(), this.vertices.get(i+1).obterZ());
						gl.glVertex3d(this.vertices.get(i+iSize).obterX(), this.vertices.get(i+iSize).obterY(), this.vertices.get(i+iSize).obterZ());
						gl.glEnd();
					}				
					
					// Laterais (traz para frente)
					for (byte i = (byte) iSize; i < iSize*2; i++) {
						gl.glBegin(GL.GL_TRIANGLE_STRIP);
						gl.glVertex3d(this.vertices.get(i).obterX(), this.vertices.get(i).obterY(), this.vertices.get(i).obterZ());
						gl.glVertex3d(this.vertices.get(i-1).obterX(), this.vertices.get(i-1).obterY(), this.vertices.get(i-1).obterZ());
						gl.glVertex3d(this.vertices.get(i-iSize).obterX(), this.vertices.get(i-iSize).obterY(), this.vertices.get(i-iSize).obterZ());
						gl.glEnd();
					}
					
					// Traseira
					gl.glBegin(GL.GL_POLYGON);
					for (byte i = (byte) iSize; i < iSize*2; i++)
						gl.glVertex3d(this.vertices.get(i).obterX(), this.vertices.get(i).obterY(), this.vertices.get(i).obterZ());
					gl.glEnd();
				}
				if (usaLuz)
					gl.glDisable(GL.GL_LIGHTING);
			}
		}
		gl.glPopMatrix();
	}
	
	private final String GLCOLOR4UB = "gl.glColor4ub('%d, %d, %d, %d);\n";
	private final String GLBEGIN    = "gl.glBegin(%s);\n";
	private final String GLVERTEX3D = "gl.glVertex3d(%.2f, %.2f, %.2f);\n";
	private final String GLEND      = "gl.glEnd();\n";
	
	private String beginPrimitivaStr(int primitiva) {
		
		String result = "";
		switch (primitiva) {
			case GL.GL_TRIANGLE_STRIP:
				result = String.format(GLBEGIN, "GL.GL_TRIANGLE_STRIP");
				break;
				
			case GL.GL_POLYGON:
				result = String.format(GLBEGIN, "GL.GL_POLYGON");
				break;
		}
		
		return result;
	}
	
	/**
	 * Gerar arquivo texto com comandos de geracao do poligono 3d
	 */
	public void gerarArquivo3d() {

		strArquivo = new StringBuilder();
		strArquivo.append(String.format(GLCOLOR4UB, (byte) cor.getRed(), (byte) cor.getGreen(), (byte) cor.getBlue(), (byte) cor.getAlpha()));
		
		int iSize = this.vertices.meio();
		
		// Frente
		strArquivo.append(beginPrimitivaStr(GL.GL_POLYGON));
		for (byte i = 0; i < iSize; i++)
			strArquivo.append(String.format(Locale.US, GLVERTEX3D, this.vertices.get(i).obterX(), this.vertices.get(i).obterY(), this.vertices.get(i).obterZ()));
		strArquivo.append(GLEND);
		
		// Lateriais
		for (byte i = 0; i < iSize; i++) {
			strArquivo.append(beginPrimitivaStr(GL.GL_TRIANGLE_STRIP));
			strArquivo.append(String.format(Locale.US, GLVERTEX3D, this.vertices.get(i).obterX(), this.vertices.get(i).obterY(), this.vertices.get(i).obterZ()));
			strArquivo.append(String.format(Locale.US, GLVERTEX3D, this.vertices.get(i+1).obterX(), this.vertices.get(i+1).obterY(), this.vertices.get(i+1).obterZ()));
			strArquivo.append(String.format(Locale.US, GLVERTEX3D, this.vertices.get(i+iSize).obterX(), this.vertices.get(i+iSize).obterY(), this.vertices.get(i+iSize).obterZ()));
			strArquivo.append(GLEND);
		}				
		
		// Lateriais
		for (byte i = (byte) iSize; i < iSize*2; i++) {
			strArquivo.append(beginPrimitivaStr(GL.GL_TRIANGLE_STRIP));
			strArquivo.append(String.format(Locale.US, GLVERTEX3D, this.vertices.get(i).obterX(), this.vertices.get(i).obterY(), this.vertices.get(i).obterZ()));
			strArquivo.append(String.format(Locale.US, GLVERTEX3D, this.vertices.get(i-1).obterX(), this.vertices.get(i-1).obterY(), this.vertices.get(i-1).obterZ()));
			strArquivo.append(String.format(Locale.US, GLVERTEX3D, this.vertices.get(i-iSize).obterX(), this.vertices.get(i-iSize).obterY(), this.vertices.get(i-iSize).obterZ()));
			strArquivo.append(GLEND);
		}
		
		// Traseira
		strArquivo.append(beginPrimitivaStr(GL.GL_POLYGON));
		for (byte i = (byte) iSize; i < iSize*2; i++)
			strArquivo.append(String.format(Locale.US, GLVERTEX3D, this.vertices.get(i).obterX(), this.vertices.get(i).obterY(), this.vertices.get(i).obterZ()));
		strArquivo.append(GLEND);
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
		
		this.calcularBbox();
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
		
		objAux.setPrimitiva2d(this.getPrimitiva2d());
		this.filhos.add(objAux);
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
	public void mover2d(double x, double y) {
		this.matriz.transladar2d(x, y);
	}
	
	/**
	 * Mover Objeto Grafico para os lados (translacao)
	 */
	public void mover3d(double x, double y, double z) {
		this.matriz.transladar3d(x, y, z);
	}
	
	/**
	 * Ampliar/reduzir Objeto Grafico (escala) 2d
	 */
	public void escalonar2d(double multiplicador) {
		this.matriz.escalar2d(multiplicador);
	}
	
	/**
	 * Ampliar/reduzir Objeto Grafico (escala) 3d
	 */
	public void escalonar3d(double multiplicador) {
		this.matriz.escalar3d(multiplicador);
	}
	
	/**
	 * Ampliar/reduzir Objeto Grafico (escala) e mantendo no mesmo local 
	 */
	public void escalonarFixado2d(double multiplicador) {
		this.matriz.escalarFixo2d(multiplicador, 
								  new Ponto4D(this.bbox.getCentro().obterX(), 
									          this.bbox.getCentro().obterY(), 
										      0f, 0f));
	}
	
	/**
	 * Ampliar/reduzir Objeto Grafico (escala) e mantendo no mesmo local 
	 */
	public void escalonarFixado3d(double multiplicador) {
		this.matriz.escalarFixo3d(multiplicador, 
								  new Ponto4D(this.bbox.getCentro().obterX(), 
									          this.bbox.getCentro().obterY(), 
									          this.bbox.getCentro().obterZ(),
									          0f));
	}
	
	/**
	 * Rotacionar o Objeto Grafico 2d em Z
	 */
	public void rotacionar2d() {
		this.matriz.rotacionarFixo2d(10f, 
								     new Ponto4D(this.bbox.getCentro().obterX(), 
									             this.bbox.getCentro().obterY(), 
										         0f, 0f));
	}
	
	/**
	 * Rotacionar o Objeto Grafico 3d em X
	 */
	public void rotacionar3dx() {
		this.matriz.rotacionarFixo3dX(10f, 
								      new Ponto4D(this.bbox.getCentro().obterX(), 
									              this.bbox.getCentro().obterY(), 
									              this.bbox.getCentro().obterZ(), 
									              0f));
	}
	
	/**
	 * Rotacionar o Objeto Grafico 3d em Y
	 */
	public void rotacionar3dy() {
		this.matriz.rotacionarFixo3dY(10f, 
								      new Ponto4D(this.bbox.getCentro().obterX(), 
									              this.bbox.getCentro().obterY(), 
									              this.bbox.getCentro().obterZ(), 
									              0f));
	}
	
	/**
	 * Rotacionar o Objeto Grafico 3d em Z
	 */
	public void rotacionar3dz() {
		this.matriz.rotacionarFixo3dZ(10f, 
								      new Ponto4D(this.bbox.getCentro().obterX(), 
									              this.bbox.getCentro().obterY(), 
									              this.bbox.getCentro().obterZ(), 
									              0f));
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