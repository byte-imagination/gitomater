package com.byteimagination.gitomater.exceptions;

public class ConfigurationNotFoundException extends RuntimeException {

  public ConfigurationNotFoundException(String path) {
    super("File '" + path + "' does not exist.");
  }

}
