package com.valik.positions;

import com.valik.Position;
import com.valik.Task;

public class Manager extends Position {

	@Override
	protected int getFixedRate() {
		return 2000;
	}

	@Override
	protected void work() {
		System.out.println("Manager: sell services");
	}

	@Override
	public boolean canMakeTask(Task task) {
		return task == Task.SELL_SERVICES;
	}
}
