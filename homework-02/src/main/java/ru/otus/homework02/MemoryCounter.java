package ru.otus.homework02;

import java.util.ArrayList;
import java.util.List;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemoryCounter {

  private static LongSupplier ALLOCATED_MEMORY = () ->
      Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

  public <T> long countMemory(Supplier<T> supplier, int itemCount) {
    List<T> list = new ArrayList<>();
    Runtime.getRuntime().gc();

    long before = ALLOCATED_MEMORY.getAsLong();
    log.info("Allocated memory before gc {} bytes", before);
    for (int i = 0; i < itemCount; i++) {
      list.add(supplier.get());
    }
    Runtime.getRuntime().gc();

    long after = ALLOCATED_MEMORY.getAsLong();
    log.info("Allocated memory after gc {} bytes", after);

    return (after - before) / list.size();
  }

}
