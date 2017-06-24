package writemodel;

public class DeleteUserCommand implements Command {

    private Long id;

    public DeleteUserCommand(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
