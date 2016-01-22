
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

}
