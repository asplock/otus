package ru.otus.homework03;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

public class MyListTest {

  private MyList<Integer> list;

  @Before
  public void setUp() throws Exception {
    list = new MyList<>();
  }

  @Test
  public void add() throws Exception {
    assertTrue(list.add(1));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void addIndexOutOfBound() {
    list.add(0);
    list.get(1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void addIndexLessThanZero() throws Exception {
    list.add(0);
    list.get(-1);
  }

  @Test
  public void addToIndex() throws Exception {
    int index = 1;
    fillList(10);
    list.add(index, 11);
    assertEquals(11, list.size());
    assertEquals(0, list.get(index - 1).intValue());
    assertEquals(11, list.get(index).intValue());
    assertEquals(1, list.get(index + 1).intValue());
  }

  @Test
  public void addToLastIndex() throws Exception {
    fillList(10);
    list.add(10, 11);
    assertEquals(11, list.get(10).intValue());
    assertEquals(11, list.size());
  }

  @Test
  public void addIndexToEmptyList() throws Exception {
    list.add(0, 0);
    assertEquals(1, list.size());
    assertEquals(0, list.get(0).intValue());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void addToIndexOutOfBound() throws Exception {
    fillList(10);
    list.add(11, 12);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void addToIndexLessZero() throws Exception {
    fillList(10);
    list.add(-1, -1);
  }

  @Test
  public void addOverCapacity() throws Exception {
    fillList(101);
    assertEquals(list.size(), 101);
  }

  @Test
  public void getFirst() throws Exception {
    list.add(1);
    Integer value = list.get(0);
    assertNotNull(value);
    assertEquals(1, value.intValue());
  }

  @Test
  public void getMiddle() throws Exception {
    fillList(10);
    Integer value = list.get(5);
    assertNotNull(value);
    assertEquals(5, value.intValue());
  }

  @Test
  public void sizeZero() throws Exception {
   assertEquals(0, list.size());
  }

  @Test
  public void size() throws Exception {
    int count = 10;
    fillList(count);
    assertEquals(count, list.size());
  }

  @Test
  public void isEmptyTrue() throws Exception {
    assertTrue(list.isEmpty());
  }

  @Test
  public void isEmptyFalse() throws Exception {
    fillList(10);
    assertFalse(list.isEmpty());
  }

  @Test
  public void indexOf() throws Exception {
    fillList(10);
    fillList(10);
    assertEquals(3, list.indexOf(3));
  }

  @Test
  public void indexOfNull() throws Exception {
    fillList(10);
    list.add(null);
    assertEquals(10, list.indexOf(null));
  }

  @Test
  public void indexNotFound() throws Exception {
    fillList(10);
    assertEquals(-1, list.indexOf(null));
  }

  @Test
  public void lastIndexOf() throws Exception {
    fillList(10);
    fillList(10);
    assertEquals(19, list.lastIndexOf(9));
  }

  @Test
  public void lastIndexOfNull() throws Exception {
    fillList(10);
    list.add(null);
    list.add(null);
    assertEquals(11, list.lastIndexOf(null));
  }

  @Test
  public void lastIndexNotFound() throws Exception {
    fillList(10);
    assertEquals(-1, list.lastIndexOf(-1));
  }

  @Test
  public void contains() throws Exception {
    fillList(10);
    assertTrue(list.contains(9));
  }

  @Test
  public void containsNull() throws Exception {
    fillList(10);
    list.add(null);
    assertTrue(list.contains(null));
  }

  @Test
  public void containsFalse() throws Exception {
    fillList(10);
    assertFalse(list.contains(-1));
  }

  @Test
  public void remove() throws Exception {
    fillList(10);
    Integer removed = list.remove(3);
    assertEquals(9, list.size());
    assertEquals(2, list.get(2).intValue());
    assertEquals(3, removed.intValue());
    assertEquals(4, list.get(3).intValue());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void removeOutOfIndex() throws Exception {
    fillList(10);
    list.remove(10);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void removeLessZero() throws Exception {
    fillList(10);
    list.remove(-1);
  }

  @Test
  public void removeObject() throws Exception {
    fillList(10);
    assertTrue(list.remove(Integer.valueOf(3)));
    assertEquals(9, list.size());
  }

  @Test
  public void removeObjectNull() throws Exception {
    fillList(10);
    list.add(null);
    assertTrue(list.remove(null));
    assertEquals(10, list.size());
  }

  @Test
  public void removeFalse() throws Exception {
    fillList(10);
    assertFalse(list.remove(null));
    assertEquals(10, list.size());
  }

  @Test
  public void clear() throws Exception {
    fillList(10);
    list.clear();
    assertTrue(list.isEmpty());
  }

  @Test
  public void set() throws Exception {
    fillList(10);
    Integer old = list.set(2, 10);
    assertEquals(2, old.intValue());
    assertEquals(10, list.get(2).intValue());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void setOutOfBounds() throws Exception {
    list.set(1, 1);
  }

  @Test
  public void toArrayObject() throws Exception {
    fillList(10);
    Object[] objects = list.toArray();
    assertEquals(list.size(), objects.length);
    for (int i = 0; i < list.size(); i++) {
      assertEquals(list.get(i), objects[i]);
    }
  }

  @Test
  public void toArrayObjectEmpty() throws Exception {
    Object[] objects = list.toArray();
    assertEquals(0, objects.length);
  }

  @Test
  public void toArray() throws Exception {
    fillList(10);
    Integer[] array = list.toArray(new Integer[0]);
    assertEquals(10, array.length);
    for (int i = 0; i < list.size(); i++) {
      assertEquals(list.get(i), array[i]);
    }
  }

  @Test
  public void toArrayBiggerThanOrigin() throws Exception {
    fillList(10);
    Integer[] array = list.toArray(new Integer[20]);
    assertEquals(20, array.length);
    for (int i = 0; i < list.size(); i++) {
      assertEquals(list.get(i), array[i]);
    }
  }

  @Test
  public void addAll() throws Exception {
    int originalSize = 90;

    fillList(90);
    List<Integer> newList = new MyList<>();
    fillList(20, newList);
    list.addAll(newList);
    assertEquals(110, list.size());
    for (int i = 0; i < newList.size(); i++) {
      assertEquals(newList.get(i), list.get(i + originalSize));
    }
  }

  @Test
  public void addAllEmpty() throws Exception {
    fillList(10);

    list.addAll(Collections.emptyList());
    assertEquals(10, list.size());
  }

  @Test
  public void containsAll() throws Exception {
    fillList(10);
    assertTrue(list.containsAll(Arrays.asList(1, 5)));
  }

  @Test
  public void containsAllFalse() throws Exception {
    fillList(10);
    assertFalse(list.containsAll(Arrays.asList(1, 5, 10)));
  }

  @Test
  public void removeAll() throws Exception {
    fillList(10);
    fillList(10);
    assertTrue(list.removeAll(Arrays.asList(1, 5)));
    assertEquals(list.size(), 16);
    assertFalse(list.contains(1));
    assertFalse(list.contains(5));
  }

  @Test
  public void removeAllFalse() throws Exception {
    fillList(10);
    assertFalse(list.removeAll(Collections.singletonList(10)));
    assertEquals(10, list.size());
  }

  @Test
  public void iteratorHasNextOnEmptyList() throws Exception {
    Iterator<Integer> it = list.iterator();
    assertNotNull(it);
    assertFalse(it.hasNext());
  }

  @Test
  public void iteratorHasNext() throws Exception {
    fillList(1);
    Iterator<Integer> it = list.iterator();
    assertTrue(it.hasNext());
    Integer value = it.next();
    assertEquals(0, value.intValue());
    assertFalse(it.hasNext());
  }

  @Test(expected = NoSuchElementException.class)
  public void iteratorNextOnEmptyList() throws Exception {
    Iterator<Integer> it = list.iterator();
    assertNotNull(it);
    it.next();
  }

  @Test(expected = IllegalStateException.class)
  public void iteratorRemoveOnEmptyList() throws Exception {
    Iterator<Integer> it = list.iterator();
    it.remove();
  }

  @Test
  public void iteratorRemove() throws Exception {
    fillList(10);
    Iterator<Integer> it = list.iterator();
    it.next();
    it.remove();
    assertEquals(9, list.size());
  }

  @Test
  public void iteratorHasPrevious() throws Exception {
    fillList(10);
    ListIterator<Integer> it = list.listIterator();
    it.next();
    assertTrue(it.hasPrevious());
  }

  @Test
  public void iteratorHasPreviousFalse() throws Exception {
    fillList(10);
    ListIterator<Integer> it = list.listIterator();
    assertFalse(it.hasPrevious());
  }

  @Test
  public void iteratorPrevious() throws Exception {
    fillList(10);
    ListIterator<Integer> it = list.listIterator();
    it.next();
    Integer value = it.previous();
    assertEquals(0, value.intValue());
  }

  @Test(expected = NoSuchElementException.class)
  public void iteratorPreviousNoSuchElement() throws Exception {
    fillList(10);
    ListIterator<Integer> it = list.listIterator();
    it.previous();
  }

  @Test
  public void iteratorSet() throws Exception {
    fillList(10);
    ListIterator<Integer> it = list.listIterator();
    it.next();
    it.set(11);
    assertEquals(11, list.get(0).intValue());
  }

  @Test(expected = IllegalStateException.class)
  public void iteratorSetIllegalState() throws Exception {
    fillList(10);
    ListIterator<Integer> it = list.listIterator();
    it.set(11);
  }

  @Test
  public void iteratorAddFirst() throws Exception {
    ListIterator<Integer> it = list.listIterator();
    it.add(1);
    assertEquals(1, list.size());
    assertEquals(1, list.get(0).intValue());
  }

  @Test
  public void iteratorAdd() throws Exception {
    fillList(10);
    ListIterator<Integer> it = list.listIterator();
    it.next();
    it.add(11);
    assertEquals(11, list.size());
    assertEquals(11, list.get(1).intValue());
  }

  private void fillList(int count) {
    fillList(count, list);
  }

  private void fillList(int count, List<Integer> list) {
    for (int i = 0; i < count ; i++) {
      list.add(i);
    }
  }

}