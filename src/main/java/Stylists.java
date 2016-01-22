import org.sql2o.*;
import java.util.List;

public class Stylists {
  private int mId;
  private String mName;

  public Stylists (String name) {
    this.mName = name;
  }

  public static List<Stylists> all() {
    String sql = "SELECT id AS mId, name AS mName FROM stylists";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Stylists.class);
    }
  }
}
