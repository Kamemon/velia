package usp.ia.velia.minimax;

import java.util.ArrayList;
import java.util.List;

import usp.ia.velia.minimax.MiniMaxInfo.Tipo;
import usp.ia.velia.tree.Tree;
import usp.ia.velia.tree.TreeNode;

/**
 * Representa a árvore MINI-MAX Responsável por determinar qual será a jogada do agente
 * 
 * @param <T> jogada: para que o método MINI-MAX possa ser re-aproveitado para qualquer tipo de jogo, a classe que
 *            define o que é uma jogada é genérica (pelo mesmo motivo, deve ser o agente que tem de construir a árvore,
 *            através de chamadas de insertNode)
 */
public class ArvoreMiniMax<T> {

    private final String ROOT_LABEL = "root";

    private TreeNode<MiniMaxInfo<T>> root = new TreeNode<MiniMaxInfo<T>>(ROOT_LABEL);
    private Tree<MiniMaxInfo<T>> arvore = new Tree<MiniMaxInfo<T>>(root);
    private List<TreeNode<MiniMaxInfo<T>>> leafs = new ArrayList<TreeNode<MiniMaxInfo<T>>>();

    /**
     * 
     * @param jogo Estado atual do jogo (quando é a vez de MAX jogar)
     */
    public ArvoreMiniMax() {

        this.root.setToken(new MiniMaxInfo<T>());
        this.root.getToken().setTipo(MiniMaxInfo.Tipo.MAX); // raiz é MAX (sem jogada associada)
    }

    public TreeNode<MiniMaxInfo<T>> getRoot() {

        return this.root;
    }

    /**
     * Use com responsabilidade
     * 
     * @return a árvore mini-max
     */
    public Tree<MiniMaxInfo<T>> getTree() {

        return this.arvore;
    }

    /**
     * Cria um novo nó na árvore mini-max 
     * Após construir a árvore com sucessiva chamadas deste método, chame finishTree
     * 
     * @param pai nó pai (comece usando a raiz)
     * @param jogada correspondência entre o nó e a jogada
     * @return nó criado (use essa referência para inserir os filhos do nó criado)
     */
    public TreeNode<MiniMaxInfo<T>> createNode(TreeNode<MiniMaxInfo<T>> pai, T jogada) {

        // cria nó e insere na árvore
        String label = jogada.toString();
        TreeNode<MiniMaxInfo<T>> node = new TreeNode<MiniMaxInfo<T>>(label);
        pai.addChild(node);

        // configura nó criado
        node.setToken(new MiniMaxInfo<T>());
        MiniMaxInfo<T> info = node.getToken();
        info.setJogada(jogada);
        if (pai.getToken().getTipo() == MiniMaxInfo.Tipo.MAX)
            info.setTipo(MiniMaxInfo.Tipo.MINI);
        else
            info.setTipo(MiniMaxInfo.Tipo.MAX);
        
        return node;
    }

    /**
     * Cria um novo nova folha na árvore mini-max 
     * Após construir a árvore com sucessiva chamadas deste método, chame finishTree
     * 
     * @param pai nó pai (comece usando a raiz)
     * @param jogada correspondência entre o nó e a jogada
     * @param heuristica da situação do jogo após jogada ser efetuada
     */
    public void createLeaf(TreeNode<MiniMaxInfo<T>> pai, T jogada, Integer heuristica) {

        TreeNode<MiniMaxInfo<T>> leaf = createNode(pai, jogada);
        leaf.getToken().setValue(heuristica);
        this.leafs.add(leaf);
    }

    /**
     * Faz o cálculo MINI-MAX: atribui os valores (alfa-beta) aos nós internos da árvore mini-max
     * a partir dos valores determinados nas folhas (heurísticas atribuídas) 
     * Deve ser chamado após as chamadas de insertNode e antes de getNextJogada 
     * 
     * Sem poda alfa-beta
     */
    public void finishTree() {

        // Esse passo poderia ser automático (atrelado ao método insertNode)
        // mas preferimos assim para diminuir o overhead, já que este é um método custoso

        for (TreeNode<MiniMaxInfo<T>> leaf: this.leafs) {
            
            Integer value = leaf.getToken().getValue();
            TreeNode<MiniMaxInfo<T>> parent = leaf.getParent();
            while (parent != this.root) {
                
                MiniMaxInfo<T> info = parent.getToken();
                if (info.getValue() == null) {
                    info.setValue(value);
                }
                else {
                    // FIXME bug: os valores são propagados pra cima indescriminadamente
                    // TODO: caso de teste pra falhar devido a esse bug
                    // TODO: caso valor não se propague para nível acima, propagação deve ser encerrada
                    if (info.getTipo() == Tipo.MAX && value > info.getValue())
                        info.setValue(value);
                    if (info.getTipo() == Tipo.MINI && value < info.getValue())
                        info.setValue(value);
                }
                
                parent = parent.getParent();
            }
        }
    }


    /**
     * Análise MINI-MAX
     * 
     * @return a jogada que deve ser efetuada por MAX
     */
    public T getNextJogada() {

        T jogada = null;
        int hmax = 0;
        for (TreeNode<MiniMaxInfo<T>> node: this.root.getChildNodes()) {
            
            MiniMaxInfo<T> info = node.getToken();
            
            // procura por MAX
            if (info.getValue() > hmax) {
                jogada = info.getJogada();
                hmax = info.getValue();
            }
        }
        
        return jogada;
    }
    
    /**
     * 
     * @return notação pré-fica da árvore exibindo os valores propagados
     * pelo MINI-MAX em cada nó da árvore
     */
    public String valuesInPolishNotation() {

        return valuesInPolishNotation(this.root);
    }
    
    private String valuesInPolishNotation(TreeNode<MiniMaxInfo<T>> node) {
        
        StringBuilder str = new StringBuilder("");
        for (TreeNode<MiniMaxInfo<T>> child: node.getChildNodes()) {
                str.append(valuesInPolishNotation(child));
        }
        return "[" + node.getLabel() + " " + node.getToken().getValue() + " " + str.toString() + "]";
    }
}
