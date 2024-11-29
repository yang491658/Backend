package project.blobus.Backend.community.service;

import project.blobus.Backend.community.entity.CommunityComment;
import project.blobus.Backend.community.entity.Post;

public interface CommentService {

     void create(Post post, String content);

    CommunityComment create(Post post, String content, Long authorId);

    void modify(CommunityComment communityComment, String content);

    CommunityComment getDetail(Integer id);

    void delete(CommunityComment communityComment);
}
