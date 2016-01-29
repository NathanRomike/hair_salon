
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
  public void client_getNameWorking_getName() {
    Stylist newStylist = new Stylist("Gloria");
    newStylist.save();
    Client newClient = new Client("Charlie", newStylist.getId());
    newClient.save();
    assertEquals("Charlie", newClient.getName());
  }

  @Test
  public void client_successfullyCreated_save() {
    Stylist newStylist = new Stylist("Gloria");
    newStylist.save();
    Client newClient = new Client("Charlie", newStylist.getId());
    newClient.save();
    assertTrue(newClient instanceof Client);
    assertEquals("Charlie", newClient.getName());
  }

  @Test
  public void client_equalMethodWorking_equals() {
    Stylist newStylist = new Stylist("Gloria");
    Client firstClient = new Client("Charlie", newStylist.getId());
    Client secondClient = new Client("Charlie", newStylist.getId());
    assertTrue(firstClient.equals(secondClient));
  }

  @Test
  public void client_successfullyFinds_find() {
    Stylist newStylist = new Stylist("Gloria");
    newStylist.save();
    Client newClient = new Client("Charlie", newStylist.getId());
    newClient.save();
    Client savedClient = Client.find(newClient.getId());
    assertTrue(newClient.equals(savedClient));
  }

  @Test
  public void client_getNameMethodWorking_getName() {
    Stylist newStylist = new Stylist("Gloria");
    newStylist.save();
    Client newClient = new Client("Charlie", newStylist.getId());
    newClient.save();
    assertEquals("Charlie", newClient.getName());
  }

  @Test
  public void client_updatesclient_update() {
    Stylist stylist = new Stylist("Gloria");
    stylist.save();
    Client client = new Client("Charlie", stylist.getId());
    client.save();
    client.update("Charles");
    assertEquals("Charles", client.getName());
  }

  @Test
  public void client_returnsStylistName_getStylistName() {
    Stylist newStylist = new Stylist("Gloria");
    newStylist.save();
    Client newClient = new Client("Charlie", newStylist.getId());
    newClient.save();
    assertEquals("Gloria", newClient.getStylistName());
  }

  @Test
  public void client_methodToGetClientsInStylistWorking_getClientsByStylist() {
    Stylist newStylist = new Stylist("Gloria");
    newStylist.save();
    Client newClient = new Client("Charlie", newStylist.getId());
    newClient.save();
    assertEquals("Charlie", Client.getClientsByStylist(newStylist.getId()).get(0).getName());
  }

  @Test
  public void client_confirmAllMethodWorking_all() {
    Stylist firstStylist = new Stylist("Gloria");
    firstStylist.save();
    Client firstClient = new Client("Charlie", firstStylist.getId());
    firstClient.save();
    Stylist secondStylist = new Stylist("Jim");
    secondStylist.save();
    Client secondClient = new Client("Jill", secondStylist.getId());
    secondClient.save();
    Client [] allClients = new Client [] {firstClient, secondClient};
    assertTrue(Client.all().containsAll(Arrays.asList(allClients)));
  }

  @Test
  public void client_successfullyDelete_delete() {
    Stylist newStylist = new Stylist("Gloria");
    newStylist.save();
    Client newClient = new Client("Charlie", newStylist.getId());
    newClient.save();
    newClient.delete();
    assertEquals(0, newClient.all().size());
  }
}
