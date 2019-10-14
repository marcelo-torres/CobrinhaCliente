package stub.comando.controlador_de_partida;

import model.agentes.ControladorDePartida;
import stub.comando.Parametros;

public class IrParaOHall extends ComandoControladorDePartida {
    
    public IrParaOHall(String codigo, ControladorDePartida controladorPartida) {
        super(codigo, controladorPartida);
    }
    
    @Override
    public void executar() {
        super.CONTROLADOR_PARTIDA.irParaOHall();
    }
    
    @Override
    public void definirParametros(Parametros parametros) {
       throw new UnsupportedOperationException("Nenhum parametro necessario");
    }
}
