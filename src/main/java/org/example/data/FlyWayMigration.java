package org.example.data;

import org.apache.log4j.Logger;
import org.flywaydb.core.Flyway;

import static org.example.data.Config.*;
//import static org.flywaydb.core.internal.configuration.ConfigUtils.PASSWORD;

public class FlyWayMigration {
    private static final Logger logger = Logger.getLogger(FlyWayMigration.class);

    public FlyWayMigration() {
    }

    public void flywayMigration() {
        logger.debug("Flyway migration execute");

        Flyway
                .configure()
                .dataSource(JDBC_URL, USERNAME, PASSWORD)
                .locations("classpath:flyway/scripts")
                .baselineOnMigrate(true)
                .load().migrate();
    }
}
