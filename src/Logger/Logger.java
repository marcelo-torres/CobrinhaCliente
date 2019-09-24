package Logger;

public class Logger {
    
    public enum Tipo {
        ERRO("ERRO"), DEBUG("DEBUG");
        
        private final String TIPO;
        
        Tipo(String valorOpcao){
            TIPO = valorOpcao;
        }
        
        public String getTIPO() {
            return this.TIPO;
        }
    }
    
    public static void registrar(Tipo tipo, String mensagem, Exception exception) {
        System.out.println("[LOG][" + tipo.getTIPO() + "] - " + mensagem + " (" + exception.getClass() + ")");
    }
}
