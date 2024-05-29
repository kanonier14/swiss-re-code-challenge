package com.company;

import com.company.dto.Employee;
import com.company.dto.EmployeeStructureNode;
import com.company.service.DataReadService;
import com.company.service.OrganizationCacheService;
import com.company.service.ReportService;
import com.company.service.impl.FileDataReadServiceImpl;
import com.company.service.impl.OrganizationCacheServiceImpl;
import com.company.service.impl.ReportServiceImpl;
import java.util.Map;

public class Main {

    private static final String FILE_PATH = "com/company/employees.csv";
    private static final OrganizationCacheService<Employee, Long> organizationCacheService = new OrganizationCacheServiceImpl();
    private static final DataReadService<Employee> dataReadService = new FileDataReadServiceImpl();
    private static final ReportService reportService = new ReportServiceImpl();

    private static final int DEFAULT_MAX_DEPTH = 3;

    private static final double DEFAULT_MAX_DISCREPANCY = 0.5;
    private static final double DEFAULT_MIN_DISCREPANCY = 0.2;
    public static final String DISCREPEANCY_OUTPUT_FORMAT = "%s %s %.2f";

    public static void main(String[] args) {

        if (args.length > 0) {
            organizationCacheService.saveAll(dataReadService.readData(args[0]));
        } else {
            organizationCacheService.saveAll(dataReadService.readData(FILE_PATH));
        }

        EmployeeStructureNode ceo = organizationCacheService.getStructure();

        Map<Employee, Double> discrepancyNotMore = reportService.getSalaryDiscrepancies(ceo, DEFAULT_MAX_DISCREPANCY, false);
        Map<Employee, Double> discrepancyNotLess = reportService.getSalaryDiscrepancies(ceo, DEFAULT_MIN_DISCREPANCY, true);

        System.out.println("Employees who earn more than should, in format: FirstName LastName Discrepancy(percent):");
        discrepancyNotMore.forEach((employee, discrepancy) -> System.out.printf((DISCREPEANCY_OUTPUT_FORMAT) + "%n", employee.getFirstName(), employee.getLastName(), discrepancy));
        System.out.println();
        System.out.println("Employees who earn less than should, in format: FirstName LastName Discrepancy(percent):");
        discrepancyNotLess.forEach((employee, discrepancy) -> System.out.printf((DISCREPEANCY_OUTPUT_FORMAT) + "%n", employee.getFirstName(), employee.getLastName(), discrepancy));
        System.out.println();
        System.out.println("Employees with long report line length:");
        Map<Employee, Integer> reportLineLength = reportService.getEmployeesReportLineLength(ceo, DEFAULT_MAX_DEPTH);
        reportLineLength.forEach((employee, length) -> System.out.printf("%s %s %d%n", employee.getFirstName(), employee.getLastName(), length));
    }
}
