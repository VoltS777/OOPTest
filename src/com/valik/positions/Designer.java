package com.valik.positions;

import com.valik.Position;
import com.valik.Task;

public class Designer extends Position {

	@Override
	protected int getHourlyRate() {
		return 15;
	}

	@Override
	protected void work() {
		System.out.println("Designer: draw layouts");
	}
	
	@Override
	public boolean canMakeTask(Task task) {
		return task == Task.DRAW_LAYOUTS;
	}
}
