import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TestStreamsSolutionOne
{
    public static void main(String args[]) {
        List<Employee> people = new ArrayList<>();
        for(Integer i=1; i<=43500000; i++) {
            people.add(new Employee(i++, "a"+i.toString(),
                    ThreadLocalRandom.current().nextInt(21,60)));
        }
//        people.add(new Employee(101, "Victor", 23));
//        people.add(new Employee(102, "Rick", 21));
//        people.add(new Employee(103, "Sam", 25));
//        people.add(new Employee(104, "John", 27));
//        people.add(new Employee(105, "Grover", 23));
//        people.add(new Employee(106, "Adam", 22));
//        people.add(new Employee(107, "Samy", 224));
//        people.add(new Employee(108, "Duke", 29));

        Instant start = Instant.now();
        double average = calculateAverage(people);
        System.out.println("Average age of employees are (classic way) : " + average);
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println("Duration : " + timeElapsed);

        Instant startTwo = Instant.now();
        average = average(people);
        System.out.println("Average age of employees are (lambda way) : " + average);
        Instant finishTwo = Instant.now();
        long timeElapsedTwo = Duration.between(startTwo, finishTwo).toMillis();
        System.out.println("Duration : " + timeElapsedTwo);
    }

    /**
     * Java Method to calculate average from a list of object without using
     * lambdas, doing it on classical java way.
     * @param employees
     * @return average age of given list of Employee
     */
    private static double calculateAverage(List<? extends Employee> employees){
        int totalEmployee = employees.size();
        double sum = 0;
        for(Employee e : employees){
            sum += e.getAge();
        }

        double average = sum/totalEmployee;
        return average;
    }

    /**
     * Java method which uses map reduce to calculate average of list of
     * employees in JDK 8.
     * @param peoples
     * @return average age of given list of Employees
     */
    private static double average(List<? extends Employee> peoples){
        return peoples.parallelStream().mapToInt(p-> p.getAge())
                .average()
                .getAsDouble();
    }

}
