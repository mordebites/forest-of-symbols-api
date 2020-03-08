package io.github.mordebites.forestofsymbolsapi.link

import io.github.mordebites.forestofsymbolsapi.item.ItemNotFoundException
import io.github.mordebites.forestofsymbolsapi.item.ItemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class LinkService {

    @Autowired
    lateinit var linkRepository: LinkRepository

    @Autowired
    lateinit var itemService: ItemService

    fun storeLink(@RequestBody link: Link): Link {
        validateLink(link)
        return linkRepository.save(link)
    }

    fun loadLinks(): List<Link> {
        return linkRepository.findAll().toList()
    }

    fun deleteAll() {
        linkRepository.deleteAll()
    }

    private fun validateLink(link: Link) {
        if (!itemService.checkItemPresenceById(link.source) || !itemService.checkItemPresenceById(link.dest)) {
            throw ItemNotFoundException("One of the items was not found")
        }
    }

}