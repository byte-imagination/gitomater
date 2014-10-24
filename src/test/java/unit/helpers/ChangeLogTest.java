package unit.helpers;

import com.byteimagination.gitomater.helpers.ChangeLog;
import com.byteimagination.gitomater.models.Repository;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ChangeLogTest {

  @Test
  public void appendsNewRepositoryToChangeLog() {
    ChangeLog changeLog = new ChangeLog();

    Repository repository1 = new Repository();
    repository1.name = "1";
    List<String> privileges1_1 = new ArrayList<String>();
    privileges1_1.add("z");
    privileges1_1.add("x");
    repository1.privileges.put("a", privileges1_1);
    List<String> privileges1_2 = new ArrayList<String>();
    privileges1_2.add("v");
    repository1.privileges.put("b", privileges1_2);

    Repository repository2 = new Repository();
    repository2.name = "2";
    List<String> privileges2_1 = new ArrayList<String>();
    privileges2_1.add("z");
    repository2.privileges.put("a", privileges2_1);
    List<String> privileges2_2 = new ArrayList<String>();
    privileges2_2.add("v");
    repository2.privileges.put("b", privileges2_2);

    Repository repository3 = new Repository();
    repository3.name = "3";

    changeLog.appendRepository(repository1);
    changeLog.appendRepository(repository2);
    changeLog.appendRepository(repository3);

    String changeLogString = changeLog.getChangeLog();
    assert changeLogString.equals(
      "+repo 1.git\n" +
      "+a z@1.git\n" +
      "+a x@1.git\n" +
      "+b v@1.git\n" +
      "+repo 2.git\n" +
      "+a z@2.git\n" +
      "+b v@2.git\n" +
      "+repo 3.git"
    ) : changeLogString;
  }

}