// Copyright 2021 Dan Cîmpianu
// Use of this source code is governed by the MIT License that can be found in the LICENSE.md file.
package io.github.jadarma.aoc.test.internal

import io.github.jadarma.aoc.AdventDay
import io.github.jadarma.aoc.AdventDayID
import io.github.jadarma.aoc.test.internal.AdventDayPart.*

/** Data holder for the test input of a single [AdventDayID]. */
internal data class TestInput(
    val input: String? = null,
    val solutionPartOne: Solution? = null,
    val solutionPartTwo: Solution? = null,
)

/** The user provided the answer to a day's part, that has been verified (by them) to being correct. */
@JvmInline
internal value class Solution(private val answer: String) {
    override fun toString() = answer

    companion object {
        /** In the case of some days, there is no part two requirement. */
        val NO_SOLUTION = Solution("NoSolution")
    }
}

/** Selector for a specific part of an [AdventDay] problem. */
internal enum class AdventDayPart { One, Two }

/** Convenience function to return the expected output for a given [part] of the [TestInput]. */
internal fun TestInput.solutionToPart(part: AdventDayPart): Solution? = when (part) {
    One -> solutionPartOne
    Two -> solutionPartTwo
}
