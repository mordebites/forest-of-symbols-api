package io.github.mordebites.forestofsymbolsapi.integration

import io.github.mordebites.forestofsymbolsapi.item.Item
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK

class ItemIT : BaseIT() {
  private final val item = Item("foo", "bar")

  @Test
  fun `should create an item`() {
    val postResponse = restTemplate.postForEntity("/items", item, Item::class.java)

    assertThat(postResponse.statusCode, equalTo(CREATED))
    assertThat(postResponse.body, equalTo(item))

    val getResponse = restTemplate.exchange(
      "/items",
      HttpMethod.GET,
      HttpEntity.EMPTY,
      object : ParameterizedTypeReference<List<Item>>() {}
    )

    assertThat(getResponse.statusCode, equalTo(OK))
    assertThat(getResponse.body?.size, equalTo(1))
    assertThat(getResponse.body?.get(0)?.id, equalTo(postResponse.body?.id))
    assertThat(getResponse.body?.get(0)?.title, equalTo(postResponse.body?.title))
    assertThat(getResponse.body?.get(0)?.type, equalTo(postResponse.body?.type))
  }

  @Test
  fun `should validate an item`() {
    val itemBodyNoType = mapOf("title" to "foo")
    val postResponseNoType = restTemplate.postForEntity("/items", itemBodyNoType, Void::class.java)

    assertThat(postResponseNoType.statusCode, equalTo(BAD_REQUEST))

    val itemBodyNoTitle = mapOf("type" to "foo")
    val postResponseNoTitle = restTemplate.postForEntity("/items", itemBodyNoTitle, Void::class.java)

    assertThat(postResponseNoTitle.statusCode, equalTo(BAD_REQUEST))

    val itemBodyEmptyTitle = mapOf("title" to "", "type" to "bar")
    val postResponseEmptyTitle = restTemplate.postForEntity("/items", itemBodyEmptyTitle, Void::class.java)

    assertThat(postResponseEmptyTitle.statusCode, equalTo(BAD_REQUEST))

    val itemBodyEmptyType = mapOf("title" to "Hello", "type" to "")
    val postResponseEmptyType = restTemplate.postForEntity("/items", itemBodyEmptyType, Void::class.java)

    assertThat(postResponseEmptyType.statusCode, equalTo(BAD_REQUEST))
  }
}