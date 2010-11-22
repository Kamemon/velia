package usp.ia.velia;

import java.util.Arrays;


/**
 * Representa a instância do jogo, i.é, como o jogo está no momento
 * @author hedema
 *
 */
public class Jogo {
	
	private final int N = 3; // dimensão, no caso tradicional 3
	private int Nocupadas;//quantidade de casinhas ocupadas
	private Jogada ultimaJogada;//jogada a ser desfeita pelo metodo UNDO
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
		Nocupadas = 0;
	}	
	// TODO
	public Jogo(Jogador[][][] tabuleiro) {
		Nocupadas = 0;
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
        	return jogador == this.jogador1? this.jogador2 : this.jogador1;
        }

        public void jogar(Jogador jogador, int x, int y, int z) throws JogadaIlegal {
            if (this.tabuleiro[x][y][z] != null) {
                throw new JogadaIlegal();
            } else {
                this.tabuleiro[x][y][z] = jogador;
                Nocupadas++;
                ultimaJogada = new Jogada(jogador,new Posicao(x,y,z));
            }
        }
       
    //Attention to changes. The 'casinhas ocupadas' must be updated
	public void jogar(Jogada jogada) throws JogadaIlegal {
		int[] coord = jogada.getPosicao().getCoord();           
        this.jogar(jogada.getJogador(),coord[0],coord[1],coord[2]);
	}
	
	/**
	 * Apenas desfaz a última!
	 */
	public void undo(){
		if(ultimaJogada==null)return;
		int x = ultimaJogada.getPosicao().getCoord()[0];
		int y = ultimaJogada.getPosicao().getCoord()[1];
		int z = ultimaJogada.getPosicao().getCoord()[2];
		this.tabuleiro[x][y][z]=null;
		ultimaJogada=null;
	}
	/**
	 * Informa posição que jogador deve jogar para vencer imediatamente
	 * @return null caso não seja possível vencer em uma jogada
	 */
	public int[] marcaPraVencer(Jogador jogador){
		Jogada jogadas[] = this.possiveisJogadas(jogador);
		Jogo copia = new Jogo(this.viewTabuleiro());
		
		for(Jogada j: jogadas){
			try {
				copia.jogar(j);
				if(copia.verificaTermino(jogador))return j.getPosicao().getCoord();
				copia.undo();
			} catch (JogadaIlegal e) {
				System.out.println("Implemente adequadamente o metodo possiveisJogadas, por favor");
			}			
		}		
		return null;
	}
	
	
	/**
	 * Heurística Extendida Para Grupo
	 * Checa a heurística de todo um grupo, definido pelo vetor de jogadores
	 * @param checando Jogadores a serem considerados como equipe. Para se saber quantas
	 * possibilidades algum jogador tem, pode-se passar um array com o jagador em questão
	 * e o jogador null.
	 * @return
	 */
	public int  XHeuPGrupo(Jogador[] checando){
		boolean conta=false;
		

		int i,j,k;
		int heuristica=0;
		int primo[] = {2,3,5,7,11,13};//2:projeção no plano i;3:no j;5: no k
		
		int[][] projecao = new int[N][N];//utilizado para 
		long[]	projdiag = new long[N];
		long	projMulD = 1;//projeção das diagonais que envolvem as 3 dimensoes
		
		//preenche cada posição das projeções com 1
		for(i=0;i<N;i++){
			Arrays.fill(projecao[i], 1);
			projdiag[i] = 1;
		}		
		
		for(k=0;k<N;k++)
			for(j=0;j<N;j++)
				for(i=0,conta=false;i<N;conta=false,i++){
					for(Jogador jogador : checando)
						conta |= (tabuleiro[i][j][k]==jogador);
					
					
					if(conta){
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
						//diagonais multi-dimensionais
						//ATENCAO ao (1,1,1) (no caso N=3)						
						if(j==i && k==i)		projMulD*=primo[0];
						if(j==i && k==N-1-i)	projMulD*=primo[1];
						if(j==N-1-1 && k==i)	projMulD*=primo[2];
						if(j==N-1-1 && k==N-1-i)projMulD*=primo[3];
	
					}
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
		
		int hMulD=0;
		for(i=0;i<4;i++)	if(projMulD % Math.pow(primo[i], 3)==0)hMulD++;
				
		return heuristica+hdiag+hMulD;
	}	
	/**
	 * Define a heurística do jogo para um jogador
	 * Quanto maior a heurística, maiores são as possibilidades de vitória
	 * @param jogador
	 * @return
	 */
	public int heuristica(Jogador jogador) {		
		return XHeuPGrupo(new Jogador[]{jogador,null});
	}
	
	
	/**
	 * A heuristica é avaliada após a jogada 
	 * (que pode ser de qualquer jogador), mas a jogada não
	 * deve é efetuada no jogo em questão. É uma hipótese
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
	 * @return null caso não haja jogada possível
	 */
	public Jogada[] possiveisJogadas(Jogador jogador) {
		int d = this.N*this.N*this.N-this.Nocupadas;//quantidade de posicoes disponiveis
		//System.out.println(this.Nocupadas);
		Jogada[] ret = new Jogada[d];
		for(int i=0,c=0	;i<N && c<d;i++)
		for(int j=0		;j<N && c<d;j++)
		for(int k=0		;k<N && c<d;k++)
			if(tabuleiro[i][j][k]==null)
				ret[c++] = new Jogada(jogador,new Posicao(i,j,k));
	    
	    return d!=0? ret:null;
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
	public boolean verificaTermino(Jogador jogador) {
		return XHeuPGrupo(new Jogador[]{jogador}) > 0;
	}
	
	public Jogador getVencedor() {        
	    return vencedor;
	}	
	public int[][] getRisca() {	    
	    return risca;
	}
}
