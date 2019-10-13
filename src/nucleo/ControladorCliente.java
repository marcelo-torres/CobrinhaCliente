package nucleo;

import aplicacao.model.agentes.ControladorDePartida;
import aplicacao.model.agentes.Jogador;
import java.io.Closeable;
import java.net.InetAddress;
import model.send.Arena;
import stub.ControladorDeConexao;

public class ControladorCliente implements ControladorDePartida, Closeable {

    private final Jogador JOGADOR;
    
    public ControladorCliente(
            InetAddress enderecoDoServidor,
            int portaTCPDoServidor) {
        this.JOGADOR = new ControladorDeConexao(this, enderecoDoServidor, portaTCPDoServidor);
    }
    
    @Override
    public void close() {
        this.JOGADOR.close();
    }
    
    public void executarSequenciaDeTestes() {
        this.JOGADOR.iniciarPartida();
        this.JOGADOR.andarParaCima();
        this.JOGADOR.andarParaBaixo();
        
        try {new Thread().sleep(3000);} catch(Exception e) {}
        
        this.JOGADOR.encerrarPartida();
        
        try {new Thread().sleep(3000);} catch(Exception e) {}
        
        this.JOGADOR.iniciarPartida();
        this.JOGADOR.andarParaEsquerda();
        this.JOGADOR.andarParaDireita();
    }

    @Override
    public void vocerPerdeu() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void voceGanhou() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void adversarioSaiu() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void irParaOHall() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void logar(String login) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void falhaAoLogar(String mensagem) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void entregarQuadro(Arena arena) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    
}
