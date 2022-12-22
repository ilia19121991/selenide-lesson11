package github;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.selector.ByText;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class SelenideWiki {

    @Test
    void shouldFindJunit5SampleCode(){
        open("https://github.com/");
        $("[data-test-selector=nav-search-input]").setValue("selenide").pressEnter();
        $$(".menu a").findBy(text("Wikis")).click();
        $("#wiki_search_results").$("[title=SoftAssertions]").click();

        $$(".highlight-source-java").findBy(text("@ExtendWith")).shouldHave(text("@ExtendWith"));
        $$(".highlight-source-java").findBy(text("@RegisterExtension")).shouldHave(text("@RegisterExtension"));

    }
}
