package fr.vertours.poseidon.service;

import fr.vertours.poseidon.domain.User;
import fr.vertours.poseidon.dto.UserDTO;
import fr.vertours.poseidon.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements IUserService{

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @PostConstruct
    private void initAdminUser() {
        User admin = new User(
                "Admin@Poseidon.com",
                encoder.encode("Password"),
                "Super Admin",
                "ADMIN");
        if (repository.findByUsername(admin.getUsername()) == null) {
            repository.save(admin);
        }
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void save(UserDTO dto) {
        User user = new User(
                dto.getUsername(),
                encoder.encode(dto.getPassword()),
                dto.getFullname(),
                dto.getRole()
        );
        repository.save(user);
        log.debug("Entity saved in DB : "+ user);
    }

    @Override
    public User findId(Integer id) {
       return repository.findById(id).orElseThrow(
               () -> new IllegalArgumentException("Invalid user Id:" + id));
    }

    @Override
    @Transactional
    public void updateId(Integer id, UserDTO dto) {
        User user = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid user Id:" + id));

        user.setUsername(dto.getUsername());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setFullname(dto.getFullname());
        user.setRole(dto.getRole());

        repository.save(user);
        log.debug("Entity update in DB : "+ user);
    }

    @Override
    public void deleteId(Integer id) {
        repository.deleteById(id);
        log.debug("Entity deleted in DB");
    }
}
