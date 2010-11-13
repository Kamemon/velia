package usp.ia.velia;

import java.util.Arrays;


/**
 * Representa a instância do jogo, i.é, como o jogo está no momento
 * @author hedema
 *
 */
public class Jogo {
	
	private final int N = 3; // dimensão, no caso tradicional 3
	private final int MAX_HEURISTICA = 42; // valor máximo da heurística
	
	private Jogador jogador1, jogador2;
	
	// a posição do tabuleiro indica qual jogador jogou naquela posição
	private Jogador[][][] tabuleiro = new Jogador[N][N][N]; 

	// atributos usados quando alguém vence o jogo
	private Jogador vencedor;
	private int[][] risca; // como o jogador venceu (3 triplas ordenados!)
	private boolean finished = false;
	
	public Jogo() {
	    
	    // TODO: receber os jogadores
	    
	}
	
	// TODO
	public Jogo(Jogador[][][] tabuleiro) {
	    // não copiar a instância tabuleiro, mas sim os valores!
	}
	
        public Jogador[][][] viewTabuleiro() {
	            
            Jogador[][][] copia = new Jogador[N][N][N];
            for (int i=0; i<N; i++)
                for (int j=0; j<N; j++)
                    for (int k=0; k<N; k++)
                        copia[i][j][k] = this.tabuleiro[i][j][k];
            return copia;
        }
        
        public Jogador getAdversarioFrom(Jogador jogador) {
            
            if (jogador == this.jogador1)
                return this.jogador2;
            else
                return this.jogador1;
        }

        public void jogar(Jogador jogador, int x, int y, int z) throws JogadaIlegal {

            if (this.tabuleiro[x][y][z] != null) {
                throw new JogadaIlegal();
            } else {
                this.tabuleiro[x][y][z] = jogador;
                this.verificaTermino();
            }
        }
       
	public void jogar(Jogada jogada) throws JogadaIlegal {
		
	    int x = jogada.getPosicao().getCoord()[0];
            int y = jogada.getPosicao().getCoord()[1];
            int z = jogada.getPosicao().getCoord()[2];
            
            this.jogar(jogada.getJogador(), x, y, z);
	}

	/**
	 * Define a heurística do jogo para um jogador
	 * Quanto maior a heurística, maiores são as possibilidades de vitória
	 * @param jogador
	 * @return
	 */
	public int heuristica(Jogador jogador) {
		int i,j,k;
		int heuristica=0;
		int primo[] = {2,3,5,7,11,13};//2:projeção no plano i;3:no j;5: no k
		
		int[][] projecao = new int[N][N];//utilizado para 
		long[]	projdiag = new long[N];
		
		//preenche cada posição das projeções com 1
		for(i=0;i<N;i++){
			Arrays.fill(projecao[i], 1);
			projdiag[i] = 1;
		}
		
		
		
		for(k=0;k<N;k++)
			for(j=0;j<N;j++)
				for(i=0;i<N;i++)
					if(tabuleiro[i][j][k]==jogador | tabuleiro[i][j][k]==null){
						projecao[j][k]*=primo[0];
						projecao[k][i]*=primo[1];
						projecao[i][j]*=primo[2];
						
						//diagonal principal
						if(j==k)projdiag[i]*=primo[0];
						if(k==i)projdiag[j]*=primo[1];
						if(i==j)projdiag[k]*=primo[2];
						//diagonal reversa
						if(j+k==N-1)projdiag[i]*=primo[3];
						if(k+i==N-1)projdiag[j]*=primo[4];
						if(i+j==N-1)projdiag[k]*=primo[5];
						
					}
				
		//atenção: a semantica dos contadores agora eh outra
		//nesses 3 for's, a heuristica maxima de saída é 27
		for(i=0;i<N;i++)
			for(j=0;j<N;j++)
				for(k=0;k<3;k++)
					if(projecao[i][j] % Math.pow(primo[k],3) == 0)heuristica++;
		
		
		int hdiag=0;
		for(i=0;i<N;i++)//percorre o vetor projdiag
			for(j=0;j<2;j++)//itera entre diagonal principal e reversa
				for(k=0;k<3;k++)//pra cada posição do projdiag, pra cada diagonal, checa os 3 planos
					if(projdiag[i] % Math.pow(primo[j*N+k],3) == 0)hdiag++;
		
		
		for(i=0;i<N;System.out.println(Arrays.toString(projecao[i++])));
		
		
		//for(i=0;i<N;i++)h+=heuBi(tabuleiro[i],jogador);			
		return heuristica+hdiag;
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
	
	/**
	 * A heuristica é avaliada após a jogada 
	 * (que pode ser de qualquer jogador)
	 * @param jogador
	 * @param jogada
	 * @return
	 */
	public int heuristica(Jogador jogador, Jogada jogada) {
		return 0;
	}
	
	/**
	 * Define todas as jogadas que o jogador pode executar na situação atual
	 * @param jogador
	 * @return
	 */
	public Jogada[] possiveisJogadas(Jogador jogador) {
	    
	    // TODO
	    return null;
	}
	
	/**
	 * Indica se jogo já acabou
	 * @return
	 */
	public boolean isFinished() {
	    
	    return finished;
	}

	/**
	 * Checa se jogo já terminou
	 * Caso sim, seta vencedor e risca
	 */
	private void verificaTermino() {
	    
	    // TODO
	    
	    Jogador[][][] t = this.tabuleiro;
	    
//	    for (int y=0; y<3; y++) { // planos y = 0, 1, 2 
//
//	        // checa "linhas"
//	        for (int z=0; z<3; z++) {
//	            if (t[0][y][z] == t[1][y][z] && t[0][y][z] == t[2][y][z]) {
//	                this.vencedor = t[0][y][z];
//	                // TODO: setar risca
//	                return;
//	            }
//	        }
//	    }
	    
	    // mór rolê isso daki =/
	    
	    // descendo em z a partir das laterais diagonais (4 pivots, 3 cada)
	    
	    // descendo em z a partir do centro (1 pivot, 1 cada)
	    
	    // descendo em z a partir das laterais centrais (4 pivots, 2 cada)
	    
	    // mock: verificação "simplificada"
	    if (t[0][0][0] != null && t[0][0][0] == t[0][0][1] && t[0][0][0] == t[0][0][2]) {
	        this.vencedor = t[0][0][0];
	        this.finished = true;
	        this.risca = new int[3][3];
	        this.risca[0] = new int[]{0, 0, 0};
                this.risca[1] = new int[]{0, 0, 1};
                this.risca[2] = new int[]{0, 0, 2};
	    }

            if (t[2][0][0] != null && t[2][0][0] == t[2][0][1] && t[2][0][0] == t[2][0][2]) {
                this.vencedor = t[2][0][0];
                this.finished = true;
                this.risca = new int[3][3];
                this.risca[0] = new int[]{2, 0, 0};
                this.risca[1] = new int[]{2, 0, 1};
                this.risca[2] = new int[]{2, 0, 2};
            }
	    
	}
	
	public Jogador getVencedor() {
        
	    return vencedor;
	}
	
	public int[][] getRisca() {
	    
	    return risca;
	}
}
