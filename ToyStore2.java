package toys;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ToyStore {

    /**
     * Вложенный класс Toy для представления игрушки с полями id, name и weight.
     * 1) Класс-конструктор принимает минимум 3 строки, содержащие три поля:
     *    id игрушки, текстовое название и частоту выпадения игрушки.
     */
    private class Toy {
        private int id;
        private String name;
        private int weight;

        public Toy(int id, String name, int weight) {
            this.id = id;
            this.name = name;
            this.weight = weight;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return id + " " + name + " " + weight;
        }
    }

    private PriorityQueue<Toy> toyQueue;  // 3) Используем PriorityQueue для добавления элементов в коллекцию
    private List<Toy> toyList;            // Общий список игрушек
    private Random random;

    // Массивы для хранения данных игрушек
    private int[] ids;
    private String[] names;
    private int[] weights;
    private int toyCount;

    public ToyStore(int maxToys) {
        toyQueue = new PriorityQueue<>(Comparator.comparingInt(Toy::getWeight).reversed());
        toyList = new ArrayList<>();
        random = new Random();

        // Инициализация массивов
        ids = new int[maxToys];
        names = new String[maxToys];
        weights = new int[maxToys];
        toyCount = 0;
    }

    /**
     * Метод добавляет новую игрушку в магазин.
     * 2) Из принятой строки id и частоты выпадения(веса) заполняем минимум три массива.
     *
     * @param id     идентификатор игрушки
     * @param name   название игрушки
     * @param weight частота выпадения (вес) игрушки
     */
    public void addToy(String id, String name, String weight) {
        int toyId = Integer.parseInt(id);
        int toyWeight = Integer.parseInt(weight);
        Toy toy = new Toy(toyId, name, toyWeight);
        toyQueue.add(toy);     // 3) Добавляем элементы в PriorityQueue
        toyList.add(toy);      // 4) Организуем общую очередь

        // Заполнение массивов
        if (toyCount < ids.length) {
            ids[toyCount] = toyId;
            names[toyCount] = name;
            weights[toyCount] = toyWeight;
            toyCount++;
        } else {
            System.out.println("Массивы игрушек заполнены, не могу добавить больше игрушек.");
        }
    }

    /**
     * Метод добавляет несколько игрушек в магазин.
     *
     * @param toyEntries строки с данными игрушек
     */
    public void put(String[] toyEntries) {
        for (String entry : toyEntries) {
            String[] parts = entry.split(" ");
            if (parts.length == 3) {
                addToy(parts[0], parts[2], parts[1]);
            } else {
                System.out.println("Некорректная строка: " + entry);
            }
        }
    }

    /**
     * Метод случайным образом возвращает идентификатор игрушки
     * в соответствии с весом (вероятностью).
     *
     * @return идентификатор случайной игрушки
     */
    public int get() {
        int rand = random.nextInt(100);
        if (rand < 20) {
            return ids[0]; // 20% случаев возвращает первую игрушку
        } else if (rand < 40) {
            return ids[1]; // 20% случаев возвращает вторую игрушку
        } else {
            return ids[2]; // 60% случаев возвращает третью игрушку
        }
    }

    /**
     * 5) Метод записывает результаты случайного выбора игрушек в файл.
     *
     * @param filename       имя файла для записи результатов
     * @param numberOfResults количество записей в файле
     */
    public void writeResultsToFile(String filename, int numberOfResults) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (int i = 0; i < numberOfResults; i++) {
                int toyId = get();  // 5) Вызов метода get 10 раз
                writer.write(toyId + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ToyStore store = new ToyStore(3);  // Задаем максимальное количество игрушек
        store.put(new String[]{"1 2 конструктор", "2 2 робот", "3 6 кукла"});

        store.writeResultsToFile("results.txt", 10);  // 5) Запись результатов в файл
    }
}
