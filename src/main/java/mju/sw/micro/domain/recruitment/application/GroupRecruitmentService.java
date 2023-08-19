package mju.sw.micro.domain.recruitment.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.group.dao.StudentGroupRepository;
import mju.sw.micro.domain.group.domain.StudentGroup;
import mju.sw.micro.domain.recruitment.dao.GroupRecruitmentRepository;
import mju.sw.micro.domain.recruitment.dao.RecruitmentImageRepository;
import mju.sw.micro.domain.recruitment.domain.GroupRecruitment;
import mju.sw.micro.domain.recruitment.domain.RecruitmentImage;
import mju.sw.micro.domain.recruitment.dto.request.GroupRecruitmentCreateServiceRequest;
import mju.sw.micro.global.adapter.S3Uploader;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.error.exception.ErrorCode;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupRecruitmentService {

	private final StudentGroupRepository groupRepository;
	private final GroupRecruitmentRepository recruitmentRepository;

	private final RecruitmentImageRepository recruitmentImageRepository;
	private final S3Uploader s3Uploader;

	@Transactional
	public ApiResponse<String> createGroupRecruitment(Long userId,
		GroupRecruitmentCreateServiceRequest request, List<MultipartFile> files) {
		Optional<StudentGroup> optionalGroup = groupRepository.findById(request.groupId());

		if (optionalGroup.isEmpty()) {
			return ApiResponse.withError(ErrorCode.INVALID_GROUP_ID);
		}

		StudentGroup group = optionalGroup.get();
		if (!group.getPresidentId().equals(userId)) {
			return ApiResponse.withError(ErrorCode.UNMATCH_PRESIDENT_ID);
		}

		List<String> descriptions = request.fileDescriptions();
		List<RecruitmentImage> imageList = new ArrayList<>();
		GroupRecruitment recruitment = request.toEntity();

		if (files != null) {
			for (int i = 0; i < files.size(); i++) {
				imageList.add(new RecruitmentImage(uploadImage(files.get(i)), descriptions.get(i), recruitment, i + 1));
			}
		}
		recruitmentImageRepository.saveAll(imageList);

		group.addRecruitment(recruitment);
		recruitmentRepository.save(recruitment);

		return ApiResponse.ok("학생 단체(동아리/학회) 공고 등록에 성공했습니다.");
	}

	private String uploadImage(MultipartFile multipartFile) {
		return s3Uploader.uploadFile(multipartFile).getData();
	}

	@Transactional
	public ApiResponse<String> deleteGroupRecruitment(Long userId, Long groupId, Long recruitmentId) {
		Optional<StudentGroup> optionalGroup = groupRepository.findById(groupId);

		if (optionalGroup.isEmpty()) {
			return ApiResponse.withError(ErrorCode.INVALID_GROUP_ID);
		}

		StudentGroup group = optionalGroup.get();
		if (!group.getPresidentId().equals(userId)) {
			return ApiResponse.withError(ErrorCode.UNMATCH_PRESIDENT_ID);
		}

		Optional<GroupRecruitment> optionalRecruitment = recruitmentRepository.findById(recruitmentId);

		if (optionalRecruitment.isEmpty()) {
			return ApiResponse.withError(ErrorCode.INVALID_GROUP_RECRUITMENT_ID);
		}

		GroupRecruitment recruitment = optionalRecruitment.get();
		recruitment.clearImageList();

		recruitmentRepository.delete(recruitment);
		return ApiResponse.ok("학생 단체(동아리/학회) 공고 삭제에 성공했습니다.");
	}

}
