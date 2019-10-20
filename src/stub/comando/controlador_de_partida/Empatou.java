package stub.comando.controlador_de_partida;

import model.agentes.IControladorGeralVisaoStubCliente;
import stub.comando.Parametros;

public class Empatou extends ComandoControladorDePartida {
    
    public Empatou(String codigo, IControladorGeralVisaoStubCliente controladorPartida) {
        super(codigo, false, controladorPartida);
    }
    
    @Override
    public Object executar(Parametros parametros) {
        super.CONTROLADOR_PARTIDA.empatou();
        return null;
    }
}
