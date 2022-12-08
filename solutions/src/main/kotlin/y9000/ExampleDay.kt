package y9000

import io.github.jadarma.aoc.AdventDay
import kotlin.math.roundToInt

/**
 * A fictitious day to exemplify basic usage.
 *
 * Input: A list of comma separated numbers.
 * Part one: What is the sum of the numbers?
 * Part two: What is the rounded average of the numbers?
 */
class ExampleDay : AdventDay(9000, 1) {

    private fun parseInput(input: String): Sequence<Int> =
        input
            .splitToSequence(",", ", ")
            .map { it.toIntOrNull() ?: throw IllegalArgumentException() }

    override fun partOne(input: String) = parseInput(input).sum()
    override fun partTwo(input: String) = parseInput(input).average().roundToInt()
}
