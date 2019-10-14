package stub.comando.gerenciador_de_udp;

import stub.Stub;
import stub.comando.Parametros;

public class ContinuarAberturaUDP extends ComandoGerenciadorDePartida {
    
    private Integer portaUDPServidor = null;
    
    public ContinuarAberturaUDP(String codigo, Stub.GerenciadorDeConexaoUDPRemota gerenciador) {
        super(codigo, gerenciador);
    }
    
    @Override
    public void executar() {
        if(this.portaUDPServidor == null) {
            throw new RuntimeException("Nao eh possivel executar o comando: numero da porta nao definido");
        }
        super.GERENCIADOR.continuarAberturaUDP(this.portaUDPServidor);
    }
    
    @Override
    public void definirParametros(Parametros parametros) {
        ContinuarAberturaUDPParametros continuarAberturaUDPParametros = (ContinuarAberturaUDPParametros) parametros;
        this.portaUDPServidor = continuarAberturaUDPParametros.getPortaUDPServidor();
    }
}