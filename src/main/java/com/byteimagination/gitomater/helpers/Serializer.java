package com.byteimagination.gitomater.helpers;

import com.byteimagination.gitomater.models.Repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Serializer {

  private final List<Repository> repositories;
  private final String path;

  public Serializer(List<Repository> repositories, String path) {
    this.repositories = repositories;
    this.path = path;
  }

  public void write() {
    createDirectories();
    writeFile();
  }

  private void createDirectories() {
    if (!path.contains("/") && !path.contains("\\"))
      return;
    int lastUnixSeparatorIndex = path.lastIndexOf("/");
    int lastDosSeparatorIndex = path.lastIndexOf("\\");
    new File(path.substring(0, Math.max(lastUnixSeparatorIndex, lastDosSeparatorIndex))).mkdirs();
  }

  private void writeFile() {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(path));
      for (Repository repository : repositories) {
        writer.write("repo " + repository.name + "\n");
        for (String key : repository.privileges.keySet()) {
          writer.write("    " + key + " = ");
          List<String> privileges = repository.privileges.get(key);
          for (String privilege : privileges)
            writer.write(privilege + " ");
          writer.write("\n");
        }
        writer.write("\n");
      }
      writer.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
