package selenide;

import com.codeborne.selenide.*;
import com.codeborne.selenide.conditions.CssClass;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;

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
        open("https://www.google.com/");
        open("/customers/orders"); // -Dselenide.baseUrl=http://123.23.23.1
        open("/", AuthenticationType.BASIC,
                new BasicAuthCredentials("", "user", ""));

        Selenide.back();
        Selenide.forward();
        Selenide.refresh();

        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        executeJavaScript("sessionStorage().clear()"); // no Selenide command for this yet

        Selenide.confirm(); // ok в диалоговом окне
        Selenide.dismiss(); // cancel в диалоговом окне

        Selenide.closeWindow(); // close active tab - закрыть текущее окно - чтобы закрыть окно, нужно сначала на него перейти: Selenide.switchTo().window
        Selenide.closeWebDriver(); //close browser completely

        Selenide.switchTo().frame("имя, id или индекс фрейма"); // перейти во фрейм
        Selenide.switchTo().defaultContent(); // вернуться из фрейма в главный DOM

        Selenide.switchTo().window("имя,id или индекс окна");

        var cookie = new Cookie("foo", "bar");
        WebDriverRunner.getWebDriver().manage().addCookie(cookie); // добавить куки , после добавления кук нужно сделать refresh

    }

    void selectors_examples() {

        $("div").click();
        element("div").click();  // $ равно element, element - для kotlin

        $("div", 2).click(); // 3rd div

        $x("//h1/div").click();
        $(byXpath("//h1/div")).click(); // Xpath можно для ускорения поиска элемента

        $(byText("full text")).click();
        $(withText("ull text")).click();

        $(byTagAndText("div", "full text")).click();
        $(withTagAndText("div", "ull text")).click(); // текст, завернутый в элементе div

        $("").parent().click();
        $("").sibling(1).click(); // следующий брат-сестра вниз, (1) - значит, 2й вниз
        $("").preceding(5).click(); // следующий брат-сестра вверх, (5) - значит, 6й вверх
        $("").ancestor("h1").click();
        $("").closest("h1").click(); // the same as ancestor - ближайший вверх
        $("div:last-child").click(); // последний ребенок

        $("div").$("h1").find(byText("abc")).click();
        $(byAttribute("abc", "x")).click();
        $("[abc=x]").click();  // the same as byAttribute

        $(byId("21435")).click(); // если id начинается не с буквы

        $(byClassName("1432asfsz")).click(); // для нестандартных классов


    }

    void actions_examples() {

        $("").click();
        $("").doubleClick();
        $("").contextClick(); // клик правой кнопкой мыши

        $("").hover();

        $("").setValue("text"); // ввести значение (сотрет то, что было)
        $("").append("text"); // добавит к тому, что было
        $("").clear(); //
        $("").setValue(""); // то же, что clear , clear может не всегда работать

        $("div").sendKeys("c"); // нажать горячую клавишу C внутри элемента
        actions().sendKeys("c").perform(); // нажать горячую клавишу C на всем приложении
        actions().sendKeys(Keys.chord(Keys.CONTROL, "f")).perform(); // Ctrl + F
        $("html").sendKeys(Keys.chord(Keys.CONTROL, "f")); // the same as previous

        $("").pressEnter();
        $("").pressEscape();
        $("").pressTab();

        actions().moveToElement($("div")).clickAndHold().moveByOffset(300, 400).release().perform();

        $("").selectOption("dropdown_option");
        $("").selectRadio("radio_option");

    }

    void assertions_examples() { // в assertions вшито неявное ожидание: поиск - ожидание - поиск (и так пока не найдет, либо не пройдет timeout 4 секунды)

        $("").shouldBe(visible);
        $("").shouldNotBe(visible);
        $("").shouldHave(text("abc"));
        $("").shouldNotHave(text("abc"));
        $("").should(appear);
        $("").shouldNot(appear); // если сайт медленный, увеличенный timeout лучше проставлять в jenkins
        // вообще should, shouldHave и  shouldNotHave - это одно и то же, просто для грамотного английского

        $("").shouldBe(visible, Duration.ofSeconds(30)); // если, скорее всего придется долго ждать

        $$("").findBy(visible).click(); // если 1 элемент, а в DOM он, как 2 элемента: 1 видимый, 1 невидимый

    }

    void conditions_examples() {

        $("").shouldBe(visible);
        $("").shouldBe(hidden);

        $("").shouldHave(text("abc"));
        $("").shouldHave(exactText("abc"));
        $("").shouldHave(textCaseSensitive("abc"));
        $("").shouldHave(exactTextCaseSensitive("abc"));

        $("").should(matchText("[0-9]abc$")); //для сложных проверок: начинается с цифры, потом какие-то буквы

        $("").shouldHave(cssClass("red")); // есть ли у элемента такой-то класс
        $("").shouldHave(cssValue("font-size", "12")); // в консоли разработчика справа в Computed

        $("").shouldHave(value("abc")); // то же самое, что shouldHave(text()) , но для инпутов
        $("").shouldHave(exactValue("abc"));
        $("").shouldBe(empty);

        $("").shouldHave(attribute("disabled")); //что есть какой-то аттрибут (класс, id, disabled и тд)
        $("").shouldHave(attribute("name", "example")); //что есть какой-то аттрибут и значение
        $("").shouldHave(attributeMatching("name", "[0-9]abc$"));

        $("").shouldBe(checked); // что чек-бокс включен
        $("").shouldNotBe(checked); // что чек-бокс не включен

        $("").should(exist); // находится ли элемент в DOM, он может быть и видимым, и невидимым

        $("").shouldBe(disabled); // что элемент disabled (прям в DOM так и называется, как это у нас было, например, с выбором пары в tradesanta
        $("").shouldBe(enabled); // но часто поле дисэблится через javaScript, и проверка не сработает

    }

    void collections_examples() {

        $$("div"); //несколько элементов, которые обладают одним и тем же селектором
        $$x("//div");

        //selections

        $$("div").filterBy(text("123")).shouldHave(size(1));
        $$("div").excludeWith(text("123")).shouldHave(size(1)); // excludeWith противополжно filterBy

        $$("div").first().click();
        elements("div").first().click();

        $$("div").last().click();
        $$("div").get(15).click(); // start with 0
        $("div", 1).click(); // the same as previous
        $$("div").findBy(text("abc17")).click(); // равно filterBy() + first()

        $$("").shouldHave(size(0));
        $$("").shouldBe(CollectionCondition.empty); // the same as previous

        $$("").shouldHave(texts("Alpha", "Beta", "Gamma")); // количество и порядок текстов должен совпадать
        $$("").shouldHave(exactTexts("Alpha", "Beta", "Gamma")); // количество и порядок текстов должен совпадать

        $$("").shouldHave(textsInAnyOrder("Beta", "Alpha", "Gamma")); // в любом порядке
        $$("").shouldHave(exactTextsCaseSensitiveInAnyOrder("Beta", "Alpha", "Gamma"));

        $$("").shouldHave(itemWithText("Beta")); // что появилась, например, одна книга, которую мы только что добавили

        //размер коллеции (кол-во элементов)
        $$("").shouldHave(sizeGreaterThan(7));
        $$("").shouldHave(sizeGreaterThanOrEqual(7));
        $$("").shouldHave(sizeLessThan(7));
        $$("").shouldHave(sizeLessThanOrEqual(7));

    }

    void file_operation_examples() throws FileNotFoundException {

        File file1 = $("a.filelink").download(); // only for <a href=".."> links
        File file2 = $("div").download(DownloadOptions.using(FileDownloadMode.FOLDER)); // more common options, but may have problems with Grid/Selenoid

        File file = new File("src/test/resources/readme.txt");
        $("#file-upload").uploadFile(file);
        $("#file-upload").uploadFromClasspath("readme.txt");
        // не забывать нажимать на кнопку
        $("upload-button").click();

    }

    void javascript_examples() {

        executeJavaScript("alert('selenide')");
        executeJavaScript("alert(arguments[0]+arguments[1])", 123,59);
        long fortytwo = executeJavaScript("return arguments[0]*arguments[1];", 6, 7);


        /** если есть анимация, и нужно подождать, пока она пройдет, потому что двигаются элементы, пока она идет,
         * например, это можно сделать, посмотрев, что какой-то параметр после завершения анимации меняет значение
         */
        $("career").click();
        $("button.selector").shouldHave(attribute("aria-expended", "true"));
        $("job").click();

    }

}


