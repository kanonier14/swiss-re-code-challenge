package com.company.service.impl;

import com.company.dto.Employee;
import com.company.dto.EmployeeStructureNode;
import com.company.service.DataReadService;
import com.company.service.OrganizationCacheService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;


class OrganizationCacheServiceImplTest {

    public static final String EMPLOYEES_CSV_PATH = "com/company/employees.csv";

    private static DataReadService<Employee> dataReadService;
    private static Set<Employee> employees;

    @BeforeAll
    public static void setUp() {
        dataReadService = new FileDataReadServiceImpl();
        employees = dataReadService.readData(EMPLOYEES_CSV_PATH);
    }

    @Test
    public void testSave() {
        OrganizationCacheService<Employee, Long> organizationCacheService = new OrganizationCacheServiceImpl();
        employees.forEach(employee -> {
            organizationCacheService.save(employee);
            Employee savedEmployee = organizationCacheService.findById(employee.getId());
            Assertions.assertEquals(employee, savedEmployee);
        });

        Assertions.assertEquals(employees.size(), organizationCacheService.findAll().size());
    }

    @Test
    public void testSaveNullShouldNotBeSaved() {
        OrganizationCacheService<Employee, Long> organizationCacheService = new OrganizationCacheServiceImpl();
        organizationCacheService.save(null);
        Assertions.assertTrue(organizationCacheService.findAll().isEmpty());
    }

    @Test
    public void testSaveEmployeeWithNullIdShouldNotBeSaved() {
        OrganizationCacheService<Employee, Long> organizationCacheService = new OrganizationCacheServiceImpl();
        Employee employee = new Employee(null, "John", "Doe", 1000.0);
        organizationCacheService.save(employee);
        Assertions.assertTrue(organizationCacheService.findAll().isEmpty());
    }

    @Test
    public void testSaveAll() {
        OrganizationCacheService<Employee, Long> organizationCacheService = new OrganizationCacheServiceImpl();
        organizationCacheService.saveAll(employees);
        employees.forEach(employee -> {
            Employee savedEmployee = organizationCacheService.findById(employee.getId());
            Assertions.assertEquals(employee, savedEmployee);
        });

        Assertions.assertEquals(employees.size(), organizationCacheService.findAll().size());
    }

    @Test
    public void testGetStructure() {
        OrganizationCacheService<Employee, Long> organizationCacheService = new OrganizationCacheServiceImpl();
        organizationCacheService.saveAll(employees);
        EmployeeStructureNode ceo = organizationCacheService.getStructure();
        Assertions.assertNotNull(ceo);
        Assertions.assertEquals(123, ceo.getEmployee().getId());
        Assertions.assertEquals("Joe", ceo.getEmployee().getFirstName());
        Assertions.assertEquals("Doe", ceo.getEmployee().getLastName());

        Set<EmployeeStructureNode> employeeStructureNodes = new HashSet<>();
        buildStructureAsSet(ceo, employeeStructureNodes);
        Assertions.assertEquals(employees.size(), employeeStructureNodes.size());
    }

    private void buildStructureAsSet(EmployeeStructureNode employee, Set<EmployeeStructureNode> employeeStructureNodes) {
        if (employee == null) {
            return;
        }
        employeeStructureNodes.add(employee);
        employee.getSubordinates().forEach(child -> buildStructureAsSet(child, employeeStructureNodes));
    }

    @Test
    public void testGetStructureCheckIfHasCycle() {
        OrganizationCacheService<Employee, Long> organizationCacheService = new OrganizationCacheServiceImpl();
        organizationCacheService.saveAll(employees);
        EmployeeStructureNode ceo = organizationCacheService.getStructure();
        Assertions.assertNotNull(ceo);
        Assertions.assertFalse(hasCycle(ceo, new HashSet<>()));
    }

    private boolean hasCycle(EmployeeStructureNode ceo, HashSet<EmployeeStructureNode> structureNodes) {
        if (ceo == null) {
            return false;
        }
        if (structureNodes.contains(ceo)) {
            return true;
        }
        structureNodes.add(ceo);
        for (EmployeeStructureNode child : ceo.getSubordinates()) {
            if (hasCycle(child, structureNodes)) {
                return true;
            }
        }
        structureNodes.remove(ceo);
        return false;
    }
}