package com.byteimagination.gitomater;

import com.byteimagination.gitomater.exceptions.ConfigurationNotFoundException;
import com.byteimagination.gitomater.exceptions.RepositoryAlreadyExistsException;
import com.byteimagination.gitomater.helpers.ChangeLog;
import com.byteimagination.gitomater.helpers.CommandExecutor;
import com.byteimagination.gitomater.helpers.Deserializer;
import com.byteimagination.gitomater.helpers.Serializer;
import com.byteimagination.gitomater.models.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Gitomater {

  public static final String CONFIGURATION_PATH = "/conf/gitolite.conf";

  private final String repositoryPath;
  private final CommandExecutor commandExecutor;
  private List<Repository> repositories;
  private ChangeLog changeLog = new ChangeLog();

  public Gitomater(String repositoryPath) {
    this(repositoryPath, new CommandExecutor());
  }

  public Gitomater(String repositoryPath, CommandExecutor commandExecutor) {
    this.repositoryPath = repositoryPath;
    this.commandExecutor = commandExecutor;
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
    List<String> argumentsList = new ArrayList<String>();
    argumentsList.add("git");
    argumentsList.add("-C");
    argumentsList.add(repositoryPath);
    argumentsList.addAll(Arrays.asList(arguments));
    commandExecutor.execute(argumentsList);
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
