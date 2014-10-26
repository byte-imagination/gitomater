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
import java.util.Map;

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

  public void addPrivileges(String repositoryName, Map<String, List<String>> privileges) {
    Repository repository = getRepository(repositoryName);
    for (String key : privileges.keySet()) {
      if (!repository.privileges.containsKey(key))
        repository.privileges.put(key, new ArrayList<String>());
      List<String> existingPrivileges = repository.privileges.get(key);
      List<String> newPrivileges = privileges.get(key);
      for (String user : newPrivileges)
        if (!existingPrivileges.contains(user)) {
          existingPrivileges.add(user);
          changeLog.appendPrivileges(key, Arrays.asList(user), repository);
        }
    }
  }

  public void takePrivileges(String repositoryName, Map<String, List<String>> privileges) {
    Repository repository = getRepository(repositoryName);
    for (String key : privileges.keySet()) {
      if (!repository.privileges.containsKey(key))
        continue;
      List<String> existingPrivileges = repository.privileges.get(key);
      List<String> privilegesToTake = privileges.get(key);
      for (String user : privilegesToTake)
        if (existingPrivileges.contains(user)) {
          existingPrivileges.remove(user);
          changeLog.appendTakenPrivileges(key, Arrays.asList(user), repository);
        }
    }
  }

  public ChangeLog getChangeLog() {
    return changeLog;
  }

  public Repository getRepository(String name) {
    for (Repository repository : getRepositories())
      if (repository.name.equals(name))
        return repository;
    return null;
  }

}
