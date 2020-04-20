package br.com.project.utils;

import java.io.File;

import javax.swing.JOptionPane;

public class Utils {
	
	//Constante site 
	public static final String SITE = "https://www.youtube.com/results?search_query=";
	public static final File PATH_ADBLOCK = new File("C:\\Users\\Admin\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Extensions\\gighmmpiobklfepjocnamgkkbiglidom\\4.10.0_0.crx");
	public static final Integer MSHORA = 3600000;
	public static final Integer MSMINUTO = 60000;
	public static final Integer MSSEGUNDO = 1000; 
	
	//Método responsável por gerar uma caixa de diálogo intuitiva para o usuário
	//digitar a música que deseja ouvir
	
	public static String jop() {
	
		String jop = JOptionPane.showInputDialog("Digite sua pesquisa");
		String string = jop;
		return string;
		
	}

}
