import org.sql2o.*;
import java.util.List;

public class Clients {
  private int mId;
  private String mName;
  private int mStylistId;

  public int getId() {
    return mId;
  }

  public String getName() {
    return mName;
  }

  public int getStylistId() {
    return mStylistId;
  }

  public Clients (String name, int stylists_id) {
    this.mName = name;
    this.mStylistId = stylists_id;
  }

  @Override
  public boolean equals(Object otherClient) {
    if (!(otherClient instanceof Clients)) {
      return false;
    } else {
      Clients newClient = (Clients) otherClient;
      return this.getName().equals(newClient.getName()) &&
        this.getId() == newClient.getId();
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO clients(name, stylist_id) VALUES (:name, :stylistId)";
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("name", this.mName)
        .addParameter("stylistId", this.mStylistId)
        .executeUpdate()
        .getKey();
    }
  }


  public static List<Clients> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName, stylist_id AS mStylistId FROM clients";
      return con.createQuery(sql)
        .executeAndFetch(Clients.class);
    }
  }

}
