package project.blobus.Backend.mypage.bookmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.blobus.Backend.mypage.bookmark.entity.Bookmark;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findAllByMember_UserId(String userId);

    List<Bookmark> findAllByMember_UserIdAndMainCategory(String userId, String mainCategory);
}
