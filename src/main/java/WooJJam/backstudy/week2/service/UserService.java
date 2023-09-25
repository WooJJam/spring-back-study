package WooJJam.backstudy.week2.service;

import WooJJam.backstudy.repository.UserRepository;
import WooJJam.backstudy.week2.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

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
        return this.userRepository.findByEmail(email) != null
                && this.userRepository.findByEmail(email).getEmail().equals(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
