package ru.otus.homework02;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

  private static final int ITEM_COUNT = 100_000;

  private static final String PATTERN = "{} size is {} bytes";

  public static void main(String... args) {
    MemoryCounter counter = new MemoryCounter();
    BiConsumer<String, Supplier<?>> printMemory = (name, supplier) ->
        log.info(PATTERN, name, counter.countMemory(supplier, ITEM_COUNT));

    printMemory.accept("EMPTY STRING", String::new);
    printMemory.accept("EMPTY OBJECT", Object::new);
    printMemory.accept("EMPTY TEST OBJECT", TestObject::new);

    TestObject object = new TestObject();
    printMemory.accept("REFERENCE", () -> object);

    printMemory.accept("EMPTY STRING ARRAY", () -> new Object[0]);
    printMemory.accept("EMPTY LIST", ArrayList::new);
    printMemory.accept("EMPTY MAP", HashMap::new);
    printMemory.accept("EMPTY SET", HashSet::new);
  }

}
