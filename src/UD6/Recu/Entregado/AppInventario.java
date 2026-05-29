package UD6.Recu.Entregado;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

/**
 * @author Ignacio Martínez Rodríguez
 */
public class AppInventario {
    public static String rutaInventario = "src\\UD6\\Recu\\Entregado\\inventario.dat";
    public static Scanner sc = new Scanner(System.in);

    public static List<Producto> leerBin(String rutaArchivo) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(rutaArchivo));) {

            List<Producto> listP = (List<Producto>) in.readObject(); 
            return listP;

        } catch (FileNotFoundException e) {
            System.out.println("No se encuentra el fichero");
        } catch (IOException e) {
            System.out.println("Error Entrada/Salida");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Problema con la lista de Productos");
        } catch (Exception e) {
            System.err.println("Error desconocido: " + e.getStackTrace());
        }
        return null;
    }

    public static boolean escribirBin(String rutaArchivo, List<Producto> listP) {
        if (listP == null || listP.isEmpty()) {
            System.err.println("No se puede agregar una lista vacía");
            return false;
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(rutaArchivo));) {

            out.writeObject(listP);
            return true;

        } catch (FileNotFoundException e) {
            System.out.println("No se encuentra el fichero");
        } catch (IOException e) {
            System.out.println("Error Entrada/Salida");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error desconocido: " + e.getStackTrace());
        }
        return false;
    }

    public static boolean addProducto(Producto p, String rutaInventario){
        if (p == null) {
            System.err.println("No se pudo agregar producto null");
            return false;
        }
        if (rutaInventario == null) {
            System.err.println("La ruta no debe ser null");
            return false;
        }
        File inv = new File(rutaInventario);
        List<Producto> listP;
        if (inv.exists()) {
            listP = new ArrayList<>(leerBin(rutaInventario));
            if(listP.contains(p)){
                System.err.println("Ya existe un producto con el mismo código");
                return false;
            }
            listP.add(p);
            listP.sort(null);
        } else {
            listP = new ArrayList<>();
            listP.add(p);
        }

        return escribirBin(rutaInventario, listP);
    }

    public static void mostrarProductos(String rutaArchivo) {
        try {
            List<Producto> listP = new ArrayList<>(leerBin(rutaInventario));
            listP.sort(null);
            System.out.println("Productos Guardados");
            System.out.println("=====================");
            for (Producto producto : listP) {
                System.out.println(producto);
            }
        } catch (Exception e) {
            System.out.println("No hay Productos guardados");
        }
    }

    public static Producto buscarProducto(int codigo, String rutaArchivo) {
        if (rutaInventario == null) {
            System.err.println("La ruta no debe ser null");
            return null;
        }
        File inv = new File(rutaInventario);
        List<Producto> listP;
        if (inv.exists()) {
            listP = new ArrayList<>(leerBin(rutaInventario));
            listP.sort(null);
        } else {
            System.out.println("No hay productos guardados");
            return null;
        }
        Predicate<Producto> isCod = p -> p.codigo != codigo;
        listP.removeIf(isCod);
        
        if (listP.size() > 1) {
            System.err.println("Error con el listado de Productos");
        } else if (!listP.isEmpty()) {
            return listP.getFirst();
        }
        return null;
    }

    public static String pedirString(String pregunta) {
        String s;
        try {
            System.out.println(pregunta);
            s = sc.nextLine();
        } catch (Exception e) {
            System.err.println("Error obteniendo el texto");
            return null;
        }
        return s;
    }
    public static Integer pedirInt(String pregunta) {
        Integer i;
        try {
            System.out.println(pregunta);
            i = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Valor inválido, indique un número");
            return null;
        }
        return i;
    }
    public static Double pedirDouble(String pregunta) {
        Double d;
        try {
            System.out.println(pregunta);
            d = sc.nextDouble();
        } catch (Exception e) {
            System.out.println("Valor inválido, indique un número");
            return null;
        }
        return d;
    }
    public static void mostrarMenu(){
        System.out.println("Menú Inventario");
        System.out.println("=================");
        System.out.println("Opciones:\n0 - Salir\n1 - Añadir nuevo producto\n2 - Listar Todos los Productos\n3 - Buscar un Producto por Código");
    }
    public static void main(String[] args) {
        String ruta = AppInventario.rutaInventario;
        boolean fin = false;
        while (!fin) {
            mostrarMenu();
            Integer resp = null;
            while (resp == null || resp < 1 || resp > 3) {
                resp = pedirInt("Seleccione una opción:");
                if (resp < 1 || resp > 3) {
                    System.out.println("Escoge una de las opciones");
                }
            }
            switch (resp) {
                case 0:
                    System.out.println("Gracias por usar el programa");
                    fin = true;
                    break;
                case 1:
                    Integer cod = null;
                    Integer cant = null;
                    Double precio = null;
                    String nombre = null;
                    Producto p = null;
                    boolean error = true;

                    while (error) {
                        while (nombre == null) {
                            nombre = pedirString("Nombre del producto?");
                        }
                        while (cant == null) {
                            cant = pedirInt("Codigo del producto?");
                        }
                        while (precio == null) {
                            precio = pedirDouble("Precio del producto?");
                        }
                        while (cod == null) {
                            cod = pedirInt("Código del producto?");
                        }

                        try {
                            p = new Producto(cod, nombre, cant, precio);
                            if (addProducto(p, ruta)) {
                                System.out.println("Producto añadido correctamente");
                            }
                            error = false;
                        } catch (Exception e) {
                            System.err.println("Valores invalidos para el producto");
                        }
                    }

                    break;
                case 2:
                    mostrarProductos(ruta);
                    break;
                case 3:
                    cod = null;
                    while (cod == null) {
                        cod = pedirInt("Código del producto a buscar?");
                    }
                    p = buscarProducto(cod, ruta);
                    if (p != null) {
                        System.out.println("Producto encontrado:\n" + p);
                    }
                    break;
            }
        }
    }

}
