package UD5.praiasdegalicia;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class AppBanderaAzul{
    public static void main(String[] args) {
        // Carga las playas desde el fichero JSON en un array de Praias
        Praia[] praias = Util.importarPraias("praias.json");
        Set<BanderaAzul> praiasCertificadas = new TreeSet<>();

        Arrays.sort(praias);
        Scanner sc = new Scanner(System.in);
        boolean salir = false;
        System.out.println("PRAIAS DE GALICIA\n" + //
                            "=================\n");
        while (!salir) {
            int respuesta = -1;
            boolean error = true;
            while (error) {
                System.out.println("=== MENÚ PRINCIPAL ===\n" + //
                                    "1. Listar Praias\n" + //
                                    "2. Asignar Bandera Azul\n" + //
                                    "3. Listar Banderas Azules\n" + //
                                    "0. SALIR\n");// Abría que crear una opción de guardado
                System.out.print("\nSeleccione una opción: ");
                try {
                    respuesta = sc.nextInt();
                    error = false;
                } catch (Exception e) {
                    System.err.println("Escriba unicamente el número correspondiente a la opción");
                    sc.nextLine();
                }
            }
            System.out.println();
            switch (respuesta) {
                case 1:
                    System.out.println("PRAIAS GUARDADAS");
                    System.out.println("================");
                    for (Praia praia : praias) {
                        System.out.println(praia.getId() + " - " + praia.toString());
                    }
                    break;
                case 2:
                    error = true;
                    while (error) {
                        System.out.print("Id de la playa a otorgar la certificación: ");
                        try {
                            respuesta = sc.nextInt();
                            error = false;
                        } catch (Exception e) {
                            System.err.println("Escriba unicamente el número de la id de la playa");
                            sc.nextLine();
                        }
                    }

                    if (praias[0].getId() > respuesta || praias[praias.length - 1].getId() < respuesta) {
                        System.out.println("Escoge unicamente una id que corresponda a una playa guardada");
                        break;
                    }

                    Praia p = null;
                    for (Praia praia : praias) {
                        if (praia.getId() == respuesta) {
                            p = praia;
                            break;
                        }
                    }
                    if (p == null) {
                        System.out.println("Escoge unicamente una id que corresponda a una playa guardada");
                        break;
                    }

                    BanderaAzul banderaAzul = new BanderaAzul(p);

                    if (!praiasCertificadas.contains(banderaAzul)) {
                        error = true;
                        while (error) {
                            System.out.println("=== Opciones del certificado ===\n" + //
                                    "1. Es accesible " + (banderaAzul.isAccesible() ? "(Sí)" : "(No)") + "\n" + //
                                    "2. Disponibilidad de duchas " + (banderaAzul.isDuchas() ? "(Sí)" : "(No)") + "\n" + //
                                    "3. Disponibilidad de aseos " + (banderaAzul.isAseos() ? "(Sí)" : "(No)") + "\n" + //
                                    "4. Con socorristas " + (banderaAzul.isSocorrista() ? "(Sí)" : "(No)") + "\n" + //
                                    "0. Guardar y salir\n");

                            try {
                                respuesta = sc.nextInt();
                            } catch (Exception e) {
                                System.err.println("Escriba unicamente el número correspondiente a la opción");
                                sc.nextLine();
                                continue;
                            }
                            switch (respuesta) {
                                case 1:
                                    banderaAzul.setAccesible(!banderaAzul.isAccesible());
                                    break;
                                case 2:
                                    banderaAzul.setDuchas(!banderaAzul.isDuchas());
                                    break;
                                case 3:
                                    banderaAzul.setAseos(!banderaAzul.isAseos());
                                    break;
                                case 4:
                                    banderaAzul.setSocorrista(!banderaAzul.isSocorrista());
                                    break;
                                case 0:
                                    error = false;
                                    break;
                                default:
                                    System.err.println("Escriba unicamente el número correspondiente a la opción");
                                    break;
                            }
                        }
                        praiasCertificadas.add(banderaAzul);
                    } else {
                        System.out.println("La playa con id \"" + p.getId() + "\" ya tiene un certificado de bandera azul");
                    }
                    break;
                case 3:
                    System.out.println("PRAIAS CERTIFICADAS");
                    System.out.println("===================");
                    for (BanderaAzul bA : praiasCertificadas) {
                        System.out.println(bA.getId() + " - " + bA);
                    }
                    break;
                case 0:
                    System.out.println("Gracias por usar el programa!");
                    salir = true;
                    break;
                
                default:
                    System.out.println("Escoge unicamente una de las opciones");
                    break;
            }
        }
        sc.close();
    }
}
