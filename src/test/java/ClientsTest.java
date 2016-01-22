
import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.*;

public class ClientsTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Clients.all().size(), 0);
  }

  @Test
  public void clients_successfullyCreated() {
    Stylists newStylist = new Stylists("Gloria");
    newStylist.save();
    Clients newClient = new Clients("Charlie", newStylist.getId());
    newClient.save();
    assertTrue(newClient instanceof Clients);
    assertEquals("Charlie", newClient.getName());
  }

  @Test
  public void clients_successfullyFinds() {
    Stylists newStylist = new Stylists("Gloria");
    newStylist.save();
    Clients newClient = new Clients("Charlie", newStylist.getId());
    newClient.save();
    Clients savedClient = Clients.find(newClient.getId());
    assertTrue(newClient.equals(savedClient));
  }

  @Test
  public void clients_successfullyUpdatesClientsAndOrStylists() {
    Stylists firstStylist = new Stylists("Gloria");
    firstStylist.save();
    Stylists secondStylist = new Stylists("Christina");
    secondStylist.save();
    Clients firstClient = new Clients("Charlie", firstStylist.getId());
    firstClient.save();
    Clients secondClient = new Clients("Charlie", firstStylist.getId());
    secondClient.save();
    Clients thirdClient = new Clients("Charlie", firstStylist.getId());
    thirdClient.save();
    firstClient.update("Charles");
    secondClient.update(secondStylist.getId());
    thirdClient.update("Chuck", secondStylist.getId());
    assertEquals("Charles", firstClient.getName());
    assertEquals("Christina", Stylists.find(secondClient.getStylistId()).getName());
    assertEquals("Chuck", thirdClient.getName());
    assertEquals("Christina", Stylists.find(thirdClient.getStylistId()).getName());
  }
}
