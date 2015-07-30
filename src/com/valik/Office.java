package com.valik;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.valik.positions.Booker;
import com.valik.positions.Designer;
import com.valik.positions.Director;
import com.valik.positions.Manager;
import com.valik.positions.Programmer;
import com.valik.positions.Tester;

public class Office {

	/*
	 ���������� ������������� ������� ������� ����� � ������� ������ ������.
� ����� �������� 10 - 100 ����������� (�������� ��������), ������ �� ��� ����� ���� ��� ����� �����
��������� (�������� �������� ��� ������� ����������): �����������, ��������, �����������, ��������,
��������, ���������.
������ ��������� ����� ���� ������� ������, �� ����� 40 ����� � ������.
������ ��������� ����� ���� ��������� ������. ��������, �������� � ��������� ����� ������������� ������.
� ����� ������ ���� ���� �� ���� ��������, �������� � ���������.

� �������� ������������� ������ ��� �������� ���� ���� ��� ����� ������ ������������ ����� �����������.
���������� ������� ������������ ����� ������ �� ������ �� ���� ����� ������ �����������, � ���������
�������� ������ ���������� ������������� �������. ��������� �� ����� ��������� ����� ������ ������������
������������.
���� �� ���������� ������������ � ����� �� ������� ��������, ����� �������� ������� ����������� (���������
�����������).
������ ������ ��������� ��������� �������� ����������� ������ �� ���������� ������������ �����.
�� ��������� ������ ���������� ������������ ��������� ����� � ����������� ������ � �������� �������� ��
���� ������� � ��������� ��� � ��������� ��������.

����������� ����� ����������� ������������:
����������� � ������� ���
�������� � ��������� �����
����������� � ������������ ���������
�������� � ���������� ������
��������� � ���������� �����������
	 */

	private final static int	MAX_EMPLOYEE_POSITIONS_COUNT	= 3;
	private final static int	MAX_OREDERS_IN_HOUR				= 3;
	private static final int	HOURS_IN_DAY					= 8;
	static ArrayList<Employee>	employees						= new ArrayList<Employee>();
	public static int			totalSalary;
	public static int			salary;

	public static void main(String[] args) throws IOException {

		generateEmployees();
		simulate();
		calculateTotalSalary();
		writeFile();		
	}

	private static Position getRandomPosition() {
		int rnd = Helper.random(0, 5);

		switch (rnd) {
		case 0:
			return new Tester();
		case 1:
			return new Booker();
		case 2:
			return new Designer();
		case 3:
			return new Director();
		case 4:
			return new Manager();
		case 5:
			return new Programmer();
		}

		return null;
	}

	private static void generateEmployees() {

		int employeesCount = Helper.random(10, 11);//���������� ����������� (�� � ��)
		int positionsCount = employeesCount * MAX_EMPLOYEE_POSITIONS_COUNT;

		ArrayList<Position> positions = new ArrayList<Position>();
		for (int i = 0; i < positionsCount; i++) {
			positions.add(getRandomPosition());
		}

		for (int i = 0; i < employeesCount; i++) {

			Employee employee = new Employee();
			int cnt = Helper.random(1, MAX_EMPLOYEE_POSITIONS_COUNT);

			for (int j = 0; j < cnt; j++) {
				employee.addPosition(positions.get(0));
				positions.remove(0);
			}

			employees.add(employee);
		}

		System.out.printf("Generated %d employees\n", employees.size());

	}

	private static void simulate() {
		int day = 0;

		do {

			for (int i = 0; i < HOURS_IN_DAY; i++) {
				makeOrders();
				addHour();
			}

			day++;
			
			if (day % 7 == 0) {
				calculateSalary();
			}
			
		} while (day < 30);
	}

	private static void addHour() {
		for (Employee employee : employees) {
			employee.addHour();
		}
	}

	private static void makeOrders() {

		// System.out.println("Make orders");

		for (Employee employee : employees) {
			if (employee.isDirector()) {

				int tasksCount = Helper.random(1, MAX_OREDERS_IN_HOUR);
				for (int i = 0; i < tasksCount; i++) {

					Task task = Task.getRandomTask();
					int hours = Helper.random(1, 2);
					if (tryGiveTask(task, hours)) {
						//
					} else {
						System.out.println("Give task to freelance");
					}
				}
			}
		}
	}

	private static boolean tryGiveTask(Task task, int hours) {

		// System.out.println("Try Give Task " + task);

		for (Employee employee : employees) {
			if (employee.canMakeTask(task, hours)) {
				return true;
			}
		}
		return false;
	}

	private static void calculateSalary() {
		for (Employee employee : employees) {
			System.out.printf("Salary: %d\n", employee.getSalary());
			employee.nextWeek();
		}
	}
	
	private static void calculateTotalSalary() {
		for (Employee employee : employees) {
			System.out.printf("Total salary: %d\n", employee.getTotalSalary());
		}
	}

	
	private static void writeFile() {
	    //���������� ����
	    File file = new File("d: Office.txt");
	 
	    try {
	        //��������: ���� ���� �� ���������� �� ������� ���
	        if(!file.exists()){
	            file.createNewFile();
	        }
	 
	        //PrintWriter ��������� ����������� ������ � ����
	        PrintWriter out = new PrintWriter(file.getAbsoluteFile());
	 
	        try {
	            //���������� ����� � ����
	            out.println("��������� ����� � ����������� ������ � �������� ��������");
	            out.println();
	            
	            out.println("Generated " + employees.size() + " employees");
	            	            
	            for (Employee employee : employees) {
	    			out.println("Worker"+ employee + "Total salary: " + employee.getTotalSalary());
	            }
	            out.println();
	        } finally {
	            //����� ���� �� ������ ������� ����
	            //����� ���� �� ���������
	            out.close();
	        }
	    } catch(IOException e) {
	        throw new RuntimeException(e);
	    }
	}

}
