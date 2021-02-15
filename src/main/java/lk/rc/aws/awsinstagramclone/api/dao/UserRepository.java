package lk.rc.aws.awsinstagramclone.api.dao;

import lk.rc.aws.awsinstagramclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    public User getUserByUsername(String username);

}
