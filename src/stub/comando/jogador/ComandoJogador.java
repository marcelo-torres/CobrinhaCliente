package stub.comando.jogador;

import stub.comando.Comando;
import model.agentes.IControladorGeralVisaoStubCliente;

public abstract class ComandoJogador extends Comando {

    protected final IControladorGeralVisaoStubCliente JOGADOR;
    
    public ComandoJogador(String codigo, boolean possuiParametro, IControladorGeralVisaoStubCliente jogador) {
        super(codigo, possuiParametro);
        this.JOGADOR = jogador;
    }
    
}