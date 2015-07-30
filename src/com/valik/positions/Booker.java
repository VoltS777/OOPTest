package com.valik.positions;

import com.valik.Position;
import com.valik.Task;

public class Booker extends Position {

	@Override
	protected int getFixedRate() {
		return 1500;
	}

	@Override
	protected void work() {
		System.out.println("Booker: making statements");
	}

	@Override
	public boolean canMakeTask(Task task) {
		return task == Task.MAKE_STATEMENTS;
	}
}
