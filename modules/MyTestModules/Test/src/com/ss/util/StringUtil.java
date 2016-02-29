package com.ss.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import atg.commerce.pricing.PricingTools;
import atg.core.util.StringUtils;

/**
 * 
 * String uitl class
 * 
 * @author: orwen xiang
 * @version: 1.0, Jun 16, 2011
 */
public class StringUtil {
	private final static DecimalFormat FORMAT = new DecimalFormat("$#0.00");

	public static String getListAsCommaSeparatedStrng(List<String> list) {
		if (null == list || list.isEmpty()) {
			return "";
		}

		String valueAsString = "";
		for (String value : list) {
			valueAsString = valueAsString.equals("") ? value : valueAsString
					+ "," + value;
		}

		return valueAsString;
	}

	public static String convertNullToEmpty(String s) {
		if (null == s)
			return "";
		else
			return s;
	}

	/**
	 * Check if the string is empty
	 * 
	 * @param s
	 * @return
	 */
	public static boolean empty(String s) {
		return s == null || s.trim().length() < 1 || s.equals("null");
	}

	/**
	 * 
	 * 
	 * 
	 * @param s
	 * @return
	 */
	public static String upperCaseFirstLetter(String s) {
		if (empty(s)) {
			return s;
		}

		return s.trim().substring(0, 1).toUpperCase()
				+ s.trim().substring(1, s.trim().length());
	}

	/**
	 * 
	 * 
	 * 
	 * @param s
	 * @return
	 */
	public static String trim(String s) {
		if (s == null) {
			return null;
		} else {
			return s.trim();
		}
	}

	/**
	 * 
	 * 
	 * 
	 * @param s
	 * @return
	 */
	public static String trimAllSpace(String s) {
		if (s == null) {
			return null;
		} else {
			return s.replaceAll(" ", "");
		}
	}

	/**
	 * 
	 * get all number from a string
	 * 
	 * @param str
	 * @return
	 */
	public static String getALLDigit(String str) {

		if (empty(str)) {
			return null;
		}

		char[] chars = str.toCharArray();
		StringBuilder builder = new StringBuilder();

		for (char ch : chars) {
			if (Character.isDigit(ch)) {
				builder.append(ch);
			}
		}

		if (empty(builder.toString())) {
			return null;
		}

		return builder.toString();
	}

	/**
	 * 
	 * convert a String object to HashMap<String,String> object
	 * 
	 * @param mapStr
	 * @param regex
	 * @return
	 */

	public static HashMap<String, String> toMap(String mapStr, String regex) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		String[] arrayStr = mapStr.split(regex);

		for (int i = 0; i < arrayStr.length; i++) {
			String[] keyValueStr = arrayStr[i].split("=");
			if (keyValueStr.length > 1) {
				resultMap.put(keyValueStr[0], keyValueStr[1]);
			}
		}
		return resultMap;
	}

	/**
	 * 
	 * Replace spaces string with length >= 2 to single space
	 * 
	 * @param pSourceString
	 * @return
	 */
	public static String trimRedundantSpaces(String pSourceString) {
		String stringToTrim = trim(pSourceString);
		stringToTrim = stringToTrim.replaceAll("\\s{2,}", " ");
		return stringToTrim;
	}
}