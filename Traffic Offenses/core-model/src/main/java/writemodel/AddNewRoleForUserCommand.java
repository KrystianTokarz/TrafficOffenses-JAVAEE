package writemodel;

public class AddNewRoleForUserCommand implements Command {

    String pesel;
    String roleName;

    public AddNewRoleForUserCommand(String pesel, String roleName) {
        this.pesel = pesel;
        this.roleName = roleName;
    }

    public String getPesel() {
        return pesel;
    }

    public String getRoleName() {
        return roleName;
    }
}
