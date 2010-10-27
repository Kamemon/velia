package usp.ia.velia.minimax;

import usp.ia.velia.Jogada;
import usp.ia.velia.tree.Tree;
import usp.ia.velia.tree.TreeNode;

public class ArvoreMiniMax {

	private TreeNode<MiniMaxInfo<Jogada>> root = new TreeNode<MiniMaxInfo<Jogada>>("root");
	private Tree arvore = new Tree(root);
	
	// TODO: método pra inserir nó
	// tem q definir se nó é MINI ou MAX
	
	// TODO: método pradizer que a árvore tá pronta
	// tem q fazer o cálculo MINIMAX (atribuir as heurísticas dos nós internos)
	
	// TODO: método pra pegar a jogada a ser efetuada
}
