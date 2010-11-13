package usp.ia.velia;

import usp.ia.velia.minimax.MiniMaxTree;

/**
 * Agente IA que define suas jogadas através do MINI-MAX
 * @author leonardo
 *
 */
public class JogadorMaquina extends Jogador {
    
    private final int N = 3;
    
    public JogadorMaquina(String nome, Insignia insignia) {
        super(nome, insignia);
    }

    @Override
    public int[] escolheJogada(Jogo jogo) {
        
        // paliativo (1a monalisa)
        
        // joga na 1a posição disponível
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                for (int k=0; k<N; k++) {
                    if (jogo.viewTabuleiro()[i][j][k] == null) {
                        return new int[]{i, j, k};
                    }
                }
            }
        }        
        return new int[3];
    }
    
    // em construção
    public int[] escolheeJogada(Jogo jogo) {
        
        MiniMaxTree<Jogada> minimax = new MiniMaxTree<Jogada>();
        Jogador adversario = jogo.getAdversarioFrom(this);
        
        // coloca primeiro nível na árvore minimax
        for (Jogada jogada: jogo.possiveisJogadas(this)) {
            
            // TODO
            // coloca no minimax
            // pra cada jogo resultante de uma possível jogada
            // avaliar próximas jogadas possívels do outro jogador e colocar no minimax
            // fazer isso ainda mais outra vez para suas jogadas
        }
        
        minimax.finishTree();
        
        return minimax.getNextJogada().getPosicao().getCoord();
    }
}
