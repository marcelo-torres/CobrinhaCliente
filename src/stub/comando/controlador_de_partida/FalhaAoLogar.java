package stub.comando.controlador_de_partida;

import model.agentes.IControladorGeralVisaoStubCliente;
import stub.comando.Parametros;

public class FalhaAoLogar extends ComandoControladorDePartida {
    
    public FalhaAoLogar(String codigo, IControladorGeralVisaoStubCliente controladorPartida) {
        super(codigo, false, controladorPartida);
    }
    
    @Override
    public Object executar(Parametros parametros) {
        FalhaAoLogarParametros falhaAoLogarParametros = (FalhaAoLogarParametros) parametros; 
        super.CONTROLADOR_PARTIDA.falhaAoLogar(falhaAoLogarParametros.getMensagem());
        return null;
    }
}