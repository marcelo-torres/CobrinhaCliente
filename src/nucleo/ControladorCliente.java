package nucleo;

import jogo.Jogador;
import java.io.Closeable;
import java.net.InetAddress;
import stub.ControladorDeConexao;

public class ControladorCliente implements Closeable {

    private final Jogador JOGADOR;
    
    public ControladorCliente(
            int portaEscutarUDP,
            InetAddress enderecoDoServidor,
            int portaTCPDoServidor) {
        this.JOGADOR = new ControladorDeConexao(this, portaEscutarUDP, enderecoDoServidor, portaTCPDoServidor);
    }
    
    @Override
    public void close() {
        this.JOGADOR.close();
    }
    
    public void executarSequenciaDeTestes() {
        this.JOGADOR.iniciarPartida();
        this.JOGADOR.andarParaCima();
        this.JOGADOR.andarParaBaixo();
    }
    

    
}
