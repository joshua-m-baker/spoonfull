package io.kotest.provided

import com.joshua_m_baker.TestContainerListener
import io.kotest.core.config.AbstractProjectConfig
import io.micronaut.test.extensions.kotest5.MicronautKotest5Extension

object ProjectConfig : AbstractProjectConfig() {
    override fun extensions() = listOf(MicronautKotest5Extension)

    @Deprecated("Use extensions. This will be removed in 6.0")
    override fun listeners() = listOf(TestContainerListener)
}
