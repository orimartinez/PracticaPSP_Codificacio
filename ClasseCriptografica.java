package ProgramaEncriptacio;

public class ClasseCriptografica {
    String lletres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ Ó";

    public String encripta(String missatge, String clau) {
        // Creem un array per guardar els valors de les lletres
        int[] valors = new int[lletres.length()];
        // Estem fent un array de booleans per assignar true i evitar repeticions
        boolean[] utilitzats = new boolean[100];

        // Generem nombres aleatoris per a donar-li valors a les lletres
        for (int i = 0; i < valors.length; i++) {
            int valor;

            do {
                valor = (int) (Math.random() * 100);
            } while (utilitzats[valor]);

            valors[i] = valor;
            utilitzats[valor] = true;
        }

        // Amb el StringBuilder podem construïr el String poc a poc
        StringBuilder xifrat = new StringBuilder();
        StringBuilder codiOperacions = new StringBuilder();
        StringBuilder taula = new StringBuilder();

        // Mostrem els valors que s'han d'enviar.
        for (int i = 0; i < lletres.length(); i++) {
            if (i > 0) {
                // L'append ens permet afegir el que hi ha dins del parentesis al final del
                // string
                taula.append(",");
            }
            // El .format serveix (en aquest cas) per afegir un 0 en cas de que el numero
            // sigui d'una xifra
            taula.append(String.format("%02d", valors[i]));
        }

        // Xifrem cada caràcter del missatge.
        for (int i = 0; i < missatge.length(); i++) {
            char caracter = missatge.charAt(i);
            int posicio = lletres.indexOf(caracter);
            int valor = valors[posicio];

            // La clau es repeteix: 1a xifra, 2a, 3a, 4a, i torna a començar.
            int posicioClau = i % clau.length(); // Amb això ens assegurem que es repeteixi cada 4 cops

            // Amb això podem passar els caràcters a números (int)
            int digitClau = Character.getNumericValue(clau.charAt(posicioClau));

            // Triem aleatòriament 1 o 2.
            int operacio = (int) (Math.random() * 2) + 1;
            int resultat;

            if (operacio == 1) {
                resultat = valor + digitClau;
            } else {
                resultat = valor - digitClau;
            }

            if (i > 0) {
                // Amb el append, el que fem és afegir al final el que hi ha entre parentesis
                // serveix per posar un espai entre els valors de les lletres un cop fets codi
                xifrat.append(" ");
            }

            // Aquí, afegim el resultat de l'operació al codi xifrat
            xifrat.append(resultat);
            // Aquí guardem el numero (1 o 2) al codiOperacions
            codiOperacions.append(operacio);
        }

        // Retornem el codi xifrat
        return taula + "|" + xifrat + "|" + codiOperacions;
    }

    public String desencripta(String missatgeXifrat, String clau) {

        // Amb això, cada vegada que el programa detecta el "|", divideix el String i
        // guarda les 3 parts en un array
        String[] parts = missatgeXifrat.split("\\|", -1);

        if (parts.length != 3) {
            return "Error: el missatge xifrat no té el format correcte.";
        }

        // Fa el mateix, quan detecta una ",", divideix i ho guarda a un array dels
        // valors
        String[] valorsTaula = parts[0].split(",");
        String xifrat = parts[1];
        String codiOperacions = parts[2];

        if (valorsTaula.length != lletres.length()) {
            return "Error: la taula no té tots els valors.";
        }

        // Per guardar els valors fem un array igual de gran que el String lletres
        int[] valors = new int[lletres.length()];

        for (int i = 0; i < valors.length; i++) {
            // Convertim el valor de la taula de text a int
            valors[i] = Integer.parseInt(valorsTaula[i]);
        }

        // Ho desxifrem per comprovar el resultat.

        StringBuilder missatgeDesxifrat = new StringBuilder();

        // Si el missatge a desxifrar està buit, no retorna res (utilitzem per evitar
        // errors del programa quan intenti desxifrar un missatge que no te res)
        if (xifrat.isEmpty()) {
            return "";
        }

        String[] nombresXifrats = xifrat.split(" ");
        // Amb el split ens permet dividir la cadena cada cop que troba un espai

        if (nombresXifrats.length != codiOperacions.length()) {
            return "Error: el nombre d'operacions no coincideix amb el missatge.";
        }

        for (int i = 0; i < nombresXifrats.length; i++) {
            //Passem a int els valors xifrats
            int valorXifrat = Integer.parseInt(nombresXifrats[i]);
            //Guardem la posicio del dígit de la clau i ens assegurem que quan arribi a l'ultim digit torni a començar
            int posicioClau = i % clau.length();
            //Passem el dígit de text a enter
            int digitClau = Character.getNumericValue(clau.charAt(posicioClau));
            int valorOriginal;

            if (codiOperacions.charAt(i) == '1') {
                // Si s'havia sumat, ara restem.
                valorOriginal = valorXifrat - digitClau;
            } else {
                // Si s'havia restat, ara sumem.
                valorOriginal = valorXifrat + digitClau;
            }

            for (int j = 0; j < valors.length; j++) {
                if (valors[j] == valorOriginal) {
                    missatgeDesxifrat.append(lletres.charAt(j));
                }
            }
        }

        return missatgeDesxifrat.toString();
    }
}
