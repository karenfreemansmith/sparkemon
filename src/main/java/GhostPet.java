import org.sql2o.*;
import java.util.List;
import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;

public class GhostPet extends Pet implements DatabaseManagement {
  private int specialLevel;
  public static final int PET_TYPE=9;
  public static final int MAX_SPECIAL_LEVEL = 8;

  public GhostPet(String name, int playerId) {
    this.name = name;
    this.playerId = playerId;
    type=PET_TYPE;
    foodLevel=MAX_FOOD_LEVEL/2;
    sleepLevel=MAX_SLEEP_LEVEL/2;
    playLevel=MAX_PLAY_LEVEL/2;
    specialLevel=MAX_SPECIAL_LEVEL/2;
    timer = new Timer();
    save();
  }



  public int getSpecialLevel() {
    return specialLevel;
  }

  public void special() {
    if(specialLevel >= MAX_SPECIAL_LEVEL) {
      throw new UnsupportedOperationException ("Even special pets can get too much attention - back off!");
    }
    specialLevel++;
  }

  public static List<GhostPet> all() {
    String sql = "SELECT * FROM pets WHERE type=9";
    try(Connection cn = DB.sql2o.open()) {
      return cn.createQuery(sql).throwOnMappingFailure(false).executeAndFetch(GhostPet.class);
    }
  }

  public static GhostPet find(int id) {
    String sql = "SELECT * FROM pets WHERE id=:id";
    try(Connection cn = DB.sql2o.open()) {
      GhostPet pet = cn.createQuery(sql)
        .addParameter("id", id)
        .throwOnMappingFailure(false)
        .executeAndFetchFirst(GhostPet.class);
      return pet;
    }
  }

}
