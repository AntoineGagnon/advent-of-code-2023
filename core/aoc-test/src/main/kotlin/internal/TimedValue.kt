// Copyright 2021 Dan Cîmpianu
// Use of this source code is governed by the MIT License that can be found in the LICENSE.md file.
package io.github.jadarma.aoc.test.internal

import kotlin.system.measureNanoTime
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

/** A clone of [kotlin.time.TimedValue], that is not experimental. */
internal data class TimedValue<T>(val value: T, val duration: Duration)

/**
 * A clone of [kotlin.time.measureTimedValue], with a different implementation that does not require opting in from
 * library consumers.
 * The caveat being we cannot return nullable types, which is fine for our particular use case.
 */
internal inline fun <T : Any> measureTimedValue(block: () -> T): TimedValue<T> {
    lateinit var output: T
    val nanos = measureNanoTime { output = block() }
    return TimedValue(output, nanos.toDuration(DurationUnit.NANOSECONDS))
}
