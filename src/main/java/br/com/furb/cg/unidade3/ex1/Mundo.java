package br.com.furb.cg.unidade3.ex1;

import java.util.ArrayList;

public class Mundo {
	
	private Camera2D camera;
	private ArrayList<ObjetoGrafico> listaObjeto;
	//private <QualTipo?> poligonoSelecionado;

	public Mundo() {
		camera = new Camera2D();
		listaObjeto = new ArrayList<ObjetoGrafico>();
	}
	
	public Camera2D getCamera() {
		return camera;
	}
	
	public ArrayList<ObjetoGrafico> getObjetoGrafico(){
		return listaObjeto;
	}
}