import java.lang.reflect.Field;
import java.util.*;

 class A {

     int a;

     public A(int a) {
         this.a = a;
     }

     @Override
     public int hashCode() {
//         System.out.println((int) a.charAt(0));
         return a;
     }
 }
public class Main {



    public static void main (String[] args) throws NoSuchFieldException, IllegalAccessException{
        MySuperHashMap<String, String> map = new MySuperHashMap<>();

        map.put("abcd", "a");
        System.out.println(map.toString());
        map.put("kuba", "a");
        System.out.println(map.toString());
        map.put("kasia", "a");
        System.out.println(map.toString());
        map.put("franek", "a");
        System.out.println(map.toString());
        map.put("aaaa", "a");
        System.out.println(map.toString());


        System.out.println(map.getBucketSize());

        System.out.println(map.toString());

    }
}
