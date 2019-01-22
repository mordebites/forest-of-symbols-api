package io.github.mordebites.forestofsymbolsapi.integration

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.client.RestTemplate

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiIntegrationTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun shouldInsertAnItem() {
        val body = mapOf("title" to "foo", "type" to "bar")
        val response = restTemplate.postForEntity("/items", body, ResponseBody::class.java)

        assertThat(response.statusCode, equalTo(CREATED))
        assertThat(response.body?.id, not(equalTo("1")))
        assertThat(response.body?.title, equalTo("foo"))
        assertThat(response.body?.type, equalTo("bar"))
    }

}