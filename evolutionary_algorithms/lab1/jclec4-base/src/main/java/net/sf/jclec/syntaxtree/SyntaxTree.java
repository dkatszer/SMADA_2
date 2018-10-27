package net.sf.jclec.syntaxtree;

import net.sf.jclec.JCLEC;

import net.sf.jclec.exprtree.ExprTree;

/**
 * Syntax tree.
 * 
 * This object represents the preorder traversal of a syntax-tree.
 * 
 * @author Sebastian Ventura
 */

public class SyntaxTree implements JCLEC 
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////

	/** Generated by Eclipse */
	
	private static final long serialVersionUID = 6978139392787525827L;

	/////////////////////////////////////////////////////////////////
	// --------------------------------------------- Parse tree nodes
	/////////////////////////////////////////////////////////////////

	/** This tree nodes */
	
	private SyntaxTreeNode [] nodes = new SyntaxTreeNode[10];
	
	/** Actual tree size */
	
	private int treeSize = 0;

	/** Actual derivation size */
	
	private int derivSize = 0;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty constructor
	 */
	
	public SyntaxTree() 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////
		
	// Tree nodes
	
	/**
	 * Add a node to this tree.
	 * 
	 * @param node Node to add
	 */
	
	public void addNode(SyntaxTreeNode node)
	{
		if (treeSize == nodes.length) {
			SyntaxTreeNode [] aux = new SyntaxTreeNode[2*treeSize];
			for (int i=0; i<treeSize; i++) 
				aux[i] = nodes[i];
			nodes = aux;
		}
		if (node.arity() != 0) {
			derivSize++;
		}
		nodes[treeSize++] = node;
	}

	/**
	 * Access to a tree node, giving its index.
	 * 
	 * @param nodeIndex Node index.
	 * 
	 * @return Desired node
	 */
	
	public SyntaxTreeNode getNode(int nodeIndex)
	{
		return nodes[nodeIndex];
	}
	
	/**
	 * Set a tree node.
	 * 
	 * @param node New node
	 * @param nodeIndex Node index
	 */
	
	public void setNode(SyntaxTreeNode node, int nodeIndex)
	{
		nodes[nodeIndex] = node;		
	}
	
	// Expression size
	
	/**
	 * Tree size (number of nodes).
	 * 
	 * @return tree size
	 */
	
	public int size()
	{
		return treeSize;
	}
	
	/**
	 * Number of derivations in this tree.
	 * 
	 * @return Number of derivations in this tree
	 */
	public int derivSize()
	{
		return derivSize;		
	}
	
	// Copy method
	
	/**
	 * Copy method.
	 * 
	 * @return A copy of this syntax tree
	 */
	
	public SyntaxTree copy()
	{
		// Copy code
		SyntaxTreeNode [] nodesCopy = new SyntaxTreeNode[nodes.length];
		for (int i=0; i<treeSize; i++) 
			nodesCopy[i] = nodes[i].copy();
		// Copy expression
		SyntaxTree result = new SyntaxTree();
		// Set treeSize
		result.treeSize = treeSize;
		// Set derivSize
		result.derivSize = derivSize;
		// Set tree nodes
		result.nodes = nodesCopy;
		// Return result
		return result;
	}
	
	// Clear tree
	
	/**
	 * Remove all nodes of this tree
	 */

	public void clear() 
	{
		treeSize = derivSize = 0;
	}		

	// Subtrees location
	
	/**
	 * Return the index ...
	 * 
	 * @param blockIndex Index of subtree root
	 * 
	 * @return Index of node inmediately after this subtree
	 */
	
	public int subTree(int blockIndex)
	{
		int resultIndex = blockIndex;
		// Number of remaining sons
		int aux = 0;
		do {
			// Increase aux to the actual node arity
			aux += nodes[resultIndex].arity();				
			// Increment resultIndex
			resultIndex++;			
		}		
		while (aux-- != 0);
		return resultIndex;
	}
	
	// Expression tree generation

	/**
	 * Generate an expression tree, using all terminal symbols in this
	 * tree.
	 * 
	 * @return Expression tree 
	 */
	
	public ExprTree getExprTree()
	{
		ExprTree result = new ExprTree();
		for (int i=0; i<treeSize; i++) {
			if(nodes[i].arity() == 0) {
				result.addBlock(((TerminalNode) nodes[i]).getCode());
			}
		}
		return result;
	}
	
	/////////////////////////////////////////////////////////////////
	// ------------------------- Overwriting java.lang.Object methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("(");
		for (int i=0; i<treeSize; i++) {
			sb.append(nodes[i]);
			if (i != treeSize-1) {
				sb.append(" ");
			}
			else {
				sb.append(")");
			}
		}
		sb.append(" -> "+this.derivSize);
		return sb.toString();
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public boolean equals(Object other)
	{
		if (other instanceof SyntaxTree) {
			SyntaxTree cother = (SyntaxTree) other;
			if (treeSize == cother.treeSize && derivSize == cother.derivSize) {
				for (int i=0; i<treeSize; i++) {
					if (! nodes[i].equals(cother.nodes[i])) 
						return false;
				}
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
}
