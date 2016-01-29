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
  public void stylist_saveMethodWorking_save() {
    Stylist newStylist = new Stylist("Gloria");
    newStylist.save();
    assertTrue(newStylist instanceof Stylist);
  }

  @Test
  public void stylist_getNameMethodWorking_getName() {
    Stylist newStylist = new Stylist("Gloria");
    newStylist.save();
    assertEquals("Gloria", newStylist.getName());
  }

  @Test
  public void stylist_equalsMethodWorking_equals() {
    Stylist firstStylist = new Stylist("Gloria");
    Stylist secondStylist = new Stylist("Gloria");
    assertTrue(firstStylist.equals(secondStylist));
  }

  @Test
  public void stylist_successfullyUpdated_update() {
    Stylist newStylist = new Stylist("Gloria");
    newStylist.save();
    newStylist.update("Glow");
    assertEquals("Glow", newStylist.all().get(0).getName());
  }

  @Test
  public void stylist_successfullyDelete_delete() {
    Stylist newStylist = new Stylist("Gloria");
    newStylist.save();
    newStylist.delete();
    assertEquals(0, newStylist.all().size());
  }

  @Test
  public void stylist_successfullyFind_find() {
    Stylist newStylist = new Stylist("Gloria");
    newStylist.save();
    Stylist savedStylist = Stylist.find(newStylist.getId());
    assertTrue(newStylist.equals(savedStylist));
  }

  @Test
  public void stylist_confirmAllMethodWorking_all() {
    Stylist firstStylist = new Stylist("Gloria");
    firstStylist.save();
    Stylist secondStylist = new Stylist("Jim");
    secondStylist.save();
    Stylist [] allStylists = new Stylist [] {firstStylist, secondStylist};
    assertTrue(Stylist.all().containsAll(Arrays.asList(allStylists)));
  }

}
