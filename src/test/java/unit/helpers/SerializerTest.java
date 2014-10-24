package unit.helpers;

import com.byteimagination.gitomater.helpers.Deserializer;
import com.byteimagination.gitomater.helpers.Serializer;
import com.byteimagination.gitomater.models.Repository;
import org.junit.Test;

import java.util.List;

public class SerializerTest {

  @Test
  public void savesTypicalConfiguration() {
    List<Repository> repositories = new Deserializer("src/test/resources/gitolite.conf").parse();
    new Serializer(repositories, "tmp/serializer_test/gitolite.conf").write();
    List<Repository> repositoriesToCheck = new Deserializer("tmp/serializer_test/gitolite.conf").parse();
    assertRepositoriesCollectionsMatch(repositories, repositoriesToCheck);
  }

  @Test
  public void savesSingleEntryConfiguration() {
    List<Repository> repositories = new Deserializer("src/test/resources/gitolite2.conf").parse();
    new Serializer(repositories, "tmp/serializer_test/gitolite2.conf").write();
    List<Repository> repositoriesToCheck = new Deserializer("tmp/serializer_test/gitolite2.conf").parse();
    assertRepositoriesCollectionsMatch(repositories, repositoriesToCheck);
  }

  @Test
  public void savesEmptyConfiguration() {
    List<Repository> repositories = new Deserializer("src/test/resources/gitolite3.conf").parse();
    new Serializer(repositories, "tmp/serializer_test/gitolite3.conf").write();
    List<Repository> repositoriesToCheck = new Deserializer("tmp/serializer_test/gitolite3.conf").parse();
    assertRepositoriesCollectionsMatch(repositories, repositoriesToCheck);
  }

  private void assertRepositoriesCollectionsMatch(List<Repository> repositories, List<Repository> repositoriesToCheck) {
    assert repositories.size() == repositoriesToCheck.size();
    for (int i = 0; i < repositories.size(); i++)
      assert repositories.get(i).equals(repositoriesToCheck.get(i)) : i;
  }

}
