package project.blobus.Backend.member.basic.util;

import project.blobus.Backend.member.member.business.dto.BusinessDTO;
import project.blobus.Backend.member.member.business.entity.BusinessMember;
import project.blobus.Backend.member.member.common.dto.MemberDTO;
import project.blobus.Backend.member.member.common.entity.MemberRole;
import project.blobus.Backend.member.member.general.dto.GeneralDTO;
import project.blobus.Backend.member.member.general.entity.GeneralMember;

public class ModelMapper {
    public static GeneralDTO generalEntityToDTO(GeneralMember generalMember) {
        return GeneralDTO.builder()
                .id(generalMember.getId())
                .userId(generalMember.getUserId())
                .userPw(generalMember.getUserPw())
                .name(generalMember.getName())
                .phoneNum(generalMember.getPhoneNum())
                .address(generalMember.getAddress())
                .birthDate(generalMember.getBirthDate())
                .gender(generalMember.getGender())
                .foreigner(generalMember.isForeigner())
                .roleName(String.valueOf(MemberRole.GENERAL))
                .build();
    }

    public static GeneralMember generalDtoToEntity(GeneralDTO generalDTO) {
        return GeneralMember.builder()
                .id(generalDTO.getId())
                .userId(generalDTO.getUserId())
                .userPw(generalDTO.getUserPw())
                .name(generalDTO.getName())
                .phoneNum(generalDTO.getPhoneNum())
                .address(generalDTO.getAddress())
                .birthDate(generalDTO.getBirthDate())
                .gender(generalDTO.getGender())
                .foreigner(generalDTO.isForeigner())
                .memberRole(MemberRole.valueOf(generalDTO.getRoleName()))
                .build();
    }

    public static BusinessDTO businessEntityToDTO(BusinessMember businessMember) {
        return BusinessDTO.builder()
                .id(businessMember.getId())
                .userId(businessMember.getUserId())
                .userPw(businessMember.getUserPw())
                .name(businessMember.getName())
                .phoneNum(businessMember.getPhoneNum())
                .email(businessMember.getEmail())
                .address(businessMember.getAddress())
                .roleName(String.valueOf(MemberRole.BUSINESS))
                .build();
    }

    public static BusinessMember businessDtoToEntity(BusinessDTO businessDTO) {
        return BusinessMember.builder()
                .id(businessDTO.getId())
                .userId(businessDTO.getUserId())
                .userPw(businessDTO.getUserPw())
                .name(businessDTO.getName())
                .phoneNum(businessDTO.getPhoneNum())
                .email(businessDTO.getEmail())
                .address(businessDTO.getAddress())
                .memberRole(MemberRole.valueOf(businessDTO.getRoleName()))
                .build();
    }

    public static MemberDTO generalToMember(GeneralMember generalMember) {
        return MemberDTO.builder()
                .id(generalMember.getId())
                .userId(generalMember.getUserId())
                .userPw(generalMember.getUserPw())
                .name(generalMember.getName())
                .phoneNum(generalMember.getPhoneNum())
                .address(generalMember.getAddress())
                .birthDate(generalMember.getBirthDate())
                .gender(generalMember.getGender())
                .foreigner(generalMember.isForeigner())
                .joinDate(generalMember.getJoinDate())
                .loginErrorCount(generalMember.getLoginErrorCount())
                .delFlag(generalMember.isDelFlag())
                .roleName(String.valueOf(MemberRole.GENERAL))
                .build();
    }

    public static MemberDTO businessToMember(BusinessMember businessMember) {
        return MemberDTO.builder()
                .id(businessMember.getId())
                .userId(businessMember.getUserId())
                .userPw(businessMember.getUserPw())
                .name(businessMember.getName())
                .phoneNum(businessMember.getPhoneNum())
                .email(businessMember.getEmail())
                .address(businessMember.getAddress())
                .joinDate(businessMember.getJoinDate())
                .loginErrorCount(businessMember.getLoginErrorCount())
                .delFlag(businessMember.isDelFlag())
                .roleName(String.valueOf(MemberRole.BUSINESS))
                .build();
    }
}
