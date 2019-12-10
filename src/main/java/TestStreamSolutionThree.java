import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestStreamSolutionThree
{
    private static final int EMPLOYEE_COUNT = 44_000_000;

    public static class Employee
    {
        private final int age, employeeNumber;
        private final String name;

        private static       Random rand    = new Random();
        private static final int    MAX_AGE = 60;
        private static final int    MIN_AGE = 21;

        Employee(int employeeNumber)
        {
            this.employeeNumber = employeeNumber;
            age = rand.nextInt(MAX_AGE - MIN_AGE) + MIN_AGE;
            name = "a" + employeeNumber;
        }

        int getAge()
        {
            return age;
        }

        @SuppressWarnings("unused")
        String getName()
        {
            return name;
        }

        @SuppressWarnings("unused")
        int getEmployeeNumber()
        {
            return employeeNumber;
        }
    }

    /**
     * Employee Processor interface
     */
    @FunctionalInterface
    interface EmployeeProcessor
    {
        double process(List<Employee> employees);
    }

    public static void main(String[] args)
    {
        // Setup list of Employees (random age)
        var employees = IntStream.range(0, EMPLOYEE_COUNT).boxed().map(Employee::new)
                .collect(Collectors.toCollection(() -> new ArrayList<>(EMPLOYEE_COUNT)));

        // Test using the various implementations
        timeFunction(employees, TestStreamSolutionThree::concurrentAverage, "parallel way");
        timeFunction(employees, TestStreamSolutionThree::classicAverage, "classic way");
        timeFunction(employees, TestStreamSolutionThree::linearAverage, "linear way");

    }

    /**
     * Time a given function applied to the specified List of employees
     */
    @SuppressWarnings("UnusedReturnValue")
    private static double timeFunction(List<Employee> employees, EmployeeProcessor func, String funcName)
    {
        var startTime = System.nanoTime();
        var result = func.process(employees);
        var duration = System.nanoTime() - startTime;

        System.out.printf("Average age of %d employees is (%s) : %.2f (%.3f seconds)\n", employees.size(), funcName, result, (duration / 1_000_000_000.0f));
        return result;
    }

    /**
     * Java Method to calculate average from a list of object without using
     * lambdas, doing it on classical java way.
     *
     * @param employees List of Employees to average
     * @return average age of given list of Employee
     */
    private static double classicAverage(List<? extends Employee> employees)
    {
        int totalEmployee = employees.size();
        double sum = 0;
        for (Employee e : employees)
        {
            sum += e.getAge();
        }

        return sum / totalEmployee;
    }

    /**
     * Java method which uses map reduce to calculate average of list of employees in JDK 8+
     * using streams
     *
     * @param employees List of Employees to average
     * @return average age of given list of Employees
     */
    private static double linearAverage(List<? extends Employee> employees)
    {
        return employees.stream().mapToDouble(Employee::getAge).average().orElse(0d);
    }

    /**
     * Java method which uses map reduce to calculate average of list of employees in JDK 8+
     * using parallel streams
     *
     * @param employees List of Employees to average
     * @return average age of given list of Employees
     */
    private static double concurrentAverage(List<? extends Employee> employees)
    {
        return employees.parallelStream().mapToDouble(Employee::getAge).average().orElse(0d);
    }
}