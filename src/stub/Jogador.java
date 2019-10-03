package stub;

import Logger.Logger;
import static Logger.Logger.Tipo.ERRO;
import static Logger.Logger.Tipo.INFO;
import java.io.IOException;
import java.net.InetAddress;
import java.util.LinkedList;
import jogo.ErroApresentavelException;
import stub.comunicacao.Comunicador;
import stub.comunicacao.Destinatario;

/**
 * blablablala escrever
 */
public class Jogador extends Destinatario implements jogo.Jogador {

    /**
     * Gerenciador de exceptions nao capturadas por metodos, isto eh, que
     * ocorreram em outras threads.
     */
    public static class GerenciadorDeException implements Thread.UncaughtExceptionHandler {
    
        private final LinkedList<Destinatario> DESTINATARIOS = new LinkedList();
        
        public void setGerenciadorDeException(Destinatario destinatario) {
            this.DESTINATARIOS.add(destinatario);
        }
        
        @Override
        public void uncaughtException(Thread th, Throwable ex) {
            Logger.registrar(ERRO, new String[]{"INTERPRETADOR"}, "Erro na comunicacao: " + ex.getMessage());
            Logger.registrar(INFO, new String[]{"INTERPRETADOR"}, "Encerrando devido a falha de comunicacao");
            
            for(Destinatario destinatario : this.DESTINATARIOS) {
                try {
                    destinatario.close();
                } catch(IOException ioe) {
                    Logger.registrar(ERRO, new String[]{"INTERPRETADOR", "DESTINATARIO"}, "Erro ao encerrar destinatario: " + ioe.getMessage());
                }
            }
        }
        
    }
    
    private final static GerenciadorDeException GERENCIADOR_DE_EXCEPTION = new GerenciadorDeException();
    
    public Jogador(
            int portaEscutarUDP,
            InetAddress enderecoDoServidor,
            int portaTCPDoServidor,
            int portaUDPDoServidor) {
        
        super(
                Comunicador.Modo.CLIENTE,
                portaEscutarUDP,
                enderecoDoServidor,
                portaTCPDoServidor,
                portaUDPDoServidor,
                GERENCIADOR_DE_EXCEPTION);
        GERENCIADOR_DE_EXCEPTION.setGerenciadorDeException(this);
    }
    
    public void iniciar() {
        try {
            super.MENSAGEIRO.iniciarTCP();
        } catch(IOException ioe) {
            Logger.registrar(ERRO, new String[]{"INTERPRETADOR"}, "Erro ao tentar iniciar a comunicaca: " + ioe.getMessage(), ioe);
            throw new ErroApresentavelException("Nao foi possivel iniciar a comunicacao com o servidor");
        }
    }
   
    
    @Override
    public void close() {
        super.MENSAGEIRO.close();
    }
    
    @Override
    public void receberMensagem(byte[] mensagem) {
        if(mensagem == null) {
            System.out.println("[!] Mano, vc ta jogando uma mensagem nula no interpretador! O que vc tem na cabeça tiw? Programa direito zeh mane");
        }
        System.out.println("[Interpretador] Mensagem recebida: " + new String(mensagem));
    }
    
    
    /* ###################################################################### */
    
    @Override
    public void iniciarPartida() {
        String mensagem = "[TCP] Jogador chama iniciarPartida()";
        super.MENSAGEIRO.inserirFilaEnvioTCP(mensagem.getBytes());
        if(!this.MENSAGEIRO.comunicadorUDPEstaAberto()) {
            try {
                super.MENSAGEIRO.iniciarUDP();
            } catch(IOException ioe) {
                Logger.registrar(ERRO, new String[]{"JOGADOR"}, "Nao foi possivel iniciar a partida devido a uma falha ao iniciar a comunicacao UDP: " + ioe.getMessage(), ioe);
                throw new ErroApresentavelException("Não foi possível iniciar a partida. Erro ao estabelecer uma conexão com o servidor");
            }
        }
    }

    @Override
    public void desistirDeProcurarPartida() {
        String mensagem = "[TCP] Jogador chama desistirDeProcurarPartida()";
        super.MENSAGEIRO.inserirFilaEnvioTCP(mensagem.getBytes());
        /*if(!this.MENSAGEIRO.comunicadorUDPEstaAberto()) {
            try {
                super.MENSAGEIRO.iniciarUDP();
            } catch(IOException ioe) {
                Logger.registrar(ERRO, new String[]{"JOGADOR"}, "Nao foi possivel iniciar a partida devido a uma falha ao iniciar a comunicacao UDP: " + ioe.getMessage(), ioe);
                throw new ErroApresentavelException("Não foi possível iniciar a partida. Erro ao estabelecer uma conexão com o servidor");
            }
        }*/
    }
    
    @Override
    public void encerrarPartida() {
        String mensagem = "[TCP] Jogador chama encerrarPartida()";
        super.MENSAGEIRO.inserirFilaEnvioTCP(mensagem.getBytes());
        if(this.MENSAGEIRO.comunicadorUDPEstaAberto()) {
            super.MENSAGEIRO.close();
        }
    }

    @Override
    public void andarParaCima() {
        String mensagem = "[UDP] Jogador chama andarParaCima()";
        super.MENSAGEIRO.inserirFilaEnvioUDP(mensagem.getBytes());
    }

    @Override
    public void andarParaBaixo() {
        String mensagem = "[UDP] Jogador chama andarParaBaixo()";
        super.MENSAGEIRO.inserirFilaEnvioUDP(mensagem.getBytes());
    }

    @Override
    public void andarParaEsquerda() {
        String mensagem = "[UDP] Jogador chama andarParaEsquerda()";
        super.MENSAGEIRO.inserirFilaEnvioUDP(mensagem.getBytes());
    }

    @Override
    public void andarParaDireita() {
        String mensagem = "[UDP] Jogador chama andarParaDireita()";
        super.MENSAGEIRO.inserirFilaEnvioUDP(mensagem.getBytes());
    }
    
    /* ###################################################################### */
    
    
    
    
    
    public void enviarMensagemTCPLembrarDeApagarEsteMetodo(byte[] mensagem) {
        super.MENSAGEIRO.inserirFilaEnvioTCP(mensagem);
    }
    
    public void enviarMensagemUDPLembrarDeApagarEsteMetodo(byte[] mensagem) {
        super.MENSAGEIRO.inserirFilaEnvioUDP(mensagem);
    }
    
    public void algumMetodoQueVaiPrecisarUsarConexaoUDP() {
        try {
            super.MENSAGEIRO.iniciarUDP();
        } catch(IOException ioe) {
            // Transparencia total eh impossivel
            throw new RuntimeException("Nao foi possivel executar o metodo algumMetodoQueVaiPrecisarUsarConexaoUDP");
        }
    }
    
    
    /* ########################### CHAMADAS DE RPC ########################## */
    
    
}
