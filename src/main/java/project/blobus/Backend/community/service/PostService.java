package project.blobus.Backend.community.service;

import project.blobus.Backend.community.dto.PostDTO;
import project.blobus.Backend.community.dto.PostListDTO;
import project.blobus.Backend.community.entity.Post;

import java.util.List;

public interface PostService {

    // 게시글 작성
    PostDTO createPost(PostDTO postDTO);

    // 게시글 단건 조회
    PostDTO getPostById(Long postId);

    // 게시글 목록 조회
    List<PostListDTO> getAllPosts(int page, int size, Post.BoardType boardType, Post.UserType userType);

    // 특정 게시판(자유/건의) 게시글 조회
    List<PostListDTO> getPostsByBoardType(String boardType);

    boolean remove(Long postId);



}
