package com.uniovi.sdi2324814spring;

import com.uniovi.sdi2324814spring.pageobjects.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.Charset;
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
	void PR05() {
		//Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		//Rellenamos el formulario.
		PO_SignUpView.fillForm(driver, "77777778A", "Josefo", "Perez", "77777", "77777");
		//Comprobamos que entramos en la sección privada y nos nuestra el texto a buscar
		String checkText = "Notas del usuario";
		List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
		Assertions.assertEquals(checkText, result.get(0).getText());
	}

	//PR06A. Prueba del formulario de registro. DNI repetido en la BD
	// Propiedad: Error.signup.dni.duplicate
	@Test
	@Order(7)
	public void PR06A() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_SignUpView.fillForm(driver, "99999990A", "Josefo", "Perez", "77777", "77777");
		List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.dni.duplicate",
				PO_Properties.getSPANISH() );
		//Comprobamos el error de DNI repetido.
		String checkText = PO_HomeView.getP().getString("Error.signup.dni.duplicate",
				PO_Properties.getSPANISH());
		Assertions.assertEquals(checkText , result.get(0).getText());
	}

	//PR06B. Prueba del formulario de registro. Nombre corto.
	// Propiedad: Error.signup.name.length
	@Test
	@Order(8)
	public void PR06B() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_SignUpView.fillForm(driver, "99999990B", "Jose", "Perez", "77777", "77777");
		List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.name.length",
				PO_Properties.getSPANISH() );
		//Comprobamos el error de Nombre corto de nombre corto .
		String checkText = PO_HomeView.getP().getString("Error.signup.name.length",
				PO_Properties.getSPANISH());
		Assertions.assertEquals(checkText , result.get(0).getText());
	}

	//PR06C. Prueba del formulario de registro. DNI corto
	// Propiedad: Error.signup.dni.length
	@Test
	@Order(9)
	public void PR06C() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_SignUpView.fillForm(driver, "99", "Josefo", "Perez", "77777", "77777");
		List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.dni.length",
				PO_Properties.getSPANISH() );
		//Comprobamos el error de DNI corto.
		String checkText = PO_HomeView.getP().getString("Error.signup.dni.length",
				PO_Properties.getSPANISH());
		Assertions.assertEquals(checkText , result.get(0).getText());
	}

	//PR06D. Prueba del formulario de registro. Apellido corto
	// Propiedad: Error.signup.lastName.length
	@Test
	@Order(10)
	public void PR06D() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_SignUpView.fillForm(driver, "99999990S", "Josefo", "P", "77777", "77777");
		List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.lastName.length",
				PO_Properties.getSPANISH() );
		//Comprobamos el error de apellido corto.
		String checkText = PO_HomeView.getP().getString("Error.signup.lastName.length",
				PO_Properties.getSPANISH());
		Assertions.assertEquals(checkText , result.get(0).getText());
	}

	@Test
	@Order(11)
	void PR07() {
		//Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//Rellenamos el formulario
		PO_LoginView.fillLoginForm(driver, "99999990A", "123456");
		//Comprobamos que entramos en la pagina privada de Alumno
		String checkText = "Notas del usuario";
		List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
		Assertions.assertEquals(checkText, result.get(0).getText());
	}

	@Test
	@Order(12)
	void PR08() {
		//Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//Rellenamos el formulario
		PO_LoginView.fillLoginForm(driver, "99999993D", "123456");
		//Comprobamos que entramos en la pagina privada de Profesor
		String checkText = "Notas del usuario";
		List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
		Assertions.assertEquals(checkText, result.get(0).getText());
	}

	@Test
	@Order(13)
	void PR09() {
		//Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//Rellenamos el formulario
		PO_LoginView.fillLoginForm(driver, " 99999988F", "123456");
		//Comprobamos que entramos en la pagina privada de Administrador
		String checkText = "Notas del usuario";
		List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
		Assertions.assertEquals(checkText, result.get(0).getText());
	}

	@Test
	@Order(14)
	void PR10() {
		//Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//Rellenamos el formulario. Contraseña incorrecta
		PO_LoginView.fillLoginForm(driver, "99999990A", "12345");
		//Comprobamos que entramos en la pagina privada de Alumno
		String checkText = "Identifícate";
		List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
		Assertions.assertEquals(checkText, result.get(0).getText());
	}

	@Test
	@Order(15)
	void PR11() {
		//Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//Rellenamos el formulario
		PO_LoginView.fillLoginForm(driver, "99999990A", "123456");
		//Comprobamos que entramos en la pagina privada de Alumno
		String checkText = "Notas del usuario";
		List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
		Assertions.assertEquals(checkText, result.get(0).getText());
		//Desconectar
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		checkText = "Identifícate";
		result = PO_View.checkElementBy(driver, "text", checkText);
		Assertions.assertEquals(checkText, result.get(0).getText());
	}
}
