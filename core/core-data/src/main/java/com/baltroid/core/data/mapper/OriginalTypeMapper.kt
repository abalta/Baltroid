package com.baltroid.core.data.mapper

import com.hitreads.core.domain.model.OriginalTypeModel

internal fun OriginalTypeModel.Original.asOriginalType() = OriginalType.Original[originalType]
