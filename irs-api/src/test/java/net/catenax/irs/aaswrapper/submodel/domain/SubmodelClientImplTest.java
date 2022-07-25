//
// Copyright (c) 2021 Copyright Holder (Catena-X Consortium)
//
// See the AUTHORS file(s) distributed with this work for additional
// information regarding authorship.
//
// See the LICENSE file(s) distributed with this work for
// additional information regarding license terms.
//

package net.catenax.irs.aaswrapper.submodel.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.retry.RetryRegistry;
import net.catenax.irs.services.OutboundMeterRegistryService;
import net.catenax.irs.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class SubmodelClientImplTest {

    private final RestTemplate restTemplate = mock(RestTemplate.class);

    private final OutboundMeterRegistryService meterRegistry = mock(OutboundMeterRegistryService.class);
    private final RetryRegistry retryRegistry = mock(RetryRegistry.class);

    private final static String url = "https://edc.io/BPNL0000000BB2OK/urn:uuid:5a7ab616-989f-46ae-bdf2-32027b9f6ee6-urn:uuid:31b614f5-ec14-4ed2-a509-e7b7780083e7/submodel?content=value&extent=withBlobValue";
    private final JsonUtil jsonUtil = new JsonUtil();
    private final SubmodelClient submodelClient = new SubmodelClientImpl(restTemplate,
            "http://aaswrapper:9191/api/service", jsonUtil, meterRegistry, retryRegistry);
    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Test
    void shouldThrowExceptionWhenSubmodelNotFound() {
        final String url = "https://edc.io/BPNL0000000BB2OK/urn:uuid:5a7ab616-989f-46ae-bdf2-32027b9f6ee6-urn:uuid:31b614f5-ec14-4ed2-a509-e7b7780083e7/submodel?content=value&extent=withBlobValue";
        final SubmodelClientImpl submodelClient = new SubmodelClientImpl(new RestTemplate(),
                "http://aaswrapper:9191/api/service", jsonUtil, meterRegistry, retryRegistry);

        assertThatExceptionOfType(RestClientException.class).isThrownBy(
                () -> submodelClient.getSubmodel(url, AssemblyPartRelationship.class));
    }

    @Test
    void shouldReturnAspectModelResponseWhen200_OK() throws IOException {
        final File file = new File("src/test/resources/submodelTest.json");
        final String data = objectMapper.readTree(file).get("data").toString();
        final AssemblyPartRelationship assemblyPartRelationship = objectMapper.readValue(data,
                AssemblyPartRelationship.class);

        final String jsonObject = jsonUtil.asString(assemblyPartRelationship);
        final ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonObject, HttpStatus.OK);
        doReturn(responseEntity).when(restTemplate).getForEntity(any(), any());

        final AssemblyPartRelationship submodelResponse = submodelClient.getSubmodel(url,
                AssemblyPartRelationship.class);

        assertThat(submodelResponse.getCatenaXId()).isEqualTo("urn:uuid:8a61c8db-561e-4db0-84ec-a693fc5ffdf6");
    }

    @Test
    void shouldReturnStringAspectModelResponseWhenRequestingForSerialPartTypization() throws IOException {
        final File file = new File("src/test/resources/__files/materialForRecycling.json");
        final String data = objectMapper.readTree(file).toString();

        final ResponseEntity<String> responseEntity = new ResponseEntity<>(data, HttpStatus.OK);
        doReturn(responseEntity).when(restTemplate).getForEntity(any(), any());

        final Object submodelResponse = submodelClient.getSubmodel(url, Object.class);

        assertThat(submodelResponse).isNotNull();
        assertThat(objectMapper.writeValueAsString(submodelResponse)).isEqualTo(data);
    }

    @Test
    void shouldCallExternalServiceOnceAndRunIntoTimeout() {
        final SocketTimeoutException timeoutException = new SocketTimeoutException("UnitTestTimeout");
        Throwable ex = new ResourceAccessException("UnitTest", timeoutException);
        doThrow(ex).when(restTemplate).getForEntity(any(), any());

        assertThrows(ResourceAccessException.class, () -> submodelClient.getSubmodel(url, Object.class));

        verify(this.restTemplate, times(1)).getForEntity(any(), any());
        verify(meterRegistry).incrementSubmodelTimeoutCounter(url);
    }
}
