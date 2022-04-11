package fr.vertours.poseidon.service;

import fr.vertours.poseidon.domain.CurvePoint;
import fr.vertours.poseidon.domain.User;
import fr.vertours.poseidon.dto.CurvePointDTO;
import fr.vertours.poseidon.dto.UserDTO;

import java.util.List;

public interface IUserService {

    List<User> findAll();

    void save(UserDTO dto);

    User findId(Integer id);

    void updateId(Integer id, UserDTO dto);

    void deleteId(Integer id);
}
