package teste;

import Logger.Logger;
import controller.ControladorGeral;
import java.net.InetAddress;
import java.net.UnknownHostException;
import stub.ControladorDeConexao;

public class TesteCliente {
    
    public static void main(String[] args) {
    
        InetAddress enderecoServidor = null;
        try {
            enderecoServidor = InetAddress.getByName("127.0.0.1");
        } catch(UnknownHostException uhe) {
            throw new RuntimeException("Erro: " + uhe.getMessage());
        }     
        int portaTCPServidor = 2573;
        
        ControladorGeralTeste controladorGeral = new ControladorGeralTeste();
        ControladorDeConexao controladorDeConexao = new ControladorDeConexao(controladorGeral, enderecoServidor, portaTCPServidor);
        //controladorDeConexao.setControladorGeral(controladorGeral);
        
        controladorDeConexao.iniciarSessao("SESSAO_TESTE");
        controladorDeConexao.encerrarSessao();
        
        boolean r1 = controladorDeConexao.iniciarPartida();
        Logger.registrar(Logger.Tipo.INFO, new String[]{" ======== TESTE ======== ",}, "Retorno iniciarPartida(): " + r1);
        boolean r2 = controladorDeConexao.desistirDeProcurarPartida();
        Logger.registrar(Logger.Tipo.INFO, new String[]{" ======== TESTE ======== ",}, "Retorno desistirDeProcurarPartida(): " + r2);
        
        boolean r3 = controladorDeConexao.iniciarPartida();
        Logger.registrar(Logger.Tipo.INFO, new String[]{" ======== TESTE ======== ",}, "Retorno iniciarPartida(): " + r3);
        controladorDeConexao.andarParaCima();
        controladorDeConexao.andarParaBaixo();
        controladorDeConexao.andarParaEsquerda();
        controladorDeConexao.andarParaDireita();
        boolean r4 = controladorDeConexao.encerrarPartida();
        Logger.registrar(Logger.Tipo.INFO, new String[]{" ======== TESTE ======== ",}, "Retorno encerrarPartida(): " + r4);
        
        try {new Thread().sleep(200);} catch(Exception e) {}
        controladorDeConexao.close();
        
        Thread.getAllStackTraces().keySet().forEach((t) -> System.out.println(t.getName() + "  " + " Is Alive: " + t.isAlive()));
    }
    
}
