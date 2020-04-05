import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashMapExtensionTest {


    @Test
    public void checkDefaultBucketSize() {
        HashMapExtension<String, String> map = new HashMapExtension<>();
        assertEquals(16, map.getBucketSize());
    }

    @Test
    public void checkCustomBucketSize() {
        HashMapExtension<String, String> map = new HashMapExtension<>(2);
        assertEquals(2, map.getBucketSize());
    }

    @Test
    public void checkCustomBucketSizeNot2n() {
        HashMapExtension<String, String> map = new HashMapExtension<>(5);
        assertEquals(8, map.getBucketSize());
    }

    @Test
    public void checkDefaultLoadFactor() {
        HashMapExtension<String, String> map = new HashMapExtension<>();
        assertEquals(0.75F, map.getLoadFactor());
    }

    @Test
    public void checkCustomLoadFactor() {
        HashMapExtension<String, String> map = new HashMapExtension<>(8, 0.5F);
        assertEquals(0.5F, map.getLoadFactor());
    }

}