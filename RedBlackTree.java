// CODE FOR RED-BLACK TREES BASED ON CODE FROM Bibeknam ON GITHUB
// https://github.com/Bibeknam/algorithmtutorprograms/blob/master/data-structures/red-black-trees/RedBlackTree.java

// Red Black Tree implementation in Java
// Author: Algorithm Tutor
// Tutorial URL: https://algorithmtutor.com/Data-Structures/Tree/Red-Black-Trees/

import java.io.*;
import java.util.*;

// data structure that represents a node in the tree
class Node {
	Vector<Word> data = new Vector<Word>(); // holds the vector of words
	String letter; // letter each word in node starts with
	Node parent; // pointer to the parent
	Node left; // pointer to left child
	Node right; // pointer to right child
	int color; // 1 . Red, 0 . Black
}


// class RedBlackTree implements the operations in Red Black Tree
public class RedBlackTree {
	private Node root;
	private Node TNULL;

	// print out the entire tree in InOrder
	private void inOrderHelper(Node node, String compType, FileWriter outfile) {
		if (node != TNULL) {
			inOrderHelper(node.left, compType, outfile); // process left side first
			
			// process current node
			// if looking for equal counts, write to text file
			if (compType == "equal")
			{
				// iterate through node's data vector
				for (int index = 0; index < node.data.size(); index++)
				{
					Word currWord = node.data.elementAt(index); // placeholder to shorten code
					
					// if counts are equal, add word to file
					if (currWord.getCountPT() == currWord.getCountYT())
					{
						try	{ outfile.write(currWord.getValue() + "		" + currWord.getCountPT() + "\n"); }
						
						catch (IOException e)
						{
							System.out.println("An error occured.");
							e.printStackTrace(); // prints error(s)
							System.exit(0); // Exits entire program
						}
					}
				}
			}
			
			// if looking for different counts, write to text file
			else if (compType == "difference")
			{
				// iterate through node's data vector
				for (int index = 0; index < node.data.size(); index++)
				{
					try
					{
						Word currWord = node.data.elementAt(index); // placeholder to shorten code
						
						// if current word has unequal counts, write to text file
						// if PT has higher count, calculate difference by subtracting YT from PT
						if (currWord.getCountPT() > currWord.getCountYT())
						{
							// if YT count is zero, output difference with " - ZERO"
							if (currWord.getCountYT() == 0) {outfile.write(currWord.getValue() + "		+" + currWord.getCountPT() + " PT - ZERO\n");}
							
							// otherwise, calculate difference
							else
							{
								int difference = currWord.getCountPT() - currWord.getCountYT();
								outfile.write(currWord.getValue() + "		+" + difference + " PT\n");
							}
						}
						
						// if YT has higher count, calculate difference by subtracting PT from YT
						else if (currWord.getCountPT() < currWord.getCountYT())
						{
							// if PT count is zero, output difference with " - ZERO"
							if (currWord.getCountPT() == 0) {outfile.write(currWord.getValue() + "		+" + currWord.getCountYT() + " YT - ZERO\n");}
							
							// otherwise, calculate difference
							else
							{
								int difference = currWord.getCountYT() - currWord.getCountPT();
								outfile.write(currWord.getValue() + "		+" + difference + " YT\n");
							}
						}
					}
						
					catch (IOException e)
					{
						System.out.println("An error occured.");
						e.printStackTrace(); // prints error(s)
						System.exit(0); // Exits entire program
					}
				}
			}
			
			// process right side
			inOrderHelper(node.right, compType, outfile);
		} 
	}

	// searches for a key in the tree using recursion
	private Node searchTreeHelper(Node node, String key) {
		// if node matches key or is TNULL, returns the current node
		if (node == TNULL || key == node.letter) {
			return node;
		}

		// if the key is less than the current node, check the left subtree
		if (key.compareTo(node.letter) == -1) {
			return searchTreeHelper(node.left, key);
		} 
		// otherwise, check the right subtree
		return searchTreeHelper(node.right, key);
	}

	// fix the rb tree modified by the delete operation
	private void fixDelete(Node x) {
		Node s; // new node for fixing operations
		
		// while the current node is not the root and is black
		while (x != root && x.color == 0) {
			// if current node is a left child
			if (x == x.parent.left) {
				s = x.parent.right; // set s to x's parent's right child
				// if s is red
				if (s.color == 1) {
					// case 3.1
					s.color = 0; // set s to black
					x.parent.color = 1; // set parent node to red
					leftRotate(x.parent); // rotate parent node
					s = x.parent.right; // set s to new right child of x's parent
				}

				// if s's children are black
				if (s.left.color == 0 && s.right.color == 0) {
					// case 3.2
					s.color = 1; // set s to red
					x = x.parent; // set x to it's parent
				} else {
					// otherwise, if the right child is black
					if (s.right.color == 0) {
						// case 3.3
						s.left.color = 0; // also set left child to black
						s.color = 1; // set s to red
						rightRotate(s); // rotate s
						s = x.parent.right; // set s to x's parent's right child
					} 

					// case 3.4
					s.color = x.parent.color; // set s to the color of x's parent
					x.parent.color = 0; // set parent to black
					s.right.color = 0; // set s's right child to black
					leftRotate(x.parent); // rotate parent node
					x = root; // set x to the root
				}
			}
			// if current node is a right child
			else {
				s = x.parent.left; // set s to x's parent's left child
				
				// if s is red
				if (s.color == 1) {
					// case 3.1
					s.color = 0; //set s to black
					x.parent.color = 1; // set parent to red
					rightRotate(x.parent); // rotate parent node
					s = x.parent.left; // set s to new left child of x's parent
				}

				// if s's children are black
				if (s.left.color == 0 && s.right.color == 0) {
					// case 3.2
					s.color = 1; // set s to red
					x = x.parent; // set x to its parent
				} else {
					// otherwise, if s's left child is black
					if (s.left.color == 0) {
						// case 3.3
						s.right.color = 0; // set right child to black
						s.color = 1; // set s to red
						leftRotate(s); // rotate s
						s = x.parent.left; // set s to x's parent's left child
					} 

					// case 3.4
					s.color = x.parent.color; // set s to color of x's parent
					x.parent.color = 0; // set x's parent to black
					s.left.color = 0; // set s's left child to black
					rightRotate(x.parent); // rotate parent
					x = root; // set x to the root
				}
			} 
		}
		// make sure root is black when done
		x.color = 0;
	}


	private void rbTransplant(Node u, Node v){
		// if u is the root node
		if (u.parent == null) {
			root = v; // reset root to v
		}
		// if u is u's parent's left child
		else if (u == u.parent.left){
			u.parent.left = v; // reset parent's left child to v
		}
		// otherwise, reset parent's right child to v
		else {
			u.parent.right = v;
		}
		// set v's parent to the same as u
		v.parent = u.parent;
	}

	private void deleteNodeHelper(Node node, Vector<Word> key) {
		// find the node containing key
		Node z = TNULL;
		Node x, y;
		
		// while node is not a null node
		while (node != TNULL){
			// if node is equal to key to remove, save in z
			if (key.elementAt(0).getValue().compareTo(node.data.elementAt(0).getValue()) == 0) {
				z = node;
			}

			// if the node is less than the key, check right child
			if ((key.elementAt(0).getValue().compareTo(node.data.elementAt(0).getValue()) == 1) || (key.elementAt(0).getValue().compareTo(node.data.elementAt(0).getValue()) == 0)) {
				node = node.right;
			}
			// otherwise, check left child
			else {
				node = node.left;
			}
		}

		// if z is still null then key was not found in tree and break out of function
		if (z == TNULL) {
			System.out.println("Couldn't find key in the tree");
			return;
		} 

		// if node was found, set y to found node
		y = z;
		int yOriginalColor = y.color; // set variable to color of y
		
		// if z's left child is null
		if (z.left == TNULL) {
			x = z.right; // set x to z's right child
			rbTransplant(z, z.right); // transplant z's right child to z
		}
		// if z's right child is null
		else if (z.right == TNULL) {
			x = z.left; // set x to z's left child
			rbTransplant(z, z.left); // transplant z's left child to z
		}
		// otherwise if neither child is null
		else {
			y = minimum(z.right); // set y to smallest node in z's right subtree
			yOriginalColor = y.color; // set variable for y's color
			x = y.right; // set x to y's right child
			// if z is the parent of y
			if (y.parent == z) {
				x.parent = y; // set x's parent to y
			} else {
				rbTransplant(y, y.right); // transplant y's right child to y
				y.right = z.right; // set new y right child to z's right child
				y.right.parent = y; // set the parent of y's right child to y
			}

			rbTransplant(z, y); // transplant y to z
			y.left = z.left; // set y's left child to z's left child
			y.left.parent = y; // set y's left child's parent to y
			y.color = z.color; // set y's color to z's color
		}
		
		// if original color of y is black
		if (yOriginalColor == 0){
			fixDelete(x); // rebalance tree
		}
	}
	
	// fix the red-black tree
	private void fixInsert(Node k){
		Node u;
		
		// while k's parent is red
		while (k.parent.color == 1) {
			
			// if k's parent is a right child
			if (k.parent == k.parent.parent.right) {
				u = k.parent.parent.left; // set u to k's grandparent's left child aka k's uncle
				
				// if k's uncle is red
				if (u.color == 1) {
					// case 3.1
					u.color = 0; // set uncle to black
					k.parent.color = 0; // set parent to black
					k.parent.parent.color = 1; // set grandparent to red
					k = k.parent.parent; // set k to grand parent
				} else {
					// if k is a left child
					if (k == k.parent.left) {
						// case 3.2.2
						k = k.parent; // set k to parent
						rightRotate(k); // rotate k
					}
					// case 3.2.1
					k.parent.color = 0; // set k's parent to black
					k.parent.parent.color = 1; // set k's grandparent to red
					leftRotate(k.parent.parent); // rotate grandparent
				}
			}
			// if k's parent is a left child
			else {
				u = k.parent.parent.right; // set u to k's grandparent's right child aka k's uncle

				if (u.color == 1) {
					// mirror case 3.1
					u.color = 0; // set uncle to black
					k.parent.color = 0; // set parent to black
					k.parent.parent.color = 1; // set grandparent to red
					k = k.parent.parent; // set k to grandparent
				} else {
					// if k is a right child
					if (k == k.parent.right) {
						// mirror case 3.2.2
						k = k.parent; // set k to parent
						leftRotate(k); // rotate k
					}
					// mirror case 3.2.1
					k.parent.color = 0; // set k's parent to black
					k.parent.parent.color = 1; // set k's grandparent to red
					rightRotate(k.parent.parent); // rotate grandparent
				}
			}
			
			// if k is the root, nothig left to do
			if (k == root) {
				break;
			}
		}
		// make sure root is black
		root.color = 0;
	}

	public RedBlackTree() {
		TNULL = new Node();
		TNULL.color = 0;
		TNULL.left = null;
		TNULL.right = null;
		root = TNULL;
	}

	// In-Order traversal
	// Left Subtree . Node . Right Subtree
	public void inorder(String compType, FileWriter outfile) {
		inOrderHelper(this.root, compType, outfile);
	}

	// search the tree for the key k
	// and return the corresponding node
	public Node searchTree(String k) {
		return searchTreeHelper(this.root, k);
	}

	// find the node with the minimum key
	public Node minimum(Node node) {
		while (node.left != TNULL) {
			node = node.left;
		}
		return node;
	}

	// find the node with the maximum key
	public Node maximum(Node node) {
		while (node.right != TNULL) {
			node = node.right;
		}
		return node;
	}

	// find the successor of a given node
	public Node successor(Node x) {
		// if the right subtree is not null,
		// the successor is the leftmost node in the
		// right subtree
		if (x.right != TNULL) {
			return minimum(x.right);
		}

		// else it is the lowest ancestor of x whose
		// left child is also an ancestor of x.
		Node y = x.parent;
		while (y != TNULL && x == y.right) {
			x = y;
			y = y.parent;
		}
		return y;
	}

	// find the predecessor of a given node
	public Node predecessor(Node x) {
		// if the left subtree is not null,
		// the predecessor is the rightmost node in the 
		// left subtree
		if (x.left != TNULL) {
			return maximum(x.left);
		}

		Node y = x.parent;
		while (y != TNULL && x == y.left) {
			x = y;
			y = y.parent;
		}

		return y;
	}

	// rotate left at node x
	public void leftRotate(Node x) {
		Node y = x.right;
		x.right = y.left;
		if (y.left != TNULL) {
			y.left.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == null) {
			this.root = y;
		} else if (x == x.parent.left) {
			x.parent.left = y;
		} else {
			x.parent.right = y;
		}
		y.left = x;
		x.parent = y;
	}

	// rotate right at node x
	public void rightRotate(Node x) {
		Node y = x.left;
		x.left = y.right;
		if (y.right != TNULL) {
			y.right.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == null) {
			this.root = y;
		} else if (x == x.parent.right) {
			x.parent.right = y;
		} else {
			x.parent.left = y;
		}
		y.right = x;
		x.parent = y;
	}

	// insert new data vector into Red-Black Tree
	public void insert(Vector<Word> key) {
		Node node = new Node();
		
		// iterate through vector because first entry is actually letter for node label
		for (int index = 0; index < key.size(); index++)
		{
			// define name/letter of node
			if (index == 0) { node.letter = key.elementAt(0).getValue(); }
			
			// otherwise, just add to node's vector
			else
			{
				Word newWord = (Word) key.elementAt(index);
				node.data.add(newWord);
			}
		}
		
		node.parent = null;
		node.left = TNULL;
		node.right = TNULL;
		node.color = 1; // new node must be red

		Node y = null;
		Node x = this.root;

		while (x != TNULL) {
			y = x;
			if ((node.data.elementAt(0).getValue().compareTo(x.data.elementAt(0).getValue())) == -1) {
				x = x.left;
			} else {
				x = x.right;
			}
		}

		// y is parent of x
		node.parent = y;
		if (y == null) {
			root = node;
		} else if ((node.data.elementAt(0).getValue().compareTo(y.data.elementAt(0).getValue())) == -1) {
			y.left = node;
		} else {
			y.right = node;
		}

		// if new node is a root node, simply return
		if (node.parent == null){
			node.color = 0;
			return;
		}

		// if the grandparent is null, simply return
		if (node.parent.parent == null) {
			return;
		}

		// Fix the tree
		fixInsert(node);
	}

	public Node getRoot(){
		return this.root;
	}

	// delete the node from the tree
	public void deleteNode(Vector<Word> data) {
		deleteNodeHelper(this.root, data);
	}
}
