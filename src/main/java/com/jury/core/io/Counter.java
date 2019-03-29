package com.jury.core.io;

import java.util.HashMap;
import java.util.Map;

public class Counter {

    public int lines = 0;
    public int objects = 0;
    public int success = 0;
    public int failed = 0;
    public Map<String, Integer> errors = new HashMap<>();

    public void failure(String message) {
        failed++;
        if (errors.containsKey(message)) {
            errors.put(message, errors.get(message) + 1);
        } else {
            errors.put(message, 1);
        }
    }

    public String toString() {
        return String.format("Lines = %d; Objects = %d; Successful = %d; Failed = %s", lines, objects, success, failed);
    }

}
