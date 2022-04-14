package fr.vertours.poseidon.utils;

import fr.vertours.poseidon.domain.User;
import fr.vertours.poseidon.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class FakeUsers {

    public static User getEntity1() {
        return new User(
                "1@",
                "Pass1",
                "1",
                "USER");
    }
    public static User getUser2() {
        return new User(
                "2@",
                "Pass2",
                "2",
                "ADMIN");
    }
    public static List<User> getAll() {
        List<User> users = new ArrayList<>();
        users.add(getEntity1());
        users.add(getUser2());

        return users;
    }
    public static UserDTO getDTO1() {
        UserDTO dto = new UserDTO();
        dto.setUsername("1@");
        dto.setPassword("Pass1");
        dto.setFullname("1");
        dto.setRole("USER");
        return dto;
    }

}
