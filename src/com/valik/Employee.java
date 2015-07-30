package com.valik;


import java.util.ArrayList;

import com.valik.positions.Director;

public class Employee {

	private ArrayList<Position>			positions;
	private int							hoursInWeek;
	private int							seizedHours;
	private Position					seizedPosition;
	private int							salary;
	private int							totalSalary;
	private int							workedHours;

	public Employee() {
		positions = new ArrayList<Position>();
		hoursInWeek = Helper.random(10, 40);
	}

	public void addPosition(Position position) {
		positions.add(position);
	}

	public boolean isDirector() {

		for (Position position : positions) {
			if (position instanceof Director) return true;
		}

		return false;
	}

	public boolean canMakeTask(Task task, int hours) {

		if (seizedHours > 0 || workedHours + hours > hoursInWeek) return false;

		for (Position position : positions) {
			if (position.canMakeTask(task)) {

				seizedHours = hours;
				seizedPosition = position;
				position.work();
				return true;
			}
		}

		return false;

	}

	public void addHour() {

		if (seizedHours > 0) {
			seizedHours--;
			workedHours++;
			salary += seizedPosition.getHourlyRate();//обнуляется каждые 7 дней
			totalSalary += seizedPosition.getHourlyRate();//не обнуляется
		}
	}

	public int getSalary() {
		return salary;
	}
	
	public int getTotalSalary() {
		return totalSalary;
	}

	public void nextWeek() {
		salary = 0;
		workedHours = 0;
	}
	
	
}

