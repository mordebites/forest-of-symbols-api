package io.github.mordebites.forestofsymbolsapi.item

import org.springframework.stereotype.Service

@Service
class ItemService(private val itemRepository: ItemRepository) {

  fun createItem(item: Item): Item {
    return itemRepository.save(item)
  }

  fun getAllItems(): List<Item> {
    return itemRepository.findAll().toList()
  }

  fun itemExistsById(id: Long): Boolean {
    return itemRepository.existsById(id)
  }

  fun deleteAllItems() {
    itemRepository.deleteAll()
  }

}