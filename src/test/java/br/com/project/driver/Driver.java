package br.com.project.driver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.project.utils.Utils;

public class Driver {
	
	//Preferences
	public static WebDriver driver;
	public static String time;
	
	
	public static String resolveFind() {
		String search = Utils.jop();
		if(search.contains(" ")){
			search.replace(" ", "+");
		}

		return search;

	}
	
	@Before
	public static void setUp() throws Exception {
		
		ChromeOptions co = new ChromeOptions();
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		co.addExtensions(Utils.PATH_ADBLOCK);
		
		desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, co);
		System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver(co);	
	}
	
	//Método para fechar a janela auxiliar
	public static void closeWindow() {
		List<String> abas = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(abas.get(1)).close();
	}
	
	
	public static void aguardarCarregamento(WebDriver driver) {
		new WebDriverWait(driver, Duration.ofMillis(7000)).until((ExpectedCondition<Boolean>) 
				wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	}
	
	@Test
	public static void process() throws InterruptedException {
			
			Long timeVideo;
			
			//Realiza a abertura do site
			driver.get(Utils.SITE.concat(Driver.resolveFind()));
			
			//Abre o primeiro vídeo da pesquisa
			driver.findElement(By.id("video-title")).click();
			//driver.manage().window().setPosition(new Point(2000, 10));
			
			//Aguarda o carregamento dos elementos
			Thread.sleep(2000);
			Driver.aguardarCarregamento(driver);
			
			time = driver.findElement(By.xpath("//span[@class=\"ytp-time-duration\"]")).getText();
			
			timeVideo = Driver.getTimeVideo(time);

			
			try {
				Thread.sleep(timeVideo);
				
			}catch(NoSuchElementException n) {
				n.printStackTrace();
				System.out.println("Elemento não encontrado");
			}
			
			Driver.remakeProcess();
			
		}
	
	//Método responsável por solicitar uma nova música quando a atual acabar
	public static void remakeProcess() {
		try {
			Driver.process();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Long getTimeVideo(String time) {
		boolean haveHours = false;
		Integer horas = 0;
		Integer minutos = 0;
		Integer segundos = 0;
		long timeVideo = 0;
		String[] clock = new String[time.length()];
		
		if(time.length() == 5) {
			clock = time.split(":");
			minutos = Integer.parseInt(clock[0]);
			segundos = Integer.parseInt(clock[1]);
		}else if(time.length() == 4) {
			clock = time.split(":");
			minutos = Integer.parseInt(clock[0]);
			segundos = Integer.parseInt(clock[1]);
		}else if(time.length() > 4) {
			haveHours = true;
			clock = time.split(":");
			horas = Integer.parseInt(clock[0]);
			minutos = Integer.parseInt(clock[1]);
			segundos = Integer.parseInt(clock[2]);
		}
		if(haveHours) {
			horas *= Utils.MSHORA; // 1 hora = 3600000 milisegundos
			minutos *= Utils.MSMINUTO; // 1 minuto = 60.000 milisegundos
			segundos *= Utils.MSSEGUNDO; // 1 segundo = 1000 milisegundos
			timeVideo = (horas + minutos + segundos); // tempo total de ms, que será passado como parâmetro para a thread
		}else {
			minutos *= Utils.MSMINUTO; 
			segundos *= Utils.MSSEGUNDO; 
			timeVideo = (minutos + segundos);
		}
		
		return timeVideo;
	}
	
}
