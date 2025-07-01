/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package llavesfirma;

/**
 *
 * @author demon
 */

import java.security.*;
import java.io.*;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.*;

public class App {
    
    private static Cipher rsa;

    public static void main(String[] args) throws Exception{
        //Generamos las claves
        
        KeyPairGenerator generadorRSA = KeyPairGenerator.getInstance("RSA");
        
        KeyPair llaves = generadorRSA.genKeyPair();
        
        PublicKey llavepublica = llaves.getPublic();
        
        PrivateKey llaveprivada = llaves.getPrivate();
        
        //vamos a crear el metodo para guardarlo en el archivo
        saveKey(llavepublica, "public.key");
        
        llavepublica = loadPublicKey("public.key");
        
        saveKey(llaveprivada, "private.key");
        
        llaveprivada = loadPrivateKey("private.key");
        
        //vamos a dar formato 
        
        rsa = 
                Cipher.getInstance("RSA/ECB/PKCS1Padding");
        
        String texto = "Habia una vez un patito que decia miau miau";
        
        //ciframos
        
        rsa.init(Cipher.ENCRYPT_MODE, llavepublica);
        
        byte[] encriptado = rsa.doFinal(texto.getBytes());
        
        //recorremos
        
        for(byte b : encriptado){
            System.out.println(Integer.toHexString(0xFF & b));
        }
        System.out.println("");
        
        rsa.init(Cipher.DECRYPT_MODE, llaveprivada);
        
        byte[] bytesdescifrados = rsa.doFinal();
        
        String textodescifrado = new String(bytesdescifrados);
        
        System.out.print(textodescifrado);
        
        
        
    }

    private static void saveKey(Key llave, String archivo) throws Exception{
        byte[] llavesbytes = llave.getEncoded();
        FileOutputStream fos = new FileOutputStream(archivo);
        fos.write(llavesbytes);
        fos.close();
    }

    private static PublicKey loadPublicKey(String archivo) throws Exception{
        FileInputStream fis = new FileInputStream(archivo);
        int numByte = fis.available();
        byte[] bytes = new byte[numByte];
        fis.read(bytes);
        fis.close();
        
        //implementamos el protocolo
        KeyFactory llavebytePublic = 
                KeyFactory.getInstance("RSA");
        KeySpec keyspec = new X509EncodedKeySpec(bytes);
        
        //comparamos
        PublicKey nuevallavepublica = 
                llavebytePublic.generatePublic(keyspec);
        
        return nuevallavepublica;
    }

    private static PrivateKey loadPrivateKey(String archivo) throws Exception{
        FileInputStream fis = new FileInputStream(archivo);
        int numByte = fis.available();
        byte[] bytes = new byte[numByte];
        fis.read(bytes);
        fis.close();
        
        //implementamos el protocolo
        KeyFactory llavebytesprivada = KeyFactory.getInstance("RSA");
        KeySpec keyspec = new PKCS8EncodedKeySpec(bytes);
        
        //comparo
        PrivateKey nuevallaveprivada = 
                llavebytesprivada.generatePrivate(keyspec);
        
        return nuevallaveprivada;
    }
}
