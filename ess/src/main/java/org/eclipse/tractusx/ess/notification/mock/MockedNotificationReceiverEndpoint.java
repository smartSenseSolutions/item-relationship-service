/********************************************************************************
 * Copyright (c) 2021,2022
 *       2022: Bayerische Motoren Werke Aktiengesellschaft (BMW AG)
 *       2022: ZF Friedrichshafen AG
 *       2022: ISTOS GmbH
 * Copyright (c) 2021,2022 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Apache License, Version 2.0 which is available at
 * https://www.apache.org/licenses/LICENSE-2.0. *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 ********************************************************************************/
package org.eclipse.tractusx.ess.notification.mock;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.eclipse.tractusx.edc.EdcSubmodelFacade;
import org.eclipse.tractusx.edc.exceptions.EdcClientException;
import org.eclipse.tractusx.edc.model.notification.EdcNotification;
import org.eclipse.tractusx.edc.model.notification.EdcNotificationHeader;
import org.eclipse.tractusx.ess.discovery.EdcDiscoveryMockConfig;
import org.eclipse.tractusx.ess.service.SupplyChainImpacted;
import org.eclipse.tractusx.irs.dtos.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Endpoint for receiving EDC notifications and responding with Mocked BPN results.
 */
@RestController
@RequestMapping("/ess/mock/notification")
@RequiredArgsConstructor
@Validated
@Hidden
public class MockedNotificationReceiverEndpoint {

    private final EdcDiscoveryMockConfig edcDiscoveryMockConfig;
    private final EdcSubmodelFacade edcSubmodelFacade;

    @Operation(operationId = "receiveEdcMockNotification", summary = "Accepts notifications sent via EDC.",
               tags = { "Environmental- and Social Standards" },
               description = "Accepts notifications via EDC. Notifications are filtered by their type and processed accordingly.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Notification received successfully."),
                            @ApiResponse(responseCode = "400", description = "Notification malformed.",
                                         content = { @Content(mediaType = APPLICATION_JSON_VALUE,
                                                              schema = @Schema(implementation = ErrorResponse.class),
                                                              examples = @ExampleObject(name = "error",
                                                                                        ref = "#/components/examples/error-response-400"))
                                         }),
    })
    @PostMapping("/receive")
    public void receiveNotification(final @Valid @RequestBody EdcNotification notification) throws EdcClientException {
        final Optional<String> incidentBpn = Optional.ofNullable(notification.getContent().get("incidentBpn"))
                                                     .map(Object::toString);

        if (incidentBpn.isPresent()) {
            final String bpn = incidentBpn.get();

            if (edcDiscoveryMockConfig.getMockEdcResult().containsKey(bpn)) {
                final SupplyChainImpacted supplyChainImpacted = edcDiscoveryMockConfig.getMockEdcResult().get(bpn);

                final String notificationId = UUID.randomUUID().toString();
                final String senderEdc = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
                final String senderBpn = notification.getHeader().getRecipientBpn();
                final String recipientBpn = notification.getHeader().getSenderBpn();
                final Map<String, Object> notificationContent = Map.of("result", supplyChainImpacted.getDescription());
                
                final EdcNotification edcRequest = edcRequest(notificationId, senderEdc, senderBpn, recipientBpn, notificationContent);

                final var response = edcSubmodelFacade.sendNotification("TODO", "notify-request-asset", edcRequest);
                if (!response.deliveredSuccessfully()) {
                    throw new EdcClientException("EDC Provider did not accept message with notificationId " + notificationId);
                }
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "BPN " + bpn + " not handled.");
            }

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Malformed EDC Notification - content without incidentBPN key.");
        }
    }

    private EdcNotification edcRequest(final String notificationId, final String senderEdc, final String senderBpn, final String recipientBpn,
            final Map<String, Object> content) {
        final EdcNotificationHeader header = EdcNotificationHeader.builder()
                                                                 .notificationId(notificationId)
                                                                 .senderEdc(senderEdc)
                                                                 .senderBpn(senderBpn)
                                                                 .recipientBpn(recipientBpn)
                                                                 .replyAssetId("ess-response-asset")
                                                                 .replyAssetSubPath("")
                                                                 .notificationType("ess-supplier-request")
                                                                 .build();

        return EdcNotification.builder().header(header).content(content).build();
    }

}
