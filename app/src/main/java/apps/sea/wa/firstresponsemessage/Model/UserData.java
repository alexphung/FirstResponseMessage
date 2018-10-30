package apps.sea.wa.firstresponsemessage.Model;

public class UserData {
    public String firstName = "Unknown";
    public String lastName = "Unknown";
    public String middleName = "Unknown";
    public String deviceUser = "Unknown";
    public String deviceName = "Unknown";
    public String deviceModel = "Unknown";
    public String deviceVersion = "Unknown";
    public String deviceLocation = "";

    public UserData() {
    }

    public UserData(String deviceUser, String deviceName, String deviceModel, String deviceVersion, String deviceLocation) {
        this.deviceUser = deviceUser;
        this.deviceName = deviceName;
        this.deviceModel = deviceModel;
        this.deviceVersion = deviceVersion;
        this.deviceLocation = deviceLocation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getDeviceUser() { return this.deviceUser; }

    public void setDeviceUser(String deviceUser) { this.deviceUser = deviceUser; }

    public String getDeviceName() { return this.deviceName; }

    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }

    public String getDeviceModel() { return this.deviceModel; }

    public void setDeviceModel(String deviceModel) { this.deviceModel = deviceModel; }

    public String getDeviceVersion() { return this.deviceVersion; }

    public void setDeviceVersion(String deviceVersion) { this.deviceVersion = deviceVersion; }

    public String getDeviceLocation() { return this.deviceLocation; }

    public void setDeviceLocation(String deviceLocation) { this.deviceLocation = deviceLocation; }

    @Override
    public String toString() {
        return "UserData{" +
                "deviceUser='" + deviceUser + "\'" +
                ", deviceName='" + deviceName + "\'" +
                ", deviceModel='" + deviceModel + "\'" +
                ", deviceVersion='" + deviceVersion + "\'" +
                ", deviceLocation='" + deviceLocation + "\'" +
                '}';
    }
}
