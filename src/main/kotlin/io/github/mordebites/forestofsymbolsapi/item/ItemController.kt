package io.github.mordebites.forestofsymbolsapi.item

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/items")
class ItemController {

  @Autowired
  lateinit var itemService: ItemService

  @ResponseStatus(CREATED)
  @PostMapping
  fun createItem(
    @RequestBody @Valid item: Item
  ): Item {
    return itemService.createItem(item)
  }

  @GetMapping
  fun getAllItems(): List<Item> {
    return itemService.getAllItems()
  }

  @Profile("test")
  @DeleteMapping
  fun deleteAllItems() {
    itemService.deleteAllItems()
  }
}