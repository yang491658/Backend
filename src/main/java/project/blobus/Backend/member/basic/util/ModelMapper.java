package project.blobus.Backend.member.basic.util;

import project.blobus.Backend.member.role.admin.entity.AdminMember;
import project.blobus.Backend.member.role.business.dto.BusinessDTO;
import project.blobus.Backend.member.role.business.entity.BusinessMember;
import project.blobus.Backend.member.role.common.dto.MemberDTO;
import project.blobus.Backend.member.role.common.entity.MemberRole;
import project.blobus.Backend.member.role.general.dto.GeneralDTO;
import project.blobus.Backend.member.role.general.entity.GeneralMember;

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
                .foreigner(generalMember.getForeigner())
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
                .foreigner(generalDTO.getForeigner())
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
                .foreigner(generalMember.getForeigner())
                .customSetting(generalMember.getCustomSetting())
                .delFlag(generalMember.isDelFlag())
                .loginErrorCount(generalMember.getLoginErrorCount())
                .joinDate(generalMember.getJoinDate())
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
                .delFlag(businessMember.isDelFlag())
                .loginErrorCount(businessMember.getLoginErrorCount())
                .joinDate(businessMember.getJoinDate())
                .roleName(String.valueOf(MemberRole.BUSINESS))
                .build();
    }

    public static MemberDTO adminToMember(AdminMember adminMember) {
        return MemberDTO.builder()
                .id(adminMember.getId())
                .userId(adminMember.getUserId())
                .userPw(adminMember.getUserPw())
                .roleName(String.valueOf(MemberRole.ADMIN))
                .build();
    }
}
