package usp.ia.velia.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa um nó da árvore sintática.
 * 
 * @author Marcelo Li Koga
 * 
 */
public class TreeNode<T> {

    /** Label  */
    private String label;

    private TreeNode<T> parent;
    private List<TreeNode<T>> childNodes;

    /** SyntaTreeNode may have a token assigned */
    private T token;

    /** Depth-first Search index */
    private int dFSIndex;

    public TreeNode(String label) {
        this.label = label;
        this.childNodes = new ArrayList<TreeNode<T>>(2);
        this.dFSIndex = 0;
    }

    /**
     * Creates a new Node, short label will be the same as the label And assigns to it an object
     * 
     * @param shortLabel/label
     */
    public TreeNode(String label, T token) {
        this(label);
        this.token = token;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the childNodes
     */
    public List<TreeNode> getChildNodes() {
        return new ArrayList<TreeNode>(this.childNodes);
    }

    public void setParent(TreeNode<T> parent) {
        this.parent = parent;
    }

    public TreeNode<T> getParent() {
        return this.parent;
    }

    public List<TreeNode> getSiblings() {
        if (this.parent != null) {
            List<TreeNode> siblings = this.parent.getChildNodes();
            siblings.remove(this);
            // A lista é outra, mas os objetos são os mesmos.
            return siblings;
        }
        return Collections.emptyList();
    }

    /**
     * @param childNodes the childNodes to set
     */
    public void setChildNodes(List<TreeNode<T>> childNodes) {
        this.childNodes = childNodes;
    }

    public T getToken() {
        return token;
    }

    public void setToken(T token) {
        this.token = token;
    }

    /* === END OF GETTERS AND SETTERS === */

    /**
     * @param childNode the childNode
     */
    public void addChild(TreeNode childNode) {
        childNode.setParent(this);
        this.childNodes.add(childNode);
    }

    /**
     * Prunes child
     * 
     * @param childNode the childNode
     */
    public void pruneChild(TreeNode childNode) {
        this.childNodes.remove(childNode);
        childNode.setParent(null);
    }

    /**
     * Prunes node itself from parent node
     */
    public void pruneItselfFromParent() {
        this.getParent().pruneChild(this);
    }

    /**
     * Retorna o número de filhos.
     * 
     * @return
     */
    public int getNumberOfChildren() {
        return childNodes.size();
    }

    public boolean isLeaf() {
        return (childNodes.size() == 0);
    }

    /**
     * Retorna o filho de número index, sendo 0 para o primeiro, 1 para o segundo, etc.
     * 
     * @param index
     * @return filho
     */
    public TreeNode getChild(int index) {
        return this.childNodes.get(index);
    }

    public String polishNotation() {
        if (this.isLeaf()) {
            if (this.token != null)
                return "[" + this.label + " " + this.token.toString() + "]";
        }
        int n = this.getNumberOfChildren();
        String str = "";
        //        if (n == 1) {
        //            str = this.getChild(0).polishNotation();
        //        } else {
        for (int i = 0; i < n; i++) {
            str = str + this.getChild(i).polishNotation();
        }
        //        }
        return "[" + this.label + " " + str + "]";
    }

    /**
     * Searches inside this tree for a node labeled label.
     * 
     * @param label
     * @return null, if not found.
     */
    public TreeNode<T> search(String label) {
        if (this.label.equals(label)) {
            return this;
        }
        for (TreeNode<T> node : this.childNodes) {
            TreeNode<T> resp = node.search(label);
            if (resp != null)
                return resp;
        }
        return null;
    }

    /**
     * Retuans a list with all Leafs
     * @return
     */
    public List<TreeNode> getAllLeafs() {
        List<TreeNode> leafs = new ArrayList<TreeNode>();
        if (this.isLeaf()) {
            leafs.add(this);
        } else {
            for (TreeNode child : this.childNodes) {
                leafs.addAll(child.getAllLeafs());
            }
        }
        return leafs;
    }

    /**
     * Nao funciona ainda
     * @return
     */
    public TreeNode getNextDFSNode() {
        if (this.dFSIndex >= 0 && this.dFSIndex < childNodes.size()) {
            TreeNode next = childNodes.get(this.dFSIndex);
            this.dFSIndex++;
            return next;
        } else
            return null;
    }

    @Override
    public String toString() {
        return this.label;
    }

}
