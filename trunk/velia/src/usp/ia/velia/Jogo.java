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

        public void jogar(Jogador jogador, int x, int y, int z) throws JogadaIlegal {

            if (this.tabuleiro[x][y][z] != null) {
                throw new JogadaIlegal();
            } else {
                this.tabuleiro[x][y][z] = jogador;
            }
        }
       
	public void jogar(Jogada jogada) throws JogadaIlegal {
		
	    int x = jogada.getPosicao().getCoord()[0];
            int y = jogada.getPosicao().getCoord()[1];
            int z = jogada.getPosicao().getCoord()[2];
            
            this.jogar(jogada.getJogador(), x, y, z);
	}

	// heurística = como está o jogo.. tal jogador está bem ou mal
	public int heuristica(Jogador jogador) {
		int h=0;
		int i;
		
		for(i=0;i<N;i++)h+=heuBi(tabuleiro[i],jogador);
			
		return h;
	}
	
	//TODO: n eh pra testas apenas se eh igual a jogador, mas se eh branco tb
	//heuristica para o caso bi-dimensional 
	private int heuBi(Jogador tab[][], Jogador jogador){
		int i,j,k;
		int h=0;//a heuristica, oh!
		int cont=0;//contador de acertos
		int contj[] = new int[N];
		int contd[] = new int[2];//contadores da diagonal
		
		for(j=0;j<N;j++){
			cont=0;
			for(i=0;i<N;i++){
				if(tab[j][i]==jogador){
					cont++;
					contj[j]++;
					if(i==j)contd[0]++;
					if(i+j+1==N)contd[1]++;
				}
			}
			if(cont==N)	h++;
		}
		for(j=0;j<N;j++)if(contj[j]==N) h++;
		for(j=0;j<2;j++)if(contd[j]==N) h++;
		
		
		return h;
	}
	
	public int heuristica(Jogador jogador, Jogada jogada) {
		
		return 0;
	}
}
