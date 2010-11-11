package usp.ia.velia.teste;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import usp.ia.velia.JogadaIlegal;
import usp.ia.velia.Jogador;
import usp.ia.velia.JogadorHumano;
import usp.ia.velia.Jogo;

public class JogoTest {

	Jogo jogo;
	Jogador leo = new JogadorHumano("Leo",null);
	Jogador gui = new JogadorHumano("Gui",null);
	
	
	@Before
	public void setUp() throws Exception {
		jogo = new Jogo();
	}

	@Test
	public void testHeuristicaJogador() throws JogadaIlegal {
		int hleo, hgui;
		
		hleo = jogo.heuristica(leo);
		hgui = jogo.heuristica(gui);		
		//System.out.println(h);
		assertEquals(hleo,27+18);
		assertEquals(hgui,27+18);
		
		jogo.jogar(leo,0,0,0);
		hleo = jogo.heuristica(leo);
		hgui = jogo.heuristica(gui);		
		//System.out.println(h);
		assertEquals(27+18,hleo);
		assertEquals(24+15,hgui);
		
		jogo.jogar(gui,1,1,1);
		hleo = jogo.heuristica(leo);
		hgui = jogo.heuristica(gui);		
		//System.out.println(h);
		assertEquals(36,hleo);
		assertEquals(24+15,hgui);
		
		long i =(long)2*2*2*3*3*3*5*5*5*7*7*7*11*11*11*13*13*13;
		System.out.println(i+"\t"+Long.MAX_VALUE);
	}

}
