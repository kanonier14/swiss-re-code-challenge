package com.company.service.impl;

import static com.company.service.impl.OrganizationCacheServiceImplTest.EMPLOYEES_CSV_PATH;

import com.company.dto.Employee;
import com.company.dto.EmployeeStructureNode;
import com.company.service.DataReadService;
import com.company.service.OrganizationCacheService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

class ReportServiceImplTest {

    private static OrganizationCacheService<Employee, Long> organizationCacheService;
    private static ReportServiceImpl reportService;

    @BeforeAll
    static void setUp() {
        organizationCacheService = new OrganizationCacheServiceImpl();
        reportService = new ReportServiceImpl();
        DataReadService<Employee> dataReadService = new FileDataReadServiceImpl();
        organizationCacheService.saveAll(dataReadService.readData(EMPLOYEES_CSV_PATH));
    }

    @Test
    public void testGetEmployeesReportLineLength() {
        EmployeeStructureNode ceo = organizationCacheService.getStructure();
        Map<Employee, Integer> reportLineLength = reportService.getEmployeesReportLineLength(ceo, 3);
        Assertions.assertFalse(reportLineLength.isEmpty());
        Assertions.assertEquals(1, reportLineLength.size());
    }

    @Test
    public void testGetEmployeesReportLineLengthWhenHierarchyIsEmpty() {
        EmployeeStructureNode ceo = new EmployeeStructureNode(new Employee(1L, "John", "Doe", 1000.0));
        Map<Employee, Integer> reportLineLength = reportService.getEmployeesReportLineLength(ceo, 1);
        Assertions.assertTrue(reportLineLength.isEmpty());
    }

    @Test
    public void testGetEmployeesReportLineLengthWhenMaxDepthIsEqualsOrMoreThanHierarchyHeight() {
        EmployeeStructureNode ceo = organizationCacheService.getStructure();
        Map<Employee, Integer> reportLineLength = reportService.getEmployeesReportLineLength(ceo, 4);
        Assertions.assertTrue(reportLineLength.isEmpty());
        reportLineLength = reportService.getEmployeesReportLineLength(ceo, 5);
        Assertions.assertTrue(reportLineLength.isEmpty());
    }

    @Test
    public void testGetSalaryDiscrepanciesWhenManagerEarnMoreTharShould() {
        EmployeeStructureNode ceo = organizationCacheService.getStructure();
        Map<Employee, Double> salaryDiscrepancies = reportService.getSalaryDiscrepancies(ceo, 0.3, false);
        Assertions.assertFalse(salaryDiscrepancies.isEmpty());
        Assertions.assertEquals(1, salaryDiscrepancies.size());
    }

    @Test
    public void testGetSalaryDiscrepanciesWhenManagerEarnLessTharShould() {
        EmployeeStructureNode ceo = organizationCacheService.getStructure();
        Map<Employee, Double> salaryDiscrepancies = reportService.getSalaryDiscrepancies(ceo, 0.3, true);
        Assertions.assertFalse(salaryDiscrepancies.isEmpty());
        Assertions.assertEquals(2, salaryDiscrepancies.size());
    }

    @Test
    public void testGetSalaryDiscrepanciesWithBoundaryValues() {
        EmployeeStructureNode ceo = organizationCacheService.getStructure();

        //Check that manager earns at least 20% more than the average salary of his subordinates
        Map<Employee, Double> salaryDiscrepancies = reportService.getSalaryDiscrepancies(ceo, 0.2, false);
        Assertions.assertFalse(salaryDiscrepancies.isEmpty());
        Assertions.assertEquals(1, salaryDiscrepancies.size());

        //Check that manager earns not more than 40% of the average salary of his subordinates
        salaryDiscrepancies = reportService.getSalaryDiscrepancies(ceo, 0.4, true);
        Assertions.assertFalse(salaryDiscrepancies.isEmpty());
        Assertions.assertEquals(2, salaryDiscrepancies.size());
    }
}