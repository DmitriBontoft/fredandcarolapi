package com.fredandcarol.fredandcarolapi.auth;

<<<<<<< HEAD
import com.fredandcarol.fredandcarolapi.auth.jwt.UserAuthenticationService;
import com.fredandcarol.fredandcarolapi.user.User;
import java.util.Optional;

<<<<<<<< HEAD:src/main/java/com/fredandcarol/fredandcarolapi/auth/SimpleAuthenticationService.java
@Service
public class SimpleAuthenticationService implements UserAuthenticationService {
========
public interface UserAuthenticationService {
>>>>>>>> master:src/main/java/com/fredandcarol/fredandcarolapi/auth/UserAuthenticationService.java

  Optional<String> login(String username, String password);

<<<<<<<< HEAD:src/main/java/com/fredandcarol/fredandcarolapi/auth/SimpleAuthenticationService.java
=======
import com.fredandcarol.fredandcarolapi.user.User;
import com.fredandcarol.fredandcarolapi.user.Users;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class SimpleAuthenticationService implements UserAuthenticationService {

  private Users users;

>>>>>>> master
  public SimpleAuthenticationService(Users users) {
    this.users = users;
  }

<<<<<<< HEAD
=======
  @Override
>>>>>>> master
  public Optional<String> login(String username, String password) {
    final String uuid = UUID.randomUUID().toString();
    users.save(new User(uuid, username, password));
    return Optional.of(uuid);
  }

  @Override
  public void logout(User user) {

  }

<<<<<<< HEAD

=======
>>>>>>> master
  @Override
  public Optional<User> findByToken(final String token) {
    return users.find(token);
  }
<<<<<<< HEAD
========
  void logout(User user);

  Optional<User> findByToken(String token);
>>>>>>>> master:src/main/java/com/fredandcarol/fredandcarolapi/auth/UserAuthenticationService.java
=======
>>>>>>> master
}
