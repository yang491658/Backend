package project.blobus.Backend.member.role.util;

import project.blobus.Backend.member.role.admin.entity.AdminMember;
import project.blobus.Backend.member.role.business.dto.BusinessDTO;
import project.blobus.Backend.member.role.business.entity.BusinessMember;
import project.blobus.Backend.member.role.common.dto.MemberDTO;
import project.blobus.Backend.member.role.common.entity.MemberRole;
import project.blobus.Backend.member.role.general.dto.GeneralDTO;
import project.blobus.Backend.member.role.general.entity.GeneralMember;

public class MemberMapper {
    public static GeneralDTO generalEntityToDto(GeneralMember member) {
        return GeneralDTO.builder()
                .id(member.getId())
                .userId(member.getUserId())
                .userPw(member.getUserPw())
                .name(member.getName())
                .phoneNum(member.getPhoneNum())
                .address(member.getAddress())
                .birthDate(member.getBirthDate())
                .gender(member.getGender())
                .foreigner(member.getForeigner())
                .roleName(String.valueOf(MemberRole.GENERAL))
                .build();
    }

    public static GeneralMember generalDtoToEntity(GeneralDTO dto) {
        return GeneralMember.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .userPw(dto.getUserPw())
                .name(dto.getName())
                .phoneNum(dto.getPhoneNum())
                .address(dto.getAddress())
                .birthDate(dto.getBirthDate())
                .gender(dto.getGender())
                .foreigner(dto.getForeigner())
                .memberRole(MemberRole.valueOf(dto.getRoleName()))
                .build();
    }

    public static BusinessDTO businessEntityToDto(BusinessMember member) {
        return BusinessDTO.builder()
                .id(member.getId())
                .userId(member.getUserId())
                .userPw(member.getUserPw())
                .name(member.getName())
                .phoneNum(member.getPhoneNum())
                .email(member.getEmail())
                .address(member.getAddress())
                .roleName(String.valueOf(MemberRole.BUSINESS))
                .build();
    }

    public static BusinessMember businessDtoToEntity(BusinessDTO dto) {
        return BusinessMember.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .userPw(dto.getUserPw())
                .name(dto.getName())
                .phoneNum(dto.getPhoneNum())
                .email(dto.getEmail())
                .address(dto.getAddress())
                .memberRole(MemberRole.valueOf(dto.getRoleName()))
                .build();
    }

    public static MemberDTO generalToMember(GeneralMember member) {
        return MemberDTO.builder()
                .id(member.getId())
                .userId(member.getUserId())
                .userPw(member.getUserPw())
                .name(member.getName())
                .phoneNum(member.getPhoneNum())
                .address(member.getAddress())
                .birthDate(member.getBirthDate())
                .gender(member.getGender())
                .foreigner(member.getForeigner())
                .customSetting(member.getCustomSetting())
                .delFlag(member.isDelFlag())
                .loginErrorCount(member.getLoginErrorCount())
                .joinDate(member.getJoinDate())
                .roleName(String.valueOf(MemberRole.GENERAL))
                .build();
    }

    public static MemberDTO businessToMember(BusinessMember member) {
        return MemberDTO.builder()
                .id(member.getId())
                .userId(member.getUserId())
                .userPw(member.getUserPw())
                .name(member.getName())
                .phoneNum(member.getPhoneNum())
                .email(member.getEmail())
                .address(member.getAddress())
                .delFlag(member.isDelFlag())
                .loginErrorCount(member.getLoginErrorCount())
                .joinDate(member.getJoinDate())
                .roleName(String.valueOf(MemberRole.BUSINESS))
                .build();
    }

    public static MemberDTO adminToMember(AdminMember member) {
        return MemberDTO.builder()
                .id(member.getId())
                .userId(member.getUserId())
                .userPw(member.getUserPw())
                .roleName(String.valueOf(MemberRole.ADMIN))
                .build();
    }
}
