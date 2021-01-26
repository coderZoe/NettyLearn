package com.coderzoe.nettydevelop.class8chatroom2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author: yhs
 * @date: 2021/1/14 20:23
 */
public class UserDataBase {
    private static List<User> users = new ArrayList<>();

    /**
     * 注册
     * @param user
     */
    public static void signUp(User user){
        UserDataBase.users.add(user);
    }

    /**
     * 返回对应用户名的User对象
     * @param name
     * @return
     */
    public static Optional<User> getUserByName(String name){
        return UserDataBase.users.stream().filter(user -> user.getName().equals(name)).findFirst();
    }

    public static Optional<User> getUserByNameAndPassword(String name,String password){
        return UserDataBase.users.stream().filter(user -> user.getName().equals(name)&&user.getPassword().equals(password)).findFirst();
    }
}
