package usp.ia.velia;

public class Jogada {
	
    private Jogador jogador;
    private Posicao posicao;
    
    public Jogada(Jogador jogador, Posicao posicao) {
        this.jogador = jogador;
        this.posicao = posicao;
    }
    
    public Jogador getJogador() {
        return jogador;
    }
    public Posicao getPosicao() {
        return posicao;
    }
    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }
    public void setPosicao(Posicao posicao) {
        this.posicao = posicao;
    }

    
}
