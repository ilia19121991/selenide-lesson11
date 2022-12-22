package github;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.selector.ByTagAndText;
import com.codeborne.selenide.selector.ByText;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static java.lang.Thread.sleep;

public class SelenideContributors {

    @Test
    void solntsevShouldBeTheTopContributor(){

        // open selenide Repository
        open("https://github.com/selenide/selenide");
        // hover mouse over the first avatar in contributors block
        $("div.Layout-sidebar").$(byText("Contributors"))
                .ancestor("div").$$("ul li").first().hover();
        // check: the popup window has text "Andrei Solntsev"
        $$(".Popover-message").first().shouldHave(text("Andrei Solntsev"));  // findBy(visible) - находит первый видимый
        Selenide.sleep(5000);

    }
}
