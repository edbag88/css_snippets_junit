package CssXpathJUnitSelenideHelp;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;



public class Snippets {

    void browser_command_examples() {
        open("https://ya.ru");
        open("customers/orders"); //-Dselenide.baseUrl=http://123.23.23.1 пример Configuration.baseUrl="https://ya.ru";
        open("/", AuthenticationType.BASIC,
                new BasicAuthCredentials("", "user", "password"));

        /*как сделать паузу с помощью вкладки source
        1) перейти на вкладку source, далее навести на элемент и нажать кнопку F8
        2) если первый вариант не работает, переходим на вкладку Console пишем setTimeout(function() {debugger}, 6000);
        нажимаем enter и наводим на нужный элемент*/

        //имитация клавиш браузера: кнопка назад и refresh
        Selenide.back();
        Selenide.refresh();

        //чистка Cookies, LocalStorage, sessionStorage
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        executeJavaScript("sessionStorage().clear();"); //no Selenide common for this yet

        //вслывающие окна JavaScript
        Selenide.confirm(); //ok
        Selenide.dismiss(); //cancel

        //Работа с окнами и фреймом
        Selenide.closeWindow(); //закрытие окна
        Selenide.closeWebDriver(); //закрытие браузера
        Selenide.switchTo().window("The internet"); //переход на другое окно
        Selenide.switchTo().frame("New"); //переключение на фрейм
        Selenide.switchTo().defaultContent(); //

        //Cookies
        var cookie = new Cookie("foo", "bar");
        WebDriverRunner.getWebDriver().manage().addCookie(cookie); //для применения cookies, нужно вначале сделать Selenide.refresh();


    }
    void selectors_examples(){
        $("div").click();
        element("div").click();

        $("div", 2).click();//the third div

        $x("h1//div").click();
        $(byXpath("//h1//div")).click();

        $(byText("text")).click();
        $(withText("te")).click();//часть текста

        $(byTagAndText("div", "text")).click();
        $(withTagAndText("div", "text")).click();

        $("").parent();//поиск родителя
        $("").sibling(1);
        $("").preceding(1);
        $("").closest("div");
        $("").ancestor("div");//the same as closest
        $("div:last-child");

        //поиск по нескольким селекторам
        $("div").$("h1").$(byText("test")).click();

        $(byAttribute("abc", "x")).click();
        $("[abc=x]").click();//сокращенный поиск по атрибуту

        $(byId("id")).click();
        $("#id").click();//поиск по id

        $(byClassName("test")).click();
        $(".test").click();//сокращенный поиск по классу

    }
    void actions_examples(){
        $x("div").click();
        $x("div").doubleClick();
        $x("div").contextClick();//правый клик

        $x("div").hover();//поднести мышку и не кликать
        $x("div").setValue("text");//удалит и напишет заново
        $x("div").append("text");//в конец текста
        $x("div").clear();
        $x("div").setValue(""); //clear
        $x("div").sendKeys("test");
        actions().sendKeys("test").perform();
        actions().sendKeys(Keys.chord(Keys.CONTROL, "f")).perform();//CTRL+F
        $x("html").sendKeys(Keys.chord(Keys.CONTROL, "f"));

        $x("div").pressEnter();
        $x("div").pressEscape();
        $x("div").pressTab();

        //навести мышку на элемент, нажать и удерживать, передвинуть на 400 вправо и 300 вниз, отпустить зажатие drag and drop
        actions().moveToElement($x("div")).clickAndHold().moveByOffset(400, 300).release().perform();

        // old html actions don't work with many modern frameworks
        $("").selectOption("dropdown_option");//сработает только если есть select, если селекта нет, то кликаем и в списке выбираем и кликаем
        $("").selectRadio("radio_options");

    }
    void assertions_examples() {
        $("").shouldBe(visible);
        $("").shouldNotBe(visible);
        $("").shouldHave(text("abc"));
        $("").shouldNotHave(text("abc"));
        $("").should(appear);
        $("").shouldNot(appear);

        //longer timeouts
        $("").shouldBe(visible, Duration.ofSeconds(30));

    }

    void conditions_examples() {
        $("").shouldBe(visible);
        $("").shouldBe(hidden);

        $("").shouldHave(text("abc"));
        $("").shouldHave(exactText("abc"));
        $("").shouldHave(textCaseSensitive("abc"));
        $("").shouldHave(exactTextCaseSensitive("abc"));
        $("").should(matchText("[0-9]abc$"));//регулярное выражение

        $("").shouldHave(cssClass("red"));
        $("").shouldHave(cssValue("font-size", "12"));

        $("").shouldHave(value("25"));
        $("").shouldHave(exactValue("25"));
        $("").shouldBe(empty);

        $("").shouldHave(attribute("disabled"));
        $("").shouldHave(attribute("name", "example"));
        $("").shouldHave(attributeMatching("name", "[0-9]abc$"));

        $("").shouldBe(checked); // for checkboxes
        $("").shouldNotBe(checked); //чек бокс не включен


        // Warning! Only checks if it is in DOM, not if it is visible! You don't need it in most tests!
        $("").should(exist);

        // Warning! Checks only the "disabled" attribute! Will not work with many modern frameworks
        $("").shouldBe(disabled);
        $("").shouldBe(enabled);
    }

    void collections_examples() {

        $$("div"); // does nothing!

        $$x("//div"); // by XPath

        // selections
        $$("div").filterBy(text("123")).shouldHave(size(1));
        $$("div").excludeWith(text("123")).shouldHave(size(1));

        $$("div").first().click();
        elements("div").first().click();
        // $("div").click();
        $$("div").last().click();
        $$("div").get(1).click(); // the second! (start with 0)
        $("div", 1).click(); // same as previous
        $$("div").findBy(text("123")).click(); //  finds first

        // assertions
        $$("").shouldHave(size(0));
        $$("").shouldBe(CollectionCondition.empty); // the same

        $$("").shouldHave(texts("Alfa", "Beta", "Gamma"));
        $$("").shouldHave(exactTexts("Alfa", "Beta", "Gamma"));

        $$("").shouldHave(textsInAnyOrder("Beta", "Gamma", "Alfa"));
        $$("").shouldHave(exactTextsCaseSensitiveInAnyOrder("Beta", "Gamma", "Alfa"));

        $$("").shouldHave(itemWithText("Gamma")); // only one text

        $$("").shouldHave(sizeGreaterThan(0));
        $$("").shouldHave(sizeGreaterThanOrEqual(1));
        $$("").shouldHave(sizeLessThan(3));
        $$("").shouldHave(sizeLessThanOrEqual(2));


    }

    void file_operation_examples() throws FileNotFoundException {

        File file1 = $("a.fileLink").download(); // only for <a href=".."> links
        File file2 = $("div").download(DownloadOptions.using(FileDownloadMode.FOLDER)); // more common options, but may have problems with Grid/Selenoid

        File file = new File("src/test/resources/readme.txt");
        $("#file-upload").uploadFile(file);
        $("#file-upload").uploadFromClasspath("readme.txt");
        // don't forget to submit!
        $("uploadButton").click();
    }

    void javascript_examples() {
        executeJavaScript("alert('selenide')");
        executeJavaScript("alert(arguments[0]+arguments[1])", "abc", 12);
        long fortytwo = executeJavaScript("return arguments[0]*arguments[1];", 6, 7);

    }
}






