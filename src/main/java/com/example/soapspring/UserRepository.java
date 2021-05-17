package com.example.soapspring;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;


@Service
public class UserRepository {
    private static final HashMap<String, String> users = new HashMap<>();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initData() {
        try{
            String sql = "SELECT * FROM users";
            List<User> allUsers = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(User.class));

            for (User user : allUsers) {
                System.out.println(user.getUsername());
                users.put(user.getUsername(), user.getPassword());
            }
        }catch (Exception e){
            System.out.println(e);
        }




//        //dummy data
//        User user = new User();
//        user.setUsername("admin");
//        user.setPassword("root");
//
//        users.put(user.getUsername(),user.getPassword());

    }


    public GetUserResponse findUser(GetUserRequest request) {
        GetUserResponse response = new GetUserResponse();

        if (users.containsKey(request.username)) {
            if (users.get(request.username).equals(request.password)) {
                User user = new User();
                user.setUsername(request.username);
                user.setPassword(request.password);
                response.setUser(user);
            }
        }
        return response;
    }

    public GetUserResponse registerUser(GetUserRequest request) {
        GetUserResponse response = new GetUserResponse();

        if (!users.containsKey(request.username)) {
            User user = new User();
            user.setUsername(request.username);
            user.setPassword(request.password);
            response.setUser(user);

            try {
                String sql = "INSERT INTO users VALUES (?,?)";
                int result = jdbcTemplate.update(sql, user.getUsername(), user.getPassword());
                if(result>0) System.out.println("Success");

            }catch (Exception e){
                System.out.println(e);
            }
        }



        return response;
    }

}
