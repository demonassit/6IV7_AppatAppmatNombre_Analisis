/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package firma;

/**
 *
 * @author demon
 */

import java.security.*;
import java.io.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;




public class App {

    public static void main(String[] args) throws Exception{
        /*
        Vamos a crear un programa para poder generar firmas en archivos
       
        */
        
        if(args.length != 1){
            mensajeAyuda();
            System.exit(1);
        }
        
        //creamos los archivos
        System.out.println("Crea los archivos " + args[0] + " secreta "
        + args[0] + " publica "+ args[0] + " privada ");
        
        //agregamos el provider
        Security.addProvider(new BouncyCastleProvider());
        
        //generamos las claves del rsa
        KeyPairGenerator generadorRSA = 
                KeyPairGenerator.getInstance("RSA", "BC");
        
        //inicializamos las llaves
        generadorRSA.initialize(1024);
        
        KeyPair clavesRSA = generadorRSA.genKeyPair();
        
        //creamos publica y privada
        PrivateKey claveprivada = clavesRSA.getPrivate();
        PublicKey clavepublica = clavesRSA.getPublic();
        
        //para agregar la firma vamos a necesitas otra instacia
        KeyFactory keyfactoryrsa = 
                KeyFactory.getInstance("RSA", "BC");
        
        /*
        Vamos a volcar la clave privada con el fichero de acuerdo a las
        normas de la firma digital para ello se ocupa el elemento
        PKCS8 
        */
        
        PKCS8EncodedKeySpec pkcs8spec = 
                new PKCS8EncodedKeySpec(claveprivada.getEncoded());
        
        //Creamos el archivo donde lo vamos a guardar
        FileOutputStream out = new FileOutputStream(args[0]+".privada");
        out.write(pkcs8spec.getEncoded());
        out.close();
        
        //para recuperar la clave privada del archivo
        
        byte[] bufferpriv = new byte[5000];
        FileInputStream in = new FileInputStream(args[0]+".privada");
        in.read(bufferpriv, 0, 5000);
        in.close();
        
        //recuperamos la clave privada desde los datos codificados
        
        PKCS8EncodedKeySpec claveprivadaSpec = 
                new PKCS8EncodedKeySpec(bufferpriv);
        
        //clave privada verificamos la firma
        
        PrivateKey clavePrivada2 = 
                keyfactoryrsa.generatePrivate(claveprivadaSpec);
        
        //validamos la clave para saber si el archivo coincide con dicha clave
        
        if(claveprivada.equals(clavePrivada2)){
            System.out.println("Ok la clave ha sido guardada y recuperada");
        }
        
        //ahora volcamos la publica
        X509EncodedKeySpec x509spec = 
                new X509EncodedKeySpec(clavepublica.getEncoded());
        
        //Creamos el archivo donde lo vamos a guardar
        out = new FileOutputStream(args[0]+".publica");
        out.write(x509spec.getEncoded());
        out.close();
        
        //para recuperar la clave privada del archivo
        
        byte[] bufferpub = new byte[5000];
        in = new FileInputStream(args[0]+".publica");
        in.read(bufferpub, 0, 5000);
        in.close();
        
        //recuperamos la clave publica
        
        X509EncodedKeySpec clavepublicaspec2 = 
                new X509EncodedKeySpec(bufferpub);
        
        PublicKey clavepublica2 = 
                keyfactoryrsa.generatePublic(clavepublicaspec2);
        
        //comparo
        if(clavepublica.equals(clavepublica2)){
            System.out.println("Ok la clave ha sido guardada y recuperada");
        }
        
        
    }

    private static void mensajeAyuda() {
        System.out.println("Ejemplo de almacenamiento de Llaves");
    }
}
