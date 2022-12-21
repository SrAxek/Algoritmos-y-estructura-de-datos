package aed.recursion;

import es.upm.aedlib.Pair;
import es.upm.aedlib.Position;
import es.upm.aedlib.indexedlist.*;
import es.upm.aedlib.positionlist.*;


public class Utils {

  public static int multiply(int a, int b) {
	  int sum = 0;
	  int sign = 1;
	  
	  if(a < 0 && b > 0) {
		  a = -a;
		  sign = -1;
	  }
	  else if(a > 0 && b < 0) {
		  b = -b;
		  sign = -1;
	  }
	  else if (a < 0 && b < 0) {
		  a = -a;
		  b = -b;
	  }
	  
	  if(a != 0) {
		  if(a%2 != 0) sum = sum + b + multiply(a/2,b*2);
		  else sum = sum + multiply(a/2, b*2);
	  }
	  
	  return sign*sum;
  }

  public static <E extends Comparable<E>> int findBottom(IndexedList<E> l) {
	  return -1;
  }

  public static <E extends Comparable<E>> NodePositionList<Pair<E,Integer>>
    joinMultiSets(NodePositionList<Pair<E,Integer>> l1, NodePositionList<Pair<E,Integer>> l2) {
	  
	  
	  
    return null;
  }
}
