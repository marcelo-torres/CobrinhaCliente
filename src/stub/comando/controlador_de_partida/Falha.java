package stub.comando.controlador_de_partida;

import model.agentes.IControladorGeralVisaoStubCliente;
import stub.comando.Parametros;

public class Falha extends ComandoControladorDePartida {
    
    public Falha(String codigo, IControladorGeralVisaoStubCliente controladorPartida) {
        super(codigo, false, controladorPartida);
    }
    
    @Override
    public Object executar(Parametros parametros) {
        FalhaParametros falhaParametros = (FalhaParametros) parametros; 
        super.CONTROLADOR_PARTIDA.falha(falhaParametros.getMensagem());
        return null;
    }
}