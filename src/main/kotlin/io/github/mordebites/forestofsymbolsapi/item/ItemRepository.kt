package io.github.mordebites.forestofsymbolsapi.item

import org.springframework.data.repository.CrudRepository

interface ItemRepository : CrudRepository<Item, Long>