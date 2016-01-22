import org.fluentlenium.adapter.FluentTest;
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Welcome to Salon Manager!");
  }

  @Test
  public void rootPageContainsStylistsList() {
    Stylists newStylist = new Stylists("Gloria");
    newStylist.save();
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Gloria");
  }

  @Test
  public void inputOnRootAddsStylist() {
    goTo("http://localhost:4567/");
    fill(".newStylistInput").with("Gloria");
    submit("#newStylist");
    assertThat(pageSource()).contains("Gloria");
  }

  @Test
  public void stylistLinkDispaysPageForStylist() {
    Stylists newStylist = new Stylists("Gloria");
    newStylist.save();
    goTo("http://localhost:4567/");
    click("a", withText("Gloria"));
    assertThat(pageSource()).contains("Gloria");
  }

  @Test
  public void newClientsAddToStylistPage() {
    Stylists newStylist = new Stylists("Gloria");
    newStylist.save();
    goTo("http://localhost:4567/newclient");
    fill("#newClientInput").with("Charlie");
    fillSelect("#stylistSelection").withText("Gloria");
    submit(".btn");
    goTo("http://localhost:4567/" + newStylist.getId());
    assertThat(pageSource()).contains("Charlie");
  }
}
