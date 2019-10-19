package stub.comando.jogador;

import stub.comando.Parametros;
import model.agentes.IControladorGeralVisaoStubCliente;

public class AndarParaDireita extends ComandoJogador {
    
    public AndarParaDireita(String codigo, IControladorGeralVisaoStubCliente jogador) {
        super(codigo, false, jogador);
    }

    @Override
    public Object executar(Parametros parametros) {
        super.JOGADOR.andarParaDireita();
        return null;
    }
}