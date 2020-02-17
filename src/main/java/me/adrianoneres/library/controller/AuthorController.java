package me.adrianoneres.library.controller;

import me.adrianoneres.library.controller.dto.AuthorDto;
import me.adrianoneres.library.controller.dto.AuthorStoreDto;
import me.adrianoneres.library.controller.dto.AuthorUpdateDto;
import me.adrianoneres.library.model.Author;
import me.adrianoneres.library.service.AuthorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/authors")
public class AuthorController {

    private ModelMapper modelMapper;
    private AuthorService authorService;

    @Autowired
    public AuthorController(ModelMapper modelMapper, AuthorService authorService) {
        this.modelMapper = modelMapper;
        this.authorService = authorService;
    }

    @GetMapping
    public List<AuthorDto> index() {
        return authorService
                .findAll()
                .stream()
                .map(a -> modelMapper.map(a, AuthorDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<AuthorDto> store(
            @RequestBody @Valid AuthorStoreDto authorStoreDto,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        Author author = modelMapper.map(authorStoreDto, Author.class);
        AuthorDto authorDto = modelMapper.map(authorService.save(author), AuthorDto.class);

        URI uri = uriComponentsBuilder.path("/authors/{id}").buildAndExpand(author.getId()).toUri();

        return ResponseEntity.created(uri).body(authorDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> show(@PathVariable Long id) {
        AuthorDto authorDto = modelMapper.map(authorService.find(id), AuthorDto.class);

        return ResponseEntity.ok(authorDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> update(
            @PathVariable Long id,
            @RequestBody @Valid AuthorUpdateDto authorUpdateDto
    ) {
        Author author = authorService.update(id, modelMapper.map(authorUpdateDto, Author.class));
        AuthorDto authorDto = modelMapper.map(author, AuthorDto.class);

        return ResponseEntity.ok(authorDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable Long id) {
        authorService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
