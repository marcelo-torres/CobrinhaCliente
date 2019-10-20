package stub.comando.controlador_de_partida;

import model.agentes.IControladorGeralVisaoStubCliente;
import stub.comando.Comando;

public abstract class ComandoControladorDePartida extends Comando {
    
    protected final IControladorGeralVisaoStubCliente CONTROLADOR_PARTIDA;
    
    public ComandoControladorDePartida(String codigo, boolean possuiRetorno, IControladorGeralVisaoStubCliente controladorPartida) {
        super(codigo, possuiRetorno);
        this.CONTROLADOR_PARTIDA = controladorPartida;
    }
    
}