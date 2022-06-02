import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.BasicThreadTest;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        logger.info("Main start");

        BasicThreadTest basicThreadTest = new BasicThreadTest();
        basicThreadTest.conflictRun();
        basicThreadTest.nonConflictRun();

        logger.info("Main end");
    }
}
