package project.blobus.Backend.youth.house.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.blobus.Backend.youth.house.entity.HouseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HouseRepositoryTest {
    @Autowired
    private HouseRepository houseRepository;

    @Test
    public void testInsert() {
        for(int i = 51; i < 201; i++) {
            HouseEntity houseEntity = HouseEntity.builder()
                    .polyRlmCd("023020")
                    .bizId("정책번호"+i)
                    .polyBizSjnm("정책명-청년 주거정책"+i)
                    .polyItcnCn("정책소개-청년주거정책소개"+i)
                    .polyBizTy("기관및지자체구분-고용노동부")
                    .mngtMson("주관부처-국토장관")
                    .cnsgNmor("운영기관-국토장관")
                    .sporCn("지원내용입니다.")
                    .bizPrdCn("사업운영기간입니다.")
                    .prdRpttSecd("002001")
                    .rqutPrdCn("사업신청기간입니다.")
                    .sporScvl("지원규모입니다")
                    .ageInfo("15-69세 까지 가능 연령입니다")
                    .prcpCn("거주지 및 소득요건입니다.")
                    .majrRqisCn("전공요건입니다")
                    .empmSttsCn("취업상태")
                    .splzRlmRqisCn("특화분야 입니다.")
                    .accrRqisCn("학력요건 입니다.")
                    .aditRscn("추가 단서사항 입니다.")
                    .prcpLmttTrgtCn("참여제한대상 입니다.")
                    .rqutProcCn("신청절차 입니다.")
                    .jdgnPresCn("심사 및 발표 입니다.")
                    .rqutUrla("신청사이트 주소 입니다.")
                    .rfcSiteUrla1("참고사이트 URL1 입니다.")
                    .rfcSiteUrla2("참고사이트 URL2 입니다.")
                    .pstnPaprCn("제출서류 입니다.")
                    .etct("기타 입니다.")
                    .build();
            houseRepository.save(houseEntity);
        }
    }
}