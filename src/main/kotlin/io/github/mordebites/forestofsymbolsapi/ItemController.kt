package io.github.mordebites.forestofsymbolsapi

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class ItemController {

    @Autowired
    lateinit var itemRepository: ItemRepository

    @ResponseStatus(CREATED)
    @PostMapping("/items")
    fun createItem(@RequestBody @Valid item: Item): Item {
        return itemRepository.save(item)

    }

    @GetMapping("/items")
    fun getItem(): List<Item> {
        return itemRepository.findAll().toList()
    }
}