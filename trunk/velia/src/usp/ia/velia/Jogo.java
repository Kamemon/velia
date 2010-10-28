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
	
       public Jogador[][][] viewTabuleiro() {
	            
            Jogador[][][] copia = new Jogador[N][N][N];
            for (int i=0; i<N; i++)
                for (int j=0; j<N; j++)
                    for (int k=0; k<N; k++)
                        copia[i][j][k] = this.tabuleiro[i][j][k];
            return copia;
        }

	public void jogar(Jogada jogada) throws JogadaIlegal {
		
	}

	// heurística = como está o jogo.. tal jogador está bem ou mal
	public int heuristica(Jogador jogador) {
		
		return heuBi(tabuleiro[0], jogador);
	}
	
	//heuristica para o caso bi-dimensional 
	private int heuBi(Jogador tab[][], Jogador jogador){
		int i,j,k;
		int h=0;//a heuristica, oh!
		int cont=0;//contador de acertos
		int contj = 0;
		
		for(j=0;j<N;j++){
			for(i=0;i<N;i++){
				if(tab[j][i].equals(jogador)){
					cont++;
					contj++;
				}else
					break;
			}
			if(cont==N)	h++;
		}
		if(contj==N) h++;
		
		
		return 0;
	}
	
	public int heuristica(Jogador jogador, Jogada jogada) {
		
		return 0;
	}
}
