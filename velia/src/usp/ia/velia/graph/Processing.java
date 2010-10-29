package usp.ia.velia.graph;

import processing.core.PApplet;
import usp.ia.velia.Jogada;
import usp.ia.velia.JogadaIlegal;
import usp.ia.velia.Jogador;
import usp.ia.velia.Jogo;
import usp.ia.velia.Posicao;
import usp.ia.velia.Jogador.Insignia;

@SuppressWarnings("serial")
public class Processing extends PApplet {

    // atributos do jogo
    private Jogo jogo;
    private Tabuleiro tab;
    private Jogador jog1, jog2;

    // rotação do modelo controlada interativamente:
    float rotX = 0;
    float rotY = 0;
    float rotZ = 0;

    public void setup() {
        
        // configurações do Processing
        size(800, 800, OPENGL);         
        perspective(PI/4, 1.0f*width/height, 0.1f, 200);
        camera(0,  0, 50,                        //  posição da câmera
                0, 0, 0,                         //  centro de atenção da câmera
                0, 1, 0);                        //  vetor vertical da câmera
        frameRate(20);

        // configurações do jogo
        this.jog1 = new Jogador("Humano", Insignia.X);
        this.jog2 = new Jogador("PC", Insignia.O);
        this.jogo = new Jogo();
        this.tab = new Tabuleiro(this, jogo);
        
        // rotina pra teste
        Posicao p1 = new Posicao(0, 0, 0);
        Posicao p2 = new Posicao(1, 1, 0);
        Jogada j1 = new Jogada(jog1, p1);
        Jogada j2 = new Jogada(jog2, p2);
        try {
            jogo.jogar(j1);
            jogo.jogar(j2);
        } catch (JogadaIlegal e) {
            e.printStackTrace();
        }
    }
    
    public void draw() {
        
        lights();

        // movimento do mouse e teclas de câmera (w, s, a, d, q, e)
        rotateX(rotX);
        rotateY(rotY);
        rotateZ(rotZ);

        this.tab.draw();
    }
    
    // respostas de comandos do usuário
    public void keyPressed() {
    
        // controle de câmera
        if (key == 'a')
                rotY += -0.1;
        if (key == 'd')
                rotY += 0.1;
        if (key == 'w')
                rotX += -0.1;
        if (key == 's')
                rotX += 0.1;
        if (key == 'q')
                rotZ += -0.1;
        if (key == 'e')
                rotZ += 0.1;
    }
    
    public static void main(String[] args) {
        
        //PApplet.main(new String[] {"--present",  "usp.ia.velia.graph.Processing" });
    }

}
