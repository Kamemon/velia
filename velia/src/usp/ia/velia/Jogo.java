package usp.ia.velia;

/**
 * Representa a instância do jogo, i.é, como o jogo está no momento
 * @author hedema
 *
 */
public class Jogo {
	
	private final int N = 3; // dimensão, no caso tradicional 3
	
	// a posição do tabuleiro indica qual jogador jogou naquela posição
	private Jogador[][][] tabuleiro = new Jogador[N][N][N]; 
	
	public void jogar(Jogada jogada) throws JogadaIlegal {
		
	}

	// heurística = como está o jogo.. tal jogador está bem ou mal
	public int heuristica(Jogador jogador) {
		
		return 0;
	}
	
	public int heuristica(Jogador jogador, Jogada jogada) {
		
		return 0;
	}
}
