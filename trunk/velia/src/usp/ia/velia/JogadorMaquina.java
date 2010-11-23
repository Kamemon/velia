package usp.ia.velia;

import usp.ia.velia.minimax.MiniMaxInfo;
import usp.ia.velia.minimax.MiniMaxTree;
import usp.ia.velia.tree.TreeNode;

/**
 * Agente IA que define suas jogadas através do MINI-MAX
 * @author leonardo
 *
 */
public class JogadorMaquina extends Jogador {
    
    public JogadorMaquina(String nome, Insignia insignia) {
        super(nome, insignia);
    }
    
    @Override
    public int[] escolheJogada(Jogo jogo) {
        
        try {
            return escolheJogadaMiniMax(jogo);
        } catch (JogadaIlegal e) {
            System.out.println("Agente cogitou jogada ilegal");
            e.printStackTrace();
        }
        return null;
    }
    
    private int[] escolheJogadaMiniMax(Jogo jogoAtual) throws JogadaIlegal {
        
        // antes de usar minimax vê se dá pra ganhar de cara
        int[] marca = jogoAtual.marcaPraVencer(this);
        if (marca != null)
            return marca;
        
        // pra cada jogo resultante de uma possível jogada do agente na situação atual do jogo
        // avaliar próximas jogadas possívels do jogador adversário
        // fazer isso ainda mais outra vez para as jogadas seguintes do agente

        MiniMaxTree<Jogada> minimax = new MiniMaxTree<Jogada>();
        Jogador adversario = jogoAtual.getAdversarioFrom(this);
        
        // coloca primeiro nível na árvore minimax
        for (Jogada primeiraJogada: jogoAtual.possiveisJogadas(this)) {
            
            TreeNode<MiniMaxInfo<Jogada>> father1 = minimax.createNode(minimax.getRoot(), primeiraJogada);
            Jogo jogo2 = new Jogo(jogoAtual); // jogo depois do 1o movimento (max)
            jogo2.jogar(primeiraJogada);
            
            // coloca segundo nível da árvore minimax
            for (Jogada segundaJogada: jogo2.possiveisJogadas(adversario)) {
                
                TreeNode<MiniMaxInfo<Jogada>> father2 = minimax.createNode(father1, segundaJogada);
                Jogo jogo3 = new Jogo(jogo2); // jogo depois do 2o movimento (mini)
                jogo3.jogar(segundaJogada);

                // folha minimax (com a heurística do jogo resultante do 3o movimento)
                for (Jogada terceiraJogada: jogo3.possiveisJogadas(this)) {
                    
                    Jogo jogo4 = new Jogo(jogo3); // jogo após o 3o movimento (max)
                    jogo4.jogar(terceiraJogada);
                    int h = jogo4.heuristica(this);
                    minimax.createLeaf(father2, terceiraJogada, h);
                }
            }
        }
        
        minimax.finishTree();
        //minimax.valuesInPolishNotationInFile();
        Jogada jog = minimax.getNextJogada();
        Posicao pos = jog.getPosicao();
        return pos.getCoord();
    }
}
