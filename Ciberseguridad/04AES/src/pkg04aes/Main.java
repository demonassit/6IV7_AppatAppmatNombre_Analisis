/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pkg04aes;

/**
 *
 * @author Alumno
 */

import java.io.*;
import java.util.*;


public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        System.out.println("Ejemplo de Cifrado AES");
        String mensaje = "habia una vez un patito que decia miau miau";
    
        String mensajeCifrado = CifradorAES.encypt(mensaje);
        System.out.println("El mensaje cifrado es: " + mensajeCifrado);
        
        String mensajeDescifrado = CifradorAES.decypt(mensajeCifrado);
        System.out.println("El mensaje descifrado es; " + mensajeDescifrado);
    }
    
}
