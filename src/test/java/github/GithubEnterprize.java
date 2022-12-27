package github;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class GithubEnterprize {
    @Test
    void githubEnterprizeTest() {
        open("https://github.com/");
        $$(".HeaderMenu-link").findBy(text("Solutions")).hover();
        $$(".HeaderMenu-dropdown-link").findBy(text("Enterprise")).click();
        sleep(5000);
        $(".application-main").shouldHave(text("GitHub for enterprises"));

    }

    @Test
    void dragAndDrop(){

        open("https://the-internet.herokuapp.com/drag_and_drop");

        $("#column-a").dragAndDropTo($("#column-b"));
        $("#column-b header").shouldHave(text("A"));
        $("#column-a header").shouldHave(text("B"));

        /** так можно находить координаты элемента:
        Point location = $("#column-b").getLocation();
        System.out.println(location);
         */



    }

}
