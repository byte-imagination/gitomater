package com.byteimagination.gitomater.helpers;

import com.byteimagination.gitomater.models.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepositoryCLIParametersParser {

  public static Repository parse(String name) {
    return parse(name, "");
  }

  public static Repository parse(String name, String privilegesString) {
    Repository repository = new Repository();
    repository.name = name.split("\\.git")[0];
    String[] privileges = privilegesString.split(",");
    for (String privilege : privileges) {
      String[] privilegeData = privilege.trim().split("=");
      if (privilegeData.length < 2)
        continue;
      String[] users = privilegeData[1].split(" ");
      if (users.length == 0)
        continue;
      List<String> privilegesList = new ArrayList<String>();
      Collections.addAll(privilegesList, users);
      repository.privileges.put(privilegeData[0], privilegesList);
    }
    return repository;
  }

}
