package nucleo;

import model.agentes.ControladorDePartida;
import java.io.Closeable;
import java.net.InetAddress;
import localizacoes.Hall;
import localizacoes.ILocal;
import model.send.Arena;
import stub.ControladorDeConexao;
import model.agentes.IControladorGeralVisaoStubCliente;

public class ControladorCliente implements Closeable {

    private  ControladorDeConexao JOGADOR;
    
    public ControladorCliente(
            InetAddress enderecoDoServidor,
            int portaTCPDoServidor) {
       // this.JOGADOR = new ControladorDeConexao(this, enderecoDoServidor, portaTCPDoServidor);
    }
    
    @Override
    public void close() {
       try {new Thread().sleep(1000);} catch(Exception e) {}
        this.JOGADOR.close();
    }
    
   
   
}
