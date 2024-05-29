package com.company.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code EmployeeStructureNode} class represents a node in the employee structure tree.
 * Each node contains an {@code Employee} and references to its manager and subordinates.
 */
public class EmployeeStructureNode {

    private final Employee employee;
    private EmployeeStructureNode manager;
    private final List<EmployeeStructureNode> subordinates;

    /**
     * Constructs a new {@code EmployeeStructureNode} with the specified employee.
     * The list of subordinates is initialized as an empty list.
     *
     * @param employee the employee associated with this node
     */
    public EmployeeStructureNode(Employee employee) {
        this.employee = employee;
        this.subordinates = new ArrayList<>();
    }

    /**
     * Returns the employee associated with this node.
     *
     * @return the employee associated with this node
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Returns the manager of this employee node.
     *
     * @return the manager of this employee node, or {@code null} if this employee has no manager
     */
    public EmployeeStructureNode getManager() {
        return manager;
    }

    /**
     * Sets the manager of this employee node.
     *
     * @param manager the manager to be set for this employee node
     */
    public void setManager(EmployeeStructureNode manager) {
        this.manager = manager;
    }

    /**
     * Returns the list of subordinates for this employee node.
     *
     * @return the list of subordinates for this employee node
     */
    public List<EmployeeStructureNode> getSubordinates() {
        return subordinates;
    }

    /**
     * Adds a subordinate to the list of subordinates for this employee node.
     *
     * @param subordinate the subordinate to be added
     */
    public void addSubordinate(EmployeeStructureNode subordinate) {
        subordinates.add(subordinate);
    }

    /**
     * Compares this employee node to the specified object. The result is {@code true} if and only if the argument is not {@code null}
     * and is an {@code EmployeeStructureNode} object that has the same employee as this node.
     *
     * @param o the object to compare this {@code EmployeeStructureNode} against
     * @return {@code true} if the given object represents an {@code EmployeeStructureNode} with the same employee, {@code false} otherwise
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeStructureNode that = (EmployeeStructureNode) o;

        return employee.equals(that.employee);
    }

    /**
     * Returns a hash code value for the employee node. This method is supported for the benefit of hash tables such as those provided by {@link java.util.HashMap}.
     *
     * @return a hash code value for this employee node
     */
    public int hashCode() {
        return employee.hashCode();
    }
}
