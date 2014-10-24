package unit;

import com.byteimagination.gitomater.Gitomater;
import com.byteimagination.gitomater.exceptions.RepositoryAlreadyExistsException;
import com.byteimagination.gitomater.helpers.CommandExecutor;
import com.byteimagination.gitomater.models.Repository;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GitomaterTest {

  public static final String TMP_GITOMATER_TEST_GITOLITE_CONF_PATH = "tmp/gitomater_test";
  private Gitomater gitomater;

  @Before
  public void initializeGitomater() {
    File file = new File(TMP_GITOMATER_TEST_GITOLITE_CONF_PATH + "/conf/gitolite.conf");
    if (file.exists())
      file.delete();
    gitomater = new Gitomater(TMP_GITOMATER_TEST_GITOLITE_CONF_PATH, new DummyCommandExecutor());
  }

  @Test
  public void addsRepository() {
    Repository repository1 = new Repository();
    repository1.name = "1";
    List<String> privileges1_1 = new ArrayList<String>();
    privileges1_1.add("z");
    privileges1_1.add("x");
    privileges1_1.add("c");
    repository1.privileges.put("a", privileges1_1);
    List<String> privileges1_2 = new ArrayList<String>();
    privileges1_2.add("v");
    privileges1_2.add("b");
    privileges1_2.add("n");
    repository1.privileges.put("b", privileges1_2);

    Repository repository2 = new Repository();
    repository2.name = "2";
    List<String> privileges2_1 = new ArrayList<String>();
    privileges2_1.add("z");
    privileges2_1.add("x");
    privileges2_1.add("c");
    repository2.privileges.put("a", privileges2_1);
    List<String> privileges2_2 = new ArrayList<String>();
    privileges2_2.add("v");
    privileges2_2.add("b");
    privileges2_2.add("n");
    repository2.privileges.put("b", privileges2_2);

    gitomater.load();
    assert gitomater.getRepositories().size() == 0;
    gitomater.addRepository(repository1);
    gitomater.addRepository(repository2);
    gitomater.save();

    Gitomater gitomater = new Gitomater(TMP_GITOMATER_TEST_GITOLITE_CONF_PATH, new DummyCommandExecutor());
    gitomater.load();
    List<Repository> repositories = gitomater.getRepositories();
    assert repositories.size() == 2;
    assert repositories.get(0).equals(repository1);
    assert repositories.get(1).equals(repository2);

    Repository repository3 = new Repository();
    repository3.name = "3";
    List<String> privileges3_1 = new ArrayList<String>();
    privileges3_1.add("z");
    privileges3_1.add("x");
    privileges3_1.add("c");
    repository3.privileges.put("a", privileges3_1);
    List<String> privileges3_2 = new ArrayList<String>();
    privileges3_2.add("v");
    privileges3_2.add("b");
    privileges3_2.add("n");
    repository3.privileges.put("b", privileges3_2);
    gitomater.addRepository(repository3);
    gitomater.save();

    gitomater = new Gitomater(TMP_GITOMATER_TEST_GITOLITE_CONF_PATH, new DummyCommandExecutor());
    gitomater.load();
    repositories = gitomater.getRepositories();
    assert repositories.size() == 3;
    assert repositories.get(0).equals(repository1);
    assert repositories.get(1).equals(repository2);
    assert repositories.get(2).equals(repository3);
  }

  @Test(expected = RepositoryAlreadyExistsException.class)
  public void throwsRepositoryAlreadyExistsException() {
    Repository repository1 = new Repository();
    repository1.name = "1";
    gitomater.load();
    gitomater.addRepository(repository1);
    gitomater.addRepository(repository1);
  }

  private static class DummyCommandExecutor extends CommandExecutor {

    @Override
    public void execute(List<String> arguments) {
    }

  }

}
