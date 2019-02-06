package io.github.mordebites.forestofsymbolsapi.link

import io.github.mordebites.forestofsymbolsapi.item.ItemNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class LinkController {

    @Autowired
    lateinit var linkService: LinkService

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/links")
    fun createLink(@RequestBody link: Link): Link {
        return linkService.storeLink(link)
    }

    @GetMapping("/links")
    fun getLink(): List<Link> {
        return linkService.loadLinks()
    }

    @Profile("test")
    @DeleteMapping("/links")
    fun deleteAll() {
        linkService.deleteAll()
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ItemNotFoundException::class)
    fun handleItemNotFound() {

    }
}