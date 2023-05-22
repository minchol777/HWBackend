package com.homework.HWBackend.controller;

import com.homework.HWBackend.DTO.PostDTO;
import com.homework.HWBackend.model.Boards;
import com.homework.HWBackend.model.Post;
import com.homework.HWBackend.model.Users;
import com.homework.HWBackend.repository.BoardsRepository;
import com.homework.HWBackend.repository.PostRepository;
import com.homework.HWBackend.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/* 요청 json 형식
*  {
* "title": "mytitle",// 제목
* "maint": "maintext", // 본문
* "board_id": 3, //이 포스트가 올라갈 게시판 id 1~3
* "user_id": 7 } //사용자 id 이건 로그인 성공시 전달받는다.

* */

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private BoardsRepository boardsRepository; // 비보세요? 이걸 까먹어요?
    @Autowired
    private UsersRepository usersRepository;




    @GetMapping //게시판 목록 불러올때 사용 음 아마 타이틀만/id만  가지고 오게 하면 될 꺼 같은대..
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }



    @PostMapping
    public Post createPost(@RequestBody PostDTO postDTO) { //fk는 객체변수로 저장된다 == @RequestBody 그대로 넣음 에러난다.
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setMain(postDTO.getMain());

        // new 안쓰고 리피지토리 인터페이스로 만드는 이유는 그래야 DB랑 연결성을 spring이 잡아주기 때문이다.
        Boards board =  boardsRepository.findById(postDTO.getBoard_id()).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 boards id"));
        post.setBoard(board);
        Users user = usersRepository.findById(postDTO.getUser_id()).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 users id"));
        post.setUser(user);
        // 이렇게 하면 포링키라 usersRepository를 통해서 스프링이 db의 알맞는 유저를 들고온다.

        return postRepository.save(post); // 이렇게 해야 id가 null이라고 에라가 안뜬다.
    }



    @GetMapping("/{id}")
    public Post getPost(@PathVariable int id) {
        return postRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 id"));
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable int id, @RequestBody Post post) {
        return postRepository.findById(id).map(thisPost -> {
                    thisPost.setTitle(post.getTitle());
                    thisPost.setMain(post.getMain());
                    return postRepository.save(thisPost);
                }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 id"));
        /*orElseThrow == 옵셔널객체가 비어있을때(null 일때 인대 null 을 객체에 그대로 넣는건 위험해서) 일경우 예외를 던진다
         *ResponseStatusException spring 에서 제공하는 예외 원하는 http 코드를 던진다.
         *옵셔널은 어떤객체든지 박싱 가능한 레퍼클래스로 혹시 null 일수 있는 겍체를 다룰때 쓴다 */
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable int id) {
        postRepository.deleteById(id);
    }
}