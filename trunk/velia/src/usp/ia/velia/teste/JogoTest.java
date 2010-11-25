package usp.ia.velia.teste;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import usp.ia.velia.Jogada;
import usp.ia.velia.JogadaIlegal;
import usp.ia.velia.Jogador;
import usp.ia.velia.JogadorHumano;
import usp.ia.velia.Jogo;

public class JogoTest {

	Jogo jogo;
	int N;
	Jogador leo = new JogadorHumano("Leo",null);
	Jogador gui = new JogadorHumano("Gui",null);
	
	
	@Before
	public void setUp() throws Exception {
		jogo = new Jogo(leo, gui);
		N = 3;
	}

	@Test
	public void testHeuristicaJogador() throws JogadaIlegal {
		int hleo, hgui;
		
		hleo = jogo.heuristica(leo);
		hgui = jogo.heuristica(gui);		
		//System.out.println(h);
		assertEquals(27+18+4,hleo);
		assertEquals(27+18+4,hgui);
		
		jogo.jogar(leo,0,0,0);
		hleo = jogo.heuristica(leo);
		hgui = jogo.heuristica(gui);		
		//System.out.println(h);
		assertEquals(27+18+4,hleo);
		assertEquals(24+15+3,hgui);
		
		jogo.jogar(gui,1,1,1);
		hleo = jogo.heuristica(leo);
		hgui = jogo.heuristica(gui);		
		//System.out.println(h);
		assertEquals(24+12+0,hleo);
		assertEquals(24+15+3,hgui);
		
		//long i =(long)2*2*2*3*3*3*5*5*5*7*7*7*11*11*11*13*13*13;
		//System.out.println(i+"\t"+Long.MAX_VALUE);
	}
	
	@Test
	public void testRisca() throws JogadaIlegal{
		Jogada[] definidas = {
				new Jogada(leo,2,0,1),
				new Jogada(gui,1,1,1),
				new Jogada(leo,0,0,0),
				new Jogada(gui,0,2,0),
				new Jogada(leo,0,0,1),
				new Jogada(gui,2,0,2),
				};
		jogo.jogar(definidas[0]);
		jogo.jogar(definidas[1]);
		jogo.jogar(definidas[2]);
		jogo.jogar(definidas[3]);
		jogo.jogar(definidas[4]);
		jogo.jogar(definidas[5]);

		//System.out.println(jogo.getRisca());
		System.out.println(jogo.getVencedor());
		for(int[] r : jogo.getRisca())
			System.out.println(r[0]+"\t"+r[1]+"\t"+r[2]);
	}
	
	
	
	
	@Test
	public void testXHeuPraUm() throws JogadaIlegal {
		Jogada[] definidas = {
				new Jogada(leo,2,0,1),
				new Jogada(gui,1,1,1),
				new Jogada(leo,0,0,0),
				new Jogada(gui,0,2,0),
				new Jogada(leo,0,0,1),
				new Jogada(gui,2,0,2),
				
				//new Jogada(gui,0,0,2),
				};
		int hleo, hgui;
		
		jogo.jogar(definidas[0]);
		jogo.jogar(definidas[1]);
		jogo.jogar(definidas[2]);
		jogo.jogar(definidas[3]);
		jogo.jogar(definidas[4]);
		
		
		jogo.jogar(definidas[5]);

		hgui = jogo.XHeuPGrupo(new Jogador[]{gui});
		assertEquals(1,hgui);
		
		
		
		//System.out.println(jogo);
		//System.out.println(jogo.isFinished() + "\t" + jogo.getVencedor());
				
		int hmax = jogo.XHeuPGrupo(new Jogador[]{leo,null,gui});
		assertEquals(Jogo.MAX_HEURISTICA, hmax);
	}
	
	
	@Test
	public void testaPossiveisJogadas(){
		int verbose=0;
		
		int x=0,y=0,z=0;
		Jogada[] disponiveis=null;
		int c=0;
		int cont=0;
		
		Jogador ja=leo;
		Jogador jb=gui;
		
		do{
			Jogo copia = new Jogo(jogo);
			jogo = copia;
			
			int[] pravencer = jogo.marcaPraVencer(ja);
			
			if(pravencer!=null){
				x = pravencer[0];
				y = pravencer[1];
				z = pravencer[2];
			}else{			
				x=Math.round((float)(Math.random()*10)%(N-1));
				y=Math.round((float)(Math.random()*10)%(N-1));
				z=Math.round((float)(Math.random()*10)%(N-1));
			}
			try {
				jogo.jogar(ja,x,y,z);
								
				cont++;				
				Jogada[] possiveis = jogo.possiveisJogadas(jb);
				assertEquals(N*N*N-cont, possiveis.length);
				
				for(Jogada j:possiveis)
					assertNotNull("nao era pra ser null", j);
					
				//desfaz a jogada aleatoriamente!
				if(Math.random()>0.5){
					jb=ja;
					if(ja==leo){ja=gui;}
					else{ja=leo;}
				}
				else{
					jogo.undo();
					cont--;
				}

				if(verbose>0)System.out.println("-------------------");
			} catch (JogadaIlegal e) {
				int x2,y2,z2,soma;
				boolean passou = false;
				int somando=1;				
				//System.out.println(x+"\t"+y+"\t"+z);
				//System.out.println(x2+"\t"+y2+"\t"+z2 + "--");				
			}		
			//System.out.println(x);
		}while(++c<700 && cont<N*N*N);
		//System.out.println(y+"\t"+z+"\t"+w);
		if(verbose>0)System.out.println("tuplas sorteadas:"+c);
	}
	
	
	

	@Test
	public void testaPossiveisJogadasPredefinidas(){
		Jogada[] definidas = {
				new Jogada(leo,2,0,1),
				new Jogada(gui,1,1,1),
				new Jogada(leo,0,0,0),
				new Jogada(gui,0,2,0),
				new Jogada(leo,0,0,1),
				new Jogada(gui,0,0,2),
				};
		
		int verbose=0;
		
		int x=0,y=0,z=0;
		Jogada[] disponiveis=null;
		int c=0;
		int cont=0;
		
		Jogador davez=leo;
		Jogador opoente=gui;
		
		do{			
			Jogo copia = new Jogo(jogo);
			jogo = copia;					
			Jogada efetuanda = definidas[c];
			davez = efetuanda.getJogador();
			opoente = davez==leo?gui:leo;
						
			int[] pravencer = jogo.marcaPraVencer(davez);
			
			if(c == definidas.length-1) 
				assertNotNull("pravencer é nulo",pravencer);
			else 
				assertNull("dd",pravencer);
			
			if(pravencer!=null){
				x = pravencer[0];
				y = pravencer[1];
				z = pravencer[2];
				efetuanda = new Jogada(davez,x,y,z);
			}
			try {
				jogo.jogar(efetuanda);
								
				cont++;//numero de jogadas efetuadas			
				
				Jogada[] possiveis = jogo.possiveisJogadas(opoente);
				assertEquals(N*N*N-cont, possiveis.length);
				
				for(Jogada j:possiveis)
					assertNotNull("nao era pra ter obj null no array", j);
				
			} catch (JogadaIlegal e) {
				fail("nao era pra ter chegado aki nesse teste");
			}		
		}while(++c<definidas.length && cont<N*N*N);
	}
}
