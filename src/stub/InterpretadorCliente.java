package stub;

import org.json.JSONArray;
import org.json.JSONObject;

public class InterpretadorCliente {
    
    private byte[] empacotarChamadaDeMetodo(String metodo, String... parametrosDoMetodo) {
        JSONObject mensagem = new JSONObject();
        
        JSONArray parametros = new JSONArray();
        for(String parametro : parametrosDoMetodo) {
            parametros.put(parametro);
        }
        
        JSONObject chamadaDeMetodo = new JSONObject();
        chamadaDeMetodo.put("nome", metodo);
        chamadaDeMetodo.put("parametros", parametros);
        
        mensagem.put("chamada_de_metodo", chamadaDeMetodo);
        
        return mensagem.toString().getBytes();
    }
    
    
    public String interpretar(byte[] mensagem) {
        return new String(mensagem);
    }
    
    public static byte[] interpretar(String mensagem) {
        return mensagem.getBytes();
    }
}