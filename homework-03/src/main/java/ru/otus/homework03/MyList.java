package ru.otus.homework03;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MyList<E> implements List<E>{

  private static final int CAPACITY = 100;

  private int size;

  private Object[] data = new Object[CAPACITY];

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public boolean add(E element) {
    checkCapacity();
    data[size++] = element;
    return true;
  }

  @Override
  public void add(int i, E element) {
    checkRangeForAdd(i);
    checkCapacity();
    System.arraycopy(data, i, data, i + 1, size - i);
    data[i] = element;
    size++;
  }

  @Override
  public E get(int i) {
    checkRange(i);
    return (E) data[i];
  }

  @Override
  public int indexOf(Object o) {
    for (int i = 0; i < size; i++) {
      if (Objects.equals(o, data[i])) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public boolean contains(Object o) {
    return indexOf(o) > 0;
  }

  @Override
  public boolean remove(Object o) {
    int i = indexOf(o);
    if (i < 0) {
      return false;
    } else {
      remove(i);
      return true;
    }
  }

  @Override
  public E remove(int i) {
    checkRange(i);
    E removed = (E) data[i];
    int moved = size - i - 1;
    if(moved > 0) {
      System.arraycopy(data, i + 1, data, i, moved);
    }
    data[--size] = null;
    return removed;
  }

  @Override
  public int lastIndexOf(Object o) {
    for (int i = size - 1; i > 0; i--) {
      if (Objects.equals(o, data[i])){
        return i;
      }
    }
    return -1;
  }


  @Override
  public void clear() {
    size = 0;
    data = new Object[CAPACITY];
  }

  @Override
  public E set(int i, E element) {
    checkRange(i);
    E oldValue = (E) data[i];
    data[i] = element;
    return oldValue;
  }

  @Override
  public Object[] toArray() {
    return Arrays.copyOf(data, size);
  }

  @Override
  public <T> T[] toArray(T[] a) {
    if (a.length < size) {
      return (T[]) Arrays.copyOf(data, size, a.getClass());
    }
    System.arraycopy(data, 0, a, 0, size);
    return a;
  }

  @Override
  public boolean addAll(Collection<? extends E> collection) {
    int addSize = collection.size();
    if (addSize == 0) {
      return false;
    }
    checkCapacity(size + addSize);
    System.arraycopy(collection.toArray(), 0, data, size, addSize);
    size += addSize;
    return true;
  }

  @Override
  public boolean containsAll(Collection<?> collection) {
    for (Object o: collection) {
      if (!contains(o)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean removeAll(Collection<?> collection) {
    boolean modified = false;
    for (Object o : collection) {
      while (remove(o)) {
        modified = true;
      }
    }
    return modified;
  }

  @Override
  public Iterator<E> iterator() {
    return new MyIterator();
  }

  @Override
  public ListIterator<E> listIterator() {
    return new MyIterator();
  }

  @Override
  public ListIterator<E> listIterator(int i) {
    return new MyIterator(i);
  }

  @Override
  public boolean addAll(int i, Collection<? extends E> collection) {
    return false;
  }

  @Override
  public boolean retainAll(Collection<?> collection) {
    return false;
  }

  @Override
  public List<E> subList(int i, int i1) {
    return null;
  }

  private void checkRange(int i) {
    if (i < 0 || i >= size) {
      throw new IndexOutOfBoundsException();
    }
  }

  private void checkRangeForAdd(int i) {
    if (i < 0 || i > size) {
      throw new IndexOutOfBoundsException();
    }
  }

  private void checkCapacity() {
    checkCapacity(size + 1);
  }

  private void checkCapacity(int newSize) {
    if (newSize >= data.length) {
      data = Arrays.copyOf(data, size + CAPACITY);
    }
  }

  class MyIterator implements ListIterator<E> {

    private int cursor;

    private int lastAccessed = -1;

    MyIterator(int i) {
      cursor = i;
    }

    MyIterator() {
    }

    @Override
    public boolean hasNext() {
      return cursor != size;
    }

    @Override
    public E next() {
      if (cursor >= size) {
        throw new NoSuchElementException();
      }
      lastAccessed = cursor;
      return (E) data[cursor++];
    }

    @Override
    public boolean hasPrevious() {
      return cursor != 0;
    }

    @Override
    public E previous() {
      int i = cursor - 1;
      if (i < 0) {
        throw new NoSuchElementException();
      }
      cursor = i;
      lastAccessed = i;
      return (E) data[i];
    }

    @Override
    public int nextIndex() {
      return cursor;
    }

    @Override
    public int previousIndex() {
      return cursor - 1;
    }

    @Override
    public void remove() {
      if (lastAccessed < 0) {
        throw new IllegalStateException();
      }
      MyList.this.remove(cursor);
      lastAccessed = -1;
    }

    @Override
    public void set(E e) {
      if (lastAccessed < 0) {
        throw new IllegalStateException();
      }
      MyList.this.set(lastAccessed, e);
    }

    @Override
    public void add(E e) {
      MyList.this.add(cursor++, e);
      lastAccessed = -1;
    }

  }
}
