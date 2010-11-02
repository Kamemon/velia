package usp.ia.velia.graph;

import processing.core.PApplet;
import usp.ia.velia.JogadaIlegal;
import usp.ia.velia.Jogador;
import usp.ia.velia.JogadorHumano;
import usp.ia.velia.JogadorMaquina;
import usp.ia.velia.Jogo;
import usp.ia.velia.Jogador.Insignia;

@SuppressWarnings("serial")
public class Sketch extends PApplet {

    private enum GameMode {CAMERA, PLAY};

    // atributos do jogo
    private Jogo jogo;
    private Tabuleiro tab;
    private Jogador humano, maquina, jogadorDaVez;
    private GameMode gameMode = GameMode.CAMERA;


    // rotação do modelo controlada interativamente:
    float rotX = 0;
    float rotY = 0;
    float rotZ = 0;

    public void setup() {
        
        // configurações do Processing
        size(600, 600, OPENGL);         
        perspective(PI/4, 1.0f*width/height, 0.1f, 200);
        camera(30,  -20, 80,                        //  posição da câmera
                10, 10, 0,                         //  centro de atenção da câmera
                0, 1, 0);                        //  vetor vertical da câmera
        frameRate(20);

        // configurações do jogo
        this.humano = new JogadorHumano("Humano", Insignia.X);
        this.maquina = new JogadorMaquina("Máquina", Insignia.O);
        this.jogo = new Jogo();
        this.tab = new Tabuleiro(this, jogo);
        this.jogadorDaVez = humano;
    }
    
    public void draw() {
        
        lights();
        background(0);

        // movimento do mouse e teclas de câmera (w, s, a, d, q, e)
        rotateX(rotX);
        rotateY(rotY);
        rotateZ(rotZ);

        // desenha tabuleiro de acordo com estado de this.jogo
        this.tab.draw();
    }
    
    // respostas de comandos do usuário
    public void keyPressed() {
    
        // alterna modo de jogo
        if (key == PApplet.TAB) {
            if (gameMode == GameMode.CAMERA)
                gameMode = GameMode.PLAY;
            else
                gameMode = GameMode.CAMERA;
        }
        
        // controle de câmera
        if (gameMode == GameMode.CAMERA) {
            this.moveCamera(key);
        }
        
        // controle do cursor e ativação da jogada
        if (gameMode == GameMode.PLAY) {
            if (key == PApplet.ENTER) {
                this.jogada();
            } else {
                if (jogadorDaVez == humano)
                    this.tab.moveCursor(key);
            }
        }
        
    }
    
    private void moveCamera(int key) {

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
    
    // TODO: por enquanto algumas coisas aqui (sincronizar e ifs com jogadorDaVez)
    // não fazem sentido porque a jogada de máquina é instantânea,
    // mas depois de implementado o minimax talvez não seja
    private synchronized void jogada() {
        
        if (jogadorDaVez == humano) {
            
            // jogada humano
            int x = this.tab.getCursorCoord()[0];
            int y = this.tab.getCursorCoord()[1];
            int z = this.tab.getCursorCoord()[2];
            try {
                this.jogo.jogar(humano, x, y, z);
            } catch (JogadaIlegal e) {
                System.out.println("Jogada ilegal");
                return;
            }
            jogadorDaVez = maquina;
            this.tab.disableCursor();
            
            this.verificaTermino();
            
            // agora é vez da máquina jogar
            int[] pos = maquina.escolheJogada(jogo);
            try {
                this.jogo.jogar(maquina, pos[0], pos[1], pos[2]);
            } catch (JogadaIlegal e) {
                e.printStackTrace();
            }

            this.verificaTermino();

            jogadorDaVez = humano;
            // volta o cursor
            this.tab.backCursor();
            this.tab.enableCursor();
        }

    }
    
    private void verificaTermino() {

        if (this.jogo.isFinished()) { // acabou
            
            this.tab.finish(jogo.getRisca());
            Jogador vencedor = jogo.getVencedor();
            System.out.println(vencedor.getNome() + " venceu");
        }
    }

    /**
     * Usando o processing processing-0191 (versão não-estável)
     * Download em:
     * http://code.google.com/p/processing/downloads/detail?name=processing-0191.tgz&can=2&q=
     * 
     * Rodar com VM Arguments = -Djava.library.path=<PROCESSING_HOME>/libraries/opengl/library
     *  
     */
    public static void main(String[] args) {
        
        PApplet.main(new String[] { "usp.ia.velia.graph.Sketch" });
    }

}
