package project.blobus.Backend.community.util;

import project.blobus.Backend.community.dto.CommentDTO;
import project.blobus.Backend.community.dto.PostDTO;
import project.blobus.Backend.community.entity.CommunityPost;
import project.blobus.Backend.community.entity.CommuntiyComment;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CommunityMapper {
    public static PostDTO postEntityToDto(CommunityPost entitiy) {
        return PostDTO.builder()
                .id(entitiy.getId())
                .boardType(entitiy.getBoardType())
                .category(entitiy.getCategory())
                .title(entitiy.getTitle())
                .content(entitiy.getContent())
                .authorId(entitiy.getAuthorId())
                .authorName(entitiy.getAuthorName())
                .authorEmail(entitiy.getAuthorEmail())
                .visibility(entitiy.isVisibility())
                .createdAt(entitiy.getCreatedAt())
                .updatedAt(entitiy.getUpdatedAt())
                .commentList(entitiy.getCommentList() != null ?
                        entitiy.getCommentList().stream()
                                .map(CommunityMapper::commentEntityToDto)
                                .collect(Collectors.toList())
                        : new ArrayList<>())
                .build();
    }

    public static CommunityPost postDtoToEntity(PostDTO dto) {
        return CommunityPost.builder()
                .boardType(dto.getBoardType())
                .category(dto.getCategory())
                .title(dto.getTitle())
                .content(dto.getContent())
                .authorId(dto.getAuthorId())
                .authorName(dto.getAuthorName())
                .authorEmail(dto.getAuthorEmail())
                .visibility(dto.isVisibility())
                .commentList(dto.getCommentList() != null ?
                        dto.getCommentList().stream()
                                .map(CommunityMapper::commentDtoToEntity)
                                .collect(Collectors.toList())
                        : new ArrayList<>())
                .build();
    }

    public static CommentDTO commentEntityToDto(CommuntiyComment entitiy) {
        return CommentDTO.builder()
                .id(entitiy.getId())
                .content(entitiy.getContent())
                .authorId(entitiy.getAuthorId())
                .authorName(entitiy.getAuthorName())
                .visibility(entitiy.isVisibility())
                .createdAt(entitiy.getCreatedAt())
                .updatedAt(entitiy.getUpdatedAt())
                .postId(entitiy.getPost().getId())
                .build();
    }

    public static CommuntiyComment commentDtoToEntity(CommentDTO dto) {
        return CommuntiyComment.builder()
                .id(dto.getId())
                .content(dto.getContent())
                .authorId(dto.getAuthorId())
                .authorName(dto.getAuthorName())
                .visibility(dto.isVisibility())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
}