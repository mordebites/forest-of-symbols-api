package io.github.mordebites.forestofsymbolsapi.item

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ItemService {

    @Autowired
    lateinit var itemRepository: ItemRepository

    fun storeItem(item: Item): Item {
        return itemRepository.save(item)
    }

    fun loadItems(): List<Item> {
        return itemRepository.findAll().toList()
    }

    fun loadById(id:Long): Boolean {
        return itemRepository.existsById(id)
    }

    fun deleteAll() {
        itemRepository.deleteAll()
    }

}