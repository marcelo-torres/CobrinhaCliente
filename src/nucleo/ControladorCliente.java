package nucleo;

import model.agentes.ControladorDePartida;
import java.io.Closeable;
import java.net.InetAddress;
import localizacoes.Hall;
import localizacoes.ILocal;
import model.send.Arena;
import stub.ControladorDeConexao;
import model.agentes.IJogador;

public class ControladorCliente implements ControladorDePartida, Closeable {

    private final IJogador JOGADOR;
    
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
        if(true) {
            this.JOGADOR.iniciarPartida();
            this.JOGADOR.desistirDeProcurarPartida();
            
            this.JOGADOR.iniciarPartida();
            this.JOGADOR.andarParaCima();
            this.JOGADOR.andarParaBaixo();
            this.JOGADOR.andarParaEsquerda();
            this.JOGADOR.andarParaDireita();

            //try {new Thread().sleep(300);} catch(Exception e) {}

            this.JOGADOR.encerrarPartida();

            //try {new Thread().sleep(300);} catch(Exception e) {}

            this.JOGADOR.iniciarPartida();

            this.JOGADOR.andarParaCima();
            this.JOGADOR.andarParaBaixo();
            this.JOGADOR.andarParaEsquerda();
            this.JOGADOR.andarParaDireita();
            
            double valorRecebido = this.JOGADOR.getVD();
            System.out.println("ControladorDeCliente: valor recebido: " + valorRecebido);

            ILocal local = this.JOGADOR.getLocalAtual();
            System.out.println("ControladorDeCliente: valor recebido: " + local);

            local = new Hall();
            System.out.println("ControladorDeCliente: enviando: " + local);
            this.JOGADOR.setLocalAtual(local);
        }
        
        
        
        
        
    }

    @Override
    public void vocerPerdeu() {
        System.out.println("    === TESTE CLIENTE === Chamada recebida do servidor: vocerPerdeu");
    }

    @Override
    public void voceGanhou() {
         System.out.println("    === TESTE CLIENTE === Chamada recebida do servidor: voceGanhou");
    }

    @Override
    public void adversarioSaiu() {
       System.out.println("    === TESTE CLIENTE === Chamada recebida do servidor: adversarioSaiu");
    }

    @Override
    public void irParaOHall() {
        System.out.println("    === TESTE CLIENTE === Chamada recebida do servidor: irParaOHall");
    }

    @Override
    public void logar(String login) {
        System.out.println("    === TESTE CLIENTE === Chamada recebida do servidor: logar -> retornando o valor: " + login);
    }

    @Override
    public void falhaAoLogar(String mensagem) {
       System.out.println("    === TESTE CLIENTE === Chamada recebida do servidor: falhaAoLogar -> retornando o valor: " + mensagem);
    }

    @Override
    public void entregarQuadro(Arena arena) {
        System.out.println("    === TESTE CLIENTE === Chamada recebida do servidor: entregarQuadro -> retornando o valor: " + arena);
    }
   
}
