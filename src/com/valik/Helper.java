package com.valik;

import java.util.Random;

public class Helper {

	public static int random(int min, int max) {

		return new Random().nextInt((max - min) + 1) + min;
	}
}
