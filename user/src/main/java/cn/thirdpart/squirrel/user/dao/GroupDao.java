package cn.thirdpart.squirrel.user.dao;

import cn.thirdpart.squirrel.user.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupDao extends JpaRepository<Group, String> {

}
