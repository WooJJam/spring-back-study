package WooJJam.backstudy.week2.Service;

import WooJJam.backstudy.week2.Repository.UserRepository;
import WooJJam.backstudy.week2.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Boolean userIsEquals(String email, String password) {
        User findUser = this.userRepository.findByEmail(email);
        if(findUser !=null && findUser.getPassword().equals(password)) {
            return true;
        }else {
            return false;
        }
    }
    public Boolean findByEmailIsEquals(String email) {
        if (this.userRepository.findByEmail(email) != null && this.userRepository.findByEmail(email).equals(email)) {
            return true;
        }else{
            return false;
        }
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
