package project.blobus.Backend.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.blobus.Backend.community.entity.CommunityPost;
import project.blobus.Backend.main.dto.AllSearchDTO;

import java.util.List;

public interface AllSearchRepository extends JpaRepository<CommunityPost,Long> {

    @Query(nativeQuery = true, value = "SELECT t.title, t.id, t.category\n" +
            "  FROM (\n" +
            "SELECT TRIM(poly_biz_sjnm) AS title , policy_id AS id\n" +
            ",'job' AS category\n" +
            "FROM tbl_youth_job_policy\n" +
            "UNION ALL\n" +
            "SELECT TRIM(poly_biz_sjnm) AS title , policy_id AS id\n" +
            " , 'house' AS category\n" +
            "FROM tbl_youth_housing_policy\n" +
            "UNION ALL\n" +
            "SELECT TRIM(policy_name) AS title  , policy_id AS id\n" +
            ", 'welfare' AS category\n" +
            "FROM youth_welfare_policy\n" +
            "UNION ALL\n" +
            "SELECT TRIM(policy_name) AS title  , policy_id AS id\n" +
            " , 'education' AS category\n" +
            "FROM youth_education_policy\n" +
            ") AS t\n" +
            "WHERE t.title LIKE CONCAT('%', ?1, '%')\n" +
            "ORDER BY title ASC\n" +
            "LIMIT ?2, 10")
    List<AllSearchDTO> getPolicyTitles(String search, int start);

    @Query(nativeQuery = true, value = "SELECT count(1) FROM (\n" +
            "SELECT TRIM(poly_biz_sjnm) AS title\n" +
            "FROM tbl_youth_job_policy\n" +
            "UNION ALL\n" +
            "SELECT TRIM(poly_biz_sjnm) AS title\n" +
            "FROM tbl_youth_housing_policy\n" +
            "UNION ALL\n" +
            "SELECT TRIM(policy_name) AS title\n" +
            "FROM youth_welfare_policy\n" +
            "UNION ALL\n" +
            "SELECT TRIM(policy_name) AS title\n" +
            "FROM youth_education_policy\n" +
            ") AS t\n" +
            "WHERE t.title LIKE CONCAT('%', ?1, '%')")
    Integer countPolicyTitles(String search);



    @Query(nativeQuery = true, value = "SELECT title,id,visibility, author_id " +
            "FROM community_post WHERE title LIKE CONCAT('%', ?1, '%') ORDER BY title ASC LIMIT ?2, 10")
    List<AllSearchDTO> findAllCommunityPostTitles(String search, int start);

    @Query(nativeQuery = true, value = "SELECT count(1) " +
            "FROM community_post WHERE title LIKE CONCAT('%', ?1, '%')")
    Integer countCommunityPostTitles(String search);

}
