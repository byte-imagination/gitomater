package unit.helpers;

import com.byteimagination.gitomater.helpers.Deserializer;
import com.byteimagination.gitomater.models.Repository;
import org.junit.Test;

import java.util.List;

public class DeserializerTest {

  @Test
  public void parsesTypicalConfiguration() {
    Deserializer deserializer = new Deserializer();
    List<Repository> repositories = deserializer.parse("src/test/resources/gitolite.conf");
    assert repositories.size() == 6;
    assert repositories.get(0).name.equals("android/AndroidLib");
    assert repositories.get(0).privileges.containsKey("RW+");
    assert repositories.get(0).privileges.get("RW+").size() == 1;
    assert repositories.get(0).privileges.get("RW+").contains("roger");
    assert repositories.get(1).name.equals("android/ForestGame");
    assert repositories.get(1).privileges.containsKey("RW+");
    assert repositories.get(1).privileges.get("RW+").size() == 1;
    assert repositories.get(1).privileges.get("RW+").contains("roger");
    assert repositories.get(1).privileges.containsKey("RW");
    assert repositories.get(1).privileges.get("RW").size() == 2;
    assert repositories.get(1).privileges.get("RW").contains("dtabisz");
    assert repositories.get(1).privileges.get("RW").contains("mtelesz");
    assert repositories.get(2).name.equals("android/LiceumBedzin");
    assert repositories.get(2).privileges.containsKey("RW+");
    assert repositories.get(2).privileges.get("RW+").size() == 1;
    assert repositories.get(2).privileges.get("RW+").contains("roger");
    assert repositories.get(2).privileges.containsKey("RW");
    assert repositories.get(2).privileges.get("RW").size() == 1;
    assert repositories.get(2).privileges.get("RW").contains("dtabisz");
    assert repositories.get(2).privileges.containsKey("R");
    assert repositories.get(2).privileges.get("R").size() == 1;
    assert repositories.get(2).privileges.get("R").contains("mtelesz");
    assert repositories.get(3).name.equals("gitolite-admin");
    assert repositories.get(3).privileges.containsKey("RW+");
    assert repositories.get(3).privileges.get("RW+").size() == 1;
    assert repositories.get(3).privileges.get("RW+").contains("roger");
    assert repositories.get(4).name.equals("gitomater");
    assert repositories.get(4).privileges.containsKey("RW+");
    assert repositories.get(4).privileges.get("RW+").size() == 1;
    assert repositories.get(4).privileges.get("RW+").contains("roger");
    assert repositories.get(5).name.equals("mobile/Krasnik");
    assert repositories.get(5).privileges.containsKey("RW+");
    assert repositories.get(5).privileges.get("RW+").size() == 1;
    assert repositories.get(5).privileges.get("RW+").contains("roger");
  }

  @Test
  public void parsesSingleEntryConfiguration() {
    Deserializer deserializer = new Deserializer();
    List<Repository> repositories = deserializer.parse("src/test/resources/gitolite2.conf");
    assert repositories.size() == 1;
    assert repositories.get(0).name.equals("gitolite-admin");
    assert repositories.get(0).privileges.containsKey("RW+");
    assert repositories.get(0).privileges.get("RW+").size() == 1;
    assert repositories.get(0).privileges.get("RW+").contains("roger");
  }

  @Test
  public void parsesEmptyConfiguration() {
    Deserializer deserializer = new Deserializer();
    List<Repository> repositories = deserializer.parse("src/test/resources/gitolite3.conf");
    assert repositories.size() == 0;
  }

}
