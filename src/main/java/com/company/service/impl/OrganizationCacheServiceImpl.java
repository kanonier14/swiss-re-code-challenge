package com.company.service.impl;

import com.company.dto.Employee;
import com.company.dto.EmployeeStructureNode;
import com.company.service.OrganizationCacheService;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The {@code OrganizationCacheServiceImpl} class implements the {@code OrganizationCacheService} interface
 * to provide caching and organizational structure functionality for {@code Employee} objects.
 */
public class OrganizationCacheServiceImpl implements OrganizationCacheService<Employee, Long> {

    private final Lock orgStructureBuildLock = new ReentrantLock();
    private final ConcurrentMap<Long, Employee> cache;
    private volatile EmployeeStructureNode ceo;

    /**
     * The {@code OrganizationCacheServiceImpl} class implements the {@code OrganizationCacheService} interface
     * to provide caching and organizational structure functionality for {@code Employee} objects.
     */
    public OrganizationCacheServiceImpl() {
        this.cache = new ConcurrentHashMap<>();
    }

    /**
     * Saves the specified employee in the cache.
     * If the employee or its ID is {@code null}, the method does nothing.
     *
     * @param employee the employee to be saved in the cache
     */
    @Override
    public void save(Employee employee) {
        if (employee == null || employee.getId() == null) {
            return;
        }
        cache.put(employee.getId(), employee);
    }

    /**
     * Saves all the specified employees in the cache.
     * Each employee is saved individually using the {@link #save(Employee)} method.
     *
     * @param employees the collection of employees to be saved in the cache
     */
    @Override
    public void saveAll(Collection<Employee> employees) {
        employees.forEach(this::save);
    }

    /**
     * Finds and returns the employee with the specified ID from the cache.
     *
     * @param employeeId the ID of the employee to be retrieved
     * @return the employee with the specified ID, or {@code null} if not found
     */
    @Override
    public Employee findById(Long employeeId) {
        return cache.get(employeeId);
    }

    /**
     * Returns a set of all employees currently stored in the cache.
     *
     * @return a set of all cached employees
     */
    @Override
    public Set<Employee> findAll() {
        return new HashSet<>(cache.values());
    }

    /**
     * Returns the root node of the organizational structure.
     * If the structure is not yet built, it builds the structure before returning it.
     *
     * @return the root {@code EmployeeStructureNode} of the organizational structure
     */
    @Override
    public EmployeeStructureNode getStructure() {

        if (ceo == null) {
            orgStructureBuildLock.lock();
            buildStructure();
            orgStructureBuildLock.unlock();
        }

        return ceo;
    }

    /**
     * Builds the organizational structure from the cached employees.
     * This method initializes the CEO node and links all employees to their respective managers.
     */
    private void buildStructure() {
        Map<Long, List<EmployeeStructureNode>> waitingForManager = new HashMap<>();
        Map<Long, EmployeeStructureNode> employeeStructureNodeMap = new HashMap<>();

        findAll().forEach(employee -> {
            EmployeeStructureNode employeeStructureNode = new EmployeeStructureNode(employee);

            if (employee.getManagerId() == null) {
                ceo = employeeStructureNode;
            }

            //Check if EmployeeStructureNode for the manager is already created, if not, add to waiting list
            if (employee.getManagerId() != null && employeeStructureNodeMap.containsKey(employee.getManagerId())) {
                employeeStructureNode.setManager(employeeStructureNodeMap.get(employee.getManagerId()));
                employeeStructureNodeMap.get(employee.getManagerId()).addSubordinate(employeeStructureNode);
            } else {
                if (waitingForManager.containsKey(employee.getManagerId())) {
                    waitingForManager.get(employee.getManagerId()).add(employeeStructureNode);
                } else {
                    waitingForManager.put(employee.getManagerId(), new ArrayList<>(List.of(employeeStructureNode)));
                }
            }

            employeeStructureNodeMap.put(employee.getId(), employeeStructureNode);
        });

        //Add subordinates to managers
        if (!waitingForManager.isEmpty()) {
            waitingForManager.forEach((managerId, subordinates) -> {
                EmployeeStructureNode manager = employeeStructureNodeMap.get(managerId);
                if (manager != null) {
                    subordinates.forEach(manager::addSubordinate);
                }
            });
        }
    }
}
