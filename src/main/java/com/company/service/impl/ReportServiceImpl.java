package com.company.service.impl;

import com.company.dto.Employee;
import com.company.dto.EmployeeStructureNode;
import com.company.service.ReportService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the ReportService interface providing methods to generate reports on salary discrepancies
 * and employee structure.
 */
public class ReportServiceImpl implements ReportService {

    /**
     * Retrieves a map of employees and their salary discrepancies exceeding the specified threshold.
     *
     * @param employeeHierarchy the hierarchical structure of employees
     * @param threshold         the salary discrepancy threshold
     * @param checkLess         if true, checks discrepancies less than the threshold, if false, only greater ones
     * @return a map associating employees with their salary discrepancies
     */
    @Override
    public Map<Employee, Double> getSalaryDiscrepancies(EmployeeStructureNode employeeHierarchy, double threshold, boolean checkLess) {
        Map<Employee, Double> salaryDiscrepancies = new HashMap<>();
        findDiscrepancies(employeeHierarchy, threshold, checkLess, salaryDiscrepancies);
        return salaryDiscrepancies;
    }

    /**
     * Recursively finds salary discrepancies for the given employee structure node and its subordinates.
     *
     * @param employeeStructureNode the current employee structure node
     * @param threshold             the salary discrepancy threshold
     * @param checkLess             if true, checks discrepancies less than the threshold, if false, only greater ones
     * @param salaryDiscrepancies   the map to store the found salary discrepancies
     */
    private void findDiscrepancies(EmployeeStructureNode employeeStructureNode, double threshold, boolean checkLess, Map<Employee, Double> salaryDiscrepancies) {

        List<EmployeeStructureNode> subordinates = employeeStructureNode.getSubordinates();

        if (!subordinates.isEmpty()) {
            double subordinatesAverageSalary = subordinates.stream()
                    .mapToDouble(node -> node.getEmployee().getSalary())
                    .average()
                    .orElse(0.0);
            double managerSalary = employeeStructureNode.getEmployee().getSalary();

            BigDecimal discrepancy = BigDecimal.ONE.subtract(BigDecimal.valueOf(subordinatesAverageSalary).divide(BigDecimal.valueOf(managerSalary), 10, RoundingMode.HALF_UP));
            if (checkLess && discrepancy.compareTo(BigDecimal.valueOf(threshold)) < 0) {
                salaryDiscrepancies.put(employeeStructureNode.getEmployee(), discrepancy.doubleValue());
            } else if (!checkLess && discrepancy.compareTo(BigDecimal.valueOf(threshold)) > 0) {
                salaryDiscrepancies.put(employeeStructureNode.getEmployee(), discrepancy.doubleValue());
            }
        }

        for (EmployeeStructureNode subordinate : employeeStructureNode.getSubordinates()) {
            findDiscrepancies(subordinate, threshold, checkLess, salaryDiscrepancies);
        }
    }

    /**
     * Retrieves a map of employees and the length of report line for each employee in the structure.
     *
     * @param employeeHierarchy the hierarchical structure of employees
     * @param maxDepth          the maximum depth of hierarchy for which to retrieve the report
     * @return a map associating employees with the length of their report line
     */
    @Override
    public Map<Employee, Integer> getEmployeesReportLineLength(EmployeeStructureNode employeeHierarchy, int maxDepth) {
        Map<Employee, Integer> employeesReportLineLength = new HashMap<>();
        if (employeeHierarchy != null && maxDepth > 0) {
            checkLineLength(employeeHierarchy, 1, maxDepth, employeesReportLineLength);
        }
        return employeesReportLineLength;
    }

    /**
     * Recursively checks the length of report line for each employee in the structure.
     *
     * @param employeeStructureNode   the current employee structure node
     * @param depth                   the current depth of hierarchy
     * @param maxDepth                the maximum depth of hierarchy for which to retrieve the report
     * @param employeesReportLineLength the map to store the length of report line for each employee
     */
    private void checkLineLength(EmployeeStructureNode employeeStructureNode, int depth, int maxDepth,
                               Map<Employee, Integer> employeesReportLineLength) {

        if (depth > maxDepth) {
            employeesReportLineLength.put(employeeStructureNode.getEmployee(), depth);
        }

        for (EmployeeStructureNode subordinate : employeeStructureNode.getSubordinates()) {
            checkLineLength(subordinate, depth + 1, maxDepth, employeesReportLineLength);
        }
    }
}
