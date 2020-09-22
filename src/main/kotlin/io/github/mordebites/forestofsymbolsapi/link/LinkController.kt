package io.github.mordebites.forestofsymbolsapi.link

import io.github.mordebites.forestofsymbolsapi.item.ItemNotFoundException
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/links")
class LinkController(private val linkService: LinkService) {

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  fun createLink(
    @RequestBody link: Link
  ): Link {
    return linkService.createLink(link)
  }

  @GetMapping
  fun getAllLinks(): List<Link> {
    return linkService.getAllLinks()
  }

  @Profile("test")
  @DeleteMapping
  fun deleteAllLinks() {
    linkService.deleteAllLinks()
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(ItemNotFoundException::class)
  fun handleItemNotFound() {

  }
}