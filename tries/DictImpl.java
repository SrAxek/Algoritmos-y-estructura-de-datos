package aed.tries;

import java.util.Arrays;
import java.util.Iterator;

import es.upm.aedlib.Pair;
import es.upm.aedlib.Position;
import es.upm.aedlib.tree.GeneralTree;
import es.upm.aedlib.tree.LinkedGeneralTree;
import es.upm.aedlib.positionlist.PositionList;
import es.upm.aedlib.positionlist.NodePositionList;


public class DictImpl implements Dictionary {
  // A boolean because we need to know if a word ends in a node or not
  GeneralTree<Pair<Character,Boolean>> tree;

  public DictImpl() {
    tree = new LinkedGeneralTree<>();
    tree.addRoot(new Pair<Character,Boolean>(null,false));
  }

  public void add(String word) {
	  
	  if(word == null || word.equals("")) throw new java.lang.IllegalArgumentException();
	  
	  Position<Pair<Character, Boolean>> e = tree.root(); Position<Pair<Character, Boolean>> b = tree.root();
	  
	  int contador = 0;
	  
	  while(b!= null && contador < word.length()) {
		  b = searchChildLabelled(word.charAt(contador), b);
		  if(b!=null) {
			  e = b; contador++;
		  }
	  }
	  
	  if(contador < word.length()) 	{
		  e = addChildAlphabetically(new Pair<Character, Boolean>(word.charAt(contador), contador == word.length()-1), e); contador++;
	  }
	  else e.element().setRight(true);
	  
	  while(contador < word.length()) {
		  Pair<Character, Boolean> a = new Pair<Character, Boolean>(word.charAt(contador), contador == word.length()-1);
		  tree.addChildFirst(e, a); e = tree.children(e).iterator().next(); contador++;
	  }
  }
  
  public Position<Pair<Character, Boolean>> addChildAlphabetically(Pair<Character, Boolean> pair, Position<Pair<Character, Boolean>> pos){
	  
	  Iterator<Position<Pair<Character, Boolean>>> it = tree.children(pos).iterator();
	  Position<Pair<Character, Boolean>> e;
	  while(it.hasNext()) {
		  e = it.next();
		  if(pair.getLeft().compareTo(e.element().getLeft()) < 0) {
			  tree.insertSiblingBefore(e, pair); return searchChildLabelled(pair.getLeft(), pos);
		  }
	  }
	  if(!it.hasNext()) tree.addChildLast(pos, pair); 
	  return searchChildLabelled(pair.getLeft(), pos); 
  }
  
  public Position<Pair<Character,Boolean>> searchChildLabelled(char ch, Position<Pair<Character, Boolean>> pos){
	  
	  for(Position<Pair<Character, Boolean>> w : tree.children(pos)) {
		  if(w.element().getLeft().equals(ch)) return w;
	  }
	  return null;
  }
  
  public void delete(String word) {
	  if(word==null) throw new java.lang.IllegalArgumentException();
	  if(isIncluded(word)) {
		  Position<Pair<Character, Boolean>> a = findPos(word);
		  a.element().setRight(false);;
	  }
  }
  
  public boolean isIncluded(String word) { 
	  if(word == null || word.equals("")) throw new java.lang.IllegalArgumentException();
	  
	  boolean res = false; Position<Pair<Character, Boolean>> a = findPos(word);
	  if(a != null && a.element().getRight()) res = true;
	  
	  return res; 
  }
  
  public PositionList<String> wordsBeginningWithPrefix(String prefix) { 
	  
	  if(prefix == null) throw new java.lang.IllegalArgumentException();
	  PositionList<String> res = new NodePositionList<String>();
	  Position<Pair<Character, Boolean>> pos;
	  
	  if(prefix.equals("")) pos = tree.root();
	  else pos = findPos(prefix);
	  
	  preorder(pos, res, prefix);
	  
	  return res;
  }
  
  public PositionList<String> preorder(Position<Pair<Character, Boolean>> w, PositionList<String> res, String prefix) {
	  
	  if(w.element().getRight()) {
		  Position<Pair<Character, Boolean>> e = w;
		  String a = "";
		  while(!e.equals(tree.root())) {
			  a = (e.element().getLeft()+ "" ).concat(a);
			  e = tree.parent(e);
		  }
		  
		  res.addLast(a);
	  }
	  
	  for(Position<Pair<Character, Boolean>> v : tree.children(w)) {
		  res = preorder(v, res, prefix);
	  }
	  
	  return res;
  }
  
  public Position<Pair<Character, Boolean>> findPos(String word){
	  Position<Pair<Character, Boolean>> pos = tree.root();
	  int contador = 0;
	  while(contador < word.length()) {
		  Position<Pair<Character, Boolean>> e = searchChildLabelled(word.charAt(contador), pos);
		  if(e!= null) pos = e; 
		  contador++;
		  if(contador == word.length()) return e;
	  }
	  return null;
  }
}
