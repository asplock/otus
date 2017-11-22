package ru.otus.homework03;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.List;
import org.junit.Test;

/**
 * Основные тесты для выполнения ДЗ
 */
public class MainTests {

  @Test
  public void addAll() throws Exception {
    List<Integer> list = new MyList<>();
    list.add(0);
    list.add(1);
    Collections.addAll(list,2, 3, 4);
    assertEquals(5, list.size());
    assertThat(list, hasItems(0, 1, 2, 3, 4));
  }

  @Test
  public void copy() throws Exception {
    List<Integer> source = new MyList<>();
    source.add(0);
    source.add(1);
    List<Integer> dest = new MyList<>();
    dest.add(2);
    dest.add(3);
    dest.add(4);
    Collections.copy(dest, source);
    assertEquals(3, dest.size());
    assertThat(dest, hasItems(0, 1, 4));
  }

  @Test
  public void sort() throws Exception {
    List<Integer> list = new MyList<>();
    list.add(1);
    list.add(0);
    list.add(2);
    Collections.sort(list, Integer::compareTo);
    assertEquals(3, list.size());
    for (int i = 0; i < list.size(); i++) {
      assertEquals(i, list.get(i).intValue());
    }
  }
}
