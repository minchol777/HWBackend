package com.homework.HWBackend.repository;

import com.homework.HWBackend.model.Boards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardsRepository extends JpaRepository<Boards,Integer> {
}
