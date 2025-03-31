package Server.Schema;


public class Vehicle
{
    //license number for uniquely identify each vehicle
    private String licenseNumber;

    //Type of vehicle
    private final String type;

    public Vehicle(String licenseNumber, String type)
    {
        this.licenseNumber = licenseNumber;

        this.type = type;
    }

    public String getLicenseNumber()
    {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber)
    {
        this.licenseNumber = licenseNumber;
    }

    public String getType()
    {
        return type;
    }

    @Override
    public String toString()
    {
        return "Vehicle{" +
                "licenseNumber='" + licenseNumber + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
