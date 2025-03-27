package org.example;

import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("[Eroare]: Niciun parametru furnizat!");
            return;
        }

        String numeTest = args[0];
        String caleFisierInput = "src/main/resources/" + numeTest + "/" + numeTest + ".in";
        String caleFisierOutput = "src/main/resources/" + numeTest + "/" + numeTest + ".out";

        try {
            BufferedReader br = new BufferedReader(new FileReader(caleFisierInput));

            Secretariat secretariat = new Secretariat();
            String linie;
            String numeStudent;
            String programDeStudii;
            String denumireCurs;
            while ((linie = br.readLine()) != null) {

                String[] argumenteComanda = linie.split(" - ");
                switch (argumenteComanda[0]) {
                    case "adauga_student":
                        programDeStudii = argumenteComanda[1];
                        numeStudent = argumenteComanda[2];

                        try {
                            secretariat.adaugaStudent(programDeStudii, numeStudent, caleFisierOutput);
                        } catch (ExceptieStudentExistent ex) {
                            System.out.println("[Exceptie Student Existent]: " + ex.getMessage());
                        }

                        break;
                    case "adauga_curs":
                        programDeStudii = argumenteComanda[1];
                        denumireCurs = argumenteComanda[2];
                        int capacitateMaxima = Integer.parseInt(argumenteComanda[3]);

                        secretariat.adaugaCurs(programDeStudii, denumireCurs, capacitateMaxima);

                        break;
                    case "citeste_mediile":
                        secretariat.citesteMediile(numeTest);
                        break;
                    case "posteaza_mediile":
                        secretariat.posteazaMediile(caleFisierOutput);
                        break;
                    case "contestatie":
                        numeStudent = argumenteComanda[1];
                        double medie = Double.parseDouble(argumenteComanda[2]);
                        secretariat.contestatie(numeStudent, medie);
                        break;
                    case "adauga_preferinte":
                        numeStudent = argumenteComanda[1];
                        String[] preferinteCursuri = Arrays.copyOfRange
                                (argumenteComanda, 2, argumenteComanda.length);
                        secretariat.adaugaPreferinte(numeStudent, preferinteCursuri);
                        break;
                    case "repartizeaza":
                        secretariat.repartizeaza();
                        break;
                    case "posteaza_curs":
                        denumireCurs = argumenteComanda[1];
                        secretariat.posteazaCurs(denumireCurs, caleFisierOutput);
                        break;
                    case "posteaza_student":
                        numeStudent = argumenteComanda[1];
                        secretariat.posteazaStudent(numeStudent, caleFisierOutput);
                    default:
                        System.out.println("[Eroare]: Comanda invalida");
                }
            }

            br.close();
        } catch (IOException ex) {
            System.out.println("[Exceptie I/O]: Eroare fisier!");
        }
    }
}
