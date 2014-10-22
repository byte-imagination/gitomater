package com.byteimagination.gitomater;

import com.byteimagination.gitomater.helpers.RepositoryCLIParametersParser;
import com.byteimagination.gitomater.models.Repository;

public class Main {

  public static void main(String[] args) {
    Command command = null;
    try {
      command = Command.valueOf(args[0]);
    } catch (Exception ignored) {
    }
    if (command == null) {
      showHelp();
      return;
    }
    handleCommand(args, command);
  }

  private static void handleCommand(String[] args, Command command) {
    switch (command) {
      case help:
        showHelp();
        break;
      case addRepository:
        addRepository(args);
        break;
    }
  }

  private static void showHelp() {
    for (Command command : Command.values())
      System.out.println(command.name() + " - " + command.description);
  }

  private static void addRepository(String[] args) {
    Repository repository = RepositoryCLIParametersParser.parse(args[2], args[3]);
    Gitomater gitomater = new Gitomater(args[1]);
    gitomater.load();
    gitomater.addRepository(repository);
    gitomater.save();
  }

}
