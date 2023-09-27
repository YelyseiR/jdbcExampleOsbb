package org.example.data;

import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.data.Config.*;


public class OsbbCrud implements Closeable {
    private static final Logger logger = Logger.getLogger(OsbbCrud.class);
    private Connection connection = null;
    private static final String REQUEST = "SELECT om.name AS owner_name, " +
            "om.surname AS owner_surname, " +
            "om.email AS owner_email, " +
            "b.building_number AS building_number, " +
            "a.apartment_number AS apartment_number, " +
            "a.area AS apartment_area, " +
            "b.adress AS building_address " +
            "FROM ownership_rights orr " +
            "JOIN rc_residents r ON orr.member_id = r.id " +
            "JOIN osbb_members om ON r.member_id = om.id " +
            "JOIN apartments a ON orr.apartment_id = a.id " +
            "JOIN buildings b ON a.building_id = b.id " +
            "WHERE r.car_permission = 0 " +
            "AND (SELECT COUNT(*) FROM ownership_rights orr2 WHERE orr2.member_id = r.id) < 2";

    public OsbbCrud init() throws SQLException {
        logger.info("OsbbCrud has initialized");
        new org.example.data.FlyWayMigration().flywayMigration();
        connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        return this;
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            throw new IOException();
        }
    }

    public List<ApartmentOwner> getOwnersWithoutCarPermissionAndLessThanTwoApartments() {
        logger.trace("Call getting owners without car access and less than two apartments");
        List<ApartmentOwner> apartmentOwners = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(REQUEST)) {
            while (resultSet.next()) {
                ApartmentOwner apartmentOwnerInfo = new ApartmentOwner(
                        resultSet.getString("owner_name"),
                        resultSet.getString("owner_surname"),
                        resultSet.getString("owner_email"),
                        resultSet.getString("building_number"),
                        resultSet.getString("apartment_number"),
                        resultSet.getFloat("apartment_area"),
                        resultSet.getString("building_address")
                );
                apartmentOwners.add(apartmentOwnerInfo);
            }
        } catch (SQLException e) {
            logger.error("Error executing SQL query: " + e.getMessage());
        }
        return apartmentOwners;
    }

    public void saveResultToFile(List<ApartmentOwner> apartmentOwners, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (ApartmentOwner apartmentOwner : apartmentOwners) {
                String line = String.format(
                        "Ім'я: %s, Прізвище: %s, Email: %s, Номер будинку: %s, Номер квартири: %s, Площа квартири: %.2f, Адреса будинку: %s%n",
                        apartmentOwner.getName(),
                        apartmentOwner.getSurname(),
                        apartmentOwner.getEmail(),
                        apartmentOwner.getBuildingNumber(),
                        apartmentOwner.getApartmentNumber(),
                        apartmentOwner.getArea(),
                        apartmentOwner.getAddress()
                );
                writer.write(line);
            }
            logger.info("Results are successful added to file  " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}