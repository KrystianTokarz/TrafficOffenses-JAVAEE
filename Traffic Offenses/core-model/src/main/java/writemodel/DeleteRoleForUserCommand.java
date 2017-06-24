package writemodel;

public class DeleteRoleForUserCommand implements Command {

    String pesel;
    String roleName;

    public DeleteRoleForUserCommand(String pesel, String roleName) {
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
