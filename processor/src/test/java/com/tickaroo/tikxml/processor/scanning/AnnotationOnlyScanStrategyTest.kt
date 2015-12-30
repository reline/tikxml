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

package com.tickaroo.tikxml.processor.scanning

import com.google.common.truth.Truth
import com.google.testing.compile.JavaFileObjects
import com.google.testing.compile.JavaSourceSubjectFactory
import com.google.testing.compile.JavaSourcesSubject
import com.tickaroo.tikxml.annotation.*
import com.tickaroo.tikxml.processor.XmlProcessor
import org.junit.Test
import javax.tools.JavaFileObject

/**
 *
 * @author Hannes Dorfmann
 */
class AnnotationOnlyScanStrategyTest {


    @Test
    fun multipleAnnotationOnField1() {
        val componentFile = JavaFileObjects.forSourceLines("test.MultipleAnnotations1",
                "package test;",
                "",
                "import ${Xml::class.java.canonicalName};",
                "import ${Attribute::class.java.canonicalName};",
                "import ${Element::class.java.canonicalName};",
                "import ${PropertyElement::class.java.canonicalName};",
                "",
                "@${Xml::class.java.simpleName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class MultipleAnnotations1 {",
                "   @${Attribute::class.java.simpleName}",
                "   @${Element::class.java.simpleName}",
                "   String aField;",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("Fields can ONLY be annotated with one of the following")
    }

    @Test
    fun multipleAnnotationOnField2() {
        val componentFile = JavaFileObjects.forSourceLines("test.MultipleAnnotations2",
                "package test;",
                "",
                "import ${Xml::class.java.canonicalName};",
                "import ${Attribute::class.java.canonicalName};",
                "import ${Element::class.java.canonicalName};",
                "import ${PropertyElement::class.java.canonicalName};",
                "",
                "@${Xml::class.java.simpleName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class MultipleAnnotations2 {",
                "   @${Element::class.java.simpleName}",
                "   @${PropertyElement::class.java.simpleName}",
                "   String aField;",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("Fields can ONLY be annotated with one of the following")
    }

    @Test
    fun multipleAnnotationOnField3() {
        val componentFile = JavaFileObjects.forSourceLines("test.MultipleAnnotations3",
                "package test;",
                "",
                "import ${Xml::class.java.canonicalName};",
                "import ${Attribute::class.java.canonicalName};",
                "import ${Element::class.java.canonicalName};",
                "import ${PropertyElement::class.java.canonicalName};",
                "",
                "@${Xml::class.java.simpleName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class MultipleAnnotations3 {",
                "   @${PropertyElement::class.java.simpleName}",
                "   @${Attribute::class.java.simpleName}",
                "   String aField;",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("Fields can ONLY be annotated with one of the following")
    }

    @Test
    fun multipleAnnotationOnField4() {
        val componentFile = JavaFileObjects.forSourceLines("test.MultipleAnnotations4",
                "package test;",
                "",
                "import ${Xml::class.java.canonicalName};",
                "import ${Attribute::class.java.canonicalName};",
                "import ${Element::class.java.canonicalName};",
                "import ${PropertyElement::class.java.canonicalName};",
                "",
                "@${Xml::class.java.simpleName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class MultipleAnnotations4 {",
                "   @${Attribute::class.java.simpleName}",
                "   @${PropertyElement::class.java.simpleName}",
                "   @${Element::class.java.simpleName}",
                "   String aField;",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("Fields can ONLY be annotated with one of the following")
    }

    @Test
    fun inlineListOnNotListType() {
        val componentFile = JavaFileObjects.forSourceLines("test.InlineListOnNotListType",
                "package test;",
                "",
                "import ${Xml::class.java.canonicalName};",
                "import ${InlineList::class.java.canonicalName};",
                "import ${Element::class.java.canonicalName};",
                "",
                "@${Xml::class.java.simpleName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class InlineListOnNotListType {",
                "   @${InlineList::class.java.simpleName}",
                "   @${Element::class.java.simpleName}",
                "   String aField;",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("The annotation @InlineList is only allowed on java.util.List types, but the field 'aField' in class test.InlineListOnNotListType is of type java.lang.String")
    }

    @Test
    fun inlineListOnNotListTypeWithPolymorphism() {
        val componentFile = JavaFileObjects.forSourceLines("test.InlineListOnNotListTypeWithPolymorphism",
                "package test;",
                "",
                "import ${Xml::class.java.canonicalName};",
                "import ${InlineList::class.java.canonicalName};",
                "import ${Element::class.java.canonicalName};",
                "",
                "@${Xml::class.java.simpleName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class InlineListOnNotListTypeWithPolymorphism {",
                "   @${InlineList::class.java.simpleName}",
                "   @${Element::class.java.simpleName}(",
                "       typesByElement = @${ElementNameMatcher::class.qualifiedName}(elementName=\"foo\" , type=java.lang.Object)",
                "    )",
                "   String aField;",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .failsToCompile()
                .withErrorContaining("The annotation @InlineList is only allowed on java.util.List types, but the field 'aField' in class test.InlineListOnNotListTypeWithPolymorphism is of type java.lang.String")
    }

    @Test
    fun inlineListOnListType() {
        val componentFile = JavaFileObjects.forSourceLines("test.InlineListOnListType",
                "package test;",
                "",
                "import ${Xml::class.java.canonicalName};",
                "import ${InlineList::class.java.canonicalName};",
                "import ${Element::class.java.canonicalName};",
                "",
                "@${Xml::class.java.simpleName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class InlineListOnListType {",
                "   @${InlineList::class.java.simpleName}",
                "   @${Element::class.java.simpleName}",
                "   java.util.List<String> aList;",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .compilesWithoutError()
    }

    @Test
    fun inlineListOnArrayListType() {
        val componentFile = JavaFileObjects.forSourceLines("test.InlineListOnArrayListType",
                "package test;",
                "",
                "@${Xml::class.java.canonicalName}(scanMode = ${ScanMode::class.qualifiedName}.${ScanMode.ANNOTATIONS_ONLY})",
                "class InlineListOnArrayListType {",
                "   @${InlineList::class.java.canonicalName}",
                "   @${Element::class.java.canonicalName}",
                "   java.util.ArrayList<String> aList;",
                "}")

        Truth.assertAbout<JavaSourcesSubject.SingleSourceAdapter, JavaFileObject>(JavaSourceSubjectFactory.javaSource())
                .that(componentFile).processedWith(XmlProcessor())
                .compilesWithoutError()
    }
}