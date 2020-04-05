public class Main {



    public static void main (String[] args) throws NoSuchFieldException, IllegalAccessException{
        HashMapExtension<String, String> map = new HashMapExtension<>(5);

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
