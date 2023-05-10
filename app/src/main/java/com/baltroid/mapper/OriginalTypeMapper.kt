package com.baltroid.mapper

import com.hitreads.core.domain.model.OriginalTypeModel

fun OriginalType.Original.asOriginalTypeModel() = OriginalTypeModel.Original[originalType]


