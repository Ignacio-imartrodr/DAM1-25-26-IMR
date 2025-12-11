package Extras;


/**
 * @Author Ignacio MR
 */
public class Prueba {
    public static void main(String[] args) throws Exception {

        long num;
        int i = 0;
        
        num = (Math.round(Math.random() * 100))+1;
        while ( i < 10000 || (num > 0 && num <= 100)){
            i++;
            num = (Math.round(Math.random() * 100))+1;
            System.out.println(num);

        }
    }
}