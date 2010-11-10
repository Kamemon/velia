package usp.ia.velia.tree;

public class Tree<T> {

    private TreeNode<T> root;

    public Tree(TreeNode<T> root) {
        super();
        this.root = root;
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    public TreeNode<T> getNextDFSNode() {
        return null;
    }
    
    public String polishNotation() {
        return root.polishNotation();    
    }
    
    public TreeNode<T> search(String label) {
        return root.search(label);
    }

    @Override
    public String toString() {
        
        
        return super.toString();
    }

}
