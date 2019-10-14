package stub.comando.gerenciador_de_udp;

import stub.Stub;
import stub.comando.Parametros;


public class FecharConexaoUDP extends ComandoGerenciadorDePartida {

    public FecharConexaoUDP(String codigo, Stub.GerenciadorDeConexaoUDPRemota gerenciador) {
        super(codigo, gerenciador);
    }
    
    @Override
    public void executar() {
        super.GERENCIADOR.fecharConexaoUDP();
    }

    @Override
    public void definirParametros(Parametros parametros) {
       throw new UnsupportedOperationException("Nenhum parametro necessario");
    }
}
