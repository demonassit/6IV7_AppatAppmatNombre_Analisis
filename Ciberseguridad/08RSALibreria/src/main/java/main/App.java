/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main;

/**
 *
 * @author demon
 */
import java.io.*;
import java.security.*;
import java.security.spec.*;
import javax.crypto.*;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;



public class App {
    
    


    public static void main(String[] args) throws Exception{
        
        //agregar el Provider para soporte con RSA
        Security.addProvider(new BouncyCastleProvider());
        
        System.out.println("1.-Vamos a crear las llaves publicas y privadas");
        
        //inicializar el algoritmo de las subllaves
        KeyPairGenerator generadorllave = 
                KeyPairGenerator.getInstance("RSA", "BC");
        
        //definimos el tama;o
        generadorllave.initialize(4096);
        
        //generar las llaves a partir del tama;o, es realizar el calculo
        //de los numero p,q,n,fi, e, d
        KeyPair clavesRSA = generadorllave.genKeyPair();

        //creamos las llaves publicas y privadas
        
        PrivateKey clavePrivada = clavesRSA.getPrivate();
        
        PublicKey clavePublica = clavesRSA.getPublic();
        
        
        //programa
        
        System.out.println("2.- Introduce el texto que deseas cifrar");
        
        /*
        Recordando que existen diferentes modos de cifrado, en el caso de BC
        este no puede formar bloques por lo tanto no es posible realizar el modo
        ECD, y solo puede tomar maximo 64 caracteres a menos que se programe
        el modo de forma manual con el manejador de flujos y bloques
        */
        
        //texto lo vamos a transformar en bytes
        byte[] bufferplano = leerLinea(System.in);
        
        //vamos con el cifrado
        Cipher cifrado = Cipher.getInstance("RSA", "BC");
        
        cifrado.init(Cipher.ENCRYPT_MODE, clavePublica);
        
        System.out.println("3.- Vamos a cifrar con clave publica");
        
        byte[] buffercifrado = cifrado.doFinal(bufferplano);
        
        System.out.println("4.- Mostramos el Texto Cifrado: ");
        
        mostrarBytes(buffercifrado);
        
        System.out.println("");
        
        //desciframos
        
        System.out.println("5.- Vamos a descifrar con clave privada");
        
        cifrado.init(Cipher.DECRYPT_MODE, clavePrivada);
        
        byte[] bufferdescifrado = cifrado.doFinal(buffercifrado);
        
        mostrarBytes(bufferdescifrado);
        
        System.out.println("");
        
        
        //ahora alreves
        
        //vamos con el cifrado
        cifrado = Cipher.getInstance("RSA", "BC");
        
        cifrado.init(Cipher.ENCRYPT_MODE, clavePrivada);
        
        System.out.println("3.- Vamos a cifrar con clave privada");
        
        buffercifrado = cifrado.doFinal(bufferplano);
        
        System.out.println("4.- Mostramos el Texto Cifrado: ");
        
        mostrarBytes(buffercifrado);
        
        System.out.println("");
        
        //desciframos
        
        System.out.println("5.- Vamos a descifrar con clave publica");
        
        cifrado.init(Cipher.DECRYPT_MODE, clavePublica);
        
        bufferdescifrado = cifrado.doFinal(buffercifrado);
        
        mostrarBytes(bufferdescifrado);
        
        System.out.println("");
        
        
        
    }

    public static byte[] leerLinea(InputStream in) throws Exception{
        byte[] buffer1 = new byte[1000];
        
        int i = 0;
        
        byte c;
        
        c = (byte)in.read();
        
        while( (c != '\n') && ( i < 1000) ){
            buffer1[i] = c;
            c = (byte)in.read();
            i++;
        }
        
        byte[] buffer2 = new byte[i];
        
        for( int j = 0; j < i; j++){
            buffer2[j] = buffer1[j];
        }
        
        
        return buffer2;
    }

    private static void mostrarBytes(byte[] buffer) {
        System.out.write(buffer, 0, buffer.length);
    }
}
