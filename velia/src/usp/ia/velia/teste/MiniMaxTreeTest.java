package usp.ia.velia.teste;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import usp.ia.velia.minimax.MiniMaxTree;
import usp.ia.velia.minimax.MiniMaxInfo;
import usp.ia.velia.tree.TreeNode;

public class MiniMaxTreeTest {

    
    @Test
    public void testBuildTree() {
        
        MiniMaxTree<String> tree = new MiniMaxTree<String>();
        TreeNode<MiniMaxInfo<String>> root = tree.getRoot();
        
        TreeNode<MiniMaxInfo<String>> node1 = tree.createNode(root, "A"); 
        tree.createLeaf(node1, "D", 12); 
        tree.createLeaf(node1, "E", 16);

        node1 = tree.createNode(root, "B"); 
        tree.createLeaf(node1, "F", 8); 
        tree.createLeaf(node1, "G", 0);

        String expected = "[root [A [D 12][E 16]][B [F 8][G 0]]]";
        String value = tree.getTree().polishNotation();
        
        assertEquals(expected, value);
    }
    
    @Test 
    public void testPlayMax() {
        
        MiniMaxTree<String> tree = new MiniMaxTree<String>();
        TreeNode<MiniMaxInfo<String>> root = tree.getRoot();
        
        TreeNode<MiniMaxInfo<String>> node1 = tree.createNode(root, "A"); 
        tree.createLeaf(node1, "C", 12);
        tree.createLeaf(node1, "D", 8);
        tree.createLeaf(node1, "E", 6);

        node1 = tree.createNode(root, "B");
        tree.createLeaf(node1, "F", 16);
        tree.createLeaf(node1, "G", 19);
        
        tree.finishTree();
        
        // testa estado da árvore
        String value = tree.valuesInPolishNotation();
        String expected = "[root 16 [A 6 [C 12 ][D 8 ][E 6 ]][B 16 [F 16 ][G 19 ]]]";
        assertEquals(expected, value);
        
        // testa jogada a ser feita
        String jogada = tree.getNextJogada();
        expected = "B";
        assertEquals(expected, jogada);
    }
    
    @Test 
    public void testPlayMax2() {
        

        MiniMaxTree<String> tree = new MiniMaxTree<String>();
         TreeNode<MiniMaxInfo<String>> root = tree.getRoot();
         
         TreeNode<MiniMaxInfo<String>> node1 = tree.createNode(root, "A"); 
         TreeNode<MiniMaxInfo<String>> node2 = tree.createNode(node1, "C");
         tree.createLeaf(node2, "F", 10);
         tree.createLeaf(node2, "G", 20);
         node2 = tree.createNode(node1, "D");
         tree.createLeaf(node2, "H", 15);

         node1 = tree.createNode(root, "B");
         node2 = tree.createNode(node1, "E");
         tree.createLeaf(node2, "I", 13);
         
         tree.finishTree();
         
         // testa estado da árvore
         String value = tree.valuesInPolishNotation();
         String expected = "[root 15 [A 15 [C 20 [F 10 ][G 20 ]][D 15 [H 15 ]]][B 13 [E 13 [I 13 ]]]]";  
         assertEquals(expected, value);
         
         // testa jogada a ser feita
         String jogada = tree.getNextJogada();
         expected = "A";
         assertEquals(expected, jogada);         
    }

}
