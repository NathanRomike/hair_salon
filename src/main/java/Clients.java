import org.sql2o.*;
import java.util.List;

public class Clients {
  private int mId;
  private String mName;
  private int mStylistId;

  public Clients (String name, int stylists_id) {
    this.mName = name;
    this.mStylistId = stylists_id;
  }

  public static List<Clients> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName, stylist_id AS mStylistId FROM clients";
      return con.createQuery(sql)
        .executeAndFetch(Clients.class);
    }
  }

}
