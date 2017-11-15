package br.furb.cg.unidade4.model.d3;

import javax.media.opengl.GL;
import br.furb.cg.unidade4.model.BBox;
import br.furb.cg.unidade4.model.auxiliar.ListaVertices;

public class BBox3D extends BBox{
	
	private final String STRVERTICE = "Vertice nro %d: x = %f; y = %f; z = %f";
	
	/**
	 * Calcular o tamanho da Bounding Box a partir dos vertices do objeto grafico
	 * 
	 * @param ListaVertices
	 * @param boolean 3D
	 */
	public void calcular(ListaVertices vertices) {
		super.calcular(vertices, true);
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
	 * Imprimir no console o formato atual da Bounding Box
	 */
	public void exibir()
	{
		System.out.println(String.format(STRVERTICE, 1, getXmin(), getYmin(), getZmin()));
		System.out.println(String.format(STRVERTICE, 2, getXmin(), getYmin(), getZmax()));
		System.out.println(String.format(STRVERTICE, 3, getXmin(), getYmax(), getZmin()));
		System.out.println(String.format(STRVERTICE, 4, getXmin(), getYmax(), getZmax()));
		System.out.println(String.format(STRVERTICE, 5, getXmax(), getYmin(), getZmin()));
		System.out.println(String.format(STRVERTICE, 6, getXmax(), getYmin(), getZmax()));
		System.out.println(String.format(STRVERTICE, 7, getXmax(), getYmax(), getZmin()));
		System.out.println(String.format(STRVERTICE, 8, getXmax(), getYmax(), getZmax()));
		System.out.println(String.format(STRCENTRO,  getCentro()));
	}
}