// Copyright 2021 Dan Cîmpianu
// Use of this source code is governed by the MIT License that can be found in the LICENSE.md file.
package io.github.jadarma.aoc

/** The abstract base of an Advent of Code daily challenge. */
public abstract class AdventDay(public val id: AdventDayID) {

    public constructor(year: Int, day: Int): this(AdventDayID(year, day))

    /** Given an [input], calculates the solution to the first part of the problem. */
    public open fun partOne(input: String): Any =
        throw NotImplementedError("Part one of $id was not implemented.")

    /** Given an [input], calculates the solution to the second part of the problem. */
    public open fun partTwo(input: String): Any =
        throw NotImplementedError("Part two of $id was not implemented.")
}
