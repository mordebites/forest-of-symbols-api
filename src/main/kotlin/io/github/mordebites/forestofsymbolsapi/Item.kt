package io.github.mordebites.forestofsymbolsapi

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.validation.constraints.*

@Entity
data class Item constructor(

        @Id
        @GeneratedValue(strategy = IDENTITY)
        val id: Long = -1,

        @field:NotBlank
        val title: String,

        @field:NotBlank
        val type: String
)