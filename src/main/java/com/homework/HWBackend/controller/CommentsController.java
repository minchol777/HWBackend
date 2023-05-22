package com.homework.HWBackend.controller;

import com.homework.HWBackend.DTO.CommentsDTO;
import com.homework.HWBackend.model.Comments;
import com.homework.HWBackend.model.Post;
import com.homework.HWBackend.repository.CommentsRepository;
import com.homework.HWBackend.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/{post_id}")
    public List<Comments> getPostComments(@PathVariable int post_id){
        Post post = postRepository.findById(post_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"post_id에 해당하는 게시글이 없습니다."));
        List<Comments> list = commentsRepository.findByPostid(post);

        //왜 이렇게 하는가? 리파지토리에 만든 findByPostId는 옵셔널이 안되여...
        if(list.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"post_id에 해당하는 댓글이 없습니다.");
        }
        return list;
    }



}
