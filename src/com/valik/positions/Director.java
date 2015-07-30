package com.valik.positions;

import com.valik.Position;
import com.valik.Task;

public class Director extends Position {

	@Override
	protected int getFixedRate() {
		return 5000;
	}

	@Override
	protected void work() {
	}

	@Override
	public boolean canMakeTask(Task task) {
		return false;
	}
}
