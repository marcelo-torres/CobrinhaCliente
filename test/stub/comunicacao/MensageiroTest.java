/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stub.comunicacao;

import java.net.InetAddress;
import java.net.Socket;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marcelo
 */
public class MensageiroTest {
    
    public MensageiroTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of iniciarTCP method, of class Mensageiro.
     */
    @Test
    public void testIniciarTCP_0args() throws Exception {
        System.out.println("iniciarTCP");
        Mensageiro instance = null;
        instance.iniciarTCP();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of iniciarTCP method, of class Mensageiro.
     */
    @Test
    public void testIniciarTCP_Socket() throws Exception {
        System.out.println("iniciarTCP");
        Socket socket = null;
        Mensageiro instance = null;
        instance.iniciarTCP(socket);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of encerrarTCP method, of class Mensageiro.
     */
    @Test
    public void testEncerrarTCP() {
        System.out.println("encerrarTCP");
        Mensageiro instance = null;
        instance.encerrarTCP();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of iniciarUDP method, of class Mensageiro.
     */
    @Test
    public void testIniciarUDP() throws Exception {
        System.out.println("iniciarUDP");
        int portaDeEscuta = 0;
        Mensageiro instance = null;
        instance.iniciarUDP(portaDeEscuta);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of definirDestinatario method, of class Mensageiro.
     */
    @Test
    public void testDefinirDestinatario() {
        System.out.println("definirDestinatario");
        InetAddress enderecoDoServidor = null;
        int portaDeEscutaUDPDoServidor = 0;
        Mensageiro instance = null;
        instance.definirDestinatario(enderecoDoServidor, portaDeEscutaUDPDoServidor);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of encerrarUDP method, of class Mensageiro.
     */
    @Test
    public void testEncerrarUDP() {
        System.out.println("encerrarUDP");
        Mensageiro instance = null;
        instance.encerrarUDP();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of comunicadorTCPEstaAberto method, of class Mensageiro.
     */
    @Test
    public void testComunicadorTCPEstaAberto() {
        System.out.println("comunicadorTCPEstaAberto");
        Mensageiro instance = null;
        boolean expResult = false;
        boolean result = instance.comunicadorTCPEstaAberto();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of comunicadorUDPEstaAberto method, of class Mensageiro.
     */
    @Test
    public void testComunicadorUDPEstaAberto() {
        System.out.println("comunicadorUDPEstaAberto");
        Mensageiro instance = null;
        boolean expResult = false;
        boolean result = instance.comunicadorUDPEstaAberto();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPortaEscutaTCP method, of class Mensageiro.
     */
    @Test
    public void testGetPortaEscutaTCP() {
        System.out.println("getPortaEscutaTCP");
        Mensageiro instance = null;
        int expResult = 0;
        int result = instance.getPortaEscutaTCP();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPortaEscutaUDP method, of class Mensageiro.
     */
    @Test
    public void testGetPortaEscutaUDP() {
        System.out.println("getPortaEscutaUDP");
        Mensageiro instance = null;
        int expResult = 0;
        int result = instance.getPortaEscutaUDP();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of close method, of class Mensageiro.
     */
    @Test
    public void testClose() {
        System.out.println("close");
        Mensageiro instance = null;
        instance.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of inserirFilaRecebimento method, of class Mensageiro.
     */
    @Test
    public void testInserirFilaRecebimento() {
        System.out.println("inserirFilaRecebimento");
        byte[] mensagem = null;
        Mensageiro instance = null;
        instance.inserirFilaRecebimento(mensagem);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removerFilaRecebimento method, of class Mensageiro.
     */
    @Test
    public void testRemoverFilaRecebimento() {
        System.out.println("removerFilaRecebimento");
        Mensageiro instance = null;
        byte[] expResult = null;
        byte[] result = instance.removerFilaRecebimento();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fecharFilaRecebimento method, of class Mensageiro.
     */
    @Test
    public void testFecharFilaRecebimento() {
        System.out.println("fecharFilaRecebimento");
        Mensageiro instance = null;
        instance.fecharFilaRecebimento();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of inserirFilaEnvioTCP method, of class Mensageiro.
     */
    @Test
    public void testInserirFilaEnvioTCP() {
        System.out.println("inserirFilaEnvioTCP");
        byte[] mensagem = null;
        Mensageiro instance = null;
        instance.inserirFilaEnvioTCP(mensagem);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of inserirFilaEnvioTCPNaFrente method, of class Mensageiro.
     */
    @Test
    public void testInserirFilaEnvioTCPNaFrente() {
        System.out.println("inserirFilaEnvioTCPNaFrente");
        byte[] mensagem = null;
        Mensageiro instance = null;
        instance.inserirFilaEnvioTCPNaFrente(mensagem);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removerFilaEnvioTCP method, of class Mensageiro.
     */
    @Test
    public void testRemoverFilaEnvioTCP() {
        System.out.println("removerFilaEnvioTCP");
        Mensageiro instance = null;
        byte[] expResult = null;
        byte[] result = instance.removerFilaEnvioTCP();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fecharFilaEnvioTCP method, of class Mensageiro.
     */
    @Test
    public void testFecharFilaEnvioTCP() {
        System.out.println("fecharFilaEnvioTCP");
        Mensageiro instance = null;
        instance.fecharFilaEnvioTCP();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of inserirFilaEnvioUDP method, of class Mensageiro.
     */
    @Test
    public void testInserirFilaEnvioUDP() {
        System.out.println("inserirFilaEnvioUDP");
        byte[] mensagem = null;
        Mensageiro instance = null;
        instance.inserirFilaEnvioUDP(mensagem);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of inserirFilaEnvioUDPNaFrente method, of class Mensageiro.
     */
    @Test
    public void testInserirFilaEnvioUDPNaFrente() {
        System.out.println("inserirFilaEnvioUDPNaFrente");
        byte[] mensagem = null;
        Mensageiro instance = null;
        instance.inserirFilaEnvioUDPNaFrente(mensagem);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removerFilaEnvioUDP method, of class Mensageiro.
     */
    @Test
    public void testRemoverFilaEnvioUDP() {
        System.out.println("removerFilaEnvioUDP");
        Mensageiro instance = null;
        byte[] expResult = null;
        byte[] result = instance.removerFilaEnvioUDP();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fecharFilaEnvioUDP method, of class Mensageiro.
     */
    @Test
    public void testFecharFilaEnvioUDP() {
        System.out.println("fecharFilaEnvioUDP");
        Mensageiro instance = null;
        instance.fecharFilaEnvioUDP();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
