// Copyright 2021 Dan Cîmpianu
// Use of this source code is governed by the MIT License that can be found in the LICENSE.md file.
package io.github.jadarma.aoc

/**
 * Identifies an Advent of Code problem.
 *
 * @property year The year this problem appeared in.
 * @property day The day associated with this problem.
 */
public data class AdventDayID(val year: Int, val day: Int) : Comparable<AdventDayID> {

    init {
        require(year >= 2015) { "Invalid year." }
        require(day in 1..25) { "Invalid day." }
    }

    override fun compareTo(other: AdventDayID): Int = compareValuesBy(this, other) { year * 100 + day }

    override fun toString(): String = "Y${year}D${day.toString().padStart(2, '0')}"
}
