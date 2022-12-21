package aed.hashtable;

import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.Arrays;

import es.upm.aedlib.Entry;
import es.upm.aedlib.EntryImpl;
import es.upm.aedlib.map.Map;
import es.upm.aedlib.positionlist.NodePositionList;
import es.upm.aedlib.positionlist.PositionList;
import es.upm.aedlib.InvalidKeyException;


/**
 * A hash table implementing using open addressing to handle key collisions.
 */
public class HashTable<K,V> implements Map<K,V> {
  Entry<K,V>[] buckets;
  int size;

  public HashTable(int initialSize) {
    this.buckets = createArray(initialSize);
    this.size = 0;
  }

  /**
   * Add here the method necessary to implement the Map api, and
   * any auxilliary methods you deem convient.
   */
  
  public boolean isEmpty() {
	  if (size == 0) return true;
	  else return false;
  }
  
  public int size() {
	  return size;
  }
  
  public boolean containsKey(Object key) {
	  
	  boolean res = false;
	  for(int i = 0; i < buckets.length && !res ; i++) {
		  if(buckets[i] != null && buckets[i].getKey().equals(key)) res = true;
	  }
	  return res;
  }
  
  public V get(K key) {
	  V res = null;
	  Entry<K,V> entry = buckets[1];
	  entry.get
	  
	  if(containsKey(key)) {
		  for(int i = 0; i < buckets.length; i++) 
			  if(buckets[i] != null && buckets[i].getKey().equals(key)) 
				  res = buckets[i].getValue();
		  return res;
	  }
	  else return null;
  }
  
  public V put(K key, V value) {
	  
	  V res = null;
	  int n = search(key);
	  
	  if(n!=-1) {
		  
		  if(!containsKey(key)) {
		  
			  buckets[n] = new EntryImpl<K,V>(key,value);;
			  System.out.println(buckets[n].getValue());
			  size++;
		  
		  } else {
		  
			  for(int i = 0; i < buckets.length; i++) {
				  if(buckets[i] != null && buckets[i].getKey().equals(key)) {
					  res = buckets[i].getValue();
					  buckets[i] = new EntryImpl<K,V>(key,value);
				  }
			  }
		  }
			  
		  if(size == buckets.length) rehash();
	  } 
	  return res;
  }
  public V remove(K key) {
	  int indexHueco = -1;
	  V res = null;

	  for(int i = 0; i < buckets.length; i++) {
		  if(buckets[i] != null && buckets[i].getKey().equals(key)) {
			  res = buckets[i].getValue();
			  buckets[i] = null;
			  indexHueco = i;
			  size--;
		  }
	  }
	  int start = indexHueco;
	  int i = indexHueco +1;
	  while(i != start && buckets[i] != null) {
		  int indicePreferido = index(buckets[i].getKey());
		  if(indexHueco-indicePreferido < i - indicePreferido) {
			  buckets[indexHueco] = buckets[i];
			  buckets[i] = null;
			  indexHueco = i;
		  }
	  }
	  return res;
  }
  public Iterable<K> keys(){
	  PositionList<K> list = new NodePositionList<K>();
	  for(int i = 0; i < buckets.length; i++) {
		  if(buckets[i] != null) {
			  list.addLast(buckets[i].getKey());
		  }
	  }
	  Iterable<K> res  = list;
	  return res;
  }
  public Iterable<Entry<K,V>> entries() {
	  PositionList<Entry<K,V>> list = new NodePositionList<Entry<K,V>>();
	  for(int i = 0; i < buckets.length; i++) {
		  if(buckets[i] != null) {
			  list.addLast(buckets[i]);
		  }
	  }
	  Iterable<Entry<K,V>> res = list;
	  return res;
  }

  // Examples of auxilliary methods: IT IS NOT REQUIRED TO IMPLEMENT THEM
  
  @SuppressWarnings("unchecked") 
  private Entry<K,V>[] createArray(int size) {
	  Entry<K,V>[] buckets = (Entry<K,V>[]) new Entry[size];
	  return buckets;
  }

  // Returns the bucket index of an object
  private int index(Object obj) {
	  int n = obj.hashCode();
	  int p = buckets.length;
	  while(n >= p) n = n - p;
	  return n;
  }

  // Returns the index where an entry with the key is located,
  // or if no such entry exists, the "next" bucket with no entry,
  // or if all buckets stores an entry, -1 is returned.
  private int search(Object obj) {
	  int res = index(obj);
	  for(int i = res; i < buckets.length; i++)
		  if(buckets[i] == null) return i;
	  for(int i = 0; i < res; i++)
		  if(buckets[i] == null) return i;
	  return -1;
  }

  // Doubles the size of the bucket array, and inserts all entries present
  // in the old bucket array into the new bucket array, in their correct
  // places. Remember that the index of an entry will likely change in
  // the new array, as the size of the array changes.
  private void rehash() {
    Entry<K,V>[] newBuckets = createArray(buckets.length*2);
    Entry<K,V>[] oldBuckets = buckets;
    buckets = newBuckets;
    for(int i = 0; i < oldBuckets.length; i++) {
    	int n = search(oldBuckets[i].getKey());
    	newBuckets[n] = oldBuckets[i];
    }
  }

  
}

