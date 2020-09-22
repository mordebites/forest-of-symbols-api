package io.github.mordebites.forestofsymbolsapi.item

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
data class Item constructor(
  @field:NotBlank
  val title: String,

  @field:NotBlank
  val type: String
) {
  @Id
  @GeneratedValue
  var id: Long = 0
}

