package org.example;

import org.apache.log4j.Logger;
import org.example.data.OsbbCrud;
import org.example.data.ApartmentOwner;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class App {
    private static final Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("The program has started");

        try (OsbbCrud osbbCrud = new OsbbCrud()
                .init()) {
            List<ApartmentOwner> apartmentOwners = osbbCrud.getOwnersWithoutCarPermissionAndLessThanTwoApartments();
            for (ApartmentOwner apartmentOwner : apartmentOwners) {
                logger.info(apartmentOwner);
            }
            osbbCrud.saveResultToFile(apartmentOwners, "result.txt");
        } catch (IOException | SQLException e) {
            logger.fatal(e);
        }
        logger.info("The program has finished");
    }
}