package com.booleanuk.library.controller;

import com.booleanuk.library.model.Book;
import com.booleanuk.library.repository.BookRepository;
import com.booleanuk.library.response.BookListResponse;
import com.booleanuk.library.response.BookResponse;
import com.booleanuk.library.response.ErrorResponse;
import com.booleanuk.library.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    private HashMap<String, String> errorMessage;

    public BookController(BookRepository bookRepository){
        this.bookRepository = bookRepository;
        this.errorMessage= new HashMap<>();
        errorMessage.put("message", "Failed");
    }

    @GetMapping
    public ResponseEntity<BookListResponse> getAllBooks() {
        BookListResponse bookListResponse = new BookListResponse();
        bookListResponse.set(this.bookRepository.findAll());
        return ResponseEntity.ok(bookListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getBookById(@PathVariable int id) {
        Book book = this.bookRepository.findById(id).orElse(null);
        if (book == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        BookResponse bookResponse = new BookResponse();
        bookResponse.set(book);
        return ResponseEntity.ok(bookResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> putById(@PathVariable int id, @RequestBody Book book){
        Book id_book = bookRepository.findById(id).orElse(null);
        if (id_book == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        id_book.setInfo(book);

        BookResponse bookResponse = new BookResponse();
        bookResponse.set(id_book);
        return ResponseEntity.ok(bookResponse);

    }
}
