public class Vehicle {


  private   String licenseNumber;

  private   String type;

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public String getType() {
        return type;
    }

    public Vehicle(String licenseNumber, String type) {
        this.licenseNumber = licenseNumber;
        this.type = type;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "licenseNumber='" + licenseNumber + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
