import org.sql2o.*;
import java.util.List;

public class Stylists {
  private int mId;
  private String mName;

  public Stylists (String name) {
    this.mName = name;
  }

  public String getName() {
    return mName;
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stylists(name) VALUES (:name)";
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("name", this.mName)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Stylists> all() {
    String sql = "SELECT id AS mId, name AS mName FROM stylists";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Stylists.class);
    }
  }

  public void update(String newName) {
    this.mName = newName;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE stylists SET name = :name";
      con.createQuery(sql)
        .addParameter("name", newName)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM stylists WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }
}
