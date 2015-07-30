package com.valik;

abstract public class Position {

	protected int getHourlyRate() {
		return 0;
	}

	protected int getFixedRate() {
		return 0;
	}

	protected abstract void work();

	public abstract boolean canMakeTask(Task task);
}
