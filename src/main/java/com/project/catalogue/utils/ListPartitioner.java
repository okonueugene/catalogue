package com.project.catalogue.utils;

import java.util.ArrayList;
import java.util.List;
public class ListPartitioner {
  public <T> List<List<T>> partition(List<T> list, int size) {
        List<List<T>> partitions = new ArrayList<>();
        if (list == null || list.isEmpty() || size < 1) {
            return partitions;
        }
        for (int i = 0; i < list.size(); i += size) {
            partitions.add(new ArrayList<>(list.subList(i, Math.min(i + size, list.size()))));
        }
        return partitions;
    }
}
