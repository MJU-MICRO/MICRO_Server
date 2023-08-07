package mju.sw.micro.domain.recruitment.dto.request;

public record ClubRecruitmentUpdateServiceRequest(String title, String content, Long clubId, Long recruitmentId) {
}
