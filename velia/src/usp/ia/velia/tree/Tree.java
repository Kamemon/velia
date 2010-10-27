package usp.ia.velia.tree;

public class Tree {

    private TreeNode root;

    public Tree(TreeNode root) {
        super();
        this.root = root;
    }

    public TreeNode getRoot() {
        return root;
    }

    public TreeNode getNextDFSNode() {
        return null;
    }
    
    public String polishNotation() {
        return root.polishNotation();    
    }
    
    public TreeNode search(String label) {
        return root.search(label);
    }

    @Override
    public String toString() {
        
        
        return super.toString();
    }

}
