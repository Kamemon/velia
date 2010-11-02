package usp.ia.velia;


public abstract class Jogador {
	
    public enum Insignia {X, O};
	
    private String nome;
    private Insignia insignia; // a marca, no caso tradicional X ou O
	
    
    public Jogador(String nome, Insignia insignia) {
        this.nome = nome;
        this.insignia = insignia;
    }

    public String getNome() {
        return nome;
    }
    public Insignia getInsignia() {
        return insignia;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setInsignia(Insignia insignia) {
        this.insignia = insignia;
    }
    
    public abstract int[] escolheJogada(Jogo jogo);
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((insignia == null) ? 0 : insignia.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Jogador other = (Jogador) obj;
        if (insignia == null) {
            if (other.insignia != null)
                return false;
        } else if (!insignia.equals(other.insignia))
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        return true;
    }
	

}
