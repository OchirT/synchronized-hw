import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(() -> {
                int count = 0;
                String text = generateRoute("RLRFR", 100);
                for (int j = 0; j < text.length(); j++) {
                    if (text.charAt(j) == 'R') {
                        count++;
                    }
                }
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(count)) {
                        sizeToFreq.put(count, sizeToFreq.get(count) + 1);
                    } else {
                        sizeToFreq.put(count, 1);
                    }
                }
            });
            thread.start();
        }

        Map.Entry<Integer, Integer> maxEntry = sizeToFreq.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);
        System.out.printf("Самое частое количество повторений %s (встретилось %s раз)", maxEntry.getKey(), maxEntry.getValue());
        System.out.println("Другие размеры: ");
        sizeToFreq.entrySet().forEach(System.out::println);

    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
