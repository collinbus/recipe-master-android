package be.collin.recipemaster.recipes.overview

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.listeners.Listener
import io.kotest.core.listeners.TestListener
import io.kotest.core.spec.Spec
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.rules.TestRule

object ProjectConfig : AbstractProjectConfig() {
    @ExperimentalCoroutinesApi
    override fun listeners(): List<Listener> = listOf(
        InstantTaskExecutorListener,
        CoroutineListener
    )
}

object InstantTaskExecutorListener : TestListener {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    override suspend fun beforeSpec(spec: Spec) {
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) = runnable.run()

            override fun postToMainThread(runnable: Runnable) = runnable.run()

            override fun isMainThread(): Boolean = true
        })
    }

    override suspend fun afterSpec(spec: Spec) {
        ArchTaskExecutor.getInstance().setDelegate(null)
    }
}

@ExperimentalCoroutinesApi
object CoroutineListener : TestListener {
    private val testDispatcher = TestCoroutineDispatcher()

    override suspend fun beforeSpec(spec: Spec) {
        Dispatchers.setMain(testDispatcher)
    }

    override suspend fun afterSpec(spec: Spec) {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}