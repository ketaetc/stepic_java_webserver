package accounts;

/**
 * edited by ketaetc
 */
public class UserProfile {
    private final String login;
    private final String pass;
    private final String email;


    public UserProfile(String login) {
        this.login = login;
        this.pass = login;
        this.email = login;
    }

    public String getLogin() {
        System.out.println("login: " + login);
        return login;
    }

    public String getPass() {
        System.out.println("pass: " + pass);
        return pass;
    }

    public String getEmail() {
        return email;
    }
}
