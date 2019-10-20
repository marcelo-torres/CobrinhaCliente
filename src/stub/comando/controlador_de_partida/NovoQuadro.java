package stub.comando.controlador_de_partida;

import model.agentes.IControladorGeralVisaoStubCliente;
import stub.comando.Parametros;

public class NovoQuadro extends ComandoControladorDePartida {

    public NovoQuadro(String codigo, IControladorGeralVisaoStubCliente controladorPartida) {
        super(codigo, false, controladorPartida);
    }
    
    @Override
    public Object executar(Parametros parametros) {
        NovoQuadroParametro entregarQuadroParametro = (NovoQuadroParametro) parametros;
        this.CONTROLADOR_PARTIDA.novoQuadro(entregarQuadroParametro.getArena());
        return null;
    }
    
    
    
}
