package genericpubsub;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utility.CommonContext;
import utility.ExampleConfigurations;

@WebListener
public class SubscribeEvent implements ServletContextListener {

	protected static final Logger logger = LoggerFactory.getLogger(SubscribeEvent.class.getClass());

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("Start to subscribe the events...");

		ExampleConfigurations exampleConfigurations = null;
		try {
			exampleConfigurations = new ExampleConfigurations("arguments.yaml");
		} catch (IOException e1) {
			CommonContext.printStatusRuntimeException("Error during to read the configuration file", e1);
		}

		// Using the try-with-resource statement. The CommonContext class implements AutoCloseable in
		// order to close the resources used.
		if(exampleConfigurations != null) {
			try (Subscribe subscribe = new Subscribe(exampleConfigurations)) {
				subscribe.startSubscription();
			} catch (Exception e) {
				CommonContext.printStatusRuntimeException("Error during Subscribe", e);
			}
		}
	}
}