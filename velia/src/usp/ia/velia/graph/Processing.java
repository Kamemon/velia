package usp.ia.velia.graph;

import processing.core.PApplet;
import usp.ia.velia.Jogo;

@SuppressWarnings("serial")
public class Processing extends PApplet {

    private Jogo jogo;
    private Tabuleiro tab;
    
    public void setup() {

        this.jogo = new Jogo();
        this.tab = new Tabuleiro(jogo, this);
    }
    
    public void draw() {
        
        this.tab.draw();
    }
}
