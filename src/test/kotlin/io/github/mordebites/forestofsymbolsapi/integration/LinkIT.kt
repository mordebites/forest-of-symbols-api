package io.github.mordebites.forestofsymbolsapi.integration

import io.github.mordebites.forestofsymbolsapi.item.Item
import io.github.mordebites.forestofsymbolsapi.link.Link
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertThat
import org.junit.Test
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK

class LinkIT : BaseIT() {
  private final val item1 = Item("foo", "bar")
  private final val item2 = Item("baz", "babaz")

  @Test
  fun `should create a link`() {
    val sourceItemResponse = restTemplate.postForEntity("/items", item1, Item::class.java)
    val destItemResponse = restTemplate.postForEntity("/items", item2, Item::class.java)
    val link = Link(sourceItemResponse.body?.id!!, destItemResponse.body?.id!!, "someType")

    val postLinkResponse = restTemplate.postForEntity("/links", link, Link::class.java)

    assertThat(postLinkResponse.statusCode, equalTo(CREATED))
    assertThat(postLinkResponse.body?.id, not(equalTo(0L)))
    assertThat(postLinkResponse.body?.type, equalTo(link.type))
    assertThat(postLinkResponse.body?.source, equalTo(link.source))
    assertThat(postLinkResponse.body?.dest, equalTo(link.dest))

    val getResponse = restTemplate.exchange(
      "/links",
      HttpMethod.GET,
      HttpEntity.EMPTY,
      object : ParameterizedTypeReference<List<Link>>() {})

    assertThat(getResponse.statusCode, equalTo(OK))
    assertThat(getResponse.body?.size, equalTo(1))
    assertThat(getResponse.body?.get(0)?.id, equalTo(postLinkResponse.body?.id))
    assertThat(getResponse.body?.get(0)?.type, equalTo(postLinkResponse.body?.type))
    assertThat(getResponse.body?.get(0)?.source, equalTo(postLinkResponse.body?.source))
    assertThat(getResponse.body?.get(0)?.dest, equalTo(postLinkResponse.body?.dest))
  }

  @Test
  fun `should validate a link`() {
    val link = Link(item1.id, 0L, "something")
    val postResponse = restTemplate.postForEntity("/links", link, Link::class.java)

    assertThat(postResponse.statusCode, equalTo(HttpStatus.NOT_FOUND))
  }
}