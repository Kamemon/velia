package usp.ia.velia.minimax;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import usp.ia.velia.Jogada;
import usp.ia.velia.JogadaIlegal;
import usp.ia.velia.Jogador;
import usp.ia.velia.Jogo;
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

    private AtomicInteger intGenerator;

    private Jogador mini, max;
    private TreeNode<MiniMaxInfo<T>> root = new TreeNode<MiniMaxInfo<T>>(ROOT_LABEL);
    private Tree<MiniMaxInfo<T>> arvore = new Tree<MiniMaxInfo<T>>(root);

    // mapa jogos
    // chave: label do nó que corresponde a uma jogada (na árvore MINI-MAX)
    // valor: estado do jogo caso a jogada seja efetuada
    private Map<String, Jogo> jogos = new HashMap<String, Jogo>();

    /**
     * 
     * @param jogo Estado atual do jogo (quando é a vez de MAX jogar)
     */
    public ArvoreMiniMax(Jogo jogo, Jogador max, Jogador mini) {

        this.max = max;
        this.mini = mini;
        this.root.getToken().setTipo(MiniMaxInfo.Tipo.MAX); // raiz é MAX (sem jogada associada)

        this.jogos.put(ROOT_LABEL, jogo);
    }

    public String getRootLabel() {

        return this.root.getLabel();
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
     * Cria um novo nó na árvore mini-max Após construir a árvore com sucessiva chamadas deste método, chame finishTree
     * 
     * @param pai label do nó pai
     * @param jogada correspondência entre o nó e a jogada
     * @return label do nó criado (use essa referência para inserir os filhos do nó criado)
     */
    public String insertNode(String pai, T jogada) {

        // cria nó e insere na árvore
        TreeNode<MiniMaxInfo<T>> fatherNode = this.arvore.search(pai);
        String label = Integer.toString(this.intGenerator.get());
        TreeNode<MiniMaxInfo<T>> node = new TreeNode<MiniMaxInfo<T>>(label);
        fatherNode.addChild(node);

        // configura nó criado
        node.getToken().setJogada(jogada);
        if (fatherNode.getToken().getTipo() == MiniMaxInfo.Tipo.MAX)
            node.getToken().setTipo(MiniMaxInfo.Tipo.MINI);
        else
            node.getToken().setTipo(MiniMaxInfo.Tipo.MAX);

        return null;
    }

    /**
     * Faz o cálculo MINI-MAX: atribui as heurísticas aos nós internos da árvore mini-max Deve ser chamado após as
     * chamadas de insertNode e antes de getNextJogada Esse passo poderia ser automático (atrelado ao método insertNode)
     * mas preferimos assim para diminuir o overhead, já que este é um método custoso
     * 
     * Sem poda alfa-beta
     */
    public void finishTree() {

        // raiz não é computada (pois representa o estado atual do jogo)
        for (TreeNode<MiniMaxInfo<T>> no : this.root.getChildNodes())
            this.compute(no, this.root.getLabel());
    }

    private void compute(TreeNode<MiniMaxInfo<T>> node, String fatherLabel) {

        // computa nó
        Jogo jogoAtual = this.jogos.get(fatherLabel);
        Jogo jogoNovo = new Jogo(jogoAtual.viewTabuleiro());
        this.jogos.put(node.getLabel(), jogoNovo);
        T jogada = node.getToken().getJogada();
        try {
            jogoNovo.jogar((Jogada) jogada); // TODO: baaad... o cast... (poderíamos ter uma interface com tipo genérico e a implementação ser o jogo Velia)
        } catch (JogadaIlegal e) {
            System.out.println("Jogada inválida salva na árvore MINI-MAX!");
            e.printStackTrace();
        }
        int h = 0;
        if (node.getToken().getTipo() == Tipo.MAX)
            h = jogoNovo.heuristica(max);
        else
            h = jogoNovo.heuristica(mini);
        node.getToken().setHeuristica(h);

        // computa filhos
        for (TreeNode<MiniMaxInfo<T>> no : node.getChildNodes())
            this.compute(no, node.getLabel());
    }

    /**
     * Busca MINI-MAX
     * 
     * @return a jogada que deve ser efetuada por MAX
     */
    public T getNextJogada() {

        return null;
    }
}
