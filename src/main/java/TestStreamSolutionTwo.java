import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class TestStreamSolutionTwo
{

    public static class Employee {
        private final int age, employeeNumber;
        private final String name;

        private static Random rand = new Random();

        private static final int MAX_AGE = 60;
        private static final int MIN_AGE = 21;

        Employee(int employeeNumber) {
            this.employeeNumber = employeeNumber;
            age = rand.nextInt(MAX_AGE - MIN_AGE) + MIN_AGE;
            name = "a" + employeeNumber;
        }
        int getAge() { return age; }
        @SuppressWarnings("unused") String getName() { return name; }
        @SuppressWarnings("unused") int getEmployeeNumber() { return employeeNumber; }
    }

    public static void main(String[] args) {
        var people = IntStream.range(0, 44_000_000).boxed().map(Employee::new).collect(Collectors.toList());
        var linearStart = System.nanoTime();
        var average = calculateAverage(people);
        var duration = System.nanoTime() - linearStart;
        System.out.printf("Average age of %d employees is (classic way) : %.2f (%.3f seconds)\n", people.size(), average, (duration / 1_000_000_000.0f));

        var lambdaStart = System.nanoTime();
        average = concurrentAverage(people);
        duration = System.nanoTime() - lambdaStart;
        System.out.printf("Average age of %d employees is (lambda way) : %.2f (%.3f seconds)", people.size(), average, (duration / 1_000_000_000.0f));
    }

    /**
     * Java Method to calculate average from a list of object without using
     * lambdas, doing it on classical java way.
     * @param employees List of Employees to average
     * @return average age of given list of Employee
     */
    private static double calculateAverage(List<? extends Employee> employees){
        int totalEmployee = employees.size();
        double sum = 0;
        for(Employee e : employees){
            sum += e.getAge();
        }

        return sum/totalEmployee;
    }

    /**
     * Java method which uses map reduce to calculate average of list of employees in JDK 8+ 
     * using parallel streams
     * @param employees List of Employees to average
     * @return average age of given list of Employees
     */
    private static double concurrentAverage(List<? extends Employee> employees) {
        return employees.parallelStream().mapToDouble(Employee::getAge)
                .average().orElse(0d);
    }
}