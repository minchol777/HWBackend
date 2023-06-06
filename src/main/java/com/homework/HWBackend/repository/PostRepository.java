package com.homework.HWBackend.repository;

import com.homework.HWBackend.model.Boards;
import com.homework.HWBackend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository; // crudRepository의 확장판 페이징과 정렬 제공
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //리파지토리라고 명시함 이거 없음 잘 못찾더라
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByBoardid(Boards boardId);
    List<Post> findByTitleContaining(String title);
}