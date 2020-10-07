package com.oozeander.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oozeander.model.Employee;

@Service
public class EmployeeService {
	private List<Employee> employees = new ArrayList<>();

	public List<Employee> getEmployees() {
		return this.employees;
	}

	public Employee getEmployee(BigInteger id) {
		return employees.stream().filter(emp -> emp.getId().compareTo(id) == 0).findFirst().get();
	}

	public void save(Employee employee) {
		employees.add(employee);
	}

	public void update(Employee employee) {
		if (employees.stream().filter(emp -> emp.getId().compareTo(employee.getId()) == 0).findFirst().isPresent()) {
			employees.remove(
					employees.stream().filter(emp -> emp.getId().compareTo(employee.getId()) == 0).findFirst().get());
			employees.add(employee);
		}
	}

	public void delete(BigInteger id) {
		if (employees.stream().filter(emp -> emp.getId().compareTo(id) == 0).findFirst().isPresent()) {
			employees.remove(employees.stream().filter(emp -> emp.getId().compareTo(id) == 0).findFirst().get());
		}
	}
}