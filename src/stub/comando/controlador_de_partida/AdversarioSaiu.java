package stub.comando.controlador_de_partida;

import jogo.ControladorDePartida;

public class AdversarioSaiu extends ComandoControladorDePartida {

    public AdversarioSaiu(String codigo, ControladorDePartida controladorPartida) {
        super(codigo, controladorPartida);
    }
    
    @Override
    public void executar() {
        super.CONTROLADOR_PARTIDA.adversarioSaiu();
    } 
    
}
