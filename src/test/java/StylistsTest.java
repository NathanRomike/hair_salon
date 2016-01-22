import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class StylistsTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
      assertEquals(Stylists.all().size(), 0);
  }

  @Test
  public void stylist_successfullyCreated() {
    Stylists newStylist = new Stylists("Gloria");
    newStylist.save();
    assertEquals("Gloria", newStylist.getName());
  }

  @Test
  public void stylist_successfullyUpdated() {
    Stylists newStylist = new Stylists("Gloria");
    newStylist.save();
    newStylist.update("Glow");
    assertEquals("Glow", newStylist.all().get(0).getName());
  }

  @Test
  public void stylist_successfullyDelete() {
    Stylists newStylist = new Stylists("Gloria");
    newStylist.save();
    newStylist.delete();
    assertEquals(0, newStylist.all().size());
  }

}
