package unit.helpers;

import com.byteimagination.gitomater.helpers.RepositoryCLIParametersParser;
import com.byteimagination.gitomater.models.Repository;
import org.junit.Test;

public class RepositoryCLIParametersParserTest {

  @Test
  public void parsesRepositoryParametersWithoutPrivileges() {
    Repository repository = RepositoryCLIParametersParser.parse("ab/Cd");
    assert repository.name.equals("ab/Cd");
    assert repository.privileges.isEmpty();
  }

  @Test
  public void parsesRepositoryParametersWithPrivileges() {
    Repository repository = RepositoryCLIParametersParser.parse("ab/Cd.git", "R=e f, RW+=g h, W=");
    assert repository.name.equals("ab/Cd");
    assert repository.privileges.size() == 2;
    assert repository.privileges.get("R").get(0).equals("e");
    assert repository.privileges.get("R").get(1).equals("f");
    assert repository.privileges.get("RW+").get(0).equals("g");
    assert repository.privileges.get("RW+").get(1).equals("h");
    assert repository.privileges.get("W") == null;
  }

}
