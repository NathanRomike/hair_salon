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

  public String getStylistName() {
    return Stylists.find(mStylistId).getName();
  }

  public Clients (String name, int stylistId) {
    this.mName = name;
    this.mStylistId = stylistId;
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

  public static Clients find(int id) {
    String sql = "SELECT id AS mId, name AS mName, stylist_id as mStylistId FROM clients WHERE id = :id";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Clients.class);
    }
  }

  public void update(String newName, int newStylistId) {
    this.mStylistId = newStylistId;
    this.mName = newName;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE clients SET stylist_id = :stylistId, name = :newName";
      con.createQuery(sql)
        .addParameter("stylistId", newStylistId)
        .addParameter("newName", newName)
        .executeUpdate();
    }
  }

  public void update(int newStylistId) {
    this.mStylistId = newStylistId;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE clients SET stylist_id = :stylistId";
      con.createQuery(sql)
        .addParameter("stylistId", newStylistId)
        .executeUpdate();
    }
  }

  public void update(String newName) {
    this.mName = newName;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE clients SET name = :name";
      con.createQuery(sql)
        .addParameter("name", newName)
        .executeUpdate();
    }
  }

  public static List<Clients> getClientsByStylist(int stylistId) {
    String sql = "SELECT id AS mId, name AS mName, stylist_id AS mStylistId FROM clients WHERE stylist_id = :id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", stylistId)
        .executeAndFetch(Clients.class);
    }
  }

  public void delete() {
    String sql = "DELETE FROM clients WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }
}
