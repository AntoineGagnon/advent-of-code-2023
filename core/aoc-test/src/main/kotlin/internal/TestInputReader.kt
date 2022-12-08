// Copyright 2021 Dan Cîmpianu
// Use of this source code is governed by the MIT License that can be found in the LICENSE.md file.
package io.github.jadarma.aoc.test.internal

import io.github.jadarma.aoc.AdventDayID

/** Provides a way to retrieve the [TestInput] for an [AdventDayID]s. */
internal fun interface TestInputReader {

    /** Retrieves the [TestInput] for the given [adventDayID], or throws if it could not be resolved. */
    fun inputFor(adventDayID: AdventDayID): TestInput
}

/**
 * Reads [TestInput]s from classpath resources.
 *
 * Looks into the `/aoc` directory, which it expects to be split by year, then by day, then by type.
 *
 * For example, the following is the valid directory tree for `Y2015D01`:
 *
 * ```
 * resources
 *  └─ aoc
 *    └─ y2015
 *      └─ d01
 *        ├─ input.txt
 *        ├─ solution_part1.txt
 *        └─ solution_part2.txt
 * ```
 */
internal object TestInputResourceReader : TestInputReader {

    override fun inputFor(adventDayID: AdventDayID): TestInput {
        val dayPath = with(adventDayID) { "/aoc/y$year/d${day.toString().padStart(2, '0')}" }
        return TestInput(
            input = readResourceAsText("$dayPath/input.txt"),
            solutionPartOne = readResourceAsText("$dayPath/solution_part1.txt").toSolution(),
            solutionPartTwo = readResourceAsText("$dayPath/solution_part2.txt").toSolution(),
        )
    }

    private fun readResourceAsText(path: String): String? =
        this::class.java
            .getResourceAsStream(path)
            ?.run { String(readAllBytes()).trimEnd() }

    private fun String?.toSolution(): Solution? = when (this) {
        null -> null
        else -> Solution(this)
    }
}
