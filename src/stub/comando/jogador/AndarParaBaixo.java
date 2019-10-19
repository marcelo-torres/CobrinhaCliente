package stub.comando.jogador;

import stub.comando.Parametros;
import model.agentes.IControladorGeralVisaoStubCliente;

public class AndarParaBaixo extends ComandoJogador {
    
    public AndarParaBaixo(String codigo, IControladorGeralVisaoStubCliente jogador) {
        super(codigo, false, jogador);
    }

    @Override
    public Object executar(Parametros parametros) {
        super.JOGADOR.andarParaBaixo();
        return null;
    }
}