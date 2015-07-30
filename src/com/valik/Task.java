package com.valik;

public enum Task {

	WRITE_CODE, DRAW_LAYOUTS, TEST_PROGRAMS, SELL_SERVICES, MAKE_STATEMENTS;

	public static Task getRandomTask() {
		return values()[(int) (Math.random() * values().length)];
	}
}
