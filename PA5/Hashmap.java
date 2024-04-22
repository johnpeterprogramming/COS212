import java.lang.Math;

public class Hashmap {
    public KeyValuePair[] array;
    public PrimeNumberGenerator prime = new PrimeNumberGenerator();

    @Override
    public String toString() {
        String res = String.valueOf(prime.currentPrime()) + "\n";
        for (int i = 0; i < array.length; i++) {
            if (i != 0) {
                res += "\n";
            }
            res += i + "\t";
            if (array[i] == null) {
                res += "-";
            } else {
                res += array[i].toString();
            }
        }
        return res;
    }

    public String toStringOneLine() {
        String res = String.valueOf(prime.currentPrime()) + "[";
        for (int i = 0; i < array.length; i++) {
            if (i != 0) {
                res += ",";
            }
            if (array[i] == null) {
                res += "-";
            } else {
                res += array[i].toString();
            }
        }
        return res + "]";
    }

    public Hashmap() {
        array = new KeyValuePair[1];
    }

    public Hashmap(String inp) {
        int currPrimeValue = Integer.parseInt(inp.substring(0, inp.indexOf('[')));
        this.prime = new PrimeNumberGenerator();
        this.prime.head = new PrimeNode(currPrimeValue);
        if (!isPrime(currPrimeValue)) {
            // System.out.println("THIS IS NOT PRIME");
            currPrimeValue = prime.nextPrime();
            // System.out.println("NEXT PRIME IS:" + currPrimeValue);
        }

        
        String[] items = inp.substring(inp.indexOf('[') + 1, inp.indexOf(']')).split(",");
        array = new KeyValuePair[items.length];

        for (int i=0; i<items.length; i++) {
            if (items[i].equals("-")) {
                array[i] = null;
                continue;
            }

            int studentNumber = Integer.parseInt(items[i].substring(items[i].indexOf('u') + 1, items[i].indexOf(':')));
            int percentage = Integer.parseInt(items[i].substring(items[i].indexOf(':') + 1, items[i].indexOf('%')));

            array[i] = new KeyValuePair(studentNumber, percentage);

        }
    }

    private boolean isPrime(int num) {
        for (int i=2; i< num; i++) {
            if (num % i == 0)
                return false;
        } 
        return true;
    }

    public int hash(int studentNumber) {
        int hashValue = 0;
        String studentNumberString = String.valueOf(studentNumber);

        for (int i=0; i< studentNumberString.length(); i++) {
            hashValue = (hashValue * this.prime.currentPrime()) + Character.getNumericValue(studentNumberString.charAt(i));
        }

        return Math.abs(hashValue % this.array.length);
    }

    public KeyValuePair search(int studentNumber) {
        int index = hash(studentNumber);
        if (array[index] != null)
            if (array[index].studentNumber == studentNumber)
                return array[index];

        // At this stage we will use quadratic probing
        if (array[Math.abs(index + 1 * prime.currentPrime()) % array.length] != null) 
            if (array[Math.abs(index + 1 * prime.currentPrime()) % array.length].studentNumber == studentNumber)
                return array[Math.abs(index + 1 * prime.currentPrime()) % array.length];  
        if (array[Math.abs(index - 1 * prime.currentPrime()) % array.length] != null) 
            if (array[Math.abs(index - 1 * prime.currentPrime()) % array.length].studentNumber == studentNumber)
                return array[Math.abs(index - 1 * prime.currentPrime()) % array.length];  
        if (array[Math.abs(index + 4 * prime.currentPrime()) % array.length] != null) 
            if (array[Math.abs(index + 4 * prime.currentPrime()) % array.length].studentNumber == studentNumber)
                return array[Math.abs(index + 4 * prime.currentPrime()) % array.length];  
        if (array[Math.abs(index - 4 * prime.currentPrime()) % array.length] != null) 
            if (array[Math.abs(index - 4 * prime.currentPrime()) % array.length].studentNumber == studentNumber)
                return array[Math.abs(index - 4 * prime.currentPrime()) % array.length];  
        if (array[Math.abs(index + 9 * prime.currentPrime()) % array.length] != null) 
            if (array[Math.abs(index + 9 * prime.currentPrime()) % array.length].studentNumber == studentNumber)
                return array[Math.abs(index + 9 * prime.currentPrime()) % array.length];  
        if (array[Math.abs(index - 9 * prime.currentPrime()) % array.length] != null) 
            if (array[Math.abs(index - 9 * prime.currentPrime()) % array.length].studentNumber == studentNumber)
                return array[Math.abs(index - 9 * prime.currentPrime()) % array.length];  

        return null;
    }

    public void insert(int studentNumber, int mark) {
        KeyValuePair searchCheck = search(studentNumber); 
        if (searchCheck != null) {
            searchCheck.mark = mark;
            return;
        }

        // At this stage the value doesn't exist yet, so new value will be inserted
        int index = hash(studentNumber);
        if (array[index] == null) {
            array[index] = new KeyValuePair(studentNumber, mark);
        } else {
            // quadratic probing checks
            if (array[Math.abs(index + 1 * prime.currentPrime()) % array.length] == null) 
                array[Math.abs(index + 1 * prime.currentPrime()) % array.length] = new KeyValuePair(studentNumber, mark);
            else if (array[Math.abs(index - 1 * prime.currentPrime()) % array.length] == null)
                array[Math.abs(index - 1 * prime.currentPrime()) % array.length] = new KeyValuePair(studentNumber, mark);
            else if (array[Math.abs(index + 4 * prime.currentPrime()) % array.length] == null)
                array[Math.abs(index + 4 * prime.currentPrime()) % array.length] = new KeyValuePair(studentNumber, mark);
            else if (array[Math.abs(index + 9 * prime.currentPrime()) % array.length] == null)
                array[Math.abs(index + 9 * prime.currentPrime()) % array.length] = new KeyValuePair(studentNumber, mark);
            else if (array[Math.abs(index - 9 * prime.currentPrime()) % array.length] == null)
                array[Math.abs(index - 9 * prime.currentPrime()) % array.length] = new KeyValuePair(studentNumber, mark);
            else {
                // Table needs to be resized
                KeyValuePair[] tempArray = new KeyValuePair[array.length];
                // Deep copy
                for (int i=0; i<tempArray.length; i++)
                    if (array[i] != null)
                        tempArray[i] = new KeyValuePair(array[i].studentNumber, array[i].mark);
                    else
                        tempArray[i] = null;

                array = new KeyValuePair[array.length * 2];
                prime.nextPrime();

                for (int i=0; i<tempArray.length; i++) {
                    if (tempArray[i] != null) {
                        insert(tempArray[i].studentNumber, tempArray[i].mark);
                    }
                }

                insert(studentNumber, mark);

            }
        }


    }

    public void remove(int studentNumber) {
        KeyValuePair searchKeyValue = search(studentNumber);
        if (searchKeyValue != null) {
            // At this stage we will use quadratic probing
            int searchIndex = hash(studentNumber);
            if (array[searchIndex] != null && array[searchIndex].studentNumber == studentNumber)
                array[searchIndex] = null;

            if (array[Math.abs(searchIndex + 1 * prime.currentPrime()) % array.length] != null) 
                if (array[Math.abs(searchIndex + 1 * prime.currentPrime()) % array.length].studentNumber == studentNumber)
                    array[Math.abs(searchIndex + 1 * prime.currentPrime()) % array.length] = null;  
            if (array[Math.abs(searchIndex - 1 * prime.currentPrime()) % array.length] != null) 
                if (array[Math.abs(searchIndex - 1 * prime.currentPrime()) % array.length].studentNumber == studentNumber)
                    array[Math.abs(searchIndex - 1 * prime.currentPrime()) % array.length] = null;  
            if (array[Math.abs(searchIndex + 4 * prime.currentPrime()) % array.length] != null) 
                if (array[Math.abs(searchIndex + 4 * prime.currentPrime()) % array.length].studentNumber == studentNumber)
                    array[Math.abs(searchIndex + 4 * prime.currentPrime()) % array.length] = null;  
            if (array[Math.abs(searchIndex - 4 * prime.currentPrime()) % array.length] != null) 
                if (array[Math.abs(searchIndex - 4 * prime.currentPrime()) % array.length].studentNumber == studentNumber)
                    array[Math.abs(searchIndex - 4 * prime.currentPrime()) % array.length] = null;  
            if (array[Math.abs(searchIndex + 9 * prime.currentPrime()) % array.length] != null) 
                if (array[Math.abs(searchIndex + 9 * prime.currentPrime()) % array.length].studentNumber == studentNumber)
                    array[Math.abs(searchIndex + 9 * prime.currentPrime()) % array.length] = null;  
            if (array[Math.abs(searchIndex - 9 * prime.currentPrime()) % array.length] != null) 
                if (array[Math.abs(searchIndex - 9 * prime.currentPrime()) % array.length].studentNumber == studentNumber)
                    array[Math.abs(searchIndex - 9 * prime.currentPrime()) % array.length] = null;  
        }
    }
}
