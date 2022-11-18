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
package org.eclipse.tractusx.irs.edc;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.dataspaceconnector.spi.types.domain.edr.EndpointDataReference;
import org.eclipse.tractusx.irs.aaswrapper.submodel.domain.RelationshipAspect;
import org.eclipse.tractusx.irs.aaswrapper.submodel.domain.RelationshipSubmodel;
import org.eclipse.tractusx.irs.component.Relationship;
import org.eclipse.tractusx.irs.edc.model.NegotiationResponse;
import org.eclipse.tractusx.irs.exceptions.EdcTimeoutException;
import org.eclipse.tractusx.irs.util.JsonUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

/**
 * Public API facade for EDC domain
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EdcSubmodelFacade {

    public static final int MAXIMUM_TASK_RUNTIME_MINUTES = 10;
    public static final int POLL_INTERVAL_MILLIS = 100;
    private final ContractNegotiationService contractNegotiationService;
    private final EdcDataPlaneClient edcDataPlaneClient;
    private final EndpointDataReferenceStorage endpointDataReferenceStorage;
    private final JsonUtil jsonUtil;
    private final ScheduledExecutorService scheduler;

    private final Clock clock;

    public CompletableFuture<List<Relationship>> getRelationships(final String submodelEndpointAddress,
            final RelationshipAspect traversalAspectType) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start("Get EDC Submodel task for relationships, endpoint " + submodelEndpointAddress);

        final String submodel = "/submodel";
        final int indexOfUrn = findIndexOf(submodelEndpointAddress, "/urn");
        final int indexOfSubModel = findIndexOf(submodelEndpointAddress, submodel);

        if (indexOfUrn == -1 || indexOfSubModel == -1) {
            throw new IllegalArgumentException(
                    "Cannot rewrite endpoint address, malformed format: " + submodelEndpointAddress);
        }

        final String providerConnectorUrl = submodelEndpointAddress.substring(0, indexOfUrn);
        final String target = submodelEndpointAddress.substring(indexOfUrn, indexOfSubModel);
        log.info("Starting contract negotiation with providerConnectorUrl {} and target {}", providerConnectorUrl,
                target);
        final NegotiationResponse negotiationResponse = contractNegotiationService.negotiate(providerConnectorUrl,
                target);

        return startSubmodelDataRetrieval(traversalAspectType, submodel, negotiationResponse.getContractAgreementId(),
                stopWatch);
    }

    private CompletableFuture<List<Relationship>> startSubmodelDataRetrieval(
            final RelationshipAspect traversalAspectType, final String submodel, final String contractAgreementId,
            final StopWatch stopWatch) {

        CompletableFuture<List<Relationship>> completionFuture = new CompletableFuture<>();
        final Runnable action = () -> retrieveSubmodelData(traversalAspectType, submodel, contractAgreementId,
                completionFuture, stopWatch);

        final ScheduledFuture<?> checkFuture = pollForSubmodel(action, completionFuture);
        completionFuture.whenComplete((result, thrown) -> checkFuture.cancel(true));
        return completionFuture;
    }

    @NotNull
    private ScheduledFuture<?> pollForSubmodel(final Runnable action,
            final CompletableFuture<List<Relationship>> completionFuture) {
        return scheduler.scheduleWithFixedDelay(
                withTimeout(action, Duration.ofMinutes(MAXIMUM_TASK_RUNTIME_MINUTES), completionFuture), 0,
                POLL_INTERVAL_MILLIS, TimeUnit.MILLISECONDS);
    }

    private Runnable withTimeout(final Runnable action, final Duration ttl,
            final CompletableFuture<List<Relationship>> taskToEndOnError) {
        final LocalTime startTime = LocalTime.now(clock);
        return () -> {
            try {
                final LocalTime now = LocalTime.now(clock);
                if (startTime.plus(ttl).isBefore(now)) {
                    throw new EdcTimeoutException("Waiting for submodel endpoint timed out after " + ttl);
                }
                action.run();
            } catch (Exception e) {
                taskToEndOnError.completeExceptionally(e);
            }
        };
    }

    private void retrieveSubmodelData(final RelationshipAspect traversalAspectType, final String submodel,
            final String contractAgreementId, final CompletableFuture<List<Relationship>> completionFuture,
            final StopWatch stopWatch) {
        log.info("Retrieving dataReference from storage for contractAgreementId {}", contractAgreementId);
        Optional<EndpointDataReference> dataReference = endpointDataReferenceStorage.get(contractAgreementId);

        if (dataReference.isPresent()) {
            final EndpointDataReference ref = dataReference.get();
            log.info("Retrieving data from EDC data plane with dataReference {}:{}", ref.getAuthKey(),
                    ref.getAuthCode());
            String data = edcDataPlaneClient.getData(ref, submodel);

            final RelationshipSubmodel relationshipSubmodel = jsonUtil.fromString(data,
                    traversalAspectType.getSubmodelClazz());

            completionFuture.complete(relationshipSubmodel.asRelationships());

            stopWatch.stop();
            log.info("EDC Task '{}' took {} ms", stopWatch.getLastTaskName(), stopWatch.getLastTaskTimeMillis());
        }
    }

    private int findIndexOf(final String endpointAddress, final String str) {
        return endpointAddress.indexOf(str);
    }

}
