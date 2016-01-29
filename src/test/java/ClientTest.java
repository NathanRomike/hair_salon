
import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.*;

public class ClientTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Client.all().size(), 0);
  }

  @Test
  public void clients_successfullyCreated() {
    Stylist newStylist = new Stylist("Gloria");
    newStylist.save();
    Client newClient = new Client("Charlie", newStylist.getId());
    newClient.save();
    assertTrue(newClient instanceof Client);
    assertEquals("Charlie", newClient.getName());
  }

  @Test
  public void clients_successfullyFinds() {
    Stylist newStylist = new Stylist("Gloria");
    newStylist.save();
    Client newClient = new Client("Charlie", newStylist.getId());
    newClient.save();
    Client savedClient = Client.find(newClient.getId());
    assertTrue(newClient.equals(savedClient));
  }

  @Test
  public void clients_successfullyUpdatesClientsAndOrStylists() {
    Stylist firstStylist = new Stylist("Gloria");
    firstStylist.save();
    Stylist secondStylist = new Stylist("Christina");
    secondStylist.save();
    Client firstClient = new Client("Charlie", firstStylist.getId());
    firstClient.save();
    Client secondClient = new Client("Charlie", firstStylist.getId());
    secondClient.save();
    Client thirdClient = new Client("Charlie", firstStylist.getId());
    thirdClient.save();
    firstClient.update("Charles");
    secondClient.update(secondStylist.getId());
    thirdClient.update("Chuck", secondStylist.getId());
    assertEquals("Charles", firstClient.getName());
    assertEquals("Christina", Stylist.find(secondClient.getStylistId()).getName());
    assertEquals("Chuck", thirdClient.getName());
    assertEquals("Christina", Stylist.find(thirdClient.getStylistId()).getName());
  }

  @Test
  public void clients_returnsStylistName() {
    Stylist newStylist = new Stylist("Gloria");
    newStylist.save();
    Client newClient = new Client("Charlie", newStylist.getId());
    newClient.save();
    assertEquals("Gloria", newClient.getStylistName());
  }

  @Test
  public void clients_getClientsByStylistId() {
    Stylist newStylist = new Stylist("Gloria");
    newStylist.save();
    Client newClient = new Client("Charlie", newStylist.getId());
    newClient.save();
    assertEquals("Charlie", Client.getClientsByStylist(newStylist.getId()).get(0).getName());
  }
}
