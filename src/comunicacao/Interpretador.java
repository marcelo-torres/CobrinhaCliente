package comunicacao;

public class Interpretador implements ReceptorDeMensagem<byte[]>{
    
    public Interpretador() {
    
    }

    @Override
    public void receberMensagemTCP(byte[] mensagem) {
        if(mensagem == null) {
            System.out.println("[!] Mano, vc ta jogando uma mensagem nula no interpretador! O que vc tem na cabeça tiw? Programa direito zeh mane");
        }
        System.out.println("[Interpretador] Mensagem recebida via TCP: " + new String(mensagem));
    }
    
    @Override
    public void receberMensagemUDP(byte[] mensagem) {
        if(mensagem == null) {
            System.out.println("[!] Mano, vc ta jogando uma mensagem nula no interpretador! O que vc tem na cabeça tiw? Programa direito zeh mane");
        }
        System.out.println("[Interpretador] Mensagem recebida via UDP: " + new String(mensagem));
    }
}
