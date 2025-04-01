package com.ninhtbm.vcl.useCase.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Task @OptIn(ExperimentalUuidApi::class) constructor(
    val id: String = Uuid.random().toString(),
    val name: String,
    val icon: Int?,
    val durationSeconds: Int
)