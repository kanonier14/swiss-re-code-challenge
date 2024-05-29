package com.company.service.impl;

import com.company.Main;
import com.company.dto.Employee;
import com.company.service.DataReadService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A service implementation for reading employee data from a file.
 */
public class FileDataReadServiceImpl implements DataReadService<Employee> {

    /**
     * The name of the column containing employee IDs in the CSV file.
     */
    private static final String ID = "Id";

    /**
     * Reads employee data from the specified file and returns a set of Employee objects.
     *
     * @param fileName The name of the CSV file containing employee data.
     * @return A set of Employee objects read from the CSV file.
     */
    @Override
    public Set<Employee> readData(String fileName) {
        Set<Employee> employees = new HashSet<>();

        try (BufferedReader bufferedReader = openFile(fileName)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //skip header
                if (line.startsWith(ID)) {
                    continue;
                }
                String[] data = line.split(",");
                Employee employee;
                if (data.length == 4 || "".equals(data[4])) {
                    employee = new Employee(Long.parseLong(data[0]), data[1], data[2], Double.parseDouble(data[3]));

                } else {
                    employee = new Employee(Long.parseLong(data[0]), data[1], data[2], Double.parseDouble(data[3]), Long.parseLong(data[4]));
                }
                employees.add(employee);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + fileName);
        }
        return employees;
    }

    private BufferedReader openFile(String fileName) throws IOException {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            return new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
            if (inputStream == null) {
                throw new FileNotFoundException("File not found: " + fileName);
            }
            return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        }
    }
}
