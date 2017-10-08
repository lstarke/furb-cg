package br.com.furb.cg.unidade3.model;

/**
 * Cena grafica
 */

import java.util.Iterator;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import br.com.furb.cg.unidade3.model.auxiliar.ListaObjetosGraficos;

public class Mundo {
	// Propriedades do Mundo/Universo
	private float tamEixoXsru;
	private float tamEixoYsru;
	private Camera2D camera;
	private ListaObjetosGraficos objetos;

	public Mundo() {
		this.tamEixoXsru = 200f;
		this.tamEixoYsru = 200f;
		this.camera = new Camera2D();
		this.objetos = new ListaObjetosGraficos();
	}
	
	public float getTamEixoXSru() {
		return this.tamEixoXsru;
	}
	
	public void setTamEixoXSru(float tamX) {
		this.tamEixoXsru = tamX;
	}
	
	public float getTamEixoYStru() {
		return this.tamEixoYsru;
	}
	
	public void setTamEixoYSru(float tamY) {
		this.tamEixoYsru = tamY;
	}
	
	public Camera2D getCamera() {
		return camera;
	}
	
	/**
	 * Retornar a lista de objetos graficos da cena
	 */
	public ListaObjetosGraficos getObjetosGraficos(){
		return objetos;
	}
	
	/**
	 * Inserir um objeto grafico na cena (lista de objetos)
	 */
	public void addObjetoGrafico(ObjetoGrafico objeto) {
		this.objetos.add(objeto);
	}
	
	public void posicionarCamera(GL gl, GLU glu) {
		this.camera.posicionar(gl, glu);
	}
	
	/**
	 * Desenha os eixos X e Y na cena grafica
	 */	
	public void SRU(GL gl, GLU glu) {
		gl.glLineWidth(1.0f);
	
		// eixo X
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
			gl.glVertex2f(-tamEixoXsru, 0.0f);
			gl.glVertex2f(tamEixoXsru, 0.0f);
		gl.glEnd();
		
		// eixo Y
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
			gl.glVertex2f(0.0f, -tamEixoYsru);
			gl.glVertex2f(0.0f, tamEixoYsru);
		gl.glEnd();
	}
	
	/**
	 * Desenhar todos os objetos graficos contidos
	 */
	public void desenharObjetos(GL gl, GLU glu) {
//		this.objetos.desenharObjetos(gl, glu);

		for (Iterator<ObjetoGrafico> it = this.objetos.iterador(); it.hasNext();) 
			it.next().desenhar(gl, glu);		
	}

	/**
	 * Procurar o ponto selecionado na lista de objetos contidos no mundo
	 */
	public Ponto4D selecionarPonto(Ponto4D p) {
		return this.objetos.localizarPonto(p);		
	}
	
	public ObjetoGrafico selecionarObjeto(Ponto4D p) {
		return this.objetos.localizarObjeto(p);
	}
}