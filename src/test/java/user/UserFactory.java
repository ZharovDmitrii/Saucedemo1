package user;

import utils.PropertyReader;

public class UserFactory {

    public static User withAdminPermission() {
        return new User(PropertyReader.getProperty("saucedemo1.user"),
                PropertyReader.getProperty("saucedemo1.password"));
    }

    public static User withUserPermission() {
        return new User(PropertyReader.getProperty("saucedemo1.locked.user"),
                PropertyReader.getProperty("saucedemo1.password"));
    }

    public static User withHRPermission() {
        return new User(PropertyReader.getProperty("hr-link.email.as.employeeHR"),
                PropertyReader.getProperty("hr-link.password.as.employeeHR"));
    }
}
