package br.furb.cg.unidade4.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Arquivo {
	
	public static void gravar(String path, String str) {
		
		BufferedWriter writer = null;
		
		try {
	        writer = new BufferedWriter(new FileWriter(new File(path)));
	        writer.write(str);
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {	        	            
	            try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}   
	    }

		System.out.println("Arquivo gravado!");
	}
}
