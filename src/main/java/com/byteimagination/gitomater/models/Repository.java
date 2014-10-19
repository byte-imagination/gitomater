package com.byteimagination.gitomater.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repository {

  public String name;
  public Map<String, List<String>> privileges = new HashMap<String, List<String>>();

}
