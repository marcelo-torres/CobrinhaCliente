package stub;

public class InterpretadorCliente {
    
    public String interpretar(byte[] mensagem) {
        return new String(mensagem);
    }
    
    public static byte[] interpretar(String mensagem) {
        return mensagem.getBytes();
    }
}