package io.github.mordebites.forestofsymbolsapi.item

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class ItemController {

    @Autowired
    lateinit var itemService: ItemService

    @ResponseStatus(CREATED)
    @PostMapping("/items")
    fun createItem(@RequestBody @Valid item: Item): Item {
        return itemService.storeItem(item)

    }

    @GetMapping("/items")
    fun getItem(): List<Item> {
        return itemService.loadItems()
    }

    @Profile("test")
    @DeleteMapping("/items")
    fun deleteAll() {
        itemService.deleteAll()
    }
}