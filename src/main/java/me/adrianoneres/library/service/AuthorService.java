package me.adrianoneres.library.service;

import me.adrianoneres.library.exception.DataNotFoundException;
import me.adrianoneres.library.model.Author;
import me.adrianoneres.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Page<Author> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    public Author find(Long id) {
        return authorRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }

    @Transactional
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    public Author update(Long id, Author updatedAuthor) {
        Author author = find(id);

        author.update(updatedAuthor);

        return author;
    }

    @Transactional
    public void delete(Long id) {
        Author author = find(id);
        authorRepository.delete(author);
    }
}
