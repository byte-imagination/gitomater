package unit.helpers;

import com.byteimagination.gitomater.helpers.RepositoryCLIParametersParser;
import com.byteimagination.gitomater.models.Repository;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class RepositoryCLIParametersParserTest {

  @Test
  public void parsesRepositoryParametersWithoutPrivileges() {
    Repository repository = RepositoryCLIParametersParser.parse("ab/Cd");
    assert repository.name.equals("ab/Cd");
    assert repository.privileges.isEmpty();
  }

  @Test
  public void parsesRepositoryParametersWithPrivileges() {
    Repository repository = RepositoryCLIParametersParser.parseRepository("ab/Cd.git", "R=e f, RW+=g h, W=");
    assert repository.name.equals("ab/Cd");
    assert repository.privileges.size() == 2;
    assert repository.privileges.get("R").get(0).equals("e");
    assert repository.privileges.get("R").get(1).equals("f");
    assert repository.privileges.get("RW+").get(0).equals("g");
    assert repository.privileges.get("RW+").get(1).equals("h");
    assert repository.privileges.get("W") == null;
  }

  @Test
  public void parsesPrivilegesString() {
    Map<String, List<String>> privileges = RepositoryCLIParametersParser.parsePrivileges("R=e f, RW+=g h, W=");
    assert privileges.size() == 2;
    assert privileges.get("R").get(0).equals("e");
    assert privileges.get("R").get(1).equals("f");
    assert privileges.get("RW+").get(0).equals("g");
    assert privileges.get("RW+").get(1).equals("h");
    assert privileges.get("W") == null;
  }

}
