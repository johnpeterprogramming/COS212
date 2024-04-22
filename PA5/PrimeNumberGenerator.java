
public class PrimeNumberGenerator {
    PrimeNode head;

    @Override
    public String toString() {
        String res = head.toString();
        PrimeNode ptr = head.next;
        while (ptr != null) {
            res += "->" + ptr.toString();
            ptr = ptr.next;
        }
        return res;
    }

    public PrimeNumberGenerator() {
        this.head = new PrimeNode(2);
    }

    public int currentPrime() {
        return head.value;
    }

    public int nextPrime() {
        if (head.next == null)
            sieveOfEratosthenes();

        this.head = this.head.next;
        return this.head.value;
    }

    public void sieveOfEratosthenes() {
        int arrlength = this.head.value * 2 + 1;
        boolean[] notPrime = new boolean[arrlength];
        for (int i = 0; i < arrlength; i++) {
            notPrime[i] = false;
        }
        
        int jump = 2;
        while (jump < arrlength) {
            int counter = jump * 2;
            while (counter < arrlength) {
                notPrime[counter] = true;
                counter += jump;
            }
            jump += 1;
        }

        PrimeNode current = this.head;
        while (current.next != null)
            current = current.next;

        for (int i = 2; i < arrlength; i ++) {
            if (notPrime[i] == false && i > currentPrime()) {
                current.next = new PrimeNode(i);
                current = current.next;
            }
        }
    }

}
