package br.furb.cg.unidade4.model.d3;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.swing.JOptionPane;

import com.sun.opengl.util.texture.TextureData;

public class Textura {
	
	private int idTexture[];
	private int width[], height[];
	private BufferedImage image;
	private TextureData td;
	private ByteBuffer buffer[];

	public Textura(GL gl) {
		//===== Comandos de inicializacao para textura
		
		// lista de identificadores de textura
		idTexture = new int[3];
		width = new int[3];
		height = new int[3];
		
		// Gera identificador de textura
		gl.glGenTextures(1, idTexture, 2);
		
		// Define os filtros de magnificacao e minificacao 
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);	
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
		buffer = new ByteBuffer [2]; // buffer da imagem

//		loadImage(0,"logoGCG.jpg"); // carrega as texturas		
//		loadImage(1,"madeira.jpg");
	}
	
	public void carregarImagem(int ind, String fileName)
	{
		// Tenta carregar o arquivo		
		image = null;
		try {
			image = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro na leitura do arquivo " + fileName);
		}

		// Obtem largura e altura
		width[ind]  = image.getWidth();
		height[ind] = image.getHeight();
		
		// Gera uma nova TextureData...
		td = new TextureData(0, 0, false, image);
		
		// ...e obtem um ByteBuffer a partir dela
		buffer[ind] = (ByteBuffer) td.getBuffer();
	}
	
	public int getIdTextura(int i) {
		return idTexture[i];
	}
	
	public ByteBuffer getBuffer(int i) {
		return buffer[i];
	}
	
	public int getLargura(int i) {
		return width[i];
	}
	
	public int getAltura(int i) {
		return height[i];
	}
}