package restaurant;

import java.time.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledService 
{
	protected ScheduledExecutorService executor; 
	
	public ScheduledService()
	{
		executor = Executors.newSingleThreadScheduledExecutor();
	}
	public ScheduledFuture<?> schedule(LocalDateTime scheduleTime, Runnable task)
	{
		long delay = Duration.between(LocalDateTime.now(), scheduleTime).toSeconds();
		return executor.schedule(task, delay, TimeUnit.SECONDS);
	}
}
