package com.uniovi.sdi2324814spring;

import com.uniovi.sdi2324814spring.pageobjects.PO_HomeView;
import com.uniovi.sdi2324814spring.pageobjects.PO_Properties;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Sdi2324814SpringApplicationTests {

	static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckodriver = "C:\\SDI\\geckodriver-v0.30.0-win64.exe";
	//Común a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox, Geckodriver);
	static String URL = "http://localhost:8090";
	public static WebDriver getDriver(String PathFirefox, String Geckodriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckodriver);
		driver = new FirefoxDriver();
		return driver;
	}

	@BeforeEach
	public void setUp(){
		driver.navigate().to(URL);
	}
	//Después de cada prueba se borran las cookies del navegador
	@AfterEach
	public void tearDown(){
		driver.manage().deleteAllCookies();
	}
	//Antes de la primera prueba
	@BeforeAll
	static public void begin() {}
	//Al finalizar la última prueba
	@AfterAll
	static public void end() {
	//Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	@Test
	@Order(1)
	void PR01A() {
		PO_HomeView.checkWelcomeToPage(driver, PO_Properties.getSPANISH());
	}

	@Test
	@Order(2)
	void PR01B() {
		List<WebElement> welcomeMessageElement = PO_HomeView.getWelcomeMessageText(driver,
				PO_Properties.getSPANISH());
		Assertions.assertEquals(welcomeMessageElement.get(0).getText(),
				PO_HomeView.getP().getString("welcome.message", PO_Properties.getSPANISH()));
	}

	@Test
	@Order(3)
	void PR02() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
	}

	@Test
	@Order(4)
	void PR03() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
	}

	@Test
	@Order(5)
	void PR04() {
		PO_HomeView.checkChangeLanguage(driver, "btnSpanish", "btnEnglish",
		PO_Properties.getSPANISH(), PO_Properties.getENGLISH());
	}

	@Test
	@Order(6)
	void PR05() {}

	@Test
	@Order(7)
	void PR06() {}

	@Test
	@Order(8)
	void PR07() {}

	@Test
	@Order(9)
	void PR08() {}

	@Test
	@Order(10)
	void PR09() {}

	@Test
	@Order(11)
	void PR10() {}
}
