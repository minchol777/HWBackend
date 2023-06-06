package com.homework.HWBackend.controller;

import com.homework.HWBackend.DTO.*;
import com.homework.HWBackend.model.Boards;
import com.homework.HWBackend.model.Comments;
import com.homework.HWBackend.model.Post;
import com.homework.HWBackend.model.Users;
import com.homework.HWBackend.repository.BoardsRepository;
import com.homework.HWBackend.repository.CommentsRepository;
import com.homework.HWBackend.repository.PostRepository;
import com.homework.HWBackend.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/* 요청 json 형식
*  {
* "title": "mytitle",// 제목
* "main": "maintext", // 본문
* "board_id": 3, //이 포스트가 올라갈 게시판 id 1~3
* "user_id": 7 } //사용자 id 이건 로그인 성공시 전달받는다.

* */

//보통은 ResponseEntity로 응답을 박싱해서 보낸다 http코드를 조작하기 위해서다.

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private BoardsRepository boardsRepository; // 비보세요? 이걸 까먹어요?
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CommentsRepository commentsRepository;



    @GetMapping //사용금지
    public List<Post> getAllPosts() {
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "모든 보드에 요청하는것은 금지됨");
    }

    // id로 가지고 오기 해당 보드에 있는 게시글 목록을 불러옴
    @GetMapping("/board/{boardId}")
    public List<PostTitleResponseDTO> getPostsByBoardId(@PathVariable int boardId) {
        Boards boards = boardsRepository.findById(boardId).orElseThrow( ()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 boards id"));
        List<Post> posts = postRepository.findByBoardid(boards);
        List<PostTitleResponseDTO> postDTOs = posts.stream()
                .map(post -> {
                    PostTitleResponseDTO dto = new PostTitleResponseDTO();
                    dto.setId(post.getId());
                    dto.setTitle(post.getTitle());
                    return dto;
                })
                .collect(Collectors.toList());
        return postDTOs;
    }


    
    // 개시글 생성하기
    @PostMapping("/create")
    public Post createPost(@RequestBody PostDTO postDTO) { //fk는 객체변수로 저장된다 == @RequestBody 그대로 넣음 에러난다.
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setMain(postDTO.getMain());

        // new 안쓰고 리피지토리 인터페이스로 만드는 이유는 그래야 DB랑 연결성을 spring이 잡아주기 때문이다.
        Boards board =  boardsRepository.findById(postDTO.getBoardid()).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 boards id"));
        post.setBoardid(board);
        Users user = usersRepository.findById(postDTO.getUserid()).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 users id"));
        post.setUserid(user);
        // 이렇게 하면 포링키라 usersRepository를 통해서 스프링이 db의 알맞는 유저를 들고온다.

        return postRepository.save(post); // 이렇게 해야 id가 null이라고 에라가 안뜬다.
    }




    
    // 포스트 아이디로 우너하는 포스트 가지고 오기
    //이렇게 않하면 직렬화할때 fk에 연결된 속성이 누락된다. 무조건 명시적으로 DTO를 쓰자
    @GetMapping("/post/{id}")
    public PostResponseDTO getPost(@PathVariable int id) {
        Post savedPost = postRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 id"));



        // BoardDTO와 UserDTO 객체 생성 및 필드 설정
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(savedPost.getBoardid().getId());
        boardDTO.setBoardname(savedPost.getBoardid().getBoardname());

        UserDTO userDTO = new UserDTO();
        userDTO.setId(savedPost.getUserid().getId());
        userDTO.setUsername(savedPost.getUserid().getUsername());

        // PostResponseDTO 객체 생성 및 필드 설정
        PostResponseDTO postResponseDTO = new PostResponseDTO();
        postResponseDTO.setId(savedPost.getId());
        postResponseDTO.setTitle(savedPost.getTitle());
        postResponseDTO.setMain(savedPost.getMain());
        postResponseDTO.setBoard(boardDTO);
        postResponseDTO.setUser(userDTO);

        return postResponseDTO;
    }

    //제목으로 탐색
    @GetMapping("/search/{title}")
    public List<PostDTO> getPostsByTitle(@PathVariable String title) {
        List<Post> posts = postRepository.findByTitleContaining(title);
        List<PostDTO> postDTOs = posts.stream()
                .map(post -> {
                    PostDTO dto = new PostDTO();
                    dto.setId((long)post.getId());
                    dto.setTitle(post.getTitle());
                    return dto;
                })
                .collect(Collectors.toList());
        return postDTOs;
    }

    
    //포스트 아이디로 원하는 포스트 수정
    @PutMapping("/post/{id}")
    public Post updatePost(@PathVariable int id, @RequestBody PostDTO postDTO) {
        return postRepository.findById(id).map(thisPost -> {
                    thisPost.setTitle(postDTO.getTitle());
                    thisPost.setMain(postDTO.getMain());
                    return postRepository.save(thisPost);
                }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 id"));
        /*orElseThrow == 옵셔널객체가 비어있을때(null 일때 인대 null 을 객체에 그대로 넣는건 위험해서) 일경우 예외를 던진다
         *ResponseStatusException spring 에서 제공하는 예외 원하는 http 코드를 던진다.
         *옵셔널은 어떤객체든지 박싱 가능한 레퍼클래스로 혹시 null 일수 있는 겍체를 다룰때 쓴다 */
    }


    //삭제
    @DeleteMapping("/post/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(@PathVariable int id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "post_id에 해당하는 게시글이 없습니다."));

        List<Comments> comments = commentsRepository.findByPostid(post);
        for (Comments comment : comments) {
            commentsRepository.delete(comment);
        }

        postRepository.delete(post);;
    }
}