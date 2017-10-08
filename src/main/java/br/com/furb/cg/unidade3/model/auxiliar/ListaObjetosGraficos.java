package br.com.furb.cg.unidade3.model.auxiliar;

/**
 * Encapsulamento da lista de objetos graficos
 * Util para trabalhar com o grafo de cena
 */

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import br.com.furb.cg.unidade3.model.ObjetoGrafico;
import br.com.furb.cg.unidade3.model.Ponto4D;

public class ListaObjetosGraficos {

	private List<ObjetoGrafico> objetos;
	private ObjetoGrafico selecionado;
	
	/**
	 * Construtor
	 */
	public ListaObjetosGraficos() {
		this.objetos = new ArrayList<ObjetoGrafico>();
		this.selecionado = null;
	}
	
	
	
	/**
	 * Adicionar objeto grafico no grafo de cena
	 * 
	 * @param novo 
	 */
	public void add(ObjetoGrafico novo) {
		this.objetos.add(novo);
	}
	
	/**
	 * Retornar o primeiro vertice da lista de vertices do objeto grafico/poligono
	 */
	public ObjetoGrafico getPrimeiro()
	{
		if (objetos.size() > 0)
			return objetos.get(0);
		else
			return null;
	}
	
	/**
	 * Retornar o ultimo vertice adicionado no objeto grafico/poligono
	 */
	public ObjetoGrafico getUltimo()
	{	
		int idxUltimo = getUltimoIndice();
		
		if (idxUltimo > -1)
			return objetos.get(idxUltimo);
		
		return null;
	}
	
	/**
	 * Retornar o indice do ultimo vertice adicionado no objeto grafico/poligono
	 */
	public int getUltimoIndice()
	{
		return objetos.size() - 1;
	}
	
	/**
	 * Indicar o numero/quantidade de vertices no objeto gradico/poligono
	 */
	public int size()
	{
		return objetos.size();
	}
	
	/**
	 * Remover o objeto grafico selecionado
	 */
	public boolean removerSelecionado()
	{
		if (selecionado != null)
		{
			objetos.remove(selecionado);
			selecionado = null;
			return true;
		}
		
		return false;
	}
	
	/**
	 * Remover ultimo objeto grafico do grafo de cena
	 */
	public void removerUltimo()
	{
		int idxUltimo = getUltimoIndice();
		
		if (idxUltimo > -1)
			objetos.remove(idxUltimo);
	}
	
	/**
	 * Imprimir vertices no console
	 */
	public void exibir()
	{
		for (int i = 0; i < objetos.size(); i++)
			System.out.println(String.format("Objetos nro %d: %s", i, objetos.get(i).toString()));
	}



	public void desenharObjetos(GL gl, GLU glu) {
		for (ObjetoGrafico objetoGrafico : objetos) {
			objetoGrafico.desenhar(gl, glu);			
		}
		
	}


	/**
	 * Procura ponto nas lista de vertices do objeto.
	 * @param Ponto4D
	 * @return Ponto4D
	 */
	public Ponto4D localizarPonto(Ponto4D p) {
		for (ObjetoGrafico objetoGrafico : objetos) {			
			return objetoGrafico.localizaVertice(p);			
		}
		return null;
	}
}