package io.github.mordebites.forestofsymbolsapi.link

import io.github.mordebites.forestofsymbolsapi.item.ItemNotFoundException
import io.github.mordebites.forestofsymbolsapi.item.ItemService
import org.springframework.stereotype.Service

@Service
class LinkService(
  private val linkRepository: LinkRepository,
  private val itemService: ItemService
) {

  fun createLink(
    link: Link
  ): Link {
    validateLink(link)
    return linkRepository.save(link)
  }

  fun getAllLinks(): List<Link> {
    return linkRepository.findAll().toList()
  }

  fun deleteAllLinks() {
    linkRepository.deleteAll()
  }

  fun linkExistsById(id: Long): Boolean {
    return linkRepository.existsById(id)
  }

  private fun validateLink(link: Link) {
    if (!itemService.itemExistsById(link.source) || !itemService.itemExistsById(link.dest)) {
      throw ItemNotFoundException("One of the items was not found!")
    }
  }
}