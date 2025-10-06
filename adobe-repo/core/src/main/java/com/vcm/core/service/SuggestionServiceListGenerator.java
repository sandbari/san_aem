package com.vcm.core.service;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;

public interface SuggestionServiceListGenerator {
  
    public  Map<String, List<JsonObject>>  createSuggestionList();
    
}
