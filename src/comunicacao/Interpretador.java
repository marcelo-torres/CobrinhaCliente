package comunicacao;

import java.util.LinkedList;

public class Interpretador implements ReceptorDeMensagem<byte[]>{
    
    private ComunicadorTCP COMUNICADOR_TCP;
    private ComunicadorUDP COMUNICADOR_UDP;
    
    public Interpretador() {
    
    }
    
    public void definirComunicador(ComunicadorTCP comunicadorTCP, 
            ComunicadorUDP comunicadorUDP) {
        
        this.COMUNICADOR_TCP = comunicadorTCP;
        this.COMUNICADOR_UDP = comunicadorUDP;
    }

    @Override
    public void receberMensagem(byte[] mensagem) {
        System.out.println("[Interpretador] Mensagem recebida: " + new String(mensagem));
    }
    
    public void enviarMensagem(LinkedList<String> mensagens) {
        for(String mensagem : mensagens) {
            this.COMUNICADOR_TCP.enviarMensagem(mensagem.getBytes());
            
            try {
                new Thread().sleep(100);
            } catch(Exception e) {
                e.printStackTrace();
                return;
            }
            
        }
    }
}
