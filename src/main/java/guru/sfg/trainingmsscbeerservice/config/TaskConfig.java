package guru.sfg.trainingmsscbeerservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@Configuration
public class TaskConfig {

	TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}
}
