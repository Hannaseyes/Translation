package com.we.util;

public class FormatUtil {
	/**
	 * 把字符串用*遮蔽
	 * @param str
	 * @return
	 */
	public static String formatAll(String str) {
		if (str == null || "".equals(str)) {
			return "";
		}

		String all = "";
		for (int j = 0; j < str.length(); j++) {
			all += "*";
		}

		return all;
	}

	/**
	 * 把字符串用*遮蔽， 保留前f后b
	 * @param str
	 * @param f
	 * @param b
	 * @return
	 */
	public static String formatMiddle(String str, int f, int b) {
		if (str == null || "".equals(str)) {
			return "";
		}

		int l = str.length();
		if (l < f || l - b <= f) {
			return str;
		}

		String begin = str.substring(0, f);
		String middle = "";
		for (int j = 0; j < l - f - b; j++) {
			middle += "*";
		}

		String end = str.substring(l - b);
		return begin + middle + end;
	}

	/**
	 * 把字符串用*遮蔽， 遮蔽前f
	 * @param str
	 * @param f
	 * @return
	 */
	public static String formatFront(String str, int f) {
		if (str == null || "".equals(str)) {
			return "";
		}

		int l = str.length();
		if (l <= f) {
			return formatAll(str);
		}

		String begin = "";
		for (int j = 0; j < f; j++) {
			begin += "*";
		}

		String end = str.substring(f);
		return begin + end;
	}

	/**
	 * 把字符串用*遮蔽， 保留前f
	 * @param str
	 * @param f
	 * @return
	 */
	public static String formatRtFront(String str, int f) {
		if (str == null || "".equals(str)) {
			return "";
		}

		int l = str.length();
		if (l <= f) {
			return str;
		}

		String begin = str.substring(0, f);

		String end = "";
		for (int j = f; j < l; j++) {
			end += "*";
		}

		return begin + end;
	}

	/**
	 * 把字符串用*遮蔽，遮蔽后b位
	 * @param str
	 * @param b
	 * @return
	 */
	public static String formatBehind(String str, int b) {
		if (str == null || "".equals(str)) {
			return "";
		}

		int l = str.length();
		if (l <= b) {
			return formatAll(str);
		}

		String begin = str.substring(0, l - b);

		String end = "";
		for (int j = 0; j < b; j++) {
			begin += "*";
		}

		return begin + end;
	}

	/**
	 * 把字符串用*遮蔽， 保留后b
	 * @param str
	 * @param b
	 * @return
	 */
	public static String formatRtBehind(String str, int b) {
		if (str == null || "".equals(str)) {
			return "";
		}

		int l = str.length();
		if (l <= b) {
			return str;
		}

		String begin = "";
		for (int j = 0; j < l - b; j++) {
			begin += "*";
		}

		String end = str.substring(l - b);

		return begin + end;
	}

	public static void main(String[] args) {
		String a = "15112345678";
		System.out.println(formatMiddle(a, 3, 4));
		System.out.println(formatFront(a, 3));
		System.out.println(formatRtFront(a, 3));
		System.out.println(formatBehind(a, 4));
		System.out.println(formatRtBehind(a, 4));

	}
}