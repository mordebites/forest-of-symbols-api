package io.github.mordebites.forestofsymbolsapi

import org.springframework.data.repository.CrudRepository

interface ItemRepository : CrudRepository<Item, Long>