package br.furb.cg.unidade4.controller;

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import com.sun.opengl.util.GLUT;

public class OpenGL {

	public GL gl;
	public GLU glu;
	public GLUT glut;
	public GLAutoDrawable glDrawable;
	
	public OpenGL(GLAutoDrawable drawable) {
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glut = new GLUT();
		glDrawable.setGL(new DebugGL(gl));
	}
}