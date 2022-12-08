import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.spec.SpecExecutionOrder

object TestConfig : AbstractProjectConfig() {
    override val specExecutionOrder = SpecExecutionOrder.Lexicographic
}
