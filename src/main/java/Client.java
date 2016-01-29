import org.sql2o.*;
import java.util.List;

public class Client {
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
    return Stylist.find(mStylistId).getName();
  }

  public Client (String name, int stylistId) {
    this.mName = name;
    this.mStylistId = stylistId;
  }

  @Override
  public boolean equals(Object otherClient) {
    if (!(otherClient instanceof Client)) {
      return false;
    } else {
      Client newClient = (Client) otherClient;
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


  public static List<Client> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName, stylist_id AS mStylistId FROM clients";
      return con.createQuery(sql)
        .executeAndFetch(Client.class);
    }
  }

  public static Client find(int id) {
    String sql = "SELECT id AS mId, name AS mName, stylist_id as mStylistId FROM clients WHERE id = :id";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Client.class);
    }
  }

  public void update(String newName, int newStylistId) {
    this.mStylistId = newStylistId;
    this.mName = newName;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE clients SET stylist_id = :stylistId, name = :newName WHERE id = :id";
      con.createQuery(sql)
        .addParameter("stylistId", newStylistId)
        .addParameter("newName", newName)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public void update(int newStylistId) {
    this.mStylistId = newStylistId;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE clients SET stylist_id = :stylistId WHERE id = :id";
      con.createQuery(sql)
        .addParameter("stylistId", newStylistId)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public void update(String newName) {
    this.mName = newName;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE clients SET name = :name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", newName)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public static List<Client> getClientsByStylist(int stylistId) {
    String sql = "SELECT id AS mId, name AS mName, stylist_id AS mStylistId FROM clients WHERE stylist_id = :id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", stylistId)
        .executeAndFetch(Client.class);
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
