package io.github.mordebites.forestofsymbolsapi

import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
class ItemController {

    @ResponseStatus(CREATED)
    @PostMapping("/items")
    fun createItem(@RequestBody item: Item): Item {
        return Item(
                id = Instant.now().toString(),
                title = item.title,
                type = item.type
        )
    }
}