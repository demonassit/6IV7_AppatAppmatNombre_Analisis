/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg04aes;

/**
 * ahi se ve??
 * @author Alumno
 */

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.*;
import  java.util.Base64.Encoder;


public class CifradorAES {
    
    //generar las subllaves y los metodos para cifrar y descifrar
    
    //un metodo para la llave
    public static final byte[] keyvalue = new byte[]{
        
        /*
        Recordemos que dentro de AES se van a manejar diferentes
        tamaños de la llave de acuerdo al tipo de operacion
        128 16 caracteres 9 rondas 
        192 24 caracteres 11 rondas
        256 32 caracteres 13 rondas
        */
        
        'q', 'w', 'e', 'r', 't', 'y', 'u', 'i',
        'q', 'w', 'e', 'r', 't', 'y', 'u', 'i'
    };
    
    //vamos a definir la instancia del algoritmo
    private static final String instancia = "AES";
    
    
    public static String encypt(String Data) throws Exception{
        /*
        para poder cifrar debemos de generar las subclaves necesarias
        para ejecutar el algoritmo acorde al numero de rondas, para
        ello vamos a ocupar un metodo de generacion de claves
        
        */
        Key subllave = generateKey();
        
        //inicializamos el cifrado
        Cipher cifrado = Cipher.getInstance(
                instancia);
        
        cifrado.init(Cipher.ENCRYPT_MODE, subllave);
        
        ////vamos a obtener el mensaje que se quiere cifrar
        //y lo transformamos en bytes
        byte[] encValores = cifrado.doFinal(Data.getBytes());
        
        System.out.println("Mensaje Cifrado sin formato: " 
                + encValores);
        
        //debemos aplicar formato de codificacion base 64 a partir
        //de la libreria sun con un ojeto BASE64Encoder
        //String valoresencriptadosformato = new 
        //BASE64Encoder().encode(encValores);
        
        String cadenaEnciptada = encValores.toString();
        
        return cadenaEnciptada;
    }
    
    
    public static String decypt(String valoresencriptados) throws Exception{
        /*
        para poder cifrar debemos de generar las subclaves necesarias
        para ejecutar el algoritmo acorde al numero de rondas, para
        ello vamos a ocupar un metodo de generacion de claves
        
        */
        Key subllave = generateKey();
        
        //inicializamos el cifrado
        Cipher cifrado = Cipher.getInstance(
                instancia);
        
        cifrado.init(Cipher.DECRYPT_MODE, subllave);
        
        ////vamos a obtener el mensaje que se quiere cifrar
        //y lo transformamos en bytes
        //byte[] decodifcarvalores = 
                //new BASE64Decoder().decodeBuffer(valoresencriptados);
        
        byte [] decValores = cifrado.doFinal(
                valoresencriptados.getBytes());
        System.out.println("Mensaje Descifrado sin formato: " 
                + decValores);
        
        //debemos aplicar formato de codificacion base 64 a partir
        //de la libreria sun con un ojeto BASE64Encoder
        //String valoresencriptadosformato = new 
        //BASE64Encoder().encode(encValores);
        
        String valoresDescifrados = new String(decValores);
        
        return valoresDescifrados;
    }

    private static Key generateKey() throws Exception{
        //vamos a ocupar llaves a partir de SecretKeySpec
        Key subllavekawaii = new SecretKeySpec(
                keyvalue, instancia);
        return subllavekawaii;
    }
    
}
