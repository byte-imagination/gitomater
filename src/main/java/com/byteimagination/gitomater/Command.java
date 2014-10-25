package com.byteimagination.gitomater;

public enum Command {

  help("Show help"),
  addRepository("Add repository (addRepository repositoryPath repositoryName R=john eve RW+=adam stan"),
  addPrivileges("Add repository privileges (addPrivileges repositoryPath repositoryName R=john eve RW+=adam stan");

  public final String description;

  Command(String description) {
    this.description = description;
  }

}
