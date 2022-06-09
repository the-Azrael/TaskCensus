import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static final int POPULATION = 10_000_000;
    private static final int ADULT_AGE = 18;
    private static final int RECRUIT_START_AGE = ADULT_AGE;
    private static final int RECRUIT_END_AGE = 27;
    private static final int WORK_START_AGE = ADULT_AGE;
    private static final int WORK_END_AGE = 60;

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < POPULATION; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        System.out.println("1. Найти количество несовершеннолетних (т.е. людей младше 18 лет)");
        Stream<Person> minorsStream = persons.stream().filter(x -> x.getAge() < ADULT_AGE);
        long minorsCount = minorsStream.count();
        System.out.println(minorsCount);
        System.out.println("---------------------------------------------");
        System.out.println();

        System.out.println("2. Получить список фамилий призывников (т.е. мужчин от 18 и до 27 лет)");
        Stream<Person> recruitsStream = persons.stream();
        recruitsStream
                .filter(x -> x.getAge() >= RECRUIT_START_AGE)
                .filter(x -> x.getAge() < RECRUIT_END_AGE)
                .map(Person::getFamily)
                .collect(Collectors.toList()).forEach(System.out::println);
        System.out.println("---------------------------------------------");
        System.out.println();

        System.out.println("3. Получить отсортированный по фамилии список потенциально работоспособных людей " +
                "с высшим образованием в выборке " +
                "(т.е. людей с высшим образованием от 18 до 60 лет для женщин и до 65 лет для мужчин)");
        Stream<Person> worksStream = persons.stream();
        worksStream
                .filter(x -> x.getAge() >= WORK_START_AGE && x.getAge() < WORK_END_AGE)
                .filter(x -> x.getEducation() == Education.HIGHER)
                .sorted((o1, o2) -> o1.getFamily().compareTo(o2.getFamily()))
                .collect(Collectors.toList())
                .forEach((s) -> System.out.println(s.getFamily() + " " + s.getName() + " " + s.getAge() + " years old"));

    }
}
