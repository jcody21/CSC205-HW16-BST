/*
 * BST (Binary Search Tree) assignment.
 *
 * @creator John Cody
 * @created 02019.04.24
 */

import java.util.ArrayList;

public class BST {

   public Node root;
   public int nodecnt;
   public char nextdata = 'A';

   public BST inOrderTraversal(Node node) {
      if (root == null) return this;
      if (node == null) node = root;
      if (node.left != null) 
         inOrderTraversal(node.left);
      node.visit(); 
      if (node.right != null) 
         inOrderTraversal(node.right);
      return this;
   }

   public BST preOrderTraversal(Node node) {
       if(root == null) return this;
       if (node == null) node = root;
       node.visit(); 
       if (node.left != null) 
           preOrderTraversal(node.left);
       if (node.right != null) 
          preOrderTraversal(node.right);
       return this;
   }

   public BST postOrderTraversal(Node node) {
       if(root == null) return this;
       if (node == null) node = root;
       if (node.left != null) 
           postOrderTraversal(node.left);
       if (node.right != null) 
           postOrderTraversal(node.right);
       node.visit(); 
       return this;
   }

   public BST levelOrderTraversal(Node node) {
      ArrayList Q = new ArrayList();
      Q.add(root);
      while(!Q.isEmpty()) {
          node = (Node)Q.remove(0);
          node.visit();
          if(node.left != null)  Q.add(node.left);
          if(node.right != null) Q.add(node.right);
      }
      return this;
   }

   public BST insert(int key) {
      if (root == null) {
         root = new Node(key, null, nextdata++);
         nodecnt++;
         return this;
      }
      return insert(key, root);
   }

   public BST insert(int key, Node parent) {
      if (key == parent.key) return this;
      nodecnt++;
      if (key < parent.key) {
         if (parent.left == null) {
            parent.left = new Node(key, parent, nextdata++);
            return this;
         }
         return insert(key, parent.left);
      } 
      if (parent.right == null) {
         parent.right = new Node(key, parent, nextdata++);
         return this;
      }
      return insert(key, parent.right);
   }

   public BST delete(int key) {
      Node replacement = null;
      Node n = find(key);
      if(n == null) return this; // the node does not exist in the BST
      
      if(n.left == null && n.right == null) { //case if the node has no kids
          if(n.parent != null){
              if(n.parent.left  == n) n.parent.left  = null;
              if(n.parent.right == n) n.parent.right = null;
          }
          else{
              root = null;
          }
          return this;
      }
      
      if(n.left != null && n.right == null) { //case if node has 1 kid
          if(n.parent != null) {
            if(n.parent.left  == n) n.parent.left  = n.left;
            if(n.parent.right == n) n.parent.right = n.left;
          }
          else{
              root = n.left;
          }
          n.left.parent = n.parent;
          return this;
      }
      if(n.left == null && n.right != null) { //case if node has 1 kid
          if(n.parent != null) {
            if(n.parent.left  == n) n.parent.left  = n.right;
            if(n.parent.right == n) n.parent.right = n.right;
          }
          else{
              root = n.right;
          }
          n.right.parent = n.parent;
          return this;
      }
      
      if(n.left != null && n.right != null) { //case if node has 2 kids
          replacement = n.right;
          int counter = 0;
          while(replacement.left != null) {
              replacement = replacement.left;
              counter++;
          }
          if(counter > 0) {
              replacement.parent.left = replacement.right;
          }
          else{
              replacement.parent.right = replacement.right;
          }
          if(replacement.right != null){
              replacement.right.parent = replacement.parent;
          }
          replacement.left  = n.left;
          replacement.right = n.right;
          n.left.parent  = replacement;
          if(n.right != null){
              n.right.parent = replacement;
          }
          replacement.parent = n.parent;
          if(n.parent != null) {
              if(n.parent.left  == n) n.parent.left  = replacement; 
              if(n.parent.right == n) n.parent.right = replacement;
          }
          else{
              root = replacement;
          }
          return this;
      }
      return this;
   }

   public Node find(int key) {
      if (root == null) return null;
      return find(key, root);
   }

   public Node find(int key, Node n) {
      Node l = null, r = null;
      if(n.key == key)
          return n;
      
      if(n.left != null) l = find(key, n.left);
      if(l != null) return l;
      
      if(n.right != null) r = find(key, n.right);
      if(r != null) return r;
      
      return null;
   }

   public Node min(Node n) {
      Node l = null, r = null;
      if(n.left  != null) l = min(n.left);
      if(n.right != null) r = min(n.right);
      
      if(l == null) {
          if(r == null) return n;
          if(r.key < n.key) return r;
          return n;
      }
      if(r == null){
          if(l.key < n.key) return l;
          return n;
      }
      if(r.key < l.key){
          if(r.key < n.key) return r;
          return n;
      }
      if(l.key < r.key) {
          if(l.key < n.key) return l;
          return n;
      }
      return n; //one of the previous statements should always be called
   }

   public Node max(Node n) {
      Node l = null, r = null;
      if(n.left  != null) l = max(n.left);
      if(n.right != null) r = max(n.right);
      
      if(l == null) {
          if(r == null) return n;
          if(r.key > n.key) return r;
          return n;
      }
      if(r == null){
          if(l.key > n.key) return l;
          return n;
      }
      if(r.key > l.key){
          if(r.key > n.key) return r;
          return n;
      }
      if(l.key > r.key) {
          if(l.key > n.key) return l;
          return n;
      }
      return n; //one of the previous statements should always be called
   }

   /*
    * BST.main(String[] argv) is used to test class BST
    */
   public static void main(String[] argv) {

      int[] a = { 30, 20, 40, 18, 25, 24, 27, 23, 21, 22, 29, 35, 42 };

      // testing delete...
      for (int i = 0; i < a.length; i++)
         delete(init(a), a[i]);
      
      // testing min and max...
      BST tree = init(a);
      System.out.println("min = " + tree.min(tree.root).key +
                         "; max = " + tree.max(tree.root).key);

      // testing find...
      int[] f = { a[0], a[a.length-1], 205, a[a.length/2] };
      for (int i = 0; i < f.length; i++)
         System.out.println("find(" + f[i] + "): " + 
                            ((tree.find(f[i]) == null) ? 
                             "not found" : "found"));

      // testing level-order traversal...
      System.out.println("\nlevel-order traversal:");  
      tree.levelOrderTraversal(null);

      // testing deleting/traversal small trees (1, 2, 3 nodes)...
      deleteSmallTrees();

   }

   static BST init(int[] a) {
      BST tree = new BST();
      System.out.print("\ninputs: ");
      for (int i = 0; i < a.length; i++) {
         tree.insert(a[i]);
         System.out.print(a[i] + " ");
      }
      System.out.println();
      return tree;
   }

   static void delete(BST tree, int key) {
      tree.preOrderTraversal(null);
      System.out.println("\ndelete(" + key + ")...");
      tree.delete(key);
      tree.preOrderTraversal(null);
   }

   static void deleteSmallTrees() {

      BST t = new BST();
      System.out.println("\ninputs: 10");
      t.insert(10);
      t.levelOrderTraversal(null);
      System.out.println("\ndelete(10)...");
      t.delete(10);
      if (t.root != null) {
         System.out.println("\t??? there should be no traversal");
         t.levelOrderTraversal(null);
      } else
         System.out.println("\tempty tree (no traversal)");

      t = new BST();
      System.out.println("\ninputs: 10 5");
      t.insert(10);
      t.insert(5);
      t.inOrderTraversal(null);
      System.out.println("\ndelete(10)...");
      t.delete(10);
      t.inOrderTraversal(null);

      t = new BST();
      System.out.println("\ninputs: 10 15");
      t.insert(10);
      t.insert(15);
      t.inOrderTraversal(null);
      System.out.println("\ndelete(10)...");
      t.delete(10);
      t.inOrderTraversal(null);

      t = new BST();
      System.out.println("\ninputs: 10 15 5");
      t.insert(10);
      t.insert(15);
      t.insert(5);
      t.inOrderTraversal(null);
      System.out.println("\ndelete(10)...");
      t.delete(10);
      t.inOrderTraversal(null);

   }

   static void printTraversals(BST tree) {
      System.out.println("\nin-order:");    
      tree.inOrderTraversal(null);

      System.out.println("\npre-order traversal:");   
      tree.preOrderTraversal(null);

      System.out.println("\npost-order traversal:");  
      tree.postOrderTraversal(null); 

      System.out.println("\nlevel-order traversal:");  
      tree.levelOrderTraversal(null); 
   }
}

class Node {

   public int key; 
   public char data;
   public Node parent;
   public Node left;
   public Node right;

   public Node(int k, Node p, char d) { 
      this(k, p, d, null, null);
   }

   public Node(int k, Node p, char d, Node l, Node r) { 
      key = k; 
      parent = p;
      data = d;
      right = r; 
      left = l;
   }

   public void visit() { 
      System.out.print("\t" + key);
      if (parent == null) 
         System.out.print(" (root node)");
      else
         System.out.print(" (parent: " + parent.key + ")"); 
      System.out.print(" left: ");
      if (left == null) System.out.print("na");
      else System.out.print(left.key);
      System.out.print("; right: ");
      if (right == null) System.out.print("na");
      else System.out.print(right.key);
      System.out.println("; " + data);
   }
}