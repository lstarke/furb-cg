package br.furb.cg.unidade4.model.d3;

import java.util.Locale;

/**
 * Camera da cena grafica
 * 
 * Preparada para trabalhar em cenas 3D Perspectiva
 */

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

public class Camera3D {
	
	private final float MOVIMENTO = 10f;

	private GL gl;
	private GLU glu;
	private double aspect;
	private float xEye, yEye, zEye;
	private float xTarget, yTarget, zTarget;
	private StringBuilder strArquivo;
	
	public Camera3D(GL gl, GLU glu) {
		this.glu = glu;
		this.gl = gl;
		
		this.aspect = 1;
		this.xEye = 0;
		this.yEye = 0;
		this.zEye = 0;
		this.xTarget = 0; 
		this.yTarget = 0; 
		this.zTarget = 0;
	}

	public double getAspect() {
		return aspect;
	}

	public void setAspect(double aspect) {
		this.aspect = aspect;
	}
	
	public String getStrArquivo() {
		if (strArquivo != null)
			return strArquivo.toString();
		return "";
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
		this.xTarget = xTarget;
		this.yTarget = yTarget;
		this.zTarget = zTarget;

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
	
	/**
	 * Gerar arquivo texto a posicao da camera 3d
	 */
	public void gerarArquivo3d() {

		strArquivo = new StringBuilder();
		strArquivo.append("gl.glMatrixMode(GL.GL_MODELVIEW);\n");
		strArquivo.append("gl.glLoadIdentity();\n");
		strArquivo.append(String.format(Locale.US, "glu.gluLookAt(%.2f, %.2f, %.2f, %.2f, %.2f, %.2f, 0, 1, 0);\n", xEye, yEye, zEye, xTarget, yTarget, zTarget));
		strArquivo.append("\n");
		strArquivo.append("gl.glMatrixMode(GL.GL_MODELVIEW);\n");
		strArquivo.append("gl.glLoadIdentity();\n");
		strArquivo.append(String.format(Locale.US, "gl.glTranslatef(0, 0, %.2f);\n", -zEye));
		strArquivo.append(String.format(Locale.US, "gl.glRotatef(%.2f, 1, 0, 0);\n", xEye));
		strArquivo.append(String.format(Locale.US, "gl.glRotatef(%.2f, 0, 1, 0);\n", yEye));
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Camera3d:\n");
		sb.append(String.format("Eye: x = %f; y = %f; z = %f\n", xEye, yEye, zEye));
		sb.append(String.format("Target: x = %f; y = %f; z = %f", xTarget, yTarget, zTarget));
		
		return sb.toString(); 
	}
}