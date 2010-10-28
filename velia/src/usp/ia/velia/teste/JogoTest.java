package usp.ia.velia.teste;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import usp.ia.velia.Jogo;

public class JogoTest {

	Jogo jogo;
	
	@Before
	public void setUp() throws Exception {
		jogo = new Jogo();
	}

	@Test
	public void testHeuristicaJogador() {
		int h = jogo.heuristica(null);
		assertEquals(0, h);		
	}

}
