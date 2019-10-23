package teste;

import Logger.Logger;
import model.agentes.IControladorGeralVisaoStubCliente;
import model.agentes.IJogadorVisaoAplicacaoCliente;
import model.send.Arena;

public class ControladorGeralTeste implements IControladorGeralVisaoStubCliente {

    @Override
    public void novoQuadro(Arena arena) {
        Logger.registrar(Logger.Tipo.INFO, new String[]{" ======== TESTE ======== ","CONTROLADOR_GERAL"}, "chamada novoQuadro() | retorno: " + arena);
    }

    @Override
    public void exibirTelaSessao() {
        Logger.registrar(Logger.Tipo.INFO, new String[]{" ======== TESTE ======== ","CONTROLADOR_GERAL"}, "chamada exibirTelaSessao()");
    }

    @Override
    public void exibirTelaBusca() {
        Logger.registrar(Logger.Tipo.INFO, new String[]{" ======== TESTE ======== ","CONTROLADOR_GERAL"}, "chamada exibirTelaBusca()");
    }

    @Override
    public void exibirTelaJogo() {
        Logger.registrar(Logger.Tipo.INFO, new String[]{" ======== TESTE ======== ","CONTROLADOR_GERAL"}, "chamada exibirTelaJogo()");
    }

    @Override
    public void exibirTelaInicio() {
        Logger.registrar(Logger.Tipo.INFO, new String[]{" ======== TESTE ======== ","CONTROLADOR_GERAL"}, "chamada exibirTelaInicio()");
    }

    @Override
    public void perdeu() {
        Logger.registrar(Logger.Tipo.INFO, new String[]{" ======== TESTE ======== ","CONTROLADOR_GERAL"}, "chamada perdeu()");
    }

    @Override
    public void ganhou() {
        Logger.registrar(Logger.Tipo.INFO, new String[]{" ======== TESTE ======== ","CONTROLADOR_GERAL"}, "chamada ganhou()");
    }

    @Override
    public void empatou() {
        Logger.registrar(Logger.Tipo.INFO, new String[]{" ======== TESTE ======== ","CONTROLADOR_GERAL"}, "chamada empatou()");
    }

    @Override
    public void adversarioSaiu() {
        Logger.registrar(Logger.Tipo.INFO, new String[]{" ======== TESTE ======== ","CONTROLADOR_GERAL"}, "chamada adversarioSaiu()");
    }

    @Override
    public void falhaAoLogar(String mensagemTextual) {
        Logger.registrar(Logger.Tipo.INFO, new String[]{" ======== TESTE ======== ","CONTROLADOR_GERAL"}, "chamada falhaAoLogar() | parametro: " + mensagemTextual);
    }

    @Override
    public void falha(String nome_invalido) {
        Logger.registrar(Logger.Tipo.INFO, new String[]{" ======== TESTE ======== ","CONTROLADOR_GERAL"}, "chamada falha() | parametro: " + nome_invalido);
    }

    /*
    @Override
    public void procurandoPartida() {
        Logger.registrar(Logger.Tipo.INFO, new String[]{" ======== TESTE ======== ","CONTROLADOR_GERAL"}, "chamada procurandoPartida()");
    }
*/

    @Override
    public void setJogador(IJogadorVisaoAplicacaoCliente CONTROLADOR_DECONEXAO) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
