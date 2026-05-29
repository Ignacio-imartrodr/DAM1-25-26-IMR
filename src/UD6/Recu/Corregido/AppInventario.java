package UD6.Recu.Corregido;

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
    public static String rutaInventario = "src\\UD6\\Recu\\Corregido\\inventario.dat";
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
        
        File archivo = new File(rutaArchivo);
        archivo.getParentFile().mkdirs(); // Obtiene la ruta de las carpetas y, si la ruta tiene carpetas y estas no existen, las crea

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo));) {

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

    public static void addProducto(Producto p, String rutaInventario) throws IllegalArgumentException{
        if (p == null) {
            throw new IllegalArgumentException("No se pudo agregar producto null");
        }
        if (rutaInventario == null) {
            throw new IllegalArgumentException("La ruta no debe ser null");
        }
        File inv = new File(rutaInventario);
        List<Producto> listP;
        if (inv.exists() && inv.length() > 0) {
            listP = leerBin(rutaInventario);
            if (listP != null) {
                listP = new ArrayList<>(listP);
                if(listP.contains(p)){
                    throw new IllegalArgumentException("Ya existe un producto con el mismo código");
                }
            }
        } else {
            listP = new ArrayList<>();
        }
        listP.add(p);
        if (listP.size() > 1) {
            listP.sort(null);
        }
        escribirBin(rutaInventario, listP);
    }

    public static void mostrarProductos(String rutaArchivo) {
        try {
            List<Producto> listP = leerBin(rutaInventario);
            if (listP != null) {
                listP = new ArrayList<>(listP);
                listP.sort(null);
                System.out.println("Productos Guardados");
                System.out.println("=====================");
                for (Producto producto : listP) {
                    System.out.println(producto);
                }
            } else {
                System.out.println("No hay Productos guardados");
            }
        } catch (Exception e) {
            System.out.println("No hay Productos guardados");
        }
    }

    public static List<Producto> buscarProducto(String rutaArchivo, int codMin, Integer codMax) {
        if (rutaInventario == null) {
            System.err.println("La ruta no debe ser null");
            return null;
        }

        File inv = new File(rutaInventario);
        List<Producto> listP;

        if (inv.exists() && inv.length() > 0) {
            listP = leerBin(rutaInventario);
            if (listP != null) {
                listP = new ArrayList<>(listP);
                listP.sort(null);
            } else {
                System.out.println("No hay productos guardados");
                return null;
            }
        } else {
            System.out.println("No hay productos guardados");
            return null;
        }

        Predicate<Producto> isCod;
        if (codMax == null) {
            isCod = p -> p.codigo != codMin;
        } else {
            isCod = p -> p.codigo < codMin || p.codigo > codMax;
        }
        listP.removeIf(isCod);
        
        if (listP.size() > 1 && codMax == null) {
            System.err.println("Error con el listado de Productos, códigos repetidos");
            return listP;
        } else if (!listP.isEmpty()) {
            return listP;
        } else {
            System.out.println("No se encontró el producto");
        }
        return null;
    }

    public static String pedirString(String pregunta) {
        try {
            System.out.println(pregunta);
            return sc.nextLine();
        } catch (Exception e) {
            System.err.println("Error obteniendo el texto");
            sc.nextLine();
            return null;
        }
    }
    public static Integer pedirInt(String pregunta) {
        try {
            System.out.println(pregunta);
            Integer i = sc.nextInt();
            sc.nextLine();
            return i;
        } catch (Exception e) {
            System.out.println("Valor inválido, indique un número");
            sc.nextLine();
            return null;
        }
    }
    public static Double pedirDouble(String pregunta) {
        try {
            System.out.println(pregunta);
            Double d = sc.nextDouble();
            sc.nextLine();
            return d;
        } catch (Exception e) {
            System.out.println("Valor inválido, indique un número");
            sc.nextLine();
            return null;
        }
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
            while (resp == null || resp < 0 || resp > 3) {
                resp = pedirInt("Seleccione una opción:");
                if (resp !=null && (resp < 0 || resp > 3)) {
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
                    
                    while (nombre == null) {
                        nombre = pedirString("Nombre del producto?");
                    }
                    while (cant == null) {
                        cant = pedirInt("Cantidad del producto?");
                    }
                    while (precio == null) {
                        precio = pedirDouble("Precio del producto?");
                    }
                    while (cod == null) {
                        cod = pedirInt("Código del producto?");
                    }

                    try {
                        p = new Producto(cod, nombre, cant, precio);
                        try {
                            addProducto(p, ruta);
                            System.out.println("Producto añadido correctamente");
                        } catch (Exception e) {
                            System.err.println("Error: " + e.getMessage());
                        }
                    } catch (Exception e) {
                        System.err.println("Valores invalidos para el producto");
                        cod = null;
                        cant = null;
                        precio = null;
                        nombre = null;
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
                    Object o = buscarProducto(ruta, cod, null);
                    if (o != null) {
                        p = ((List<Producto>) o).getFirst();
                        System.out.println("Producto encontrado:\n" + p);
                    }
                    break;
            }
            System.out.println();
        }
    }

}
