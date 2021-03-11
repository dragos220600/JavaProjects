package Controller;

import dao.UserDao;
import model.User;

import java.util.Optional;

public class UserController {
    private static final class Singleton {
        public static final UserController INSTANCE = new UserController();
    }

    private final UserDao userDao;

    private UserController() {
        userDao = new UserDao(ConnectionManager.getInstance().getConnection());
    }

    public static UserController getInstance(){
        return Singleton.INSTANCE;
    }

    public boolean create(User user) {
        Optional<User> userOptional = userDao.findBy(user.getUsername());
        Optional<User> userOptional1 = userDao.findByEmail(user.getEmail());

        if (userOptional.isEmpty() && userOptional1.isEmpty()) {
            return userDao.create(user);
        }

        return false;
    }

    public Optional<User> findUsername(String username) {
        return userDao.findBy(username);
    }

    public Optional<User> findMail(String email) {
        return userDao.findByEmail(email);
    }

    public Optional<User> findId(int id) {
        return userDao.findById(id);
    }

    public boolean updateUser(String old, String nou) {
        return userDao.updateUser(old, nou);
    }

    public boolean updateMail(String old, String nou) {
        return userDao.updateMail(old, nou);
    }

    public boolean updatePassw(String old, String nou) {
        return userDao.updatePassw(old, nou);
    }
}
