package mju.sw.micro.domain.club.dto.request;

public record ClubRecruitmentUpdateServiceRequest(String title, String content, Long clubId, Long recruitmentId) {
}
