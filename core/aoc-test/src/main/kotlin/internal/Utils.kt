package io.github.jadarma.aoc.test.internal

/**
 * If the given string is multiline, adds a new line before it, otherwise leaves it as is.
 * This makes reading multiline inputs or outputs in test reports easier.
 */
internal fun String.wrapWithNewLinesIfMultiline() = if (contains('\n')) "\n$this\n" else this
