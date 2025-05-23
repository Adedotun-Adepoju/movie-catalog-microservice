package com.example.Movie_info_service.controller;

import com.example.Movie_info_service.models.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class movieInfoController {

  @GetMapping("/{movieId}")
  public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
    return new Movie(movieId, "Test name");
  }
}
