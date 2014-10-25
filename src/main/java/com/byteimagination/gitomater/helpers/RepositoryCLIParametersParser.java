package com.byteimagination.gitomater.helpers;

import com.byteimagination.gitomater.models.Repository;

import java.util.*;

public class RepositoryCLIParametersParser {

  public static Repository parse(String name) {
    return parseRepository(name, "");
  }

  public static Repository parseRepository(String name, String privilegesString) {
    Repository repository = new Repository();
    repository.name = name.split("\\.git")[0];
    repository.privileges = parsePrivileges(privilegesString);
    return repository;
  }

  public static Map<String, List<String>> parsePrivileges(String privilegesString) {
    String[] privileges = privilegesString.split(",");
    Map<String, List<String>> privilegesMap = new HashMap<String, List<String>>();
    for (String privilege : privileges) {
      String[] privilegeData = privilege.trim().split("=");
      if (privilegeData.length < 2)
        continue;
      String[] users = privilegeData[1].split(" ");
      if (users.length == 0)
        continue;
      List<String> privilegesList = new ArrayList<String>();
      Collections.addAll(privilegesList, users);
      privilegesMap.put(privilegeData[0], privilegesList);
    }
    return privilegesMap;
  }

}
