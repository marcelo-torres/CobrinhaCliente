package stub.comando.controlador_de_partida;

import model.agentes.ControladorDePartida;
import stub.comando.Parametros;

public class Logar extends ComandoControladorDePartida {
    
    public Logar(String codigo, ControladorDePartida controladorPartida) {
        super(codigo, controladorPartida);
    }
    
    private String login = null;
    
    @Override
    public void executar() {
        if(this.login == null) {
            throw new RuntimeException("Nao eh possivel executar o comando: login nao definido");
        }
        super.CONTROLADOR_PARTIDA.logar(this.login);
    }
    
    @Override
    public void definirParametros(Parametros parametros) {
       LogarParametros logarParametros = (LogarParametros) parametros;
       this.login = logarParametros.getLogin();
    }
}