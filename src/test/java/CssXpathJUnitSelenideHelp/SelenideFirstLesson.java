package CssXpathJUnitSelenideHelp;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class SelenideFirstLesson {
    @Test
    public void checkSearchFieldGitHub() {
        open("https://github.com/");
        $(".header-search-button").click();
        $("#query-builder-test").setValue("Selenide").pressEnter();
        $x("//a[contains(@href, '/selenide/selenide') and contains(.//span[@class='Text-sc-17v1xeu-0 qaOIC search-match'], 'selenide/selenide')]").shouldHave(text("selenide/selenide"));

    }
    @Test
    public void checkContributorsPopover(){
        open("https://github.com/selenide/selenide");
        $x("//div[contains(@class, 'BorderGrid-cell')]//a[contains(text(), 'Contributors')]").ancestor(".BorderGrid-row").$$("ul li").first().hover();
        sleep(4000);
    }
}
