/*
 * Copyright (C) 2015 Hannes Dorfmann
 * Copyright (C) 2015 Tickaroo, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.tickaroo.tikxml.annotationprocessing.element.polymorphism.constructor

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.ElementNameMatcher
import com.tickaroo.tikxml.annotation.Xml

/**
 * @author Hannes Dorfmann
 */
@Xml(name = "paper")
class PaperConstructor(
        @param:Element(typesByElement = [
            ElementNameMatcher(type = JournalistConstructor::class, name = "journalist"),
            ElementNameMatcher(type = OrganisationConstructor::class, name = "organisation")
        ])
        val writer: WriterConstructor?
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PaperConstructor) return false

        val that = other as PaperConstructor?

        return if (writer != null) writer == that!!.writer else that!!.writer == null
    }

    override fun hashCode(): Int {
        return writer?.hashCode() ?: 0
    }
}
