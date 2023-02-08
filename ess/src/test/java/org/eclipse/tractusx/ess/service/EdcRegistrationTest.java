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
package org.eclipse.tractusx.ess.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class EdcRegistrationTest {

    private EdcRegistration testee;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        testee = new EdcRegistration(restTemplate, "", "");
    }

    @Test
    void shouldRegisterAssets_whenGetRequestReturnsNoResults() {
        when(restTemplate.getForEntity(anyString(), any())).thenReturn(ResponseEntity.ok().body("[]"));
        when(restTemplate.postForEntity(anyString(), anyString(), any())).thenReturn(ResponseEntity.ok().build());

        testee.registerEdcAsset();

        verify(restTemplate, times(1)).getForEntity(anyString(), any());
        verify(restTemplate, times(3)).postForEntity(anyString(), anyString(), any());
    }

    @Test
    void shouldNotRegisterAssets_whenGetRequestReturnsResults() {
        when(restTemplate.getForEntity(anyString(), any())).thenReturn(ResponseEntity.ok().body("[{}]"));

        testee.registerEdcAsset();

        verify(restTemplate, times(1)).getForEntity(anyString(), any());
        verify(restTemplate, never()).postForEntity(anyString(), anyString(), any());
    }
}