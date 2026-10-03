import java.util.Scanner;

public class ProgramaPrincipal {
    Scanner sc = new Scanner(System.in);
    String lletres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";

    public static void main(String[] args) {
        ProgramaPrincipal p = new ProgramaPrincipal();
        p.principal();
    }

    public String llegirString() {
        String frase = "";

        try {
            frase = sc.nextLine();
        } catch (Exception e) {
            System.out.println("Error, torna-ho a intentar!");
        }

        return frase;
    }

    public void principal() {
        boolean sortirPrograma = false;
        ClasseCriptografica criptografia = new ClasseCriptografica();

        while (!sortirPrograma) {
            System.out.println("Quin sistema vols utilitzar?");
            System.out.println("1. El meu sistema de xifrat");
            System.out.println("2. AES");
            String opcio = llegirString();

            if (opcio.equals("1")) {
                System.out.println("Introdueix el missatge que vols xifrar:");
                String missatge = llegirString().toUpperCase();

                // Comprovem que tots els caràcters siguin lletres o espais.
                boolean missatgeValid = true;

                for (int i = 0; i < missatge.length(); i++) {
                    char caracter = missatge.charAt(i);
                    int posicio = lletres.indexOf(caracter);

                    if (posicio == -1) {
                        System.out.println("Caràcter no admès: " + caracter);
                        missatgeValid = false;
                    }
                }

                if (!missatgeValid) {
                    continue; // Aquest continue fa que salti tota la volta del while, per tant amb això podem
                              // comprovar que si no és un missatge vàlid, no executarà res mes.
                }

                System.out.println("Introdueix una clau:");
                String clau = llegirString();

                while (!clau.matches("[0-9]+")) {
                    System.out.println("La clau ha de contenir xifres. Torna-ho a provar:");
                    clau = llegirString();
                }

                //Ho xifrem utilitzant el mètode
                String missatgeXifrat = criptografia.encripta(missatge, clau);

                System.out.println("\n--- VALORS PER ENVIAR ---");
                System.out.println("El paquet conté la taula | el missatge xifrat | el codi d'operacions");
                System.out.println(missatgeXifrat);

                // Ho desxifrem utilitzant el mètode
                String missatgeDesxifrat = criptografia.desencripta(missatgeXifrat, clau);

                System.out.println("\nClau utilitzada: " + clau);
                System.out.println("Missatge desxifrat: " + missatgeDesxifrat);

            } else if (opcio.equals("2")) {
                //Fem servir les dades que ens dona l'enunciat per provar AES.
                String missatge = "Aquest és un missatge secret.";
                String clau = "1234567890123456";

                System.out.println("\nMissatge original: " + missatge);

                //Xifrem el missatge utilitzant la classe ClasseAES.
                String missatgeXifrat = ClasseAES.encripta(missatge, clau);
                System.out.println("Missatge xifrat: " + missatgeXifrat);

                //Desxifrem el missatge amb la mateixa clau per comprovar que es recupera correctament.
                String missatgeDesxifrat = ClasseAES.desencripta(missatgeXifrat, clau);
                System.out.println("Missatge recuperat: " + missatgeDesxifrat);

            } else {
                System.out.println("Opció no vàlida.");
                continue;
            }

            System.out.println("\nVols tornar al menú? Escriu S per continuar:");
            String resposta = llegirString();

            if (!resposta.equalsIgnoreCase("S")) {
                sortirPrograma = true;
            }
        }
    }
}