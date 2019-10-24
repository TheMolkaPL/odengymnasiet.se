/*
 * Copyright 2019 Aleksander Jagiełło <themolkapl@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package se.odengymnasiet.util;

/**
 * Date time utilities
 */
public final class DateTimeUtils {

    private DateTimeUtils() {
    }

    /**
     * Return given number in the "00" format. If the number is smaller than 10,
     * the number is prefixed with a zero, if not - the number is returned.
     */
    public static String numberToTime(int number) {
        return number <= 9 ? "0" + number : Integer.toString(number);
    }
}
