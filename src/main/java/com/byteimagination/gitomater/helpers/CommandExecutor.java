package com.byteimagination.gitomater.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Logger;

public class CommandExecutor {

  public void execute(List<String> arguments) {
    try {
      Process process = new ProcessBuilder(arguments).start();
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

}
