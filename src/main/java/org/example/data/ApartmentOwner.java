package org.example.data;

public class ApartmentOwner {
    private final String name;
    private final String surname;
    private final String email;
    private final String buildingNumber;
    private final String apartmentNumber;
    private final float area;
    private final String address;

    public ApartmentOwner(String name, String surname, String email,
                          String buildingNumber, String apartmentNumber, float area,
                          String address) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
        this.area = area;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public float getArea() {
        return area;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "ownerName= " + name +
                ", surname= " + surname +
                ", ownerEmail= " + email +
                ", buildingNumber= " + buildingNumber +
                ", apartmentNumber= " + apartmentNumber +
                ", apartmentArea= " + area +
                ", buildingAddress= " + address;
    }
}
