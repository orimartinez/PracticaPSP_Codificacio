import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ClasseAES {

    // Aquest mètode rep el missatge i la clau, i retorna el missatge xifrat.
    public static String encripta(String missatge, String clau) {
        try {
            // Preparem la clau perquè AES la pugui utilitzar.
            SecretKeySpec secretKey = creaClau(clau);

            // Creem el sistema de xifrat AES.
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            // Li diem que volem xifrar i li passem la clau.
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // Convertim el missatge a bytes i el xifrem.
            byte[] missatgeXifrat = cipher.doFinal(
                    missatge.getBytes(StandardCharsets.UTF_8)
            );

            // Convertim els bytes xifrats a text per poder-los mostrar o guardar.
            return Base64.getEncoder().encodeToString(missatgeXifrat);

        } catch (Exception e) {
            // Si hi ha algun problema mentre xifrem, mostrem aquest error.
            throw new IllegalArgumentException("No s'ha pogut encriptar el missatge.", e);
        }
    }

    // Aquest mètode rep el missatge xifrat i la clau, i recupera el missatge original.
    public static String desencripta(String missatgeXifrat, String clau) {
        try {
            // Preparem la mateixa clau que hem fet servir per xifrar.
            SecretKeySpec secretKey = creaClau(clau);

            // Creem el sistema AES i li diem que ara volem desxifrar.
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            // Convertim el text Base64 a bytes xifrats.
            byte[] bytesXifrats = Base64.getDecoder().decode(missatgeXifrat);

            // Desxifrem els bytes amb la clau.
            byte[] missatgeOriginal = cipher.doFinal(bytesXifrats);

            // Convertim els bytes recuperats a text i retornem el missatge.
            return new String(missatgeOriginal, StandardCharsets.UTF_8);

        } catch (Exception e) {
            // Pot passar, per exemple, si la clau no és la correcta.
            throw new IllegalArgumentException(
                    "No s'ha pogut desencriptar el missatge. Comprova la clau i el text xifrat.", e
            );
        }
    }

    // Aquest mètode comprova la clau i la prepara per utilitzar-la amb AES.
    private static SecretKeySpec creaClau(String clau) {
        // Passem la clau de text a bytes.
        byte[] bytesClau = clau.getBytes(StandardCharsets.UTF_8);

        // AES només accepta claus de 16, 24 o 32 bytes.
        if (bytesClau.length != 16 && bytesClau.length != 24 && bytesClau.length != 32) {
            throw new IllegalArgumentException(
                    "La clau ha de tenir 16, 24 o 32 bytes."
            );
        }

        // Creem la clau AES a partir dels bytes.
        return new SecretKeySpec(bytesClau, "AES");
    }

    // Aquí provem que el missatge es pugui xifrar i després recuperar.
    public static void main(String[] args) {
        String missatge = "Hola, aquest és un missatge secret!";
        String clau = "clauDe16Bytes123"; // Aquesta clau té 16 bytes.

        // Xifrem el missatge amb la clau.
        String xifrat = encripta(missatge, clau);

        // Desxifrem el resultat amb la mateixa clau.
        String desxifrat = desencripta(xifrat, clau);

        // Mostrem el missatge original, el xifrat i el recuperat.
        System.out.println("Missatge original: " + missatge);
        System.out.println("Missatge xifrat: " + xifrat);
        System.out.println("Missatge desencriptat: " + desxifrat);
    }
}