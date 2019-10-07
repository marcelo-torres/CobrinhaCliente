
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    
    public static void main(String[] args) throws Exception{
        /*String s = "UDP_ABRIR 52445";
        
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(s);
        while(m.find()) {
            System.out.println(m.group());
        }*/
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                
                try {
                    
                    int porta = 6666;
                
                    DatagramSocket s = new DatagramSocket();
                
                    s.connect(InetAddress.getLocalHost(), porta);

                    int i = 0;
                    while(i++ < 4) {
                        new Thread().sleep(200);
                        System.out.println(s.getRemoteSocketAddress());
                        byte[] buffer = "CHEGUEIIIIIIIIIII".getBytes();
                        DatagramPacket p = new DatagramPacket(buffer, buffer.length);
                        s.send(p);
                        s.send(p);
                    }
                } catch(Exception e) {
                }
            }
        }).start();
            
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DatagramSocket s2 = new DatagramSocket(6666);
                    byte[] chegou = new byte[1024];
                    DatagramPacket pacote = new DatagramPacket(chegou, chegou.length);
                    System.out.println("porta");
                    int i = 0;
                    while(i++ < 3) {
                        s2.receive(pacote);
                        System.out.println("A: " + new String(pacote.getData()));
                    }
                } catch(Exception e) {
                        e.printStackTrace();
                }
            }
         }).start();
    }
}
