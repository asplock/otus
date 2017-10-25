package ru.otus.homework01;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public class App {

  public static void main(String... args) {
    long count = Arrays.stream(args).filter(StringUtils::isNumeric).count();
    System.out.println("Numeric args: " + count);
  }

}
