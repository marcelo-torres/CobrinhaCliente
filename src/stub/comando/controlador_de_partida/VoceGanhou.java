package stub.comando.controlador_de_partida;

import jogo.ControladorDePartida;

public class VoceGanhou extends ComandoControladorDePartida {
    
    public VoceGanhou(String codigo, ControladorDePartida controladorPartida) {
        super(codigo, controladorPartida);
    }
    
    @Override
    public void executar() {
        super.CONTROLADOR_PARTIDA.voceGanhou();
    }
    
}
