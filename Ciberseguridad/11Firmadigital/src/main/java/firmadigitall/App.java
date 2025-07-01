/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package firmadigitall;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Arrays;

public class App {

    public static void main(String[] args) throws Exception {
        // 1. Generar un par de claves (pública y privada)
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048); // Tamaño de clave de 2048 bits
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        // 2. Datos a firmar
        byte[] data = "Este es el texto a firmar".getBytes("UTF-8");

        // 3. Firma digital
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data);
        byte[] digitalSignature = signature.sign();

        System.out.println("Firma digital: " + Arrays.toString(digitalSignature));

        // 4. Verificación de la firma
        signature.initVerify(publicKey);
        signature.update(data);
        boolean isVerified = signature.verify(digitalSignature);

        System.out.println("La firma es válida: " + isVerified);
    }
}