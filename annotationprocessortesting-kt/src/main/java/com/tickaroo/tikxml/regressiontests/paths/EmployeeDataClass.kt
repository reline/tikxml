package com.tickaroo.tikxml.regressiontests.paths

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml
data class EmployeeDataClass(
        @field:PropertyElement
        var name: String? = null
) : Person()