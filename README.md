# Employee Report Generator

This program is designed to generate reports on salary discrepancies and employee structure based on provided data.

## Features

- **Data Source**: uses CSV file as data source.
- **Salary Discrepancy Report**: Generates a report highlighting discrepancies in salaries among employees.
- **Employee Structure Report**: Provides a report on the hierarchical structure of employees along with the length of report lines.

## Assumptions

- **Input Data**: Assumes that input data is correct (each CSV row contains full and correct information about an employee) and represents a hierarchical structure of organization (a tree without cycles).

## Getting Started

### Prerequisites

To run this program, ensure that you have the following installed on your system:

- Java Development Kit (JDK) 11 or higher

### Installation

1. Clone this repository to your local machine:

   ```sh
   git clone https://github.com/kanonier14/swiss-re-code-challenge
    ```
   
2. Build the project using the following command:

   ```sh
   mvn clean install
   ```
   
3. Navigate to the target directory and run the following command:

   ```sh
   java -jar employee-structure-analyzer-1.0-SNAPSHOT.jar <path_to_csv_file>
   ```   
   Replace path_to_csv_file with the path to the CSV file containing employee data. If no argument is specified, the program will use the default file from the resources folder (src/main/resources/com/company/employees.csv).

