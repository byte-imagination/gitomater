package com.byteimagination.gitomater.helpers;

import com.byteimagination.gitomater.models.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChangeLog {

  private StringBuilder changeLog = new StringBuilder();

  public void appendRepository(Repository repository) {
    appendNewLineIfNeeded();
    changeLog.append("+repo ").append(repository.name).append(".git");
    List<String> keySet = getSortedPrivilegesKeySet(repository);
    for (String key : keySet)
      appendPrivileges(key, repository.privileges.get(key), repository);
  }

  private List<String> getSortedPrivilegesKeySet(Repository repository) {
    List<String> keySet = new ArrayList<String>();
    keySet.addAll(repository.privileges.keySet());
    Collections.sort(keySet);
    return keySet;
  }

  public void appendPrivileges(String key, List<String> users, Repository repository) {
    for (String user : users) {
      appendNewLineIfNeeded();
      changeLog.append("+").append(key).append(" ").append(user).append("@").append(repository.name).append(".git");
    }
  }

  public void appendTakenPrivileges(String key, List<String> users, Repository repository) {
    for (String user : users) {
      appendNewLineIfNeeded();
      changeLog.append("-").append(key).append(" ").append(user).append("@").append(repository.name).append(".git");
    }
  }

  private void appendNewLineIfNeeded() {
    if (changeLog.length() > 0)
      changeLog.append("\n");
  }

  public String getChangeLog() {
    return changeLog.toString();
  }

}
