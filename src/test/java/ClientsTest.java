
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

}
