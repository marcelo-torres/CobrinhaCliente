package stub.comando.controlador_de_partida;

import model.agentes.IControladorGeralVisaoStubCliente;
import stub.comando.Parametros;

public class ProcurandoPartida extends ComandoControladorDePartida {
    
    public ProcurandoPartida(String codigo, IControladorGeralVisaoStubCliente controladorPartida) {
        super(codigo, false, controladorPartida);
    }
    
    @Override
    public Object executar(Parametros parametros) {
        //super.CONTROLADOR_PARTIDA.procurandoPartida();
        return null;
    }
}