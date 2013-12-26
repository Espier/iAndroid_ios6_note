package org.espier.note6.util;

import org.espier.note.R;

public class ColorsUtils {
	public static int COLOR_BROWN = 0;
	public static int COLOR_GREEN = 1;
	public static int COLOR_BLUE = 2;
	public static int COLOR_BLACK = 3;
	public static int COLOR_PURPLE = 4;
	public static int COLOR_RED = 5;
	public static int COLOR_ORANGE = 6;

	public static int getColor(int color) {
		int noteColor = 0;
		if (color == COLOR_BROWN) {
			noteColor = R.color.text_brown;
		} else if (color == COLOR_GREEN) {
			noteColor = R.color.text_green;
		} else if (color == COLOR_BLUE) {
			noteColor = R.color.text_blue;
		} else if (color == COLOR_BLACK) {
			noteColor = R.color.text_black;
		} else if (color == COLOR_PURPLE) {
			noteColor = R.color.text_purple;
		} else if (color == COLOR_RED) {
			noteColor = R.color.text_red;
		} else if (color == COLOR_ORANGE) {
			noteColor = R.color.text_orange;
		}
		return noteColor;
	}
}
