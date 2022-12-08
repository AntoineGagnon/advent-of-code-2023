// Copyright 2021 Dan Cîmpianu
// Use of this source code is governed by the MIT License that can be found in the LICENSE.md file.
package io.github.jadarma.aoc.test

import io.kotest.core.Tag

/**
 * A tag that marks a spec or test as containing expensive computations.
 *
 * Useful for marking the test specs for some days (like brute force challenges) so they can be excluded conditionally
 * in bulk test executions.
 */
public object ExpensiveDay : Tag()
