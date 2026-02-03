package UD4.Araujo;

import java.util.Scanner;

public class EjercicioAraujo {
    class Alumno {
        String nombre;
        String apellido1;
        String apellido2;

        public String getUsername() {
            nombre = nombre.toLowerCase();
            apellido1 = apellido1.toLowerCase();
            apellido2 = apellido2.toLowerCase();
            nombre = nombre.substring(0, 1);
            nombre = nombre.replace('á', 'a');
            nombre = nombre.replace('é', 'e');
            nombre = nombre.replace('ó', 'o');
            nombre = nombre.replace('í', 'i');
            nombre = nombre.replace('ú', 'u');
            nombre = nombre.replace('ü', 'u');
            nombre = nombre.replace('ñ', 'n');

            apellido1 = apellido1.replace('á', 'a');
            apellido1 = apellido1.replace('é', 'e');
            apellido1 = apellido1.replace('ó', 'o');
            apellido1 = apellido1.replace('í', 'i');
            apellido1 = apellido1.replace('ú', 'u');
            apellido1 = apellido1.replace('ü', 'u');
            apellido1 = apellido1.replace('ñ', 'n');

            apellido2 = apellido2.replace('á', 'a');
            apellido2 = apellido2.replace('é', 'e');
            apellido2 = apellido2.replace('ó', 'o');
            apellido2 = apellido2.replace('í', 'i');
            apellido2 = apellido2.replace('ú', 'u');
            apellido2 = apellido2.replace('ü', 'u');
            apellido2 = apellido2.replace('ñ', 'n');

            for (int i = 0; i < apellido1.length(); i++) {
                char ch = apellido1.charAt(i);
                if (Character.isSpaceChar(ch)) {
                    apellido1 = apellido1.substring(0, 2);
                } else if (apellido1.length() < 4) {
                    apellido1 = apellido1.substring(0, apellido1.length());
                } else {
                    apellido1 = apellido1.substring(0, 4);
                }
            }
            for (int i = 0; i < apellido2.length(); i++) {
                char ch = apellido2.charAt(i);
                if (Character.isSpaceChar(ch)) {
                    apellido2 = apellido2.substring(0, 2);
                } else if (apellido2.length() < 4) {
                    apellido2 = apellido2.substring(0, apellido2.length());
                } else {
                    apellido2 = apellido2.substring(0, 4);
                }
            }

            String username = nombre + apellido1 + apellido2;

            return username.toLowerCase().strip();
        }

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Alumno[] alumnos = new Alumno[2];
        for (int i = 0; i < alumnos.length; i++) {
            alumnos[i] = new EjercicioAraujo().new Alumno();
            alumnos[i].nombre = sc.nextLine();
            alumnos[i].apellido1 = sc.nextLine();
            alumnos[i].apellido2 = sc.nextLine();
        }
        for (Alumno alumno2 : alumnos) {
            System.out.println(alumno2.getUsername());
        }

        sc.close();

    }
}
