package com.byteimagination.gitomater.helpers;

import com.byteimagination.gitomater.models.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Deserializer {

  public List<Repository> parse(String path) {
    File file = new File(path);
    if (!file.exists())
      throw new RuntimeException("File '" + path + "' does not exist.");
    List<String> lines = readFile(file);
    List<Repository> repositories = new ArrayList<Repository>();
    int nextRepositoryLine = 0;
    do {
      nextRepositoryLine = findNextRepositoryLine(nextRepositoryLine, lines);
      if (nextRepositoryLine < 0)
        break;
      Repository repository = new Repository();
      repositories.add(repository);
      repository.name = lines.get(nextRepositoryLine).split(" ")[1].trim();
      parsePrivileges(repository, nextRepositoryLine + 1, lines);
    } while ((nextRepositoryLine = findNextRepositoryLine(nextRepositoryLine + 1, lines)) >= 0);
    Collections.sort(repositories, new Comparator<Repository>() {
      public int compare(Repository r1, Repository r2) {
        return r1.name.compareTo(r2.name);
      }
    });
    return repositories;
  }

  private List<String> readFile(File file) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(file));
      List<String> lines = new ArrayList<String>();
      String line;
      while ((line = reader.readLine()) != null)
        lines.add(line);
      return lines;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private int findNextRepositoryLine(int previousIndex, List<String> lines) {
    for (int i = previousIndex; i < lines.size(); i++)
      if (lines.get(i).startsWith("repo"))
        return i;
    return -1;
  }

  private void parsePrivileges(Repository repository, int repositoryStartingLine, List<String> lines) {
    for (int i = repositoryStartingLine; i < lines.size(); i++) {
      String line = lines.get(i);
      if (line.startsWith("repo") || line.trim().isEmpty())
        break;
      String[] lineParts = line.split("=");
      String privilegesType = lineParts[0].trim();
      if (!repository.privileges.containsKey(privilegesType))
        repository.privileges.put(privilegesType, new ArrayList<String>());
      String[] users = lineParts[1].split("\\s+");
      List<String> usersList = repository.privileges.get(privilegesType);
      for (String user : users)
        if (!user.trim().isEmpty())
          usersList.add(user);
    }
  }

}
