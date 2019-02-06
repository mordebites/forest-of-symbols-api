package io.github.mordebites.forestofsymbolsapi.integration

import org.hamcrest.CoreMatchers.*
import org.junit.After
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
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@ActiveProfiles("test")
@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiIntegrationTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate
    val itemBody = mapOf("title" to "foo", "type" to "bar")
    val linkBody = mapOf("type" to "ref", "source" to "1", "dest" to "2")

    @After
    fun cleanup(){
        restTemplate.delete("/items")
        restTemplate.delete("/links")
    }

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
        assertThat(getResponse.body?.get(0)?.id, not(nullValue()))
        assertThat(getResponse.body?.get(0)?.title, equalTo("foo"))
        assertThat(getResponse.body?.get(0)?.type, equalTo("bar"))
    }

    @Test
    fun shouldValidateAnItem() {
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

    @Test
    fun shouldInsertALink() {
        val sourceItemResponse = restTemplate.postForEntity("/items", itemBody, ItemResponseBody::class.java)
        val destItemResponse = restTemplate.postForEntity("/items", itemBody, ItemResponseBody::class.java)
        val linkBody = mapOf("type" to "ref", "source" to sourceItemResponse.body?.id, "dest" to destItemResponse.body?.id)

        val postLinkResponse = restTemplate.postForEntity("/links", linkBody, LinkResponseBody::class.java)

        assertThat(postLinkResponse.statusCode, equalTo(CREATED))
        assertThat(postLinkResponse.body?.id, not(equalTo("-1")))
        assertThat(postLinkResponse.body?.type, equalTo("ref"))
        assertThat(postLinkResponse.body?.source, equalTo("1"))
        assertThat(postLinkResponse.body?.dest, equalTo("2"))

        val getResponse = restTemplate.exchange("/links", HttpMethod.GET, HttpEntity.EMPTY, object : ParameterizedTypeReference<List<LinkResponseBody>>(){})

        assertThat(getResponse.statusCode, equalTo(OK))
        assertThat(getResponse.body?.size, equalTo(1))
        assertThat(getResponse.body?.get(0)?.id, equalTo("1"))
        assertThat(getResponse.body?.get(0)?.type, equalTo("ref"))
        assertThat(getResponse.body?.get(0)?.source, equalTo("1"))
        assertThat(getResponse.body?.get(0)?.dest, equalTo("2"))
    }

    @Test
    fun shouldFailWithInvalidLink() {
        val postResponse = restTemplate.postForEntity("/links", linkBody, LinkResponseBody::class.java)

        assertThat(postResponse.statusCode, equalTo(NOT_FOUND))
    }
}