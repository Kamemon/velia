package usp.ia.velia.minimax;

public class MiniMaxInfo<T> {

    public enum Tipo {
        MINI, MAX
    };

    private Tipo tipo;
    private int heuristica;
    private T jogada;

    public Tipo getTipo() {
        return tipo;
    }

    public int getHeuristica() {
        return heuristica;
    }

    public T getJogada() {
        return jogada;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public void setHeuristica(int heuristica) {
        this.heuristica = heuristica;
    }

    public void setJogada(T jogada) {
        this.jogada = jogada;
    }
}
