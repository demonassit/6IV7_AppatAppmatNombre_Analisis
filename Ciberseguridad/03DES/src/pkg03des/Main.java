/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pkg03des;

/**
 *
 * @author Alumno
 */
//es para definir entradas y salidas del sistema
//para el manejo de archivos
import java.io.*;
//es para el caculo de subllaves
import java.security.*;
//es para definir el algoritmo de cifrado
import javax.crypto.*;

//para el algoritmo
import javax.crypto.interfaces.*;
//para definir el tamaño de la clave y subclaves
import javax.crypto.spec.*;



public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        
        /*
        Lo primero es que vamos a crear un programa mediante
        el cual debe de leer un archivo de texto plano
        se debe de introducir una clave
        debe de cifrarlo y generar el archivo correspondiente
        cifrado
        */
        
        //Vamos usar DES 
        //comprobar que exista un archivo cargado
        if(args.length != 1){
            mensajeAyuda();
            System.exit(1);
        }
        
        //Paso 1 Debemos de definir el algoritmo y su clave
        System.out.println("1.- Generar las claves DES");
        //Para generar las claves utilizamos la clase KeyGenerator
        KeyGenerator generadorDES = 
                KeyGenerator.getInstance("DES");
        System.out.println("");
        //Debemos de inicializar el tamaño de la clave
        generadorDES.init(56);//el tamaño de la subllave es de 64 - 8 bit´s de paridad
        //El algoritmo envia error si no es exactamente 56
        
        //tenemos dos opciones para la clave, la creamos de forma manual o
        //utilizamos la clase SecretKey
        //si es de forma manual se ingresa por parte del usuario
        //se valida el tamaño 8 caracteres
        //transformamos la clave en bits
        
        //Estas son las subclaves para las 16 rondas
        SecretKey clave = generadorDES.generateKey();
        
        System.out.println("La clave es: " + clave);
        
        //no es posible distinguir los bytes de un caracter 
        //sino esta codificado
        mostrarBytes(clave.getEncoded());
        
        System.out.println(
                "Clave codificada : "+ clave.getEncoded());
        System.out.println("");
        
        /*
        El tipo de cifrado es DES, es de tipo Simetrico
        significa que la clave de cifrado es la misma para
        descifrar.
        Hay que definir el modo de operacion del cifrado:
        Flujo o es por Bloques
        ECB 
        Si va a tener o no Relleno
        Debemos de aplicar un estandar para dich relleno
        Este es el estandar de relleno para poderlo
        programar PKCS5
        */
        
        Cipher cifrador = 
                Cipher.getInstance(
                        "DES/ECB/PKCS5Padding");
        
        //vamos a crear el menu para cifrar y descifrar
        System.out.println("2.- Cifrar un fichero con DES : " 
        + args[0] + " dejamos el resultado en: " 
        + args[0] + ".cifrado");
        
        // tenemos que cargar el archivo y ejecutar el cirado
        
        cifrador.init(Cipher.ENCRYPT_MODE, clave);
        
        //aqui es donde es importante recordar el modo
        //ECB no puede automatizar el flujo del bloque
        
        byte[] buffer = new byte[1000];
        //este arreglo sirve para guardar el resultado
        byte[] buffercifrado;
        
        //definir el archivo
        FileInputStream entrada = new FileInputStream(args[0]);
        FileOutputStream salida = new FileOutputStream(args[0]+".cifrado");
        
        int bytesleidos = entrada.read(buffer, 0, 1000);
        
        while(bytesleidos != -1){
            buffercifrado = cifrador.update(
                    buffer, 0, 1000);
            salida.write(buffercifrado);
            bytesleidos = entrada.read(
                    buffer, 0, bytesleidos);
            
        }
        
        //construir salida
        buffercifrado = cifrador.doFinal();
        //genero el archivo de salida
        salida.write(buffercifrado);
        
        entrada.close();
        salida.close();
        
        //ahora  a descifrar
        System.out.println("3.- Descifrar un fichero con DES : " 
        + args[0] + ".cifrado " 
        + args[0] + " .descifrado");
        
        // tenemos que cargar el archivo y ejecutar el cirado
        
        cifrador.init(Cipher.DECRYPT_MODE, clave);
        
        //aqui es donde es importante recordar el modo
        //ECB no puede automatizar el flujo del bloque
        
        
        //este arreglo sirve para guardar el resultado
        byte[] bufferdescifrado;
        
        //definir el archivo
        entrada = new FileInputStream(args[0]+".cifrado");
        salida = new FileOutputStream(args[0]+".descifrado");
        
        bytesleidos = entrada.read(buffer, 0, 1000);
        
        while(bytesleidos != -1){
            bufferdescifrado = cifrador.update(
                    buffer, 0, 1000);
            salida.write(bufferdescifrado);
            bytesleidos = entrada.read(
                    buffer, 0, bytesleidos);
            
        }
        
        //construir salida
        bufferdescifrado = cifrador.doFinal();
        //genero el archivo de salida
        salida.write(bufferdescifrado);
        
        entrada.close();
        salida.close();
        
    }

    private static void mensajeAyuda() {
        System.out.println("Ejemplo de un programa "
                + "que sirve para cifrar y descifrar con DES");
        System.out.println("Favor de ingresar un archivo de "
                + "texto plano, sino no funciona osea .txt");
        
    }

    private static void mostrarBytes(byte[] buffer) {
        //gracias a que es ECB solo tenemos que escribir
        //el formato del tipo de buffer para el archivo
        System.out.write(buffer, 0, buffer.length);
    }
    
}
