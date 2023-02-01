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
package org.eclipse.tractusx.edc;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.eclipse.tractusx.irs.component.enums.BomLifecycle;
import org.eclipse.tractusx.irs.component.enums.Direction;

/**
 * Relationship aspect types
 */
@SuppressWarnings("PMD.FieldNamingConventions")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public enum RelationshipAspect {
    SINGLE_LEVEL_BOM_AS_PLANNED("SingleLevelBomAsPlanned", SingleLevelBomAsPlanned.class, BomLifecycle.AS_PLANNED,
            Direction.DOWNWARD),
    ASSEMBLY_PART_RELATIONSHIP("AssemblyPartRelationship", AssemblyPartRelationship.class, BomLifecycle.AS_BUILT,
            Direction.DOWNWARD),
    SINGLE_LEVEL_USAGE_AS_BUILT("SingleLevelUsageAsBuilt", SingleLevelUsageAsBuilt.class, BomLifecycle.AS_BUILT,
            Direction.UPWARD);

    private final String name;
    private final Class<? extends RelationshipSubmodel> submodelClazz;
    private final BomLifecycle bomLifecycle;
    private final Direction direction;

    /**
     * @param bomLifecycle lifecycle
     * @param direction    direction
     * @return Returns traversal aspect type
     * asBuilt + downward => AssemblyPartRelationship
     * asPlanned + downward => SingleLevelBomAsPlanned
     * asBuilt + upward => SingleLevelUsageAsBuilt
     * asPlanned + upward => SingleLevelXXXAsPlanned
     */
    public static RelationshipAspect from(final BomLifecycle bomLifecycle, final Direction direction) {
        return Stream.of(RelationshipAspect.values())
                     .filter(aspect -> aspect.bomLifecycle.equals(bomLifecycle) && aspect.direction.equals(direction))
                     .findFirst()
                     .orElseThrow(NoSuchElementException::new);
    }
}
