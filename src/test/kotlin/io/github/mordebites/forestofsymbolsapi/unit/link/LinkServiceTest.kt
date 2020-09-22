package io.github.mordebites.forestofsymbolsapi.unit.link

import io.github.mordebites.forestofsymbolsapi.item.Item
import io.github.mordebites.forestofsymbolsapi.item.ItemService
import io.github.mordebites.forestofsymbolsapi.link.Link
import io.github.mordebites.forestofsymbolsapi.link.LinkRepository
import io.github.mordebites.forestofsymbolsapi.link.LinkService
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@ActiveProfiles("test")
@RunWith(SpringRunner::class)
@SpringBootTest
class LinkServiceTest {
  @Autowired
  lateinit var itemService: ItemService

  @Autowired
  lateinit var linkRepository: LinkRepository

  @Autowired
  lateinit var linkService: LinkService

  private val item1 = Item("American Psycho", "Book")
  private val item2 = Item("Misery", "Book")

  @Before
  fun setup() {
    itemService.createItem(item1)
    itemService.createItem(item2)
  }

  @After
  fun teardown() {
    linkRepository.deleteAll()
    itemService.deleteAllItems()
  }

  @Test
  fun `should create a link`() {
    val link = Link(item1.id, item2.id, "type")

    linkService.createLink(link)
    val lastLink = linkRepository.findAll().last()!!
    assertEquals(link.source, lastLink.source)
    assertEquals(link.dest, lastLink.dest)
    assertEquals(link.type, lastLink.type)
  }

  @Test
  fun `should find all links`() {
    val links = listOf(
      Link(item1.id, item2.id, "type1"),
      Link(item2.id, item1.id, "type2"),
      Link(item1.id, item2.id, "type3")
    )

    for (link in links) {
      linkService.createLink(link)
    }

    val allLinks = linkService.getAllLinks()
    assertEquals(links, allLinks)
  }

  @Test
  fun `should check link exists by id`() {
    val link = Link(item1.id, item2.id, "type")
    val newLink = linkRepository.save(link)

    assertTrue(linkService.linkExistsById(newLink.id))
  }

  @Test
  fun `should delete all links`() {
    val links = listOf(
      Link(item1.id, item2.id, "type1"),
      Link(item2.id, item1.id, "type2"),
      Link(item1.id, item2.id, "type3")
    )

    for (link in links) {
      linkService.createLink(link)
    }

    assertEquals(links.size, linkRepository.findAll().count())

    linkService.deleteAllLinks()

    assertTrue(linkRepository.findAll().none())
  }
}