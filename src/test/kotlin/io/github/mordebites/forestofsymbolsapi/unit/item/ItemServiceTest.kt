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
@SpringBootTest()
class ItemServiceTest {
    @Autowired
    lateinit var itemRepository: ItemRepository

    @Autowired
    lateinit var itemService: ItemService


    @Test
    fun shouldStoreAnItem() {
        val desiredTitle = "American Psycho"
        val desiredType = "Book"
        val item = Item(-1, desiredTitle, desiredType)

        itemService.storeItem(item)
        val lastItem = itemRepository.findAll().last()
        assertEquals(desiredTitle, lastItem.title)
        assertEquals(desiredType, lastItem.type)
    }

    @Test
    fun shouldLoadAllItems() {
        val title = "American Psycho"
        val type = "Book"
        val item = Item(-1, title, type)

        val maxItems = 3
        for (i in 1..maxItems) {
            itemRepository.save(item)
        }

        val allItems = itemService.loadItems()
        assertEquals(maxItems, allItems.size)
        for (item in allItems) {
            assertEquals(title, item.title)
            assertEquals(type, item.type)
        }
    }

    @Test
    fun shouldCheckItemPresenceById() {
        val desiredTitle = "American Psycho"
        val desiredType = "Book"
        val item = Item(-1, desiredTitle, desiredType)
        val savedItem = itemRepository.save(item)

        assertTrue(itemService.checkItemPresenceById(savedItem.id))
    }

    @Test
    fun shouldDeleteAllItems() {
        val item = Item(-1, "American Psycho", "Book")
        val maxItems = 3
        for (i in 1..maxItems) {
            itemRepository.save(item)
        }
        assertEquals(maxItems, itemRepository.findAll().count())

        itemService.deleteAll()

        assertTrue(itemRepository.findAll().none())
    }
}