package com.homework.HWBackend.controller;


import com.homework.HWBackend.DTO.PointsDTO;
import com.homework.HWBackend.model.Comments;
import com.homework.HWBackend.model.Points;
import com.homework.HWBackend.model.Post;
import com.homework.HWBackend.model.Users;
import com.homework.HWBackend.repository.CommentsRepository;
import com.homework.HWBackend.repository.PointsRepository;
import com.homework.HWBackend.repository.PostRepository;
import com.homework.HWBackend.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController
@RequestMapping("/points")
public class PointsController {
    public static final int NEED_POINT = 100;

    @Autowired
    private PointsRepository pointsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    // 포인트 획득 
    @PutMapping("/add")
    public Points updatePoint(@RequestBody PointsDTO pointsDTO) {
        Users users = usersRepository.findById(pointsDTO.getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 user id"));
        Points points = pointsRepository.findByUserid(users);
        if (points == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "점수 정보가 없는 사용자");
        }
        int p = points.getPoint();
        p = p + (pointsDTO.getPoint());

        points.setPoint(p);

        return pointsRepository.save(points);
    }


    // 포인트 사용
    @DeleteMapping("/delete/{postid}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteByPoint(@PathVariable int postid, @RequestBody PointsDTO pointsDTO) {
        Users users = usersRepository.findById(pointsDTO.getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 user id"));
        Points points = pointsRepository.findByUserid(users);
        if (points == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "점수 정보가 없는 사용자");
        }
        int p = points.getPoint();
        if (p < NEED_POINT) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "포인트가 부족합니다");
        } else {
            p = p - 100;
            points.setPoint(p);
        }


        Post post = postRepository.findById(postid).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "post_id에 해당하는 게시글이 없습니다."));
        pointsRepository.save(points);
        List<Comments> comments = commentsRepository.findByPostid(post);
        for (Comments comment : comments) {
            commentsRepository.delete(comment);
        }

        postRepository.delete(post);
        ;
    }

}
