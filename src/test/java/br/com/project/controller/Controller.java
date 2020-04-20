package br.com.project.controller;

import br.com.project.driver.Driver;

public class Controller {

	public static void main(String[] args) throws Exception {
		
		Driver.setUp();
		Driver.process();
		Driver.closeWindow();
		
	}

}
