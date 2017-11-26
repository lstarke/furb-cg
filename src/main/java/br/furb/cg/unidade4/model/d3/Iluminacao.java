package br.furb.cg.unidade4.model.d3;

import javax.media.opengl.GL;

public class Iluminacao {

	private GL gl;
	private float posLight[] = { 0f, 0f, 0f, 0f };

	public Iluminacao(GL gl) {
		// ja inicializa com tudo zerado
		this.gl = gl;
		this.setEnable(false);
	}
	
	public Iluminacao(GL gl, float x, float y, float z) {
		this.gl = gl;
		this.setPos(x, y, z);
		this.setEnable(false);
	}
	
	public float[] getIluminacao() {
		float iluminacao[] = {this.posLight[0], 
		                      this.posLight[1],
		                      this.posLight[2],
		                      this.posLight[3]};

		return iluminacao;
	}
	
	public void setPos(float x, float y, float z) {
		this.posLight[0] = x;
		this.posLight[1] = y;
		this.posLight[2] = z;
		this.posLight[3] = 0;
	}
	
	public void setEnable(boolean enable) {
		if (enable)
			gl.glEnable(GL.GL_LIGHT0);
		else
			gl.glDisable(GL.GL_LIGHT0);
	}
	
	public void poscionar() {
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, posLight, 0);
	}
}