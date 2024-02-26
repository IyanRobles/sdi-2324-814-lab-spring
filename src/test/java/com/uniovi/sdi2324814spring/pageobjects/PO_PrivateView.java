package com.uniovi.sdi2324814spring.pageobjects;

import com.uniovi.sdi2324814spring.util.SeleniumUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class PO_PrivateView extends PO_NavView {
    static public void fillFormAddMark(WebDriver driver, int userOrder, String descriptionp, String scorep)
    {
        //Esperamos 5 segundo a que carge el DOM porque en algunos equipos falla
        SeleniumUtils.waitSeconds(driver, 5);
        //Seleccionamos el alumnos userOrder
        new Select(driver.findElement(By.id("user"))).selectByIndex(userOrder);
        //Rellenemos el campo de descripción
        WebElement description = driver.findElement(By.name("description"));
        description.clear();
        description.sendKeys(descriptionp);
        WebElement score = driver.findElement(By.name("score"));
        score.click();
        score.clear();
        score.sendKeys(scorep);
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }

    private static final String PRIVATE_PAGE_MESSAGE = "Notas del usuario";
    static public void checkPrivatePage(WebDriver driver) {
        List<WebElement> result = PO_View.checkElementBy(driver, "text", PRIVATE_PAGE_MESSAGE);
    }

    static public void checkPrivatePage(WebDriver driver, String checkText) {
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
    }

    private static final int MARKS_PER_PAGE = 2;
    static public void countNotes(WebDriver driver, int expectedNotes) {
        ////Contamos el número de filas de notas
        List<WebElement> marksList = SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr",
                PO_View.getTimeout());
        Assertions.assertEquals(expectedNotes, marksList.size());
    }

    static public void doLogin(WebDriver driver, String dni, String password) {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, dni, password);
    }

    static public void doLogout(WebDriver driver) {
        //Ahora nos desconectamos y comprobamos que aparece el menú de registro
        String loginText = PO_HomeView.getP().getString("signup.message", PO_Properties.getSPANISH());
        PO_PrivateView.clickOption(driver, "logout", "text", loginText);
    }

    private static final String MARK_DETAILS_TEXT = "Detalles de la nota";
    static public void checkMarkDetails(WebDriver driver, String markDescription) {
        SeleniumUtils.waitLoadElementsByXpath(driver, "//td[contains(text(), '" + markDescription + "')]", getTimeout());
        //Contamos las notas
        By enlace = By.xpath("//td[contains(text(), '" + markDescription + "')]/following-sibling::*[2]");
        driver.findElement(enlace).click();
        //Esperamos por la ventana de detalle

        List<WebElement> result = PO_View.checkElementBy(driver, "text", MARK_DETAILS_TEXT);
        Assertions.assertEquals(MARK_DETAILS_TEXT, result.get(0).getText());
    }

    static public void doChangePage(WebDriver driver, int pageNumber) {
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
        //Ahora lo clickamos
        elements.get(pageNumber).click();
    }

    static public void doAddMark(WebDriver driver, int user, String description, String score) {
        //Pinchamos en la opción de menú de Notas: //li[contains(@id, 'marks-menu')]/a
        //List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//li[contains(@id, 'marks-menu')]/a");
        List<WebElement> elements = PO_View.checkElementBy(driver, "free",
                "//*[@id='myNavbar']/ul[1]/li[2]");
        elements.get(0).click();
        //Esperamos a que aparezca la opción de añadir nota: //a[contains(@href, 'mark/add')]
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'mark/add')]");
        //Pinchamos en agregar Nota.
        elements.get(0).click();
        //Ahora vamos a rellenar la nota con mas de 20 caracteres. //option[contains(@value, '4')]
        PO_PrivateView.fillFormAddMark(driver, user, description, score);
    }

    static public void checkMarkExists(WebDriver driver, String markDescription) {
        //Esperamos a que se muestren los enlaces de paginación de la lista de notas
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
        //Nos vamos a la última página
        elements.get(4).click();
        //Comprobamos que aparece la nota en la página
        elements = PO_View.checkElementBy(driver, "text", markDescription);
        Assertions.assertEquals(markDescription, elements.get(0).getText());
    }

    static public void checkMarkNotExists(WebDriver driver, String markDescription) {
        //Volvemos a la última página
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
        elements.get(4).click();
        //Y esperamos a que NO aparezca la última "Creando una nota nueva"
        SeleniumUtils.waitTextIsNotPresentOnPage(driver, markDescription,PO_View.getTimeout());
    }

    static public void doDeleteMark(WebDriver driver, String markDescription) {
        //Pinchamos en la opción de menú de Notas: //li[contains(@id, 'marks-menu')]/a
        //List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//li[contains(@id, 'marks-menu')]/a");
        List<WebElement> elements = PO_View.checkElementBy(driver, "free",
                "//*[@id='myNavbar']/ul[1]/li[2]");
        elements.get(0).click();
        //Pinchamos en la opción de lista de notas.
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'mark/list')]");
        elements.get(0).click();
        //Esperamos a que se muestren los enlaces de paginación la lista de notas
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
        //Nos vamos a la última página
        elements.get(4).click();
        //Esperamos a que aparezca la Nueva nota en la última página
        //Y Pinchamos en el enlace de borrado de la Nota "Nota sistemas distribuidos"
        elements = PO_View.checkElementBy(driver, "free",
                "//td[contains(text(), '" + markDescription + "')]/following-sibling::*/a[contains(@href, 'mark/delete')]");
        elements.get(0).click();
    }
}