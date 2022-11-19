package Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logging {

private static final Logger logger = LoggerFactory.getLogger(Logging.class);
	
	public static Logger getLogger() {
		return logger;
	}

}