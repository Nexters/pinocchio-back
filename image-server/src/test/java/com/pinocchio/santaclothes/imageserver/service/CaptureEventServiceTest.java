package com.pinocchio.santaclothes.imageserver.service;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.BDDAssertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.pinocchio.santaclothes.common.message.CaptureEventCreateMessage;
import com.pinocchio.santaclothes.common.message.CaptureEventProcessRequestMessage;
import com.pinocchio.santaclothes.imageserver.repository.CaptureImageRepository;
import com.pinocchio.santaclothes.imageserver.service.dto.CaptureImageRequest;

import reactor.core.publisher.Sinks;
import reactor.test.StepVerifier;

@SpringBootTest
@EmbeddedKafka(
	topics = {"captureCreate", "captureProcessRequest"},
	bootstrapServersProperty = "spring.kafka.bootstrap-servers"
)
public class CaptureEventServiceTest {
	private CaptureEventService eventService;
	private Sinks.Many<CaptureEventCreateMessage> createMessage;
	private Sinks.Many<CaptureEventProcessRequestMessage> eventProcessMessage;

	@Autowired
	private CaptureImageRepository captureImageRepository;

	@BeforeEach
	void setUp() {
		createMessage = Sinks.many().multicast().onBackpressureBuffer();
		eventProcessMessage = Sinks.many().multicast().onBackpressureBuffer();
		eventService = new CaptureEventService(createMessage, eventProcessMessage, captureImageRepository);
	}

	@Test
	void saveImage() {
		// given
		String imageId = "imageId";
		String eventId = "eventId";
		String fileName = "mockupFileName";
		MultipartFile mockFile = new MockMultipartFile("image", fileName, "application/json", "test".getBytes());

		CaptureImageRequest request = CaptureImageRequest.builder()
			.eventId(eventId)
			.imageId(imageId)
			.image(mockFile)
			.build();
		// when
		eventService.saveImage(request);

		// then
		CaptureEventCreateMessage captureEventCreateMessage = new CaptureEventCreateMessage(eventId, imageId);
		CaptureEventProcessRequestMessage captureEventProcessRequestMessage =
			new CaptureEventProcessRequestMessage(eventId, imageId);

		createMessage.tryEmitComplete();
		eventProcessMessage.tryEmitComplete();

		StepVerifier.create(createMessage.asFlux())
			.expectNext(captureEventCreateMessage)
			.verifyComplete();

		StepVerifier.create(eventProcessMessage.asFlux())
			.expectNext(captureEventProcessRequestMessage)
			.verifyComplete();

		assertThat(captureImageRepository.findById(imageId)).hasValueSatisfying((it) -> {
				then(it.getImageId()).isEqualTo(imageId);
				then(it.getOriginalFileName()).isEqualTo(fileName);
			}
		);
	}
}
