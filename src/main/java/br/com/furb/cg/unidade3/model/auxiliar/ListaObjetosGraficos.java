package br.furb.cg.unidade3.model.auxiliar;

/**
 * Encapsulamento da lista de objetos graficos
 * Util para trabalhar com o grafo de cena
 */

import java.util.ArrayList;
import java.util.List;

import br.furb.cg.unidade3.model.ObjetoGrafico;

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
	 * @return
	 */
	public ObjetoGrafico add(ObjetoGrafico novo) {
		return null;
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
}