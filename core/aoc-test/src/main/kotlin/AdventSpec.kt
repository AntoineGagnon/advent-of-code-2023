// Copyright 2021 Dan Cîmpianu
// Use of this source code is governed by the MIT License that can be found in the LICENSE.md file.
package io.github.jadarma.aoc.test

import io.github.jadarma.aoc.AdventDay
import io.github.jadarma.aoc.ExpensiveAdventDay
import io.github.jadarma.aoc.test.internal.AdventDayPart
import io.github.jadarma.aoc.test.internal.AdventDayPart.One
import io.github.jadarma.aoc.test.internal.AdventDayPart.Two
import io.github.jadarma.aoc.test.internal.Solution
import io.github.jadarma.aoc.test.internal.TestInput
import io.github.jadarma.aoc.test.internal.TestInputResourceReader
import io.github.jadarma.aoc.test.internal.measureTimedValue
import io.github.jadarma.aoc.test.internal.wrapWithNewLinesIfMultiline
import io.github.jadarma.aoc.test.internal.solutionToPart
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.withClue
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

/**
 * A specialized [FunSpec] with extra DSL for simplifying the testing of [AdventDay] implementations.
 * Normal [FunSpec] DSL can be used on top of it to test helper classes relevant to the same day, if necessary.
 *
 * @property day The implementation for the [AdventDay] to test.
 * @param body The spec configuration block, for where you write the tests.
 */
@Suppress("MemberVisibilityCanBePrivate")
public abstract class AdventSpec(public val day: AdventDay, body: AdventSpec.() -> Unit = {}) : FunSpec() {

    private val testData: TestInput by lazy { TestInputResourceReader.inputFor(day.id) }

    /** Allow expensive days to run up to an hour, otherwise use the project default. */
    override fun invocationTimeout(): Long? = if (day is ExpensiveAdventDay) 3600_000 else null

    /** The advent days and test inputs should be stateless, there is no need for isolated tests. */
    override fun isolationMode(): IsolationMode = IsolationMode.SingleInstance

    init {
        if (day is ExpensiveAdventDay) tags(ExpensiveDay)
        apply(body)
    }

    internal fun partTest(part: AdventDayPart, test: suspend AdventPartTestContext.() -> Unit = noExampleTests) {
        context("Part $part") {
            val partFunction = when (part) {
                One -> day::partOne
                Two -> day::partTwo
            }
            test("Outputs a solution").config(enabled = testData.input != null) {
                val (input, solution) = with(testData) {
                    checkNotNull(input) { "Impossible state, test should be disabled on null input." }
                    input to solutionToPart(part)
                }

                val (answer, duration) = shouldNotThrowAny {
                    measureTimedValue { Solution(partFunction(input).toString()) }
                }

                val correctSolutionIsUnknown = solution == null
                val unverifiedSuffix = if (correctSolutionIsUnknown) " (Unverified)" else ""

                println("Your$unverifiedSuffix answer was: ${answer.toString().wrapWithNewLinesIfMultiline()}")
                println("Your time was: $duration")

                if (part == One) {
                    withClue("Part one of any AdventDay is always defined! NoSolution cannot be the answer.") {
                        answer shouldNotBe Solution.NO_SOLUTION
                        solution shouldNotBe Solution.NO_SOLUTION
                    }
                }

                if (!correctSolutionIsUnknown) {
                    withClue("Correct solution was: ${solution?.toString()?.wrapWithNewLinesIfMultiline()}") {
                        answer shouldBe solution
                    }
                }
            }
            if (test != noExampleTests) {
                test("Validates the examples") {
                    AdventPartTestContext(partFunction).test()
                }
            }
        }
    }

    internal fun xpartTest(part: AdventDayPart) {
        xcontext("Part $part") {}
    }

    /**
     * Provides a context to test the implementation of an [AdventDay.partOne] function.
     * Should only be called once per [AdventSpec].
     *
     * Will create a context with two tests:
     * - Verifies the output, given the input file has been added to the test resources.
     *   If the solution is known as well, also validates the answer matches it.
     * - Verifies the given examples in an [AdventPartTestContext], useful for a TDD approach when implementing the solution
     *   for the first time.
     */
    public fun partOne(test: suspend AdventPartTestContext.() -> Unit = noExampleTests): Unit = partTest(One, test)

    /** Adds a disabled equivalent of a [partOne] context. */
    @Suppress("UNUSED_PARAMETER")
    public fun xpartOne(test: suspend AdventPartTestContext.() -> Unit): Unit = xpartTest(One)

    /**
     * Provides a context to test the implementation of an [AdventDay.partTwo] function.
     * Should only be called once per [AdventSpec].
     *
     * Will create a context with two tests:
     * - Verifies the output, given the input file has been added to the test resources, or disables the test.
     *   If the solution is known as well, also validates the answer matches it.
     * - Verifies the given examples in an [AdventPartTestContext], useful for a TDD approach when implementing the solution
     *   for the first time.
     */
    public fun partTwo(test: suspend AdventPartTestContext.() -> Unit = noExampleTests): Unit = partTest(Two, test)

    /** Adds a disabled equivalent of a [partTwo] context. */
    @Suppress("UNUSED_PARAMETER")
    public fun xpartTwo(test: suspend AdventPartTestContext.() -> Unit): Unit = xpartTest(Two)

    private companion object {
        val noExampleTests: suspend AdventPartTestContext.() -> Unit = {}
    }
}
