package com.homework.HWBackend.repository;


import com.homework.HWBackend.model.Comments;
import com.homework.HWBackend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Integer> {
    List<Comments> findByPostid(Post postid);// Postid가 Comments에서는 Post객체라 이렇게 해야한다.
}
