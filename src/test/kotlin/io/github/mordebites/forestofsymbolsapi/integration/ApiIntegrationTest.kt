package io.github.mordebites.forestofsymbolsapi.integration

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus.*
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiIntegrationTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate
    val itemBody = mapOf("title" to "foo", "type" to "bar")
    val linkBody = mapOf("type" to "ref", "item1" to "1", "item2" to "2")

    @Test
    fun shouldInsertAnItem() {
        val postResponse = restTemplate.postForEntity("/items", itemBody, ItemResponseBody::class.java)

        assertThat(postResponse.statusCode, equalTo(CREATED))
        assertThat(postResponse.body?.id, not(equalTo("-1")))
        assertThat(postResponse.body?.title, equalTo("foo"))
        assertThat(postResponse.body?.type, equalTo("bar"))

        val getResponse = restTemplate.exchange("/items", HttpMethod.GET, HttpEntity.EMPTY, object : ParameterizedTypeReference<List<ItemResponseBody>>(){})

        assertThat(getResponse.statusCode, equalTo(OK))
        assertThat(getResponse.body?.size, equalTo(1))
        assertThat(getResponse.body?.get(0)?.id, equalTo("1"))
        assertThat(getResponse.body?.get(0)?.title, equalTo("foo"))
        assertThat(getResponse.body?.get(0)?.type, equalTo("bar"))
    }

    @Test
    fun shouldInsertALink() {
        val postResponse = restTemplate.postForEntity("/links", linkBody, LinkResponseBody::class.java)

        assertThat(postResponse.statusCode, equalTo(CREATED))
        assertThat(postResponse.body?.id, not(equalTo("-1")))
        assertThat(postResponse.body?.type, equalTo("ref"))
        assertThat(postResponse.body?.item1, equalTo("1"))
        assertThat(postResponse.body?.item2, equalTo("2"))

        val getResponse = restTemplate.exchange("/links", HttpMethod.GET, HttpEntity.EMPTY, object : ParameterizedTypeReference<List<LinkResponseBody>>(){})

        assertThat(getResponse.statusCode, equalTo(OK))
        assertThat(getResponse.body?.size, equalTo(1))
        assertThat(getResponse.body?.get(0)?.id, equalTo("1"))
        assertThat(getResponse.body?.get(0)?.type, equalTo("ref"))
        assertThat(getResponse.body?.get(0)?.item1, equalTo("1"))
        assertThat(getResponse.body?.get(0)?.item2, equalTo("2"))
    }
}