package writemodel;

public class ActiveUserCommand implements Command {

    private Long id;

    public ActiveUserCommand(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
