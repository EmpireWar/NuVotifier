package com.vexsoftware.votifier.sponge;

import com.vexsoftware.votifier.platform.scheduler.ScheduledVotifierTask;
import com.vexsoftware.votifier.platform.scheduler.VotifierScheduler;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.ScheduledTask;
import org.spongepowered.api.scheduler.Task;

import java.util.concurrent.TimeUnit;

class SpongeScheduler implements VotifierScheduler {
    private final NuVotifier plugin;

    SpongeScheduler(NuVotifier plugin) {
        this.plugin = plugin;
    }

    private Task.Builder taskBuilder(Runnable runnable) {
        return Task.builder()
                .plugin(plugin.getPluginContainer())
                .execute(runnable);
    }

    @Override
    public ScheduledVotifierTask delayedOnPool(Runnable runnable, int delay, TimeUnit unit) {
        return new TaskWrapper(Sponge.asyncScheduler().submit(taskBuilder(runnable).delay(delay, unit).build()));
    }

    @Override
    public ScheduledVotifierTask repeatOnPool(Runnable runnable, int delay, int repeat, TimeUnit unit) {
        return new TaskWrapper(Sponge.asyncScheduler().submit(taskBuilder(runnable).delay(delay, unit).interval(repeat, unit).build()));
    }

    private static class TaskWrapper implements ScheduledVotifierTask {
        private final ScheduledTask task;

        private TaskWrapper(ScheduledTask task) {
            this.task = task;
        }

        @Override
        public void cancel() {
            task.cancel();
        }
    }
}
