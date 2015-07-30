package com.valik.positions;

import com.valik.Position;
import com.valik.Task;

public class Programmer extends Position {
	
	public Programmer() {
	
	}

	@Override
	protected int getHourlyRate() {
		return 30;
	}
	
	@Override
	protected void work() {
		System.out.println("Progremmer: write code");
	}
	
	@Override
	public boolean canMakeTask(Task task) {
		return task == Task.WRITE_CODE;
	}
}
