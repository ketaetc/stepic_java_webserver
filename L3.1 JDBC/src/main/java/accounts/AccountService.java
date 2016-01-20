package accounts;

import dbService.DBException;
import dbService.DBService;
import dbService.dataSets.UsersDataSet;

import java.sql.SQLException;

/**
 * edited by ketaetc
 */
public class AccountService {

    private DBService dbService = new DBService();

    public void addNewUser(UserProfile userProfile) throws DBException {
        dbService.addUser(userProfile.getLogin(), userProfile.getPass());
    }

    public UserProfile getUserByName(String login) throws DBException, SQLException {
        UsersDataSet usersData = dbService.getUserByName(login);
        return new UserProfile(usersData.getName());
    }

}
