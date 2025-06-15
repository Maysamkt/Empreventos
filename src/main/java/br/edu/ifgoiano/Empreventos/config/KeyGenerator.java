package br.edu.ifgoiano.Empreventos.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import javax.crypto.SecretKey;

public class KeyGenerator {
    public static void main(String[] args) {
        // Gera uma chave SecretKey de 512 bits para HS512
        SecretKey key = Jwts.SIG.HS512.key().build();

        // Codifica a chave para Base64 URL-Safe para colocar no application.properties
        String encodedKey = Encoders.BASE64URL.encode(key.getEncoded());

        System.out.println("Sua chave JWT (HS512) Base64-encoded: " + encodedKey);
        System.out.println("Tamanho da chave em bits: " + (key.getEncoded().length * 8));
    }
}
