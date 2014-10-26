package unit;

import com.byteimagination.gitomater.Gitomater;
import com.byteimagination.gitomater.exceptions.RepositoryAlreadyExistsException;
import com.byteimagination.gitomater.helpers.CommandExecutor;
import com.byteimagination.gitomater.helpers.RepositoryCLIParametersParser;
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

    assert gitomater.getChangeLog().getChangeLog().contains("+repo 1.git");
    assert gitomater.getChangeLog().getChangeLog().contains("+repo 2.git");
    assert gitomater.getChangeLog().getChangeLog().contains("+b v@2.git");
    assert gitomater.getRepository("1").equals(repository1);

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

  @Test
  public void addsPrivilegesToExistingRepository() {
    Repository repository10 = new Repository();
    repository10.name = "10";
    List<String> privileges10_1 = new ArrayList<String>();
    privileges10_1.add("z");
    privileges10_1.add("x");
    privileges10_1.add("c");
    repository10.privileges.put("a", privileges10_1);
    List<String> privileges10_2 = new ArrayList<String>();
    privileges10_2.add("v");
    privileges10_2.add("b");
    privileges10_2.add("n");
    repository10.privileges.put("b", privileges10_2);

    gitomater.load();
    assert gitomater.getRepositories().size() == 0;
    gitomater.addRepository(repository10);
    gitomater.save();

    gitomater = new Gitomater(TMP_GITOMATER_TEST_GITOLITE_CONF_PATH, new DummyCommandExecutor());
    gitomater.load();
    gitomater.addPrivileges("10", RepositoryCLIParametersParser.parsePrivileges("R=e f, RW+=g h, W=, b=e v"));
    gitomater.save();

    assert gitomater.getChangeLog().getChangeLog().contains("+R e@10.git");
    assert gitomater.getChangeLog().getChangeLog().contains("+R f@10.git");
    assert gitomater.getChangeLog().getChangeLog().contains("+RW+ g@10.git");
    assert gitomater.getChangeLog().getChangeLog().contains("+RW+ h@10.git");
    assert gitomater.getChangeLog().getChangeLog().contains("+b e@10.git");
    assert !gitomater.getChangeLog().getChangeLog().contains("+b v@10.git");

    gitomater = new Gitomater(TMP_GITOMATER_TEST_GITOLITE_CONF_PATH, new DummyCommandExecutor());
    gitomater.load();
    Repository repository = gitomater.getRepository("10");
    assert repository.privileges.size() == 4;
    assert repository.privileges.get("R").size() == 2;
    assert repository.privileges.get("R").contains("e");
    assert repository.privileges.get("R").contains("f");
    assert repository.privileges.get("RW+").size() == 2;
    assert repository.privileges.get("RW+").contains("g");
    assert repository.privileges.get("RW+").contains("h");
    assert repository.privileges.get("W") == null;
    assert repository.privileges.get("a").size() == 3;
    assert repository.privileges.get("a").contains("z");
    assert repository.privileges.get("a").contains("x");
    assert repository.privileges.get("a").contains("c");
    assert repository.privileges.get("b").size() == 4;
    assert repository.privileges.get("b").contains("v");
    assert repository.privileges.get("b").contains("b");
    assert repository.privileges.get("b").contains("n");
    assert repository.privileges.get("b").contains("e");
  }

  @Test
  public void takesPrivilegesToExistingRepository() {
    Repository repository11 = new Repository();
    repository11.name = "11";
    List<String> privileges11_1 = new ArrayList<String>();
    privileges11_1.add("z");
    privileges11_1.add("x");
    privileges11_1.add("c");
    repository11.privileges.put("a", privileges11_1);
    List<String> privileges11_2 = new ArrayList<String>();
    privileges11_2.add("v");
    privileges11_2.add("b");
    privileges11_2.add("n");
    repository11.privileges.put("b", privileges11_2);

    gitomater.load();
    assert gitomater.getRepositories().size() == 0;
    gitomater.addRepository(repository11);
    gitomater.save();

    gitomater = new Gitomater(TMP_GITOMATER_TEST_GITOLITE_CONF_PATH, new DummyCommandExecutor());
    gitomater.load();
    gitomater.takePrivileges("11", RepositoryCLIParametersParser.parsePrivileges("R=e f, RW+=g h, W=, b=e v"));
    gitomater.save();

    assert !gitomater.getChangeLog().getChangeLog().contains("-R e@11.git");
    assert !gitomater.getChangeLog().getChangeLog().contains("-R f@11.git");
    assert !gitomater.getChangeLog().getChangeLog().contains("-b e@11.git");
    assert gitomater.getChangeLog().getChangeLog().contains("-b v@11.git");

    gitomater = new Gitomater(TMP_GITOMATER_TEST_GITOLITE_CONF_PATH, new DummyCommandExecutor());
    gitomater.load();
    Repository repository = gitomater.getRepository("11");
    assert repository.privileges.size() == 2;
    assert repository.privileges.get("R") == null;
    assert repository.privileges.get("W") == null;
    assert repository.privileges.get("a").size() == 3;
    assert repository.privileges.get("a").contains("z");
    assert repository.privileges.get("a").contains("x");
    assert repository.privileges.get("a").contains("c");
    assert repository.privileges.get("b").size() == 2;
    assert repository.privileges.get("b").contains("b");
    assert repository.privileges.get("b").contains("n");
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
