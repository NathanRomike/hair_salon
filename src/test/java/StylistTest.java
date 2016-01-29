import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class StylistTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
      assertEquals(Stylist.all().size(), 0);
  }

  @Test
  public void stylist_successfullyCreated() {
    Stylist newStylist = new Stylist("Gloria");
    newStylist.save();
    assertEquals("Gloria", newStylist.getName());
  }

  @Test
  public void stylist_successfullyUpdated() {
    Stylist newStylist = new Stylist("Gloria");
    newStylist.save();
    newStylist.update("Glow");
    assertEquals("Glow", newStylist.all().get(0).getName());
  }

  @Test
  public void stylist_successfullyDelete() {
    Stylist newStylist = new Stylist("Gloria");
    newStylist.save();
    newStylist.delete();
    assertEquals(0, newStylist.all().size());
  }

  @Test
  public void stylist_successfullyFind() {
    Stylist newStylist = new Stylist("Gloria");
    newStylist.save();
    Stylist savedStylist = Stylist.find(newStylist.getId());
    assertTrue(newStylist.equals(savedStylist));
  }

}
