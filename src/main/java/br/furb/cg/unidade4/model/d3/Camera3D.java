package br.furb.cg.unidade4.model.d3;

/**
 * Camera da cena grafica
 * 
 * Preparada para trabalhar em cenas 3D Perspectiva
 */

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

public class Camera3D {
	
	private final float MOVIMENTO = 2f;

	private GL gl;
	private GLU glu;
	private double aspect;
	private float xEye, yEye, zEye;
	
	public Camera3D(GL gl, GLU glu) {
		this.glu = glu;
		this.gl = gl;
		
		this.aspect = 1;
		this.xEye = 0;
		this.yEye = 0;
		this.zEye = 0;
	}

	public double getAspect() {
		return aspect;
	}

	public void setAspect(double aspect) {
		this.aspect = aspect;
	}

	/**
	 * Posicionar a camera
	 */
	public void especificar(double fovy, double near, double far) {
		// Especifica sistema de coordenadas de projecao
		gl.glMatrixMode(GL.GL_PROJECTION);
		
		// Inicializa sistema de coordenadas de projecao
		gl.glLoadIdentity();

		// Especifica a projecao perspectiva(angulo, aspecto, zMin, zMax)
		glu.gluPerspective(fovy, this.aspect, near, far);
	}
	
	/**
	 * Indicar objetivo para a camera seguir (LookAt)
	 * 
	 * @param xOrigem
	 * @param yEye
	 * @param zEye
	 * @param xTarget
	 * @param yTarget
	 * @param zTarget
	 */
	public void olharPara(float xEye, float yEye, float zEye, 
						  float xTarget, float yTarget, float zTarget) {

		this.xEye = xEye;
		this.yEye = yEye;
		this.zEye = zEye;

		gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
		glu.gluLookAt(xEye, yEye, zEye, xTarget, yTarget, zTarget, 0, 1, 0);
	}
	
	public void posicionar() {
		// Especifica sistema de coordenadas do modelo
		gl.glMatrixMode(GL.GL_MODELVIEW);
		
		// Inicializa sistema de coordenadas do modelo
		gl.glLoadIdentity();
		
		// Especifica posicao do observador e do alvo
		gl.glTranslatef(0, 0, -zEye);
		gl.glRotatef(xEye, 1, 0, 0);
		gl.glRotatef(yEye, 0, 1, 0);
	}

	public void baixo() {
		this.xEye -= MOVIMENTO;
	}

	public void cima() {
		this.xEye += MOVIMENTO;
	}

	public void direita() {
		this.yEye -= MOVIMENTO;
	}

	public void esquerda() {
		this.yEye += MOVIMENTO;
	}
	
	public void zoomIn() {
		this.zEye -= MOVIMENTO;
	}

	public void zoomOut() {
		this.zEye += MOVIMENTO;
	}
}