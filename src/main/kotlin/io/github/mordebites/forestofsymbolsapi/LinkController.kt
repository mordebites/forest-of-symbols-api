package io.github.mordebites.forestofsymbolsapi

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class LinkController {

    @Autowired
    lateinit var linkRepository: LinkRepository

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/links")
    fun createLink(@RequestBody link: Link): Link {
        return linkRepository.save(link)
    }

    @GetMapping("/links")
    fun getLink(): List<Link> {
        return linkRepository.findAll().toList()
    }
}