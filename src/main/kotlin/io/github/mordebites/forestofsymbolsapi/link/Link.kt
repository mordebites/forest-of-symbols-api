package io.github.mordebites.forestofsymbolsapi.link

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Link constructor(
  val source: Long,
  val dest: Long,
  val type: String
) {
  @Id
  @GeneratedValue
  var id: Long = 0
}