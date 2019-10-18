package model.agentes;

import model.send.Arena;

public interface ControladorDePartida {
    
    public void vocerPerdeu();
    public void voceGanhou();
    public void adversarioSaiu();
    public void empatou();
    
    public void irParaOHall();
    public void logar(String login);
    public void falhaAoLogar(String mensagem);
    public void entregarQuadro(Arena arena);
      
    public void falha(String nome_inválido);
    
    public void partidaIniciada();
    public void procurandoPartida();
    
}
