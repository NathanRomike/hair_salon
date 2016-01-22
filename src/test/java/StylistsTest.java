import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class StylistsTest {

  // @Rule
  // public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
      assertEquals(Stylists.all().size(), 0);
  }

  @Test
  public void stylist_seccesfullyCreated() {
    Stylists newStylist = new Stylists("Gloria");
    newStylist.save();
    assertEquals("Gloria", newStylist.getName());
  }

}
