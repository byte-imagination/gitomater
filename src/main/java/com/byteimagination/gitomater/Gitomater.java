package com.byteimagination.gitomater;

import com.byteimagination.gitomater.exceptions.ConfigurationNotFoundException;
import com.byteimagination.gitomater.exceptions.RepositoryAlreadyExistsException;
import com.byteimagination.gitomater.helpers.ChangeLog;
import com.byteimagination.gitomater.helpers.Deserializer;
import com.byteimagination.gitomater.helpers.Serializer;
import com.byteimagination.gitomater.models.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Gitomater {

  public static final String CONFIGURATION_PATH = "/conf/gitolite.conf";

  private final String repositoryPath;
  private List<Repository> repositories;
  private ChangeLog changeLog = new ChangeLog();

  public Gitomater(String repositoryPath) {
    this.repositoryPath = repositoryPath;
  }

  public void load() {
    executeGitCommand("pull");
    try {
      repositories = new Deserializer(configurationPath()).parse();
    } catch (ConfigurationNotFoundException ignored) {
      repositories = new ArrayList<Repository>();
    }
  }

  public void save() {
    new Serializer(repositories, configurationPath()).write();
    executeGitCommand("commit", "-a", "-m", changeLog.getChangeLog());
    executeGitCommand("push");
  }

  private void executeGitCommand(String... arguments) {
    try {
      List<String> argumentsList = new ArrayList<String>();
      argumentsList.add("git");
      argumentsList.add("-C");
      argumentsList.add(repositoryPath);
      argumentsList.addAll(Arrays.asList(arguments));
      Process process = new ProcessBuilder(argumentsList).start();
      showOutput(process);
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  private void showOutput(Process process) throws IOException {
    BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()));
    BufferedReader errorOutput = new BufferedReader(new InputStreamReader(process.getErrorStream()));
    String line;
    while ((line = output.readLine()) != null)
      Logger.getAnonymousLogger().info(line);
    while ((line = errorOutput.readLine()) != null)
      Logger.getAnonymousLogger().info(line);
  }

  private String configurationPath() {
    return repositoryPath + CONFIGURATION_PATH;
  }

  public List<Repository> getRepositories() {
    return repositories;
  }

  public void addRepository(Repository repository) {
    for (Repository existingRepository : repositories)
      if (repository.name.equals(existingRepository.name))
        throw new RepositoryAlreadyExistsException();
    repositories.add(repository);
    changeLog.appendRepository(repository);
  }

}
