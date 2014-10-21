package unit.models;

import com.byteimagination.gitomater.models.Repository;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RepositoryTest {

  @Test
  public void repositoriesAreEqual() {
    Repository repository1 = new Repository();
    repository1.name = "1";
    List<String> privileges1_1 = new ArrayList<String>();
    privileges1_1.add("z");
    privileges1_1.add("x");
    privileges1_1.add("c");
    repository1.privileges.put("a", privileges1_1);
    List<String> privileges1_2 = new ArrayList<String>();
    privileges1_1.add("v");
    privileges1_1.add("b");
    privileges1_1.add("n");
    repository1.privileges.put("b", privileges1_2);

    Repository repository2 = new Repository();
    repository2.name = "1";
    List<String> privileges2_1 = new ArrayList<String>();
    privileges2_1.add("z");
    privileges2_1.add("x");
    privileges2_1.add("c");
    repository2.privileges.put("a", privileges2_1);
    List<String> privileges2_2 = new ArrayList<String>();
    privileges2_1.add("v");
    privileges2_1.add("b");
    privileges2_1.add("n");
    repository2.privileges.put("b", privileges2_2);

    assert repository1.equals(repository2);
  }

  @Test
  public void repositoriesAreEqualWhenPrivilegesAreEmpty() {
    Repository repository1 = new Repository();
    repository1.name = "1";

    Repository repository2 = new Repository();
    repository2.name = "1";

    assert repository1.equals(repository2);
  }

  @Test
  public void repositoriesDoesNotEqualWhenFirstPrivilegesAreEmpty() {
    Repository repository1 = new Repository();
    repository1.name = "1";

    Repository repository2 = new Repository();
    repository2.name = "1";
    List<String> privileges2_1 = new ArrayList<String>();
    privileges2_1.add("z");
    privileges2_1.add("x");
    privileges2_1.add("c");
    repository2.privileges.put("a", privileges2_1);
    List<String> privileges2_2 = new ArrayList<String>();
    privileges2_1.add("v");
    privileges2_1.add("b");
    privileges2_1.add("n");
    repository2.privileges.put("b", privileges2_2);

    assert !repository1.equals(repository2);
  }

  @Test
  public void repositoriesDoesNotEqualWhenSecondPrivilegesAreEmpty() {
    Repository repository1 = new Repository();
    repository1.name = "1";
    List<String> privileges1_1 = new ArrayList<String>();
    privileges1_1.add("z");
    privileges1_1.add("x");
    privileges1_1.add("c");
    repository1.privileges.put("a", privileges1_1);
    List<String> privileges1_2 = new ArrayList<String>();
    privileges1_1.add("v");
    privileges1_1.add("b");
    privileges1_1.add("n");
    repository1.privileges.put("b", privileges1_2);

    Repository repository2 = new Repository();
    repository2.name = "1";

    assert !repository1.equals(repository2);
  }

  @Test
  public void repositoriesDoesNotEqualWhenPrivilegesDiffer() {
    Repository repository1 = new Repository();
    repository1.name = "1";
    List<String> privileges1_1 = new ArrayList<String>();
    privileges1_1.add("z");
    privileges1_1.add("x");
    privileges1_1.add("c");
    repository1.privileges.put("a", privileges1_1);
    List<String> privileges1_2 = new ArrayList<String>();
    privileges1_1.add("v");
    privileges1_1.add("b");
    privileges1_1.add("n");
    repository1.privileges.put("b", privileges1_2);

    Repository repository2 = new Repository();
    repository2.name = "1";
    List<String> privileges2_1 = new ArrayList<String>();
    privileges2_1.add("z");
    privileges2_1.add("x");
    privileges2_1.add("c");
    repository2.privileges.put("a", privileges2_1);
    List<String> privileges2_2 = new ArrayList<String>();
    privileges2_1.add("v");
    privileges2_1.add("DIFFERS");
    privileges2_1.add("n");
    repository2.privileges.put("b", privileges2_2);

    assert !repository1.equals(repository2);
  }

  @Test
  public void repositoriesDoesNotEqualWhenNamesDiffer() {
    Repository repository1 = new Repository();
    repository1.name = "1";
    List<String> privileges1_1 = new ArrayList<String>();
    privileges1_1.add("z");
    privileges1_1.add("x");
    privileges1_1.add("c");
    repository1.privileges.put("a", privileges1_1);
    List<String> privileges1_2 = new ArrayList<String>();
    privileges1_1.add("v");
    privileges1_1.add("b");
    privileges1_1.add("n");
    repository1.privileges.put("b", privileges1_2);

    Repository repository2 = new Repository();
    repository2.name = "2";
    List<String> privileges2_1 = new ArrayList<String>();
    privileges2_1.add("z");
    privileges2_1.add("x");
    privileges2_1.add("c");
    repository2.privileges.put("a", privileges2_1);
    List<String> privileges2_2 = new ArrayList<String>();
    privileges2_1.add("v");
    privileges2_1.add("b");
    privileges2_1.add("n");
    repository2.privileges.put("b", privileges2_2);

    assert !repository1.equals(repository2);
  }

}
