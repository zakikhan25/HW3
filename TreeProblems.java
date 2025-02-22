/*
 * *** Zaki Khan / 400 001 ***
 *
 * This java file contains several simple tree problems that need to be
 * codified. These routines must use the TreeMap and TreeSet library
 * classes from the Java Collection Framework.
 *
 */

import java.util.*;

public class TreeProblems {

  /**
   * Method different()
   *
   * Given two TreeSets of integers, return a TreeSet containing all elements
   * that are NOT in both sets. In other words, return a TreeSet of all the
   * elements that are in one set but not the other.
   */

  public static Set<Integer> different(Set<Integer> setA, Set<Integer> setB) {
    Set<Integer> resultSet = new TreeSet<>(setA);
    resultSet.addAll(setB);
    Set<Integer> commonSet = new TreeSet<>(setA);
    commonSet.retainAll(setB);
    resultSet.removeAll(commonSet);
    return resultSet;
  }

  /**
   * Method removeEven()
   *
   * Given a treeMap with the key as an integer, and the value as a String,
   * remove all <key, value> pairs where the key is even.
   */

  public static void removeEven(Map<Integer, String> treeMap) {
    Iterator<Integer> iterator = treeMap.keySet().iterator();
    while (iterator.hasNext()) {
      if (iterator.next() % 2 == 0) {
        iterator.remove();
      }
    }
  }

  /**
   * Method treesEqual()
   *
   * Given two treeMaps, each with the key as an integer, and the value as a String,
   * return a boolean value indicating if the two trees are equal or not.
   */

  public boolean treesEqual(Map<Integer, String> tree1, Map<Integer, String> tree2) {
    if (tree1.size() != tree2.size()) {
      return false;
    }
    for (Map.Entry<Integer, String> entry : tree1.entrySet()) {
      if (!entry.getValue().equals(tree2.get(entry.getKey()))) {
        return false;
      }
    }
    return true;
  }

} // end TreeProblems class
