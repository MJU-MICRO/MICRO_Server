package mju.sw.micro.domain.sample.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mju.sw.micro.domain.sample.domain.Sample;

@Getter
@NoArgsConstructor
public class SampleCreateServiceRequest {

	private String title;
	private String content;
	private int price;

	@Builder
	private SampleCreateServiceRequest(String title, String content, int price) {
		this.title = title;
		this.content = content;
		this.price = price;
	}

	public static SampleCreateServiceRequest of(String title, String content, int price) {
		return SampleCreateServiceRequest.builder()
			.title(title)
			.content(content)
			.price(price)
			.build();
	}

	public Sample toEntity() {
		return Sample.builder()
			.title(title)
			.content(content)
			.price(price)
			.build();
	}

}
