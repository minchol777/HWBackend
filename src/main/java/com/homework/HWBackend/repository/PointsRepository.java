package com.homework.HWBackend.repository;


import com.homework.HWBackend.model.Boards;
import com.homework.HWBackend.model.Points;
import com.homework.HWBackend.model.Post;
import com.homework.HWBackend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointsRepository extends JpaRepository<Points,Integer>{
    Points findByUserid(Users usersId);
}
