
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author marcelo
 */
public class Teste {
    
    public static String empacotarChamadaDeMetodo(String metodo, String... parametrosDoMetodo) {
        JSONObject mensagem = new JSONObject();
        
        JSONArray parametros = new JSONArray();
        for(String parametro : parametrosDoMetodo) {
            parametros.put(parametro);
        }
        
        JSONObject chamadaDeMetodo = new JSONObject();
        chamadaDeMetodo.put("nome", metodo);
        chamadaDeMetodo.put("parametros", parametros);
        
        mensagem.put("chamada_de_metodo", chamadaDeMetodo);
        
        return mensagem.toString();
    }
    
    public static void teste(Object... a) {
        
    }
    
    public static void main(String[] args) throws Exception{
        
        teste(new Object[10]);
        
        String m = empacotarChamadaDeMetodo("andarParaEsquerda", "Para1", "Para2");
         
        JSONObject decodificador = new JSONObject(m);
        JSONObject chamada_de_metodo = decodificador.getJSONObject("chamada_de_metodo");
        JSONArray parametros = chamada_de_metodo.getJSONArray("parametros");
         
        System.out.println("Parametros");
        for (int i = 0; i < parametros.length(); i++) {
            System.out.println("(" + i + ") " + parametros.get(i));
        }
        System.out.println();
        
        System.out.println(m);
        
    }
}
