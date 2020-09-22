package io.github.mordebites.forestofsymbolsapi.unit.item

import io.github.mordebites.forestofsymbolsapi.item.Item
import io.github.mordebites.forestofsymbolsapi.item.ItemRepository
import io.github.mordebites.forestofsymbolsapi.item.ItemService
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
class ItemServiceTest {
  @Autowired
  lateinit var itemRepository: ItemRepository

  @Autowired
  lateinit var itemService: ItemService

  @Before
  fun setup() {
    itemRepository.deleteAll()
  }

  @Test
  fun `should create an item`() {
    val item = Item("American Psycho", "Book")

    itemService.createItem(item)
    val lastItem = itemRepository.findAll().last()
    assertEquals(item.title, lastItem.title)
    assertEquals(item.type, lastItem.type)
  }

  @Test
  fun `should find all items`() {
    val items = listOf(
      Item("Il nome della rosa", "Book"),
      Item("Om Shanti Om", "Movie"),
      Item("Vento d'estate", "Song")
    )

    for (item in items) {
      itemRepository.save(item)
    }

    val allItems = itemService.getAllItems()
    assertEquals(items, allItems)
  }

  @Test
  fun `should check item exists by id`() {
    val item = Item("American Psycho", "Book")
    itemRepository.save(item)

    assertTrue(itemService.itemExistsById(item.id))
  }

  @Test
  fun `should delete all items`() {
    val items = listOf(
      Item("Il nome della rosa", "Book"),
      Item("Om Shanti Om", "Movie"),
      Item("Vento d'estate", "Song")
    )
    for (item in items) {
      itemRepository.save(item)
    }
    assertEquals(items.size, itemRepository.findAll().count())

    itemService.deleteAllItems()

    assertTrue(itemRepository.findAll().none())
  }
}