package usp.ia.velia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Representa a instância do jogo, i.é, como o jogo está no momento
 * @author hedema
 *
 */
public class Jogo {
	
	private final int N = 3; // dimensão, no caso tradicional 3
	private int Nocupadas;//quantidade de casinhas ocupadas
	private Jogada ultimaJogada;//jogada a ser desfeita pelo metodo UNDO
	public static final int MAX_HEURISTICA = 49; // valor máximo da heurística
	
	private boolean finished;
	private Jogador jogador1, jogador2;
	
	//List riscas;
	int risca_dir[][]={{1,0,0},{0,1,0},{0,0,1},{0,0,0},{0,0,0},{0,0,0}};
	int risca_dir_id=-1;
	int risca_ini[]=null;
	
	
	

	// a posição do tabuleiro indica qual jogador jogou naquela posição
	private Jogador[][][] tabuleiro = new Jogador[N][N][N]; 

	// atributos usados quando alguém vence o jogo
	private Jogador vencedor;
	private int[][] risca; // como o jogador venceu (3 triplas ordenados!)

	
	public Jogo(Jogador jogador1, Jogador jogador2) {	    
	    this.jogador1 = jogador1;
	    this.jogador2 = jogador2;
	    Nocupadas = 0;
	    finished=false;
	}	

	public Jogo(Jogo outro) {
		Nocupadas = outro.Nocupadas;
		Jogador[][][] tab = outro.viewTabuleiro();
		this.jogador1=outro.jogador1;
		this.jogador2=outro.jogador2;
		if(outro.ultimaJogada!=null)
			this.ultimaJogada=new Jogada(outro.ultimaJogada.getJogador(),outro.ultimaJogada.getPosicao());
		this.finished=outro.finished;
		this.tabuleiro = new Jogador[3][3][3];
		for (int i=0; i<N; i++)
	                for (int j=0; j<N; j++)
	                    for (int k=0; k<N; k++)
	                        this.tabuleiro[i][j][k] = tab[i][j][k];
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
                verificaTermino(jogador);
            }
        }
       
    //Attention to changes. The 'casinhas ocupadas' must be updated
	public void jogar(Jogada jogada) throws JogadaIlegal {	    
	    Posicao pos = jogada.getPosicao();
	    int[] coord = pos.getCoord();           
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
		Jogador ultimo = ultimaJogada.getJogador();
		ultimaJogada=null;
		finished = false;
		vencedor = null;
		risca = null;
		Nocupadas--;
		verificaTermino(ultimo);
	}
	/**
	 * Informa posição que jogador deve jogar para vencer imediatamente
	 * @return null caso não seja possível vencer em uma jogada
	 */
	public int[] marcaPraVencer(Jogador jogador){		
		Jogada jogadas[] = this.possiveisJogadas(jogador);
		Jogo copia = new Jogo(this);		
		for(Jogada j: jogadas){				
			try {
				copia.jogar(j);
				if(copia.getVencedor()==jogador)return j.getPosicao().getCoord();
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
		
		// calcula projeções
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
						if(j==N-1-i && k==i)	projMulD*=primo[2];
						if(j==N-1-i && k==N-1-i)projMulD*=primo[3];
	
					}
				}
				
		// verifica "unidimensionais"
		//atenção: a semantica dos contadores agora eh outra
		//nesses 3 for's, a heuristica maxima de saída é 27
		for(i=0;i<N;i++)
			for(j=0;j<N;j++)
				for(k=0;k<3;k++)
					if(projecao[i][j] % Math.pow(primo[k],3) == 0){
						heuristica++;
						risca_dir_id=k;
						risca_ini = new int[]{i,j};
					}
		
		// verifica "diagonais planares"
		int hdiag=0;
		for(i=0;i<N;i++)//percorre o vetor projdiag
			for(j=0;j<2;j++)//itera entre diagonal principal e reversa
				for(k=0;k<3;k++)//pra cada posição do projdiag, pra cada diagonal, checa os 3 planos
					if(projdiag[i] % Math.pow(primo[j*N+k],3) == 0)hdiag++;
		
		// verifica diagonais "multi-dimensionais"
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
	    Jogador outro = getAdversarioFrom(jogador);    
	    // heurística "crua", definida pelas possibilidades de vitória do jogador e do adversário
	    
	    //int h = XHeuPGrupo(new Jogador[]{jogador,null}) - XHeuPGrupo(new Jogador[]{outro,null});
	    int h = XHeuPGrupo(new Jogador[]{jogador,null});
	    
	    // heurística manipulada, definida pelas possibilidades de vitória iminente
	    // do jogador ou do adversário
//	    if (marcaPraVencer(jogador) != null)
//	        h += 10;
//            if (marcaPraVencer(outro) != null)
//                h -= 10;
	    return h;
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
	    
	    //TODO: as vezes d fica com um valor maior do que deveria
		int d = this.N*this.N*this.N-this.Nocupadas;//quantidade de posicoes disponiveis
		//System.out.println(this.Nocupadas);
		Jogada[] ret = new Jogada[d];
		for(int i=0,c=0	;i<N && c<d;i++)
		for(int j=0		;j<N && c<d;j++)
		for(int k=0		;k<N && c<d;k++)
			if(tabuleiro[i][j][k]==null) {
				ret[c++] = new Jogada(jogador,new Posicao(i,j,k));
			}
	    
	    return ret;
	}
	

	/**
	 * Checa se jogo já terminou
	 * Caso sim, seta vencedor e risca
	 */
	private void verificaTermino(Jogador jogador) {
		int heu = XHeuPGrupo(new Jogador[]{jogador});
		
		boolean venceu = heu > 0;
		if(venceu){
			vencedor=jogador;
			finished|=venceu;
			defineRisca();
			
		}
	}
	private void defineRisca(){
		//int offset=0;//de 0 a 2..cardinalidade do ponto na risca
		risca = new int[N][3];
//		System.out.println(12);
		//int[][] uteis = new int[0][3];
		//int i,j,k;
		//for(int i=0;i<)
		defineRisca_aux(0,0,0,0);
		
		//risca=new int[][]{{1,2,3},{7,8,9},{10,13,79}};
	}
	private boolean defineRisca_aux(int offset,int fi,int fj,int fk){
		int i,j,k;
		for(i=fi;i<N;i++)
			for(j=fj;j<N;j++)
				for(k=fk;k<N;k++)
					if(tabuleiro[i][j][k]==vencedor){
						//System.out.println(tabuleiro[i][j][k]);
						risca[offset][0]=i;
						risca[offset][1]=j;
						risca[offset][2]=k;
						if(offset==N-1)
							return true;
						else{
							if(defineRisca_aux(offset+1,i+1,j,k))return true;
							if(defineRisca_aux(offset+1,i,j+1,k))return true;
							if(defineRisca_aux(offset+1,i,j,k+1))return true;
						}
					}
		return false;
	}
	
	
	public boolean isFinished(){
		return finished;
	}
	public Jogador getVencedor() {        
	    return vencedor;
	}	
	public int[][] getRisca() {
		List<Posicao> considerar = new ArrayList<Posicao>();
		List<Posicao> riscas = new ArrayList<Posicao>();
		
		for(int j=0;j<N;j++)
			for(int k=0;k<N;k++)
				for(int i=0;i<N;i++)
					if(tabuleiro[i][j][k]==vencedor)
						considerar.add(new Posicao(i,j,k));
		
		boolean achou=false;
		
		Posicao pos = ultimaJogada.getPosicao();
		considerar.remove(pos);
		//infelizmente, testa duas vezes cada par..
		for(Posicao testa: considerar){
//			System.out.println("testando contra " + testa);
			
			Posicao dif = Posicao.moddif(pos, testa);
//			System.out.println(pos);
//			System.out.println(testa);
			
			for(Posicao t2:considerar){
				
				if(t2!=testa){
					
					Posicao dif2 = Posicao.moddif(pos, t2);
					Posicao dif3 = Posicao.moddif(testa, t2);

//					System.out.println("e tb" + t2);
//					System.out.println("difs:");
//					System.out.println(dif);
//					System.out.println(dif2);
//					System.out.println(dif3);
					
					if(dif.equals(dif2)){//dif3 relaciona os mais distantes
						riscas.add(testa);
						riscas.add(pos);
						riscas.add(t2);
						achou=true;
					} else if (dif.equals(dif3)){//dif2 relaciona os mais distantes
						riscas.add(pos);
						riscas.add(testa);
						riscas.add(t2);
						achou=true;
					} else if (dif2.equals(dif3)){//dif relaciona os mais distantes
						riscas.add(testa);
						riscas.add(t2);
						riscas.add(pos);
						achou=true;
					}					
				}//if(t2!=testa)
				if(achou)break;
			}//for t2 in considerar
//			considerar.remove(testa); //dá pau..
			
			if(achou)break;
		}
		
//		System.out.println("achou = " +achou);
	

		int[][] resp = new int[3][];
		int cont=0;
		
		if(achou)
			for(Posicao x : riscas){
				resp[cont++] = x.getCoord();
			}
		
	    return resp;
	}
	public String toString(){
		String resp="";
		
		for(int j=0;j<N;j++){
			for(int k=0;k<N;k++){
				for(int i=0;i<N;i++)
					if(tabuleiro[i][j][k]!=null)
						resp+=tabuleiro[i][j][k].getNome()+" ";
					else
						resp+="  - ";
				resp+="\t";
			}
			resp+="\n";
		}
		
		return resp;
	}
}
