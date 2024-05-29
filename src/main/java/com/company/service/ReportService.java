package com.company.service;

import com.company.dto.Employee;
import com.company.dto.EmployeeStructureNode;

import java.util.Map;

/**
 * Service for generating reports on salary discrepancies and employee structure.
 */
public interface ReportService {

    /**
     * Retrieves a map of employees and their salary discrepancies exceeding the specified threshold.
     *
     * @param employeeHierarchy the hierarchical structure of employees
     * @param threshold         the salary discrepancy threshold
     * @param checkLess         if true, checks discrepancies less than the threshold, if false, more or equal to the threshold
     * @return a map associating employees with their salary discrepancies
     */
    Map<Employee, Double> getSalaryDiscrepancies(EmployeeStructureNode employeeHierarchy, double threshold, boolean checkLess);

    /**
     * Retrieves a map of employees and the length of report line for each employee in the structure.
     *
     * @param employeeHierarchy the hierarchical structure of employees
     * @param maxDepth          the maximum depth of hierarchy for which to retrieve the report
     * @return a map associating employees with the length of their report line
     */
    Map<Employee, Integer> getEmployeesReportLineLength(EmployeeStructureNode employeeHierarchy, int maxDepth);
}
