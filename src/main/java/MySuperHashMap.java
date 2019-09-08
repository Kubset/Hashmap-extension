import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import static java.util.Objects.nonNull;

/**
 * @param <K>
 * @param <V>
 */
public class MySuperHashMap<K,V> extends HashMap<K,V> {
    private int initialCapacity;

    MySuperHashMap(int capacity, float loadFactor) {
        super(capacity,loadFactor);
        this.initialCapacity = getInitialCapacity(capacity);
        System.out.println("----INITIALIZING HASHMAP capacity: " + this.initialCapacity + "  loadFactor: " + loadFactor);
    }

    MySuperHashMap(int capacity) {
        super(capacity);
        this.initialCapacity = getInitialCapacity(capacity);
        System.out.println("----INITIALIZING HASHMAP capacity: " + this.initialCapacity + "  loadFactor: 0.75");
    }

    MySuperHashMap() {
        super();
        this.initialCapacity = 16;
        System.out.println("----INITIALIZING HASHMAP capacity: 16 loadFactor: 0.75");
    }

    static final int hash(Object var0) {
        int var1;
        return var0 == null ? 0 : (var1 = var0.hashCode()) ^ var1 >>> 16;
    }

    private int getInitialCapacity(int capacity) {
        String binaryCapacity = Integer.toBinaryString(capacity);



        int cap = 2;
        while (cap < capacity) {
            cap = cap << 1;
        }

        if(binaryCapacity.substring(1).contains("1")) {
            System.out.println("----INITIALIZING HASHMAP capcity is diffrent than 2^n, setting from: " + capacity + " to " + cap);
        }

        return cap;
    }


    @Override
    public V put(K k, V v) {
        int bucketSize;
        if(size() > getBucketSize()*getLoadFactor()) {
            bucketSize = getBucketSize() * 2;
            System.out.println("----INCREASING BUCKET SIZE, REHASHING bucket size: " + bucketSize);
        } else {
            bucketSize = getBucketSize();
        }
        int chosenBucket = hash(k) % (bucketSize);

        System.out.println("----PUT OPERATION --> bucket size: " + bucketSize +
                " object hashcode: " + k.hashCode() +
                " choosen bucket: " + chosenBucket + "--> PUT");

        return super.put(k, v);

    }


    /**
     * @return int
     */
    public int getBucketSize() {
        int result;

        try {
            Field f = this.getClass().getSuperclass().getDeclaredField("table"); //NoSuchFieldException
            f.setAccessible(true);
            result = ((Object[]) f.get(this)).length; //IllegalAccessException
        } catch (IllegalAccessException | NoSuchFieldException | NullPointerException f) {
            return this.initialCapacity;
        }
        return result;
    }

    public float getLoadFactor() {
        float loadFactor;

        try {
            Field f = this.getClass().getSuperclass().getDeclaredField("loadFactor"); //NoSuchFieldException
            f.setAccessible(true);
            loadFactor = (Float) f.get(this); //IllegalAccessException
        } catch (IllegalAccessException | NoSuchFieldException f) {
            return -1;
        }
        return loadFactor;

    }


    //easier implementation of Hashmap
    public LinkedList<String>[] getBuckets() {
        LinkedList<String>[] finalBuckets = new LinkedList[0];

        try {


            //get class name by reflection
            Class<?> hashMapNode = super.getClass().getSuperclass().getDeclaredClasses()[12];

            //get field next in Hashmap.Node nested classs
            Field nextField = hashMapNode.getDeclaredField("next");

            // get field with table of declared nodes in hashmap
            Field f = super.getClass().getSuperclass().getDeclaredField("table"); //NoSuchFieldException

            f.setAccessible(true);
            nextField.setAccessible(true);

            Object[] buckets = (Object[]) f.get(this); //IllegalAccessException
            finalBuckets = new LinkedList[buckets.length];


            for(int i=0; i<buckets.length; i++) {
                LinkedList<String> list = new LinkedList<>();
                if(nonNull(buckets[i])) {
                    list.add(buckets[i].toString());
                    Object next = nextField.get(buckets[i]);
                    if(nonNull(next)) {
                        list.add(next.toString());
                        while (nonNull(next = nextField.get(next))) {
                            list.add(next.toString());
                        }
                    }
                }
                finalBuckets[i] = list;
            }

        } catch (IllegalAccessException | NoSuchFieldException f) {
            System.out.println(f);
        }

        return finalBuckets;
    }


    @Override
    public String toString() {
        LinkedList<String>[] buckets = getBuckets();
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<buckets.length; i++) {
           sb.append("| ");
           if(buckets[i].size() != 0) {
               buckets[i].forEach(node -> sb.append(node).append(" --> "));
               sb.setLength(sb.length() - 4);
           } else {
               sb.append("null");
           }
           sb.append(" ").append("\n");
        }

        return sb.toString();

    }
}