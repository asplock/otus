package ru.otus.homework02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemoryCounter {

  private static LongSupplier ALLOCATED_MEMORY = () ->
      Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

  public <T> long countObjectMemory(Supplier<T> supplier, int itemCount) {
    List<T> list = new ArrayList<>();
    long memory = fillAndCount(supplier, list, itemCount);
    return memory / list.size();
  }

  public <T> Map<Integer, Long> countContainerMemoryChanges(Supplier<T> objectSupplier,
                                                            Supplier<Collection<T>> collectionSupplier) {
    long objectMemory = countObjectMemory(objectSupplier, 100_000);
    Map<Integer, Long> result = new LinkedHashMap<>();
    Stream.of(10, 100, 1_000, 10_000, 100_000).forEach(size -> {
      Collection<T> collection = collectionSupplier.get();
      long fullMemory = fillAndCount(objectSupplier, collection, size);
      result.put(collection.size(), fullMemory - collection.size() * objectMemory);
    });
    return result;
  }

  private <T> long fillAndCount(Supplier<T> supplier, Collection<T> collection, int itemCount) {
    Runtime.getRuntime().gc();
    long before = ALLOCATED_MEMORY.getAsLong();
    log.debug("Allocated memory before gc {} bytes", before);
    for (int i = 0; i < itemCount; i++) {
      collection.add(supplier.get());
    }
    Runtime.getRuntime().gc();

    long after = ALLOCATED_MEMORY.getAsLong();
    log.debug("Allocated memory after gc {} bytes", after);
    return after - before;
  }

}
