package utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


/**
 * Logger utility to log the code flow
 * Below snippet includes log-levels of INFO and SEVERE
 * Can add more log-levels if needed
 */
public class loggerUtil extends Logger {
    private static final LogManager logManager = LogManager.getLogManager();
    protected loggerUtil(String name, String resourceBundleName) {
        super(name, resourceBundleName);
    }
    static {
        Logger logger = Logger.getLogger(loggerUtil.class.getName());
        InputStream inputStream = loggerUtil.class.getClassLoader().getResourceAsStream("logger.properties");
        try {
            logManager.readConfiguration(inputStream);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error in Loading Configuration", e);
        }
    }

    public void setLog(Logger loggerObj, String strMessage, String level) {
        switch (level) {
            case "Info":
                loggerObj.info(strMessage);
                break;
            case "Severe":
                loggerObj.severe(strMessage);
                break;
            default:
                loggerObj.info(strMessage);
                break;
        }
    }
}