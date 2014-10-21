package com.byteimagination.gitomater.models;

import java.util.*;

public class Repository {

  public String name;
  public Map<String, List<String>> privileges = new HashMap<String, List<String>>();

  @Override
  public boolean equals(Object objectToCheck) {
    if (!(objectToCheck instanceof Repository))
      return false;
    Repository repository = (Repository) objectToCheck;
    return name.equals(repository.name) &&
        privilegesAreEqual(repository);
  }

  private boolean privilegesAreEqual(Repository repository) {
    Set<String> privilegesToCheckKeySet = repository.privileges.keySet();
    Set<String> privilegesKeySet = privileges.keySet();
    if (privilegesKeySet.size() != privilegesToCheckKeySet.size())
      return false;
    for (String key : privilegesKeySet) {
      if (!privilegesToCheckKeySet.contains(key))
        return false;
      List<String> privilegesValues = privileges.get(key);
      List<String> privilegesToCheckValues = repository.privileges.get(key);
      if (privilegesValues.size() != privilegesToCheckValues.size())
        return false;
      for (String privilegesValue : privilegesValues)
        if (!privilegesToCheckValues.contains(privilegesValue))
          return false;
    }
    return true;
  }

}
