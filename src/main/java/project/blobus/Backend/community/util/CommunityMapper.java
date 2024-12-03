package project.blobus.Backend.community.util;

import project.blobus.Backend.community.dto.CommentDTO;
import project.blobus.Backend.community.dto.PostDTO;
import project.blobus.Backend.community.entity.CommunityPost;
import project.blobus.Backend.community.entity.CommuntiyComment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class CommunityMapper {
    public static PostDTO postEntityToDto(CommunityPost entity) {
        return PostDTO.builder()
                .id(entity.getId())
                .boardType(entity.getBoardType())
                .category(entity.getCategory())
                .title(entity.getTitle())
                .content(entity.getContent())
                .authorId(entity.getAuthorId())
                .authorName(entity.getAuthorName())
                .authorEmail(entity.getAuthorEmail())
                .visibility(entity.isVisibility())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .commentList(entity.getCommentList() != null ?
                        entity.getCommentList().stream()
                                .map(CommunityMapper::commentEntityToDto)
                                .sorted(Comparator.comparing(CommentDTO::getId).reversed())
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

    public static CommentDTO commentEntityToDto(CommuntiyComment entity) {
        return CommentDTO.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .authorId(entity.getAuthorId())
                .authorName(entity.getAuthorName())
                .visibility(entity.isVisibility())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .postId(entity.getPost().getId())
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