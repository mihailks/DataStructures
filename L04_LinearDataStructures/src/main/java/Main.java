import implementations.ArrayList;
import interfaces.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> testList = new ArrayList<>();

        testList.add(1);
        testList.add(5);
        testList.add(10);
        testList.add(11);
        testList.add(15);

        testList.remove(-1);

        for (Integer i : testList) {
            System.out.println(i);
        }

    }
}
