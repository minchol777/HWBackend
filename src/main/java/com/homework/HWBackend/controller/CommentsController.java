package com.homework.HWBackend.controller;

import com.homework.HWBackend.DTO.CommentsDTO;
import com.homework.HWBackend.model.Comments;
import com.homework.HWBackend.model.Post;
import com.homework.HWBackend.model.Users;
import com.homework.HWBackend.repository.CommentsRepository;
import com.homework.HWBackend.repository.PostRepository;
import com.homework.HWBackend.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/comment/{id}")
    public List<Comments> getPostComments(@PathVariable int id){
        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"post_id에 해당하는 게시글이 없습니다."));
        List<Comments> list = commentsRepository.findByPostid(post);

        //왜 이렇게 하는가? 리파지토리에 만든 findByPostId는 옵셔널이 안되여...
        if(list.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"post_id에 해당하는 댓글이 없습니다.");
        }
        return list;
    }

    @PostMapping("/comment/{id}")
    public Comments addComment(@PathVariable int id, @RequestBody CommentsDTO commentDTO) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"post_id에 해당하는 게시글이 없습니다."));
        Users user = usersRepository.findById(commentDTO.getUserid()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"user_id에 해당하는 사용자가 없습니다."));

        Comments comment = new Comments();
        comment.setPostid(post);
        comment.setUserid(user);
        comment.setMain(commentDTO.getMain());

        return commentsRepository.save(comment);
    }

    @DeleteMapping("/comment/{id}")
    public void deleteComment(@PathVariable int id) {
        if (!commentsRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "comment_id에 해당하는 댓글이 없습니다.");
        }
        commentsRepository.deleteById(id);
    }
}


