package stub.comando.controlador_de_partida;

import model.agentes.IControladorGeralVisaoStubCliente;
import stub.comando.Parametros;

public class ExibirTelaInicio extends ComandoControladorDePartida {
    
    public ExibirTelaInicio(String codigo, IControladorGeralVisaoStubCliente controladorPartida) {
        super(codigo, false, controladorPartida);
    }
    
    @Override
    public Object executar(Parametros parametros) {
        super.CONTROLADOR_PARTIDA.exibirTelaInicio();
        return null;
    }
}