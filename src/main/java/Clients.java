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

}
