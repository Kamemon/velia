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
		jogo = new Jogo();
		N = 3;
	}

	@Test
	public void testHeuristicaJogador() throws JogadaIlegal {
		int hleo, hgui;
		
		hleo = jogo.heuristica(leo);
		hgui = jogo.heuristica(gui);		
		//System.out.println(h);
		assertEquals(hleo,27+18+4);
		assertEquals(hgui,27+18+4);
		
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
	public void testaPossiveisJogadas(){
		int x=0,y=0,z=0,w=0;
		Jogada[] disponiveis;
		int c=0;
		int cont=0;
		do{
			x=Math.round((float)(Math.random()*10)%(N-1));
			y=Math.round((float)(Math.random()*10)%(N-1));
			z=Math.round((float)(Math.random()*10)%(N-1));
			
			try {
				jogo.jogar(leo,x,y,z);
				cont++;
			} catch (JogadaIlegal e) {
				int x2,y2,z2,soma;
				boolean passou = false;
				int somando=1;				
				//System.out.println(x+"\t"+y+"\t"+z);
				//System.out.println(x2+"\t"+y2+"\t"+z2 + "--");
							
				
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			//System.out.println(x);
		}while(++c<600 && cont<N*N*N);
	//	System.out.println(y+"\t"+z+"\t"+w);
		//System.out.println("truplas sorteadas:"+c);
	}
}
