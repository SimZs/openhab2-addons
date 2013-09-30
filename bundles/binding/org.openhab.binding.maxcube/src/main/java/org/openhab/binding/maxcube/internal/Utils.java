/**
 * openHAB, the open Home Automation Bus.
 * Copyright (C) 2010-2013, openHAB.org <admin@openhab.org>
 *
 * See the contributors.txt file in the distribution for a
 * full listing of individual contributors.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 *
 * Additional permission under GNU GPL version 3 section 7
 *
 * If you modify this Program, or any covered work, by linking or
 * combining it with Eclipse (or a modified version of that library),
 * containing parts covered by the terms of the Eclipse Public License
 * (EPL), the licensors of this Program grant you additional permission
 * to convey the resulting work.
 */
package org.openhab.binding.maxcube.internal;

import java.util.Calendar;
import java.util.Date;

/**
* Utility class for common tasks within the MAX!Cube binding package. 
* 
* @author Andreas Heil (info@aheil.de)
* @since 1.4.0
*/
public final class Utils {
	
	/**
	 * Returns the integer value of an  hexadecimal number (base 16).
	 * 
	 * @param hex
	 * 			the hex value to be parsed into an integer 
	 * @return the given hex value as integer
	 */
	public static final int fromHex(String hex) {
		return Integer.parseInt(hex, 16);
	}
	
	/**
	 * Returns the hexadecimal number of a number of integer values.
	 * 
	 * @param values
	 * 			the integer values to be converted into hexadecimal numbers
	 * @return the given numbers as hexadecimal number
	 */
	public static final String toHex(int... values) {
		String returnValue = "";
		for (int v : values) {
			returnValue += v < 16 ? "0" + Integer.toHexString(v).toUpperCase() : Integer.toHexString(v).toUpperCase();
		}	
		return returnValue;
	}

	/**
	 * Converts an Java signed byte into its general (unsigned) value as being used in other programming languages and platforms.
	 * 
	 * @param b
	 * 			the byte to be converted into its integer value
	 * @return the integer value represented by the given byte
	 */
	public static final int fromByte(byte b) {
		return b & 0xFF;
	}
	
	/**
	 * Resolves the date and time based based on a three byte encoded within a MAX!Cube L message.
	 *
	 * Date decoding (two byte)
	 * <pre>
	 * Hex     Binary
	 * 9D0B    1001110100001011
     *   MMMDDDDDM YYYYYY
     *   100     0        = 1000b  = 8d  = month
     *      11101         = 11101b = 29d = day
     *             001011 = 1011b  = 11d = year-2000  
	 * </pre>
	 * 
	 * Time decoding (one byte)
	 * <pre>
	 * Hex     Decimal
	 * 1F      31 * 0.5 hours = 15:30
	 * </pre>
	 * 
	 * @param date
	 * 			the date to be converted based on two bytes
	 * @param time
	 * 			the time to be converted based on a single byte
	 * @return the date time based on the values provided
	 */
	public static Date resolveDateTime(int date, int time) {
		
		int month = ((date & 0xE000) >> 12)+((date & 0x80) >> 7);
		int day = (date & 0x1F00) >> 8;
		int year = (date & 0x0F) + 2000;
	
		int hours = (int)(time * 0.5);
		int minutes = (int)(60 * ((time * 0.5)-hours));
		
		return new Date(year, month, day, hours, minutes);
	}
}