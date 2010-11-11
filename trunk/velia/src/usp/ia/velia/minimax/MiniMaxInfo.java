package usp.ia.velia.minimax;

public class MiniMaxInfo<T> {

    public enum Tipo {
        MINI, MAX
    };

    private Tipo tipo;
    private Integer value; // alfa-beta
    private T jogada;

    public Tipo getTipo() {
        return tipo;
    }

    public Integer getValue() {
        return value;
    }

    public T getJogada() {
        return jogada;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setJogada(T jogada) {
        this.jogada = jogada;
    }
    
    public String toString() {
        
        return Integer.toString(this.value);
    }
}
