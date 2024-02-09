package br.com.library.controller

import br.com.library.model.Author
import br.com.library.services.AuthorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/author/v1")
class AuthorController {

    @Autowired
    private lateinit var service: AuthorService;

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    private fun findALl(@RequestParam(value = "page", defaultValue = "0") page: Int,
                        @RequestParam(value = "size", defaultValue = "12") size: Int,
                        @RequestParam(value = "direction", defaultValue = "asc") direction: String
    ): ResponseEntity<PagedModel<EntityModel<Author>>> {
        val sortDirection: Sort.Direction =
            if("desc".equals(direction, ignoreCase = true)) Sort.Direction.DESC
            else Sort.Direction.ASC
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(sortDirection,"name"))
        return ResponseEntity.ok(service.findAll(pageable))
    }

    @GetMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    private fun findById(@PathVariable(value = "id") id: Long): Author? {
        return service.findById(id)
    }

    @GetMapping(value = ["/name/{name}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    private fun findByName(
        @PathVariable(value = "name") name: String,
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "12") size: Int,
        @RequestParam(value = "direction", defaultValue = "asc") direction: String):ResponseEntity<PagedModel<EntityModel<Author>>> {
        val sortDirection: Sort.Direction =
            if("desc".equals(direction, ignoreCase = true)) Sort.Direction.DESC
            else Sort.Direction.ASC
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(sortDirection,"name"))
        return ResponseEntity.ok(service.findByName(name, pageable))
    }
}