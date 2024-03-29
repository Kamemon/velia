package usp.ia.velia;

public class Jogada {
	
    private Jogador jogador;
    private Posicao posicao;
    
    public Jogada(Jogador jogador, Posicao posicao) {
        this.jogador = jogador;
        this.posicao = posicao;
    }

    public Jogada(Jogador jogador,int x,int y, int z) {
        this(jogador, new Posicao(x,y,z));
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

    @Override
    public String toString() {
        String str = "";
        if (jogador != null) {
            str = jogador.getNome();
        }
        if (jogador != null && posicao != null) {
            str += " em ";
        }
        if (posicao != null)
            str += posicao.toString();
        return str;
    }
}
