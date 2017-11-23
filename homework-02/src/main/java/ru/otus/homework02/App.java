package ru.otus.homework02;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

  private static final int ITEM_COUNT = 100_000;

  private static final String PATTERN = "{} size is {} bytes";

  private static final String COLLECTION_PATTERN = "{} size changes: {}";

  public static void main(String... args) {
    MemoryCounter counter = new MemoryCounter();
    countMemoryForObjects(counter);
    countMemoryForCollectionsSize(counter);
  }

  private static void countMemoryForObjects(MemoryCounter counter) {
    log.info("========= OBJECTS SIZES ===========");
    BiConsumer<String, Supplier<?>> printMemory = (name, supplier) ->
        log.info(PATTERN, name, counter.countObjectMemory(supplier, ITEM_COUNT));

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

  private static void countMemoryForCollectionsSize(MemoryCounter counter) {
    log.info("========= COLLECTIONS SIZE CHANGES ===========");
    BiConsumer<String, Supplier<Collection<TestObject>>> printMemory = (name, collectionSupplier) ->
        log.info(COLLECTION_PATTERN, name,
                 counter.countContainerMemoryChanges(TestObject::new, collectionSupplier));

    printMemory.accept("ARRAY_LIST", ArrayList::new);
    printMemory.accept("LINKED_LIST", LinkedList::new);
    printMemory.accept("HASH_SET", HashSet::new);
    printMemory.accept("LINKED_HASH_SET", LinkedHashSet::new);
  }

}
