package com.valik.positions;

import com.valik.Position;
import com.valik.Task;

public class Tester extends Position {

	@Override
	protected int getHourlyRate() {
		return 20;
	}

	@Override
	protected void work() {
		System.out.println("Tester: test programs");
	}
	
	@Override
	public boolean canMakeTask(Task task) {
		return task == Task.TEST_PROGRAMS;
	}
}
