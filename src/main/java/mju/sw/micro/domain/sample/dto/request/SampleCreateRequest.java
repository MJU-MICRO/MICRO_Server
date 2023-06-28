package mju.sw.micro.domain.sample.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
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

	@Builder
	private SampleCreateRequest(String title, String content, int price) {
		this.title = title;
		this.content = content;
		this.price = price;
	}

	public static SampleCreateRequest of(String title, String content, int price) {
		return SampleCreateRequest.builder()
			.title(title)
			.content(content)
			.price(price)
			.build();
	}

	public SampleCreateServiceRequest toServiceRequest() {
		return SampleCreateServiceRequest.builder()
			.title(title)
			.content(content)
			.price(price)
			.build();
	}

}
