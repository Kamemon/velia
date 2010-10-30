package usp.ia.velia;

public class JogadorMaquina extends Jogador {
    
    private final int N = 3;
    
    public JogadorMaquina(String nome, Insignia insignia) {
        super(nome, insignia);
    }

    @Override
    public int[] escolheJogada(Jogo jogo) {
        
        // IA...
        
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
}
