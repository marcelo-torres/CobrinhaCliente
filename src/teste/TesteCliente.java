package teste;

import Logger.Logger;
import controller.ControladorGeral;
import java.net.InetAddress;
import java.net.UnknownHostException;
import stub.ControladorDeConexao;
import stub.comunicacao.GerenciadorDePortas;

public class TesteCliente {
    
    public static int cont = 0;
    
    public static void main(String[] args) {
        String enderecoServidor = "127.0.0.1";
        int portaServidor = 2573;
        
        if(args.length >= 2) {
            enderecoServidor = args[0];
            portaServidor = Integer.valueOf(args[1]);
        }
        
        teste(enderecoServidor, portaServidor);
        
        /*final String endS = enderecoServidor;
        final int port = portaServidor;
        for(int i = 0; i < 10; i++) {
            new Thread(new Runnable() {public void run(){TesteCliente.teste(endS, port);}}).start();
            esperar(100);
        }*/
    }
    
    public static void teste(String enderecoServ, int portaTCPServidor) {
        cont++;
        
        InetAddress enderecoServidor = null;
        try {
            enderecoServidor = InetAddress.getByName(enderecoServ);
        } catch(UnknownHostException uhe) {
            throw new RuntimeException("Erro: " + uhe.getMessage());
        }     
        
        ControladorGeralTeste controladorGeral = new ControladorGeralTeste();
        GerenciadorDePortas gerenciadorDePortas = new GerenciadorDePortas();
        ControladorDeConexao controladorDeConexao = new ControladorDeConexao(controladorGeral, enderecoServidor, portaTCPServidor, gerenciadorDePortas);
        controladorDeConexao.iniciarStub();
        
        controladorDeConexao.iniciarSessao("SESSAO_TESTE");
        controladorDeConexao.encerrarSessao();
        
        boolean r1 = controladorDeConexao.iniciarPartida();
        Logger.registrar(Logger.Tipo.INFO, new String[]{" ======== TESTE ======== ",}, "Retorno iniciarPartida(): " + r1);
        esperar(300);
        boolean r2 = controladorDeConexao.desistirDeProcurarPartida();
        Logger.registrar(Logger.Tipo.INFO, new String[]{" ======== TESTE ======== ",}, "Retorno desistirDeProcurarPartida(): " + r2);
        
        boolean r3 = controladorDeConexao.iniciarPartida();
        Logger.registrar(Logger.Tipo.INFO, new String[]{" ======== TESTE ======== ",}, "Retorno iniciarPartida(): " + r3);
        controladorDeConexao.andarParaCima();
        controladorDeConexao.andarParaBaixo();
        controladorDeConexao.andarParaEsquerda();
        controladorDeConexao.andarParaDireita();
        esperar(300);
        boolean r4 = controladorDeConexao.encerrarPartida();
        Logger.registrar(Logger.Tipo.INFO, new String[]{" ======== TESTE ======== ",}, "Retorno encerrarPartida(): " + r4);
        
        esperar(300);
        controladorDeConexao.close();
        
        
        System.out.println("==== PROGRAMA CLIENTE(" + cont + ") ENCERRADO ====");
    }
 
    public static void esperar(int tempo) {
        try {new Thread().sleep(tempo);} catch(Exception e) {}
    }
    
    public static void exibirThreadAtivas() {
        System.out.println("Threads ativas:");
        Thread.getAllStackTraces().keySet().forEach((t) -> System.out.println(t.getName() + "  " + " Is Alive: " + t.isAlive()));
    }
}
