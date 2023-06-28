package mju.sw.micro.domain.sample.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SampleCreateRequest {

	@NotBlank(message = "샘플 이름은 필수 값입니다.")
	private String title;

	@NotBlank(message = "샘플 내용은 필수 값입니다.")
	private String content;

	@Positive(message = "샘플 가격은 필수 값입니다.")
	private int price;

	public SampleCreateServiceRequest toServiceRequest() {
		return SampleCreateServiceRequest.builder()
			.title(title)
			.content(content)
			.price(price)
			.build();
	}
	
}
