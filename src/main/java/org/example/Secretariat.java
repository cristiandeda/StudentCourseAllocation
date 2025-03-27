package org.example;

import java.io.*;
import java.util.*;

public class Secretariat {
    private HashMap<String, Student> studenti;
    private HashMap<String, Curs<StudentLicenta>> cursuriLicenta;
    private HashMap<String, Curs<StudentMaster>> cursuriMaster;

    public Secretariat() {
        this.studenti = new HashMap<>();
        this.cursuriLicenta = new HashMap<>();
        this.cursuriMaster = new HashMap<>();
    }

    public void adaugaStudent(String programDeStudii, String numeStudent,
                              String caleFisierOutput) throws ExceptieStudentExistent {
        if (studenti.containsKey(numeStudent)) {
            try {
                FileWriter fw = new FileWriter(caleFisierOutput, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw);

                out.println("***");

                out.println("Student duplicat: " + numeStudent);

                out.close();
            } catch (IOException ex) {
                System.out.println("[IOException]: " + ex.getMessage());
            }
            throw new ExceptieStudentExistent("Student duplicat: " + numeStudent);
        }

        Student student = null;
        if (programDeStudii.equals("licenta")) {
            student = new StudentLicenta(numeStudent);
        } else if (programDeStudii.equals("master")) {
            student = new StudentMaster(numeStudent);
        }

        this.studenti.put(numeStudent, student);
    }

    public void adaugaCurs(String programDeStudii, String denumireCurs,
                           int capacitateMaxima) {

        if (programDeStudii.equals("licenta")) {
            Curs<StudentLicenta> cursLicenta = new Curs<>(denumireCurs, capacitateMaxima);
            cursuriLicenta.put(denumireCurs, cursLicenta);
        } else if (programDeStudii.equals("master")) {
            Curs<StudentMaster> cursMaster = new Curs<>(denumireCurs, capacitateMaxima);
            cursuriMaster.put(denumireCurs, cursMaster);
        }
    }

    public void citesteMediile(String numeTest) {
        String numeDirector = "src/main/resources/" + numeTest;
        File director = new File(numeDirector);
        File[] fisiere = director.listFiles();

        if (fisiere != null) {
            for (File fisier : fisiere) {
                if (fisier.isFile() && fisier.getName().startsWith("note_")) {
                    this.seteazaMediileDinFisier(fisier);
                }
            }
        }
    }

    private void seteazaMediileDinFisier(File fisier) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fisier));
            String linie;
            while ((linie = br.readLine()) != null) {
                String[] argumenteMedii = linie.split(" - ");
                String numeStudent = argumenteMedii[0];
                double medie = Double.parseDouble(argumenteMedii[1]);

                Student student = studenti.get(numeStudent);
                if (student != null) {
                    student.setMedie(medie);
                }
            }
        } catch (IOException ex) {
            System.out.println("[IOException]: " + ex.getMessage());
        }
    }

    public void posteazaMediile(String caleFisierOutput) {
        ArrayList<Student> studentiSortati = new ArrayList<>(studenti.values());

        studentiSortati.sort(Comparator.comparingDouble(Student::getMedie)
                .reversed().thenComparing(Student::getNume));

        try {
            FileWriter fw = new FileWriter(caleFisierOutput, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);

            out.println("***");

            for (Student student : studentiSortati) {
                out.println(student.getNume() + " - " + student.getMedie());
            }

            out.close();
        } catch (IOException ex) {
            System.out.println("[IOException]: " + ex.getMessage());
        }
    }

    public void contestatie(String numeStudent, double medie) {
        Student student = studenti.get(numeStudent);
        student.setMedie(medie);
    }

    public void adaugaPreferinte(String numeStudent, String[] preferinteCursuri) {
        Student student = studenti.get(numeStudent);
        for (String denumireCurs : preferinteCursuri) {
            student.getPreferinteCursuri().add(denumireCurs);
        }
    }

    public void repartizeaza() {
        ArrayList<Student> studentiSortati = new ArrayList<>(studenti.values());

        studentiSortati.sort(Comparator.comparingDouble(Student::getMedie)
                .reversed().thenComparing(Student::getNume));

        for (Student student : studentiSortati) {
            ArrayList<String> preferinteCursuri = student.getPreferinteCursuri();

            if (student instanceof StudentLicenta) {
                for (String denumireCurs : preferinteCursuri) {

                    Curs<StudentLicenta> curs = cursuriLicenta.get(denumireCurs);
                    if (curs != null && curs.getStudentiInrolati().size() < curs.getCapacitateMaxima()) {
                        curs.getStudentiInrolati().add((StudentLicenta) student);
                        student.setDenumireCursAsignat(denumireCurs);
                        break;
                    }
                    if (curs != null && curs.getStudentiInrolati().size() >= curs.getCapacitateMaxima()) {
                        if (student.getMedie() == curs.getStudentiInrolati().
                                get(curs.getStudentiInrolati().size() - 1).getMedie()) {
                            curs.getStudentiInrolati().add((StudentLicenta) student);
                            student.setDenumireCursAsignat(denumireCurs);
                            break;
                        }
                    }
                }
            } else if (student instanceof StudentMaster) {
                for (String denumireCurs : preferinteCursuri) {

                    Curs<StudentMaster> curs = cursuriMaster.get(denumireCurs);
                    if (curs != null && curs.getStudentiInrolati().size() < curs.getCapacitateMaxima()) {
                        curs.getStudentiInrolati().add((StudentMaster) student);
                        student.setDenumireCursAsignat(denumireCurs);
                        break;
                    }
                    if (curs != null && curs.getStudentiInrolati().size() >= curs.getCapacitateMaxima()) {
                        if (student.getMedie() == curs.getStudentiInrolati().
                                get(curs.getStudentiInrolati().size() - 1).getMedie()) {
                            curs.getStudentiInrolati().add((StudentMaster) student);
                            student.setDenumireCursAsignat(denumireCurs);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void posteazaCurs(String denumireCurs, String caleFisierOutput) {
        HashMap<String, Curs<?>> cursuri = new HashMap<>(cursuriLicenta);
        cursuri.putAll(cursuriMaster);

        Curs<?> cursDePostat = cursuri.get(denumireCurs);

        ArrayList<Student> studentiSortati = new ArrayList<>(cursDePostat.getStudentiInrolati());

        studentiSortati.sort(Comparator.comparing(Student::getNume));

        try {
            FileWriter fw = new FileWriter(caleFisierOutput, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);

            out.println("***");

            out.println(denumireCurs + " (" + cursDePostat.getCapacitateMaxima() + ")");

            for (Student student : studentiSortati) {
                out.println(student.getNume() + " - " + student.getMedie());
            }

            out.close();
        } catch (IOException ex) {
            System.out.println("[IOException]: " + ex.getMessage());
        }
    }

    public void posteazaStudent(String numeStudent, String caleFisierOutput) {
        Student student = studenti.get(numeStudent);
        try {
            FileWriter fw = new FileWriter(caleFisierOutput, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);

            out.println("***");

            if (student instanceof StudentLicenta) {
                out.println("Student Licenta: " + student.getNume() +
                        " - " + student.getMedie() + " - " + student.getDenumireCursAsignat());
            } else if (student instanceof StudentMaster) {
                out.println("Student Master: " + student.getNume() +
                        " - " + student.getMedie() + " - " + student.getDenumireCursAsignat());
            }

            out.close();
        } catch (IOException ex) {
            System.out.println("[IOException]: " + ex.getMessage());
        }
    }
}
