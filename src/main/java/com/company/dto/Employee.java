package com.company.dto;

/**
 * The {@code Employee} class represents an employee in the organization.
 * It includes details such as the employee's ID, first name, last name, salary, and manager ID.
 */
public class Employee {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final double salary;
    private final Long managerId;

    /**
     * Constructs a new {@code Employee} with the specified ID, first name, last name, and salary.
     * This constructor is used when the employee does not have a manager.
     *
     * @param id the ID of the employee
     * @param firstName the first name of the employee
     * @param lastName the last name of the employee
     * @param salary the salary of the employee
     */
    public Employee(Long id, String firstName, String lastName, double salary) {
        this(id, firstName, lastName, salary, null);
    }

    /**
     * Constructs a new {@code Employee} with the specified ID, first name, last name, salary, and manager ID.
     *
     * @param id the ID of the employee
     * @param firstName the first name of the employee
     * @param lastName the last name of the employee
     * @param salary the salary of the employee
     * @param managerId the ID of the manager of the employee, or {@code null} if the employee does not have a manager
     */
    public Employee(Long id, String firstName, String lastName, double salary, Long managerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = managerId;
    }

    /**
     * Returns the ID of the employee.
     *
     * @return the ID of the employee
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the first name of the employee.
     *
     * @return the first name of the employee
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name of the employee.
     *
     * @return the last name of the employee
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the salary of the employee.
     *
     * @return the salary of the employee
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Returns the ID of the manager of the employee, or {@code null} if the employee does not have a manager.
     *
     * @return the ID of the manager, or {@code null} if the employee does not have a manager
     */
    public Long getManagerId() {
        return managerId;
    }

    /**
     * Compares this employee to the specified object. The result is {@code true} if and only if the argument is not {@code null}
     * and is an {@code Employee} object that has the same ID as this object.
     *
     * @param object the object to compare this {@code Employee} against
     * @return {@code true} if the given object represents an {@code Employee} with the same ID as this employee, {@code false} otherwise
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Employee employee = (Employee) object;
        return id.equals(employee.id);
    }

    /**
     * Returns a hash code value for the employee.
     *
     * @return a hash code value for this employee
     */
    public int hashCode() {
        return id.hashCode();
    }
}
