package stub.comando.controlador_de_partida;

import model.agentes.IControladorGeralVisaoStubCliente;
import stub.comando.Parametros;

public class Ganhou extends ComandoControladorDePartida {
    
    public Ganhou(String codigo, IControladorGeralVisaoStubCliente controladorPartida) {
        super(codigo, false, controladorPartida);
    }
    
    @Override
    public Object executar(Parametros parametros) {
        super.CONTROLADOR_PARTIDA.ganhou();
        return null;
    }
}