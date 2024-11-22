package project.blobus.Backend.community.service;

import project.blobus.Backend.community.dto.PostDTO;

import java.util.List;

public interface PostService {

    // 게시글 단건 조회
    PostDTO getPostById(Long postId);

    // 게시글 목록 조회
    List<PostDTO> getAllPosts();

    // 특정 게시판(자유/건의) 게시글 조회
    List<PostDTO> getPostsByBoardType(String boardType);
}
