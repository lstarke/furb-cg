package br.furb.cg.unidade4.model.d2;

/**
 * Bounding Box de um objeto grafico
 * 
 * Especificado para 2 dimensoes
 * Cada objeto grafico possui uma Bound Box individual
 */

import javax.media.opengl.GL;
import br.furb.cg.unidade4.model.BBox;
import br.furb.cg.unidade4.model.Ponto4D;
import br.furb.cg.unidade4.model.auxiliar.ListaVertices;

public class BBox2D extends BBox{
	
	private final String STRVERTICE = "Vertice nro %d: x = %f; y = %f";

	/**
	 * Atualizar Bounding Box para 2D
	 * 
	 * @param xMin
	 * @param getYmin()
	 * @param getXmax()
	 * @param getYmax()
	 */
	public void atualizar(double xMin, double xMax, double yMin, double yMax) {
		super.atualizar(xMin, xMax, yMin, yMax, 0, 0);
	}
	
	/**
	 * Calcular o tamanho da Bounding Box a partir dos vertices do objeto grafico
	 * 
	 * @param ListaVertices
	 * @param boolean 3D
	 */
	public void calcular(ListaVertices vertices) {
		super.calcular(vertices, false);
	}
	
	/**
	 * Desenhar Bounding Box
	 * 
	 * @param GL
	 */
	public void desenhar(GL gl) {
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glLineStipple(1, (short)0XAAAA);
		
		gl.glEnable(GL.GL_LINE_STIPPLE);
		gl.glBegin(GL.GL_LINE_LOOP);
		{	
			gl.glVertex2d(getXmin(), getYmin());
			gl.glVertex2d(getXmax(), getYmin());
			gl.glVertex2d(getXmax(), getYmax());
			gl.glVertex2d(getXmin(), getYmax());
		}
		gl.glEnd();
		gl.glDisable(GL.GL_LINE_STIPPLE);
		
		gl.glBegin(GL.GL_POINTS);
		{	
			gl.glVertex2d(getXmin(), getYmin());
			gl.glVertex2d(getXmax(), getYmin());
			gl.glVertex2d(getXmax(), getYmax());
			gl.glVertex2d(getXmin(), getYmax());
			
			gl.glVertex2d(getXmin(), getCentro().obterY());
			gl.glVertex2d(getXmax(), getCentro().obterY());
			gl.glVertex2d(getCentro().obterX(), getYmax());
			gl.glVertex2d(getCentro().obterX(), getYmin());
		}
		gl.glEnd();
	}
	
	/**
	 * Testar se o ponto comparado esta dentro da Bounding Box
	 * 
	 * @param Ponto4D
	 * @return boolean
	 */
	public boolean pontoEstaDentro(Ponto4D pontoComparado) {
		
		double x = pontoComparado.obterX();
		double y = pontoComparado.obterY();

		return (x > this.getXmin() && x < this.getXmax() && 
			    y > this.getXmin() && y < this.getXmax());
	}

	/**
	 * Imprimir no console o formato atual da Bounding Box
	 */
	public void exibir()
	{
		System.out.println(String.format(STRVERTICE, 1, getXmin(), getYmin()));
		System.out.println(String.format(STRVERTICE, 2, getXmax(), getYmin()));
		System.out.println(String.format(STRVERTICE, 3, getXmax(), getYmax()));
		System.out.println(String.format(STRVERTICE, 4, getXmin(), getYmax()));
		System.out.println(String.format(STRCENTRO,  getCentro()));
	}
}