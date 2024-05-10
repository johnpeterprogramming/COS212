
public class Hashmap<K, V> {
    public HashNode<K, V>[] data;
    public int numValues;
    public int capacity;

    public Hashmap() {
        capacity = 2;
        data = new HashNode[capacity];

        numValues = 0;
    }

    public boolean insert(K key, V value) {
        int hash = hornerHash(key);
        int step_size = secondaryHash(key);

        for (int j = hash; data[j] != null; j = (j + step_size) % data.length) {
            if (data[j].key.equals(key))
                // KEY ALREDY EXISTS
                return false;
        }

        int i = hash;
        while (data[i] != null) {
            i = (i + step_size) % data.length;
        }

        data[i] = new HashNode<>(key, value);

        if (++numValues >= capacity) {
            HashNode<K, V>[] tempData = data;
            capacity *= 2;
            numValues = 0;

            data = new HashNode[capacity];
            for (int k = 0; k< tempData.length; k++) {
                insert(tempData[k].key, tempData[k].value);
            }
        }

        return true;


    }

    public void delete(K key) {
        int hash = hornerHash(key);
        int step_size = secondaryHash(key);

        for (int j = hash; data[j] != null; j = (j + step_size) % data.length) {
            if (data[j].key.equals(key)) {
                data[j] = null;
                numValues--;
                return;
            }
        }
    }

    public V get(K key) {
        int hash = hornerHash(key);
        int step_size = secondaryHash(key);

        for (int j = hash; data[j] != null; j = (j + step_size) % data.length) {
            if (data[j].key.equals(key)) {
                return data[j].value;
            }
        }
        return null;
    }


    public Object[] getKeys() {
        Object[] keys = new Object[numValues];

        for (int i=0,j=0; i<capacity; i++) {
            if (data[i] != null)
                keys[j++] = data[i].key;
        }

        return keys;
    }

    public void clear() {
        capacity = 2;
        data = new HashNode[capacity];

        numValues = 0;
    }

    /* -------------------------------------------------------------------------- */
    /* Please don't change this toString, I tried to make it pretty. */
    /* -------------------------------------------------------------------------- */
    /* -------------------------------------------------------------------------- */
    /* Also we may test against it */
    /* -------------------------------------------------------------------------- */

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String header = String.format("%-10s | %-20s | %-20s%n", "Index", "Key", "Value");
        sb.append(header);
        for (int i = 0; i < header.length() - 1; i++) {
            sb.append("-");
        }
        sb.append("\n");
        for (int i = 0; i < capacity; i++) {
            if (data[i] != null) {
                String row = String.format("%-10d | %-20s | %-20s%n", i, data[i].key.toString(),
                        data[i].value.toString());
                sb.append(row);
            } else {
                String row = String.format("%-10d | %-20s | %-20s%n", i, "null", "null");
                sb.append(row);
            }
        }

        return sb.toString();
    }

    public int hornerHash(K key) {
        String keyStr = key.toString();
        int hashVal = 0;
        for (int i = 0; i < keyStr.length(); i++)
            hashVal = 37 * hashVal + keyStr.charAt(i);
        hashVal %= capacity;
        if (hashVal < 0)
            hashVal += capacity;
        return hashVal;
    }

    public int secondaryHash(K key) {
        int hash = key.hashCode();
        // Ensure the step size is odd to ensure it's coprime with capacity, since
        // capacity is a power of 2.
        int step = (hash & (capacity - 1)) | 1; // This ensures the step size is always odd.
        return step;
    }

}
