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
	 Необходимо смоделировать рабочий процесс офиса в течении одного месяца.
В офисе работает 10 - 100 сотрудников (задается случайно), каждый из них имеет одну или более одной
должности (задается случайно для каждого сотрудника): Программист, Дизайнер, Тестировщик, Менеджер,
Директор, Бухгалтер.
Каждый сотрудник имеет свой рабочий график, не более 40 часов в неделю.
Каждая должность имеет свою почасовую ставку. Директор, Менеджер и Бухгалтер имеют фиксированную ставку.
В фирме должны быть хотя бы один Директор, Менеджер и Бухгалтер.

В процессе моделирования каждый час Директор дает одно или более одного распоряжения своим сотрудникам.
Выполнение каждого распоряжения может занять от одного до двух часов каждым сотрудником, в должности
которого входит выполнение поставленного задания. Сотрудник не может выполнять более одного распоряжения
одновременно.
Если на выполнение распоряжения в офисе не имеется ресурсов, фирма передает задание фрилансерам (удаленным
сотрудникам).
Каждую неделю Бухгалтер начисляет зарплату сотрудникам исходя из фактически отработанных часов.
По окончании месяца необходимо сформировать суммарный отчет о выполненной работе и выданной зарплате по
всем рабочим и сохранить его в текстовый документ.

Минимальный набор должностных обязанностей:
Программист – «писать код»
Дизайнер – «рисовать макет»
Тестировщик – «тестировать программу»
Менеджер – «продавать услуги»
Бухгалтер – «составить отчетность»
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

		int employeesCount = Helper.random(10, 11);//количество сотрудников (от и до)
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
	    //Определяем файл
	    File file = new File("d: Office.txt");
	 
	    try {
	        //проверка: если файл не существует то создаем его
	        if(!file.exists()){
	            file.createNewFile();
	        }
	 
	        //PrintWriter обеспечит возможности записи в файл
	        PrintWriter out = new PrintWriter(file.getAbsoluteFile());
	 
	        try {
	            //Записываем текст в файл
	            out.println("Суммарный отчет о выполненной работе и выданной зарплате");
	            out.println();
	            
	            out.println("Generated " + employees.size() + " employees");
	            	            
	            for (Employee employee : employees) {
	    			out.println("Worker"+ employee + "Total salary: " + employee.getTotalSalary());
	            }
	            out.println();
	        } finally {
	            //После чего мы должны закрыть файл
	            //Иначе файл не запишется
	            out.close();
	        }
	    } catch(IOException e) {
	        throw new RuntimeException(e);
	    }
	}

}
