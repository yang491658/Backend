package project.blobus.Backend.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.blobus.Backend.community.entity.CommunityComment;
import project.blobus.Backend.community.entity.Post;
import project.blobus.Backend.community.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void create(Post post, String content) {
       create(post, content, null);
    }

    @Override
    public CommunityComment create(Post post, String content, Long authorId) {
        return null;
    }

    @Override
    public void modify(CommunityComment communityComment, String content) {

    }

    @Override
    public CommunityComment getDetail(Integer id) {
        return null;
    }

    @Override
    public void delete(CommunityComment communityComment) {

    }
}
