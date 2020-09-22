package io.github.mordebites.forestofsymbolsapi.integration

import io.github.mordebites.forestofsymbolsapi.ForestOfSymbolsApiApplication
import org.junit.After
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@ActiveProfiles("test")
@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ContextConfiguration(classes = [ForestOfSymbolsApiApplication::class])
abstract class BaseIT {

  @Autowired
  lateinit var restTemplate: TestRestTemplate

  @After
  fun cleanup() {
    restTemplate.delete("/items")
    restTemplate.delete("/links")
  }

}